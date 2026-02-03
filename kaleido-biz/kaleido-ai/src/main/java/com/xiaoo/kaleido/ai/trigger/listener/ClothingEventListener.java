package com.xiaoo.kaleido.ai.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoo.kaleido.ai.domain.clothing.service.impl.ClothingVectorService;
import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服装事件监听器
 * 监听服装创建、更新、删除事件，更新服装向量库
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ClothingEventListener {

    @Value("${spring.rabbitmq.topic.clothing-event}")
    private String topic;

    private final ClothingVectorService clothingVectorService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.clothing-event}"))
    public void listener(String message) {
        try {
            log.info("监听服装事件，开始处理向量库更新 topic: {} message: {}", topic, message);
            
            // 解析消息
            ClothingEventMessage clothingEventMessage = parseEventMessage(message);
            
            if (clothingEventMessage == null) {
                log.error("服装事件消息格式错误: {}", message);
                return;
            }
            
            // 根据事件类型调用不同的处理逻辑
            ClothingEventTypeEnums eventType = clothingEventMessage.getEventType();
            if (eventType == null) {
                log.warn("服装事件类型为空: {}", message);
                return;
            }
            
            switch (eventType) {
                case CREATE:
                    clothingVectorService.handleCreateEvent(clothingEventMessage);
                    break;
                case UPDATE:
                    clothingVectorService.handleUpdateEvent(clothingEventMessage);
                    break;
                case DELETE:
                    clothingVectorService.handleDeleteEvent(clothingEventMessage);
                    break;
                default:
                    log.warn("未知的服装事件类型: {}", eventType);
                    break;
            }
            
            log.info("服装事件处理完成，事件类型: {}, 服装ID: {}, 用户ID: {}", 
                    eventType, clothingEventMessage.getClothingId(), clothingEventMessage.getUserId());
        } catch (Exception e) {
            log.error("监听服装事件，消费失败 topic: {} message: {}", topic, message, e);
            throw e; // 抛出异常让RabbitMQ进行重试
        }
    }
    
    /**
     * 解析事件消息
     */
    private ClothingEventMessage parseEventMessage(String message) {
        try {
            // 使用通用的JSON解析
            JSONObject jsonObject = JSON.parseObject(message);
            
            // 解析事件数据
            JSONObject dataJson = jsonObject.getJSONObject("data");
            if (dataJson == null) {
                throw new IllegalArgumentException("事件消息缺少data字段");
            }
            
            // 使用fastjson自动反序列化整个data对象为ClothingEventMessage
            // fastjson可以自动将字符串转换为枚举
            return JSON.parseObject(dataJson.toJSONString(), ClothingEventMessage.class);
        } catch (Exception e) {
            log.error("解析服装事件消息失败: {}", message, e);
            throw new IllegalArgumentException("服装事件消息格式错误", e);
        }
    }
}
