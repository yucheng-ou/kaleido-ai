package com.xiaoo.kaleido.ai.domain.chat.service;

import reactor.core.publisher.Flux;

/**
 * 聊天服务接口
 * <p>
 * 定义聊天服务的核心业务逻辑，支持基于Agent的聊天和基于默认ChatClient的聊天
 *
 * @author ouyucheng
 * @date 2026/1/31
 */
public interface IChatService {

    /**
     * 基于Agent的聊天（带过滤表达式）
     * <p>
     * 使用指定的Agent进行聊天，支持动态过滤表达式
     *
     * @param agentId        Agent ID
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @param userId         用户id
     * @return 聊天响应流
     */
    Flux<String> chatWithAgent(
            String agentId,
            String message,
            String conversationId,
            String userId);

    /**
     * 基于默认ChatClient的聊天（带用户ID过滤）
     * <p>
     * 使用默认ChatClient进行聊天，支持用户ID过滤
     *
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @param userId         用户ID（用于向量存储过滤）
     * @return 聊天响应流
     */
    Flux<String> chatWithDefault(
            String message,
            String conversationId,
            String userId);

}
