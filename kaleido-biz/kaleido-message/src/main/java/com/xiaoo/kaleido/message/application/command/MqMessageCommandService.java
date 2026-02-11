package com.xiaoo.kaleido.message.application.command;

import com.xiaoo.kaleido.api.message.command.CreateMqMessageCommand;
import com.xiaoo.kaleido.message.domain.mq.adapter.repository.IMqMessageRepository;
import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import com.xiaoo.kaleido.message.domain.mq.service.IMqMessageDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MQ消息命令服务
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqMessageCommandService {

    private final IMqMessageDomainService mqMessageDomainService;
    private final IMqMessageRepository mqMessageRepository;

    /**
     * 创建消息
     *
     * @param userId  用户ID
     * @param command 创建命令
     * @return 消息ID
     */
    public String createMessage(String userId, CreateMqMessageCommand command) {
        log.info("开始创建MQ消息, userId: {}, topic: {}", userId, command.getTopic());
        
        // 1. 调用领域服务创建聚合根
        MqMessageAggregate aggregate = mqMessageDomainService.createMessage(
                userId, 
                command.getMessage(), 
                command.getTopic()
        );
        
        // 2. 持久化
        mqMessageRepository.save(aggregate);
        
        log.info("MQ消息创建并持久化成功, messageId: {}", aggregate.getId());
        return aggregate.getId();
    }

    /**
     * 标记消息为完成
     *
     * @param messageId 消息ID
     */
    public void markAsCompleted(String messageId) {
        log.info("开始标记消息为完成, messageId: {}", messageId);
        
        // 1. 调用领域服务更新状态
        MqMessageAggregate aggregate = mqMessageDomainService.markAsCompleted(messageId);
        
        // 2. 持久化更新
        mqMessageRepository.update(aggregate);
        
        log.info("消息标记为完成成功, messageId: {}", messageId);
    }

    /**
     * 标记消息为失败
     *
     * @param messageId 消息ID
     */
    public void markAsFailed(String messageId) {
        log.info("开始标记消息为失败, messageId: {}", messageId);
        
        // 1. 调用领域服务更新状态
        MqMessageAggregate aggregate = mqMessageDomainService.markAsFailed(messageId);
        
        // 2. 持久化更新
        mqMessageRepository.update(aggregate);
        
        log.info("消息标记为失败成功, messageId: {}", messageId);
    }
}
