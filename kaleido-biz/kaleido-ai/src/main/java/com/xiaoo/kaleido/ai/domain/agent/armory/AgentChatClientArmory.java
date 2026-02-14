package com.xiaoo.kaleido.ai.domain.agent.armory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoo.kaleido.ai.domain.agent.armory.config.McpConfig;
import com.xiaoo.kaleido.ai.domain.agent.armory.config.MemoryConfig;
import com.xiaoo.kaleido.ai.domain.agent.armory.config.VectorStoreConfig;
import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.mongo.MongoChatMemoryRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Agent ChatClient 工厂类
 * <p>
 * 负责根据Agent配置创建ChatClient实例
 * 支持动态配置解析和工具集成
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AgentChatClientArmory {

    private final OpenAiApi openAiApi;
    private final MilvusVectorStore vectorStore;
    private final ObjectMapper objectMapper;
    private final MongoChatMemoryRepository mongoChatMemoryRepository;
    private final ChatMemory myChatMemory;
    private final Environment environment;
    private final List<McpSyncClient> mcpSyncClients = new ArrayList<>();

    /**
     * 根据Agent配置创建ChatClient
     *
     * @param agent Agent聚合根
     * @return 配置好的ChatClient实例
     */
    public ChatClient createChatClient(AgentAggregate agent) {
        log.info("开始创建ChatClient，Agent ID: {}, 名称: {}", agent.getId(), agent.getName());

        // 1. 构建ChatModel
        ChatModel chatModel = buildChatModel(agent);

        // 2. 构建ChatClient.Builder
        ChatClient.Builder builder = ChatClient
                .builder(chatModel)
                .defaultSystem(agent.getSystemPrompt());

        // 3. 添加工具配置
        configureTools(builder, agent.getTools());

        ChatClient chatClient = builder.build();
        log.info("ChatClient创建成功，Agent ID: {}", agent.getId());

        return chatClient;
    }

    /**
     * 创建默认ChatClient
     * <p>
     * 创建与ChatController中完全一样的ChatClient配置
     * 包含：记忆工具、向量存储工具、MCP工具
     *
     * @return 默认ChatClient实例
     */
    public ChatClient createDefaultChatClient() {
        log.info("开始创建默认ChatClient");

        // 1. 构建ChatModel（与ChatController中相同）
        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(resolveDefaultModel())
                        .build())
                .build();

        // 2. 构建ChatClient.Builder
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        // 3. 添加默认Advisors（与ChatController中相同）
        // 3.1 记忆工具
        builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(myChatMemory).build());

        // 3.2 向量存储工具
        builder.defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build());

        // 4. 添加MCP工具回调（如果可用）
        if (!mcpSyncClients.isEmpty()) {
            SyncMcpToolCallbackProvider callbackProvider = SyncMcpToolCallbackProvider.builder()
                    .mcpClients(mcpSyncClients)
                    .build();
            builder.defaultToolCallbacks(callbackProvider);
        }

        ChatClient defaultChatClient = builder.build();
        log.info("默认ChatClient创建成功");

        return defaultChatClient;
    }

    /**
     * 构建ChatModel
     */
    private ChatModel buildChatModel(AgentAggregate agent) {
        String modelName = agent.getModelName();
        String defaultModel = resolveDefaultModel();
        if (!StringUtils.hasText(modelName)) {
            modelName = defaultModel;
        }
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(modelName)
                .temperature(agent.getTemperature() != null ? agent.getTemperature().doubleValue() : 0.70)
                .maxTokens(agent.getMaxTokens() != null ? agent.getMaxTokens() : 2000)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(options)
                .build();
    }

    private String resolveDefaultModel() {
        String configuredModel = environment.getProperty("spring.ai.openai.chat.options.model");
        if (StringUtils.hasText(configuredModel)) {
            return configuredModel.trim();
        }
        return "deepseek-v3";
    }

    /**
     * 配置工具
     */
    private void configureTools(ChatClient.Builder builder, List<AgentTool> tools) {
        if (tools == null || tools.isEmpty()) {
            log.debug("Agent没有配置任何工具");
            return;
        }

        List<McpSyncClient> mcpClients = new ArrayList<>();

        for (AgentTool tool : tools) {
            try {
                switch (tool.getToolType()) {
                    case MEMORY:
                        configureMemoryTool(builder, tool);
                        break;
                    case VECTOR_STORE:
                        break;
                    case MCP:
                        McpSyncClient mcpClient = configureMcpTool(tool);
                        if (mcpClient != null) {
                            mcpClients.add(mcpClient);
                        }
                        break;
                    default:
                        log.warn("未知的工具类型: {}", tool.getToolType());
                }
            } catch (Exception e) {
                log.error("配置工具失败，工具编码: {}, 错误: {}", tool.getToolCode(), e.getMessage(), e);
            }
        }

        // 配置MCP工具回调
        if (!mcpClients.isEmpty()) {
            configureMcpToolCallbacks(builder, mcpClients);
        }
    }

    /**
     * 配置记忆工具
     */
    private void configureMemoryTool(ChatClient.Builder builder, AgentTool tool) {
        try {
            MemoryConfig config = objectMapper.readValue(tool.getToolConfig(), MemoryConfig.class);
            if (config.getEnabled() != null && !config.getEnabled()) {
                log.debug("记忆工具已禁用，工具编码: {}", tool.getToolCode());
                return;
            }

            MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                    .chatMemoryRepository(mongoChatMemoryRepository)
                    .maxMessages(config.getMaxMessages() != null ? config.getMaxMessages() : 200)
                    .build();

            MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

            builder.defaultAdvisors(advisor);
            log.debug("记忆工具配置成功，工具编码: {}, 最大消息数: {}", tool.getToolCode(), config.getMaxMessages());
        } catch (Exception e) {
            // 使用默认配置
            MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(myChatMemory).build();
            builder.defaultAdvisors(advisor);
            log.debug("使用默认记忆工具配置，工具编码: {}", tool.getToolCode());
        }
    }

    /**
     * 配置向量存储工具
     * 向量检索工具不从tool中读取 每一次对话的时候在设置
     */
    private void configureVectorStoreTool(ChatClient.Builder builder) {
        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor.builder(vectorStore).build();
        builder.defaultAdvisors(advisor);
    }

    /**
     * 配置MCP工具
     */
    private McpSyncClient configureMcpTool(AgentTool tool) {
        try {
            McpConfig config = objectMapper.readValue(tool.getToolConfig(), McpConfig.class);
            if (config.getEnabled() != null && !config.getEnabled()) {
                log.debug("MCP工具已禁用，工具编码: {}", tool.getToolCode());
                return null;
            }

            if (config.getBaseUri() == null || config.getBaseUri().isEmpty()) {
                log.warn("MCP工具配置缺少baseUri，工具编码: {}", tool.getToolCode());
                return null;
            }

            HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport.builder(config.getBaseUri())
                    .sseEndpoint(config.getEndpoint())
                    .build();

            McpSyncClient mcpSyncClient = McpClient
                    .sync(sseClientTransport)
                    .requestTimeout(Duration.ofMinutes(config.getConnectTimeout()))
                    .build();

            log.debug("MCP工具配置成功，工具编码: {}, baseUri: {}", tool.getToolCode(), config.getBaseUri());
            return mcpSyncClient;
        } catch (Exception e) {
            log.error("配置MCP工具失败，工具编码: {}, 错误: {}", tool.getToolCode(), e.getMessage(), e);
            return null;
        }
    }

    /**
     * 配置MCP工具回调
     */
    private void configureMcpToolCallbacks(ChatClient.Builder builder, List<McpSyncClient> mcpClients) {
        try {
            SyncMcpToolCallbackProvider callbackProvider = SyncMcpToolCallbackProvider.builder()
                    .mcpClients(mcpClients)
                    .build();
            builder.defaultToolCallbacks(callbackProvider);
            log.debug("MCP工具回调配置成功，MCP客户端数量: {}", mcpClients.size());
        } catch (Exception e) {
            log.error("配置MCP工具回调失败，错误: {}", e.getMessage(), e);
        }
    }
}
