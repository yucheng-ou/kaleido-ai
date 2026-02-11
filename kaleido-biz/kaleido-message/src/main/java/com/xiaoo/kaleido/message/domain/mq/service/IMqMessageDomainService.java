package com.xiaoo.kaleido.message.domain.mq.service;

import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import com.xiaoo.kaleido.message.domain.mq.model.vo.MessageState;

/**
 * MQ消息领域服务接口
 * <p>
 * 定义MQ消息领域服务的核心业务逻辑，处理消息的创建、状态更新、查询等操作
 * 遵循领域服务设计原则：包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 * 不能直接调用仓储层写入或更新数据库
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
public interface IMqMessageDomainService {

    /**
     * 创建消息
     * <p>
     * 创建新的MQ消息记录
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param userId  用户ID，不能为空
     * @param message 消息主体，不能为空
     * @param topic   消息主题，不能为空
     * @return 消息聚合根
     */
    MqMessageAggregate createMessage(String userId, String message, String topic);

    /**
     * 根据ID查找消息
     *
     * @param messageId 消息ID，不能为空
     * @return 消息聚合根
     */
    MqMessageAggregate findByIdOrThrow(String messageId);

    /**
     * 更新消息状态
     *
     * @param messageId 消息ID，不能为空
     * @param state     新的状态，不能为空
     * @return 更新后的消息聚合根
     */
    MqMessageAggregate updateState(String messageId, MessageState state);

    /**
     * 标记消息为完成状态
     *
     * @param messageId 消息ID，不能为空
     * @return 更新后的消息聚合根
     */
    MqMessageAggregate markAsCompleted(String messageId);

    /**
     * 标记消息为失败状态
     *
     * @param messageId 消息ID，不能为空
     * @return 更新后的消息聚合根
     */
    MqMessageAggregate markAsFailed(String messageId);

}
