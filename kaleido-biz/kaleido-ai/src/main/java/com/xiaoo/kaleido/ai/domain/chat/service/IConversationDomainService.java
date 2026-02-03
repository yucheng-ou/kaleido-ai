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
     * @param userId         用户ID，不能为空
     * @return 会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    ConversationAggregate createConversation(String userId);

    /**
     * 更新会话标题
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param title          新会话标题，不能为空
     * @param userId         用户ID，不能为空
     * @return 更新后的会话聚合根
     */
    ConversationAggregate updateConversationTitle(String conversationId, String title, String userId);

    /**
     * 删除会话
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param userId         用户ID，不能为空
     */
    void deleteConversation(String conversationId, String userId);

}
