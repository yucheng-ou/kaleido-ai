package com.xiaoo.kaleido.ai.application.service;

import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Agent聊天服务
 * <p>
 * 提供Agent聊天相关的业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentChatService {

    private final AgentFactory agentFactory;

    /**
     * 与Agent进行聊天
     *
     * @param agentId        Agent ID
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    public Flux<String> chat(String agentId, String message, String conversationId) {
        log.info("开始Agent聊天，Agent ID: {}, 会话ID: {}, 消息长度: {}",
                agentId, conversationId, message.length());

        // 获取ChatClient
        ChatClient chatClient = agentFactory.getChatClient(agentId);

        // 生成或使用提供的会话ID
        String memoryId = conversationId != null && !conversationId.isEmpty()
                ? conversationId
                : UUID.randomUUID().toString();

        log.debug("使用会话ID: {}", memoryId);

        // 执行聊天
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, memoryId))
                .user(message)
                .stream()
                .content();
    }

    /**
     * 检查Agent是否可用
     *
     * @param agentId Agent ID
     * @return 如果Agent可用返回true，否则返回false
     */
    public boolean isAgentAvailable(String agentId) {
        try {
            agentFactory.getChatClient(agentId);
            return true;
        } catch (Exception e) {
            log.debug("Agent不可用，Agent ID: {}, 原因: {}", agentId, e.getMessage());
            return false;
        }
    }

    /**
     * 获取Agent状态信息
     *
     * @param agentId Agent ID
     * @return 状态信息
     */
    public String getAgentStatus(String agentId) {
        if (agentFactory.isAgentRegistered(agentId)) {
            return "REGISTERED";
        } else if (isAgentAvailable(agentId)) {
            return "AVAILABLE";
        } else {
            return "UNAVAILABLE";
        }
    }
}
