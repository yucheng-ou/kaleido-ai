package com.xiaoo.kaleido.message.domain.mq.adapter.repository;

import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;

import java.util.List;

/**
 * MQ消息仓储接口
 * <p>
 * 定义MQ消息聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
public interface IMqMessageRepository {

    /**
     * 保存消息聚合根
     * <p>
     * 保存消息聚合根到数据库，如果是新消息则插入，如果是已存在消息则更新
     *
     * @param messageAggregate 消息聚合根，不能为空
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当保存失败时抛出
     */
    void save(MqMessageAggregate messageAggregate);

    /**
     * 更新消息聚合根
     * <p>
     * 更新消息聚合根信息到数据库
     *
     * @param messageAggregate 消息聚合根，不能为空
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当更新失败或消息不存在时抛出
     */
    void update(MqMessageAggregate messageAggregate);

    /**
     * 根据ID查找消息聚合根
     * <p>
     * 根据消息ID查询消息聚合根
     *
     * @param messageId 消息ID，不能为空
     * @return 消息聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    MqMessageAggregate findById(String messageId);

    /**
     * 根据ID查找消息聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保消息存在的场景，如果消息不存在则抛出异常
     *
     * @param messageId 消息ID，不能为空
     * @return 消息聚合根
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当消息不存在时抛出
     */
    MqMessageAggregate findByIdOrThrow(String messageId);

    /**
     * 根据用户ID查找消息列表
     * <p>
     * 查询指定用户的所有消息
     *
     * @param userId 用户ID，不能为空
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findByUserId(String userId);

    /**
     * 根据主题查找消息列表
     * <p>
     * 查询指定主题的所有消息
     *
     * @param topic 消息主题，不能为空
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findByTopic(String topic);

    /**
     * 根据用户ID和主题查找消息列表
     * <p>
     * 查询指定用户和主题的所有消息
     *
     * @param userId 用户ID，不能为空
     * @param topic  消息主题，不能为空
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findByUserIdAndTopic(String userId, String topic);

    /**
     * 根据状态查找消息列表
     * <p>
     * 查询指定状态的所有消息
     *
     * @param state 消息状态，不能为空
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findByState(String state);

    /**
     * 检查消息是否存在
     * <p>
     * 检查指定ID的消息是否存在
     *
     * @param messageId 消息ID，不能为空
     * @return 是否存在，true表示存在，false表示不存在
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当参数无效或查询失败时抛出
     */
    boolean exists(String messageId);

    /**
     * 查询所有未被删除的消息
     * <p>
     * 查询所有未被删除的消息列表
     *
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findAllNotDeleted();

    /**
     * 查询处理中的消息（创建状态）
     * <p>
     * 查询所有状态为创建的消息
     *
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findProcessingMessages();

    /**
     * 查询终态的消息（已完成或失败）
     * <p>
     * 查询所有状态为已完成或失败的消息
     *
     * @return 消息聚合根列表
     * @throws com.xiaoo.kaleido.message.types.exception.MessageException 当查询失败时抛出
     */
    List<MqMessageAggregate> findFinalStateMessages();
}
