package com.xiaoo.kaleido.message.domain.mq.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.message.domain.mq.adapter.repository.IMqMessageRepository;
import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import com.xiaoo.kaleido.message.domain.mq.model.vo.MessageState;
import com.xiaoo.kaleido.message.domain.mq.service.IMqMessageDomainService;
import com.xiaoo.kaleido.message.types.exception.MessageException;
import com.xiaoo.kaleido.message.types.exception.MessageErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * MQ消息领域服务实现
 * <p>
 * 实现MQ消息领域服务的核心业务逻辑，处理消息的创建、状态更新、查询等操作
 * 遵循领域服务设计原则：包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 * 不能直接调用仓储层写入或更新数据库
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqMessageDomainServiceImpl implements IMqMessageDomainService {

    private final IMqMessageRepository mqMessageRepository;

    @Override
    public MqMessageAggregate createMessage(String userId, String message, String topic) {
        // 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
        // 这里假设userId、message、topic已经经过基础校验，我们只需要校验业务规则
        
        // 1.校验消息长度（最大512字符）
        if (StrUtil.isNotBlank(message) && message.length() > 512) {
            throw MessageException.of(MessageErrorCode.PARAM_LENGTH_ERROR, "消息内容过长，最大支持512个字符");
        }
        
        // 2.校验主题长度（最大64字符）
        if (StrUtil.isNotBlank(topic) && topic.length() > 64) {
            throw MessageException.of(MessageErrorCode.PARAM_LENGTH_ERROR, "消息主题过长，最大支持64个字符");
        }
        
        // 3.创建消息聚合根
        MqMessageAggregate messageAggregate = MqMessageAggregate.create(userId, message, topic);
        
        log.info("创建MQ消息成功，消息ID: {}, 用户ID: {}, 主题: {}", 
                messageAggregate.getId(), userId, topic);
        
        return messageAggregate;
    }

    @Override
    public MqMessageAggregate findByIdOrThrow(String messageId) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        return mqMessageRepository.findByIdOrThrow(messageId);
    }

    @Override
    public MqMessageAggregate updateState(String messageId, MessageState state) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        if (state == null) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息状态不能为空");
        }
        
        // 1.查找消息
        MqMessageAggregate message = mqMessageRepository.findByIdOrThrow(messageId);
        
        // 2.校验业务规则：终态消息不能修改状态
        if (message.isFinalState()) {
            throw MessageException.of(MessageErrorCode.FINAL_STATE_CANNOT_MODIFY, "终态消息不能修改状态");
        }
        
        // 3.更新状态
        message.updateState(state);
        
        log.info("更新消息状态成功，消息ID: {}, 原状态: {}, 新状态: {}", 
                messageId, message.getState(), state);
        
        return message;
    }

    @Override
    public MqMessageAggregate markAsCompleted(String messageId) {
        return updateState(messageId, MessageState.COMPLETED);
    }

    @Override
    public MqMessageAggregate markAsFailed(String messageId) {
        return updateState(messageId, MessageState.FAIL);
    }

    @Override
    public MqMessageAggregate updateMessage(String messageId, String newMessage) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        if (StrUtil.isBlank(newMessage)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息内容不能为空");
        }
        
        // 1.校验消息长度（最大512字符）
        if (newMessage.length() > 512) {
            throw MessageException.of(MessageErrorCode.PARAM_LENGTH_ERROR, "消息内容过长，最大支持512个字符");
        }
        
        // 2.查找消息
        MqMessageAggregate message = mqMessageRepository.findByIdOrThrow(messageId);
        
        // 3.校验业务规则：终态消息不能修改内容
        if (message.isFinalState()) {
            throw MessageException.of(MessageErrorCode.FINAL_STATE_CANNOT_MODIFY, "终态消息不能修改内容");
        }
        
        // 4.更新消息内容
        message.updateMessage(newMessage);
        
        log.info("更新消息内容成功，消息ID: {}, 消息长度: {}", messageId, newMessage.length());
        
        return message;
    }

    @Override
    public MqMessageAggregate updateTopic(String messageId, String newTopic) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        if (StrUtil.isBlank(newTopic)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息主题不能为空");
        }
        
        // 1.校验主题长度（最大64字符）
        if (newTopic.length() > 64) {
            throw MessageException.of(MessageErrorCode.PARAM_LENGTH_ERROR, "消息主题过长，最大支持64个字符");
        }
        
        // 2.查找消息
        MqMessageAggregate message = mqMessageRepository.findByIdOrThrow(messageId);
        
        // 3.校验业务规则：终态消息不能修改主题
        if (message.isFinalState()) {
            throw MessageException.of(MessageErrorCode.FINAL_STATE_CANNOT_MODIFY, "终态消息不能修改主题");
        }
        
        // 4.更新主题
        message.updateTopic(newTopic);
        
        log.info("更新消息主题成功，消息ID: {}, 新主题: {}", messageId, newTopic);
        
        return message;
    }

    @Override
    public boolean exists(String messageId) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        return mqMessageRepository.exists(messageId);
    }

    @Override
    public boolean isFinalState(String messageId) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        MqMessageAggregate message = mqMessageRepository.findByIdOrThrow(messageId);
        return message.isFinalState();
    }

    @Override
    public boolean isProcessing(String messageId) {
        if (StrUtil.isBlank(messageId)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息ID不能为空");
        }
        
        MqMessageAggregate message = mqMessageRepository.findByIdOrThrow(messageId);
        return message.isProcessing();
    }
}
