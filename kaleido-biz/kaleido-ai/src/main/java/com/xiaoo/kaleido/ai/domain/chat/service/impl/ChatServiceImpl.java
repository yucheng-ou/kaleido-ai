package com.xiaoo.kaleido.ai.domain.chat.service.impl;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.agent.armory.config.VectorStoreConfig;
import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
import com.xiaoo.kaleido.api.ai.enums.ToolType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * 聊天服务实现类
 * <p>
 * 提供聊天相关的业务逻辑，支持基于Agent的聊天和基于默认ChatClient的聊天
 *
 * @author ouyucheng
 * @date 2026/1/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService {

    private final AgentFactory agentFactory;

    /**
     * 基于Agent的聊天（带过滤表达式）
     * <p>
     * 使用指定的Agent进行聊天，支持动态过滤表达式
     *
     * @param agentId        Agent ID
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    public Flux<String> chatWithAgent(String agentId, String message, String conversationId, String userId) {
        log.info("开始基于Agent的聊天，Agent ID: {}, 会话ID: {}, 消息长度: {}",
                agentId, conversationId, message.length());

        // 获取ChatClient
        ChatClient chatClient = agentFactory.getChatClient(agentId);
        if (chatClient == null) {
            log.error("获取ChatClient失败，Agent ID: {}", agentId);
            return Flux.empty();
        }

        // 获取Agent配置
        AgentAggregate agentConfig = agentFactory.getAgentConfig(agentId);
        
        // 提取向量存储配置
        VectorStoreConfig vectorConfig = extractVectorStoreConfig(agentConfig);

        // 生成或使用提供的会话ID
        String memoryId = conversationId != null && !conversationId.isEmpty()
                ? conversationId
                : UUID.randomUUID().toString();

        // 开始构建ChatClient prompt
        var promptSpec = chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, memoryId))
                .advisors(advisorSpec -> advisorSpec.param(
                        QuestionAnswerAdvisor.FILTER_EXPRESSION,
                        String.format("userId == '%s'", userId)));

        // 如果有向量存储配置，添加相关参数
        if (vectorConfig != null) {
            promptSpec = promptSpec
                    .advisors(advisorSpec -> advisorSpec.param("topK", vectorConfig.getTopK()))
                    .advisors(advisorSpec -> advisorSpec.param("similarityThreshold",
                            vectorConfig.getSimilarityThreshold().toString()));
        }

        // 执行聊天
        return promptSpec
                .user(message)
                .stream()
                .content();
    }

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
    public Flux<String> chatWithDefault(String message, String conversationId, String userId) {
        log.info("开始基于默认ChatClient的聊天（带用户过滤），用户ID: {}, 会话ID: {}, 消息长度: {}",
                userId, conversationId, message.length());

        // 获取默认ChatClient
        ChatClient chatClient = agentFactory.getDefaultChatClient();

        // 生成或使用提供的会话ID
        String memoryId = conversationId != null && !conversationId.isEmpty()
                ? conversationId
                : UUID.randomUUID().toString();

        log.info("使用会话ID: {}, 用户ID: {}", memoryId, userId);

        // 执行聊天，添加用户ID过滤
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, memoryId))
                .advisors(advisorSpec -> advisorSpec.param(
                        QuestionAnswerAdvisor.FILTER_EXPRESSION,
                        String.format("userId == '%s'", userId)))
                .user(message)
                .stream()
                .content();
    }

    /**
     * 从Agent配置中提取向量存储配置
     *
     * @param agentConfig Agent配置
     * @return 向量存储配置，如果未找到则返回null
     */
    private VectorStoreConfig extractVectorStoreConfig(AgentAggregate agentConfig) {
        if (agentConfig == null || agentConfig.getTools() == null) {
            return null;
        }

        for (AgentTool tool : agentConfig.getTools()) {
            // 使用字符串比较工具类型，避免导入ToolType枚举
            if (ToolType.VECTOR_STORE.equals(tool.getToolType())) {
                return JSONUtil.toBean(tool.getToolConfig(), VectorStoreConfig.class);
            }
        }
        return null;
    }
}
