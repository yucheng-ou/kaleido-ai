package com.xiaoo.kaleido.recommend.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import com.xiaoo.kaleido.api.ai.event.OutfitRecommendCompletedMessage;
import com.xiaoo.kaleido.api.wardrobe.IRpcOutfitService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.domain.recommend.service.IRecommendRecordDomainService;
import com.xiaoo.kaleido.recommend.types.enums.RecommendRecordStatusEnum;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 穿搭推荐完成事件监听器
 * 监听穿搭推荐执行完成事件，创建穿搭并更新推荐记录状态
 *
 * @author ouyucheng
 * @date 2026/2/4
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OutfitRecommendCompletedEventListener {

    @Value("${spring.rabbitmq.topic.outfit-recommend-completed}")
    private String topic;

    private final IRecommendRecordRepository recommendRecordRepository;
    private final IRecommendRecordDomainService recommendRecordDomainService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcOutfitService rpcOutfitService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.outfit-recommend-completed}"))
    public void listener(String message) {
        try {
            log.info("监听穿搭推荐完成事件，开始处理推荐记录更新 topic: {} message: {}", topic, message);

            // 解析消息
            OutfitRecommendCompletedMessage eventMessage = parseEventMessage(message);

            if (eventMessage == null) {
                log.error("穿搭推荐完成事件消息格式错误: {}", message);
                return;
            }

            String executionId = eventMessage.getExecutionId();
            String userId = eventMessage.getUserId();
            WorkflowExecutionStatusEnum status = eventMessage.getStatus();

            // 根据执行记录ID查找推荐记录
            RecommendRecordAggregate recommendRecord = recommendRecordRepository.findByExecutionId(executionId);
            if (recommendRecord == null) {
                log.error("未找到对应的推荐记录，执行记录ID: {}", executionId);
                return;
            }

            // 检查推荐记录是否已经是终态，避免重复处理
            if (recommendRecord.isFinalStatus()) {
                log.warn("推荐记录已是终态，跳过处理，推荐记录ID: {}, 状态: {}", 
                        recommendRecord.getId(), recommendRecord.getStatus());
                return;
            }

            // 根据事件状态处理
            if (status == WorkflowExecutionStatusEnum.SUCCESS) {
                handleSuccessEvent(eventMessage, recommendRecord, userId);
            } else if (status == WorkflowExecutionStatusEnum.FAILED) {
                handleFailedEvent(eventMessage, recommendRecord);
            } else {
                log.warn("未知的穿搭推荐执行状态: {}", status);
            }

            log.info("穿搭推荐完成事件处理完成，执行记录ID: {}, 用户ID: {}, 状态: {}", 
                    executionId, userId, status);
        } catch (Exception e) {
            log.error("监听穿搭推荐完成事件，消费失败 topic: {} message: {}", topic, message, e);
            throw e; // 抛出异常让RabbitMQ进行重试
        }
    }

    /**
     * 处理成功事件
     */
    private void handleSuccessEvent(
            OutfitRecommendCompletedMessage eventMessage,
            RecommendRecordAggregate recommendRecord,
            String userId
    ) {
        try {
            String executionId = eventMessage.getExecutionId();
            String result = eventMessage.getResult();

            // 解析服装ID列表
            List<String> clothingIds = parseClothingIds(result);
            if (clothingIds.isEmpty()) {
                log.error("穿搭推荐执行结果中没有服装ID，执行记录ID: {}, 结果: {}", executionId, result);
                updateRecommendRecordToFailed(recommendRecord, "穿搭推荐执行结果中没有服装ID");
                return;
            }

            // 创建穿搭
            String outfitId = createOutfit(userId, clothingIds);
            if (outfitId == null) {
                log.error("创建穿搭失败，执行记录ID: {}, 用户ID: {}", executionId, userId);
                updateRecommendRecordToFailed(recommendRecord, "创建穿搭失败");
                return;
            }

            // 更新推荐记录状态为已完成，关联穿搭ID
            RecommendRecordAggregate recommendRecordAggregate = recommendRecordDomainService.completeRecommendRecord(
                    recommendRecord.getId(),
                    outfitId
            );
            recommendRecordRepository.update(recommendRecordAggregate);

            log.info("推荐记录处理成功，推荐记录ID: {}, 执行记录ID: {}, 穿搭ID: {}", 
                    recommendRecord.getId(), executionId, outfitId);
        } catch (Exception e) {
            log.error("处理穿搭推荐执行成功事件失败，执行记录ID: {}, 用户ID: {}", 
                    eventMessage.getExecutionId(), userId, e);
            updateRecommendRecordToFailed(recommendRecord, e.getMessage());
        }
    }

    /**
     * 处理失败事件
     */
    private void handleFailedEvent(
            OutfitRecommendCompletedMessage eventMessage,
            RecommendRecordAggregate recommendRecord
    ) {
        String errorMessage = eventMessage.getErrorMessage();
        updateRecommendRecordToFailed(recommendRecord, errorMessage);
    }

    /**
     * 解析服装ID列表
     */
    private List<String> parseClothingIds(String result) {
        List<String> clothingIds = new ArrayList<>();
        try {
            if (result == null || result.trim().isEmpty()) {
                return clothingIds;
            }

            // 尝试解析JSON数组
            JSONArray jsonArray = JSON.parseArray(result);
            for (int i = 0; i < jsonArray.size(); i++) {
                String clothingId = jsonArray.getString(i);
                if (clothingId != null && !clothingId.trim().isEmpty()) {
                    clothingIds.add(clothingId.trim());
                }
            }
        } catch (Exception e) {
            log.error("解析服装ID列表失败，结果: {}", result, e);
        }
        return clothingIds;
    }

    /**
     * 创建穿搭
     */
    private String createOutfit(String userId, List<String> clothingIds) {
        try {
            // 生成穿搭名称和描述
            String outfitName = generateOutfitName();
            String outfitDescription = generateOutfitDescription();

            // 构建图片信息（使用默认图片）
            List<OutfitImageInfoCommand> images = createDefaultImages();

            // 构建创建穿搭命令
            CreateOutfitWithClothingsCommand command = CreateOutfitWithClothingsCommand.builder()
                    .name(outfitName)
                    .description(outfitDescription)
                    .clothingIds(clothingIds)
                    .images(images)
                    .build();

            // 调用RPC服务创建穿搭
            Result<String> result = rpcOutfitService.createOutfitWithClothingsAndImages(userId, command);
            if (result != null && Boolean.TRUE.equals(result.getSuccess())) {
                return result.getData();
            } else {
                log.error("创建穿搭RPC调用失败，用户ID: {}, 错误: {}", 
                        userId, result != null ? result.getMsg() : "未知错误");
                return null;
            }
        } catch (Exception e) {
            log.error("创建穿搭异常，用户ID: {}, 服装数量: {}", userId, clothingIds.size(), e);
            return null;
        }
    }

    /**
     * 生成穿搭名称
     */
    private String generateOutfitName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String timestamp = LocalDateTime.now().format(formatter);
        return "AI推荐穿搭 " + timestamp;
    }

    /**
     * 生成穿搭描述
     */
    private String generateOutfitDescription() {
        return "根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。";
    }

    /**
     * 创建默认图片信息
     */
    private List<OutfitImageInfoCommand> createDefaultImages() {
        List<OutfitImageInfoCommand> images = new ArrayList<>();
        
        // 添加一个默认图片
        OutfitImageInfoCommand image = OutfitImageInfoCommand.builder()
                .path("default/outfit/default-outfit-image.jpg") // 默认图片路径
                .imageOrder(1)
                .isPrimary(true)
                .build();
        
        images.add(image);
        return images;
    }

    /**
     * 更新推荐记录状态为失败
     */
    private void updateRecommendRecordToFailed(RecommendRecordAggregate recommendRecord, String errorMessage) {
        try {
            RecommendRecordAggregate recommendRecordAggregate = recommendRecordDomainService.failRecommendRecord(recommendRecord.getId(), errorMessage);
            recommendRecordRepository.update(recommendRecordAggregate);
            log.info("推荐记录更新为失败状态，推荐记录ID: {}, 错误信息: {}", 
                    recommendRecord.getId(), errorMessage);
        } catch (Exception e) {
            log.error("更新推荐记录状态为失败异常，推荐记录ID: {}", recommendRecord.getId(), e);
        }
    }

    /**
     * 解析事件消息
     */
    private OutfitRecommendCompletedMessage parseEventMessage(String message) {
        try {
            // 使用通用的JSON解析
            JSONObject jsonObject = JSON.parseObject(message);
            
            // 解析事件数据
            JSONObject dataJson = jsonObject.getJSONObject("data");
            if (dataJson == null) {
                throw RecommendException.of(RecommendErrorCode.PARAM_FORMAT_ERROR, "事件消息缺少data字段");
            }
            
            // 使用fastjson自动反序列化整个data对象为OutfitRecommendCompletedMessage
            return JSON.parseObject(dataJson.toJSONString(), OutfitRecommendCompletedMessage.class);
        } catch (Exception e) {
            log.error("解析穿搭推荐完成事件消息失败: {}", message, e);
            throw RecommendException.of(RecommendErrorCode.PARAM_FORMAT_ERROR, "穿搭推荐完成事件消息格式错误");
        }
    }
}
