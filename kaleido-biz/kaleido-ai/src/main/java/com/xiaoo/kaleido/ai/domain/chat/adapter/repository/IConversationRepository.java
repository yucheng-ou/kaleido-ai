package com.xiaoo.kaleido.ai.domain.chat.adapter.repository;

import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;

import java.util.List;

/**
 * 会话仓储接口
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IConversationRepository {

    /**
     * 保存会话聚合根
     * <p>
     * 保存会话聚合根到数据库，如果是新会话则插入，如果是已存在会话则更新
     *
     * @param conversationAggregate 会话聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void save(ConversationAggregate conversationAggregate);

    /**
     * 更新会话聚合根
     * <p>
     * 更新会话聚合根信息到数据库
     *
     * @param conversationAggregate 会话聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当更新失败或会话不存在时抛出
     */
    void update(ConversationAggregate conversationAggregate);

    /**
     * 根据ID查找会话聚合根
     * <p>
     * 根据会话ID查询会话聚合根
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @return 会话聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    ConversationAggregate findById(String conversationId);

    /**
     * 根据ID查找会话聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保会话存在的场景，如果会话不存在则抛出异常
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @return 会话聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当会话不存在时抛出
     */
    ConversationAggregate findByIdOrThrow(String conversationId);

    /**
     * 根据用户ID查找会话列表
     * <p>
     * 查询指定用户的所有会话
     *
     * @param userId 用户ID，不能为空
     * @return 会话聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<ConversationAggregate> findByUserId(String userId);

    /**
     * 根据用户ID查找活跃会话列表
     * <p>
     * 查询指定用户的活跃会话（24小时内有消息的会话）
     *
     * @param userId 用户ID，不能为空
     * @return 活跃会话聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<ConversationAggregate> findActiveConversationsByUserId(String userId);

    /**
     * 根据用户ID查找闲置会话列表
     * <p>
     * 查询指定用户的闲置会话（超过指定天数没有消息的会话）
     *
     * @param userId      用户ID，不能为空
     * @param maxIdleDays 最大闲置天数，不能小于0
     * @return 闲置会话聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<ConversationAggregate> findIdleConversationsByUserId(String userId, int maxIdleDays);

    /**
     * 检查会话ID是否存在
     * <p>
     * 检查指定会话ID是否已存在于数据库中
     *
     * @param conversationId 会话ID，不能为空
     * @return 如果存在返回true，否则返回false
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    boolean existsById(String conversationId);

}
