package com.xiaoo.kaleido.ai.domain.chat.service;

import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;

import java.util.List;

/**
 * 聊天领域服务接口
 * <p>
 * 定义聊天领域服务的核心业务逻辑，处理会话的创建、消息发送、历史记录查询等操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IConversationDomainService {

    /**
     * 创建新会话
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param userId         用户ID，不能为空
     * @param title          会话标题，可为空
     * @return 会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    ConversationAggregate createConversation(
            String conversationId,
            String userId,
            String title);

    /**
     * 根据会话ID查找会话
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @return 会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当会话不存在时抛出
     */
    ConversationAggregate findConversationByIdOrThrow(String conversationId);

    /**
     * 根据用户ID查找会话列表
     *
     * @param userId 用户ID，不能为空
     * @return 用户的所有会话聚合根列表
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    List<ConversationAggregate> findConversationsByUserId(String userId);

    /**
     * 更新会话标题
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param title          新会话标题，不能为空
     * @return 更新后的会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当会话不存在时抛出
     */
    ConversationAggregate updateConversationTitle(String conversationId, String title);

    /**
     * 更新会话最后消息时间
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @return 更新后的会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当会话不存在时抛出
     */
    ConversationAggregate updateConversationLastMessageTime(String conversationId);

    /**
     * 删除会话
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param userId         用户ID，不能为空
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当会话不存在或不属于该用户时抛出
     */
    void deleteConversation(String conversationId, String userId);

}
