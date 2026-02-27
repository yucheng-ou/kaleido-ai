package com.xiaoo.langchain4j.demo.controller.rag.advanced;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.builder.sql.LanguageModelSqlFilterBuilder;
import dev.langchain4j.store.embedding.filter.builder.sql.TableDefinition;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

import static dev.langchain4j.data.document.Metadata.metadata;
import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

/**
 * 元数据过滤高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含元数据过滤技术
 * 通过元数据进行过滤 比如查询名字叫ouyucheng的所有文本
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/metadata-filtering-rag")
@RestController
@RequiredArgsConstructor
public class MetadataFilteringAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 静态元数据过滤 RAG 聊天接口
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat/static-filter")
    public String staticMetadataFilterRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到静态元数据过滤 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有静态元数据过滤功能的聊天服务
            ChatService staticFilterRagChatService = createStaticMetadataFilterRagChatService();
            
            // 使用静态元数据过滤 RAG 服务进行聊天
            String response = staticFilterRagChatService.chat(userMessage);
            log.info("静态元数据过滤 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("静态元数据过滤 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "静态元数据过滤 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 动态元数据过滤 RAG 聊天接口
     *
     * @param userId 用户ID
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat/dynamic-filter")
    public String dynamicMetadataFilterRagChat(
            @RequestParam String userId,
            @RequestParam @NotBlank String userMessage) {
        log.info("收到动态元数据过滤 RAG 聊天请求，用户ID: {}, 消息: {}", userId, userMessage);
        
        try {
            // 创建带有动态元数据过滤功能的聊天服务
            ChatService dynamicFilterRagChatService = createDynamicMetadataFilterRagChatService();
            
            // 使用动态元数据过滤 RAG 服务进行聊天
            String response = dynamicFilterRagChatService.chat(userMessage);
            log.info("动态元数据过滤 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("动态元数据过滤 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "动态元数据过滤 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * LLM生成元数据过滤 RAG 聊天接口
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat/llm-generated-filter")
    public String llmGeneratedMetadataFilterRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到LLM生成元数据过滤 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有LLM生成元数据过滤功能的聊天服务
            ChatService llmGeneratedFilterRagChatService = createLlmGeneratedMetadataFilterRagChatService();
            
            // 使用LLM生成元数据过滤 RAG 服务进行聊天
            String response = llmGeneratedFilterRagChatService.chat(userMessage);
            log.info("LLM生成元数据过滤 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("LLM生成元数据过滤 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "LLM生成元数据过滤 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有静态元数据过滤功能的聊天服务
     *
     * @return 配置了静态元数据过滤的聊天服务
     */
    private ChatService createStaticMetadataFilterRagChatService() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 创建文本段并添加元数据
        TextSegment dogsSegment = TextSegment.from("Article about dogs ...", metadata("animal", "dog"));
        TextSegment birdsSegment = TextSegment.from("Article about birds ...", metadata("animal", "bird"));

        // 创建嵌入存储并添加嵌入向量和段
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.add(embeddingModel.embed(dogsSegment).content(), dogsSegment);
        embeddingStore.add(embeddingModel.embed(birdsSegment).content(), birdsSegment);
        
        log.info("嵌入存储包含关于狗和鸟的片段");

        // 创建静态过滤器：仅选择动物为狗的片段
        Filter onlyDogs = metadataKey("animal").isEqualTo("dog");

        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .filter(onlyDogs) // 通过指定静态过滤器，将搜索限制为仅关于狗的片段
                .build();

        log.info("创建内容检索器，使用静态过滤器: animal = 'dog'");

        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建带有动态元数据过滤功能的聊天服务
     *
     * @return 配置了动态元数据过滤的聊天服务
     */
    private ChatService createDynamicMetadataFilterRagChatService() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 创建文本段并添加元数据
        TextSegment user1Info = TextSegment.from("My favorite color is green", metadata("userId", "1"));
        TextSegment user2Info = TextSegment.from("My favorite color is red", metadata("userId", "2"));

        // 创建嵌入存储并添加嵌入向量和段
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.add(embeddingModel.embed(user1Info).content(), user1Info);
        embeddingStore.add(embeddingModel.embed(user2Info).content(), user2Info);
        
        log.info("嵌入存储包含关于第一个和第二个用户的信息");

        // 创建动态过滤器：根据查询中的用户ID过滤
        Function<Query, Filter> filterByUserId =
                (query) -> metadataKey("userId").isEqualTo(query.metadata().chatMemoryId().toString());

        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                // 通过指定动态过滤器，将搜索限制为仅属于当前用户的片段
                .dynamicFilter(filterByUserId)
                .build();

        log.info("创建内容检索器，使用动态过滤器: userId = chatMemoryId");

        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建带有LLM生成元数据过滤功能的聊天服务
     *
     * @return 配置了LLM生成元数据过滤的聊天服务
     */
    private ChatService createLlmGeneratedMetadataFilterRagChatService() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 创建文本段并添加元数据
        TextSegment forrestGump = TextSegment.from("Forrest Gump", metadata("genre", "drama").put("year", 1994));
        TextSegment groundhogDay = TextSegment.from("Groundhog Day", metadata("genre", "comedy").put("year", 1993));
        TextSegment dieHard = TextSegment.from("Die Hard", metadata("genre", "action").put("year", 1998));

        // 将元数据键描述为SQL表中的列
        TableDefinition tableDefinition = TableDefinition.builder()
                .name("movies")
                .addColumn("genre", "VARCHAR", "one of: [comedy, drama, action]")
                .addColumn("year", "INT")
                .build();

        // 创建SQL过滤器构建器
        LanguageModelSqlFilterBuilder sqlFilterBuilder = new LanguageModelSqlFilterBuilder(chatModel, tableDefinition);

        // 创建嵌入存储并添加嵌入向量和段
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.add(embeddingModel.embed(forrestGump).content(), forrestGump);
        embeddingStore.add(embeddingModel.embed(groundhogDay).content(), groundhogDay);
        embeddingStore.add(embeddingModel.embed(dieHard).content(), dieHard);
        
        log.info("嵌入存储包含电影信息: Forrest Gump, Groundhog Day, Die Hard");

        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .dynamicFilter(query -> sqlFilterBuilder.build(query)) // LLM将动态生成过滤器
                .build();

        log.info("创建内容检索器，使用LLM生成的SQL过滤器");

        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }
    
    /**
     * 获取元数据过滤示例
     *
     * @return 元数据过滤示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                元数据过滤高级 RAG 示例说明:
                
                场景: 需要根据元数据条件过滤检索结果
                
                三种元数据过滤方法:
                
                1. 静态元数据过滤:
                   - 使用预定义的固定过滤器
                   - 示例: 仅检索 animal = 'dog' 的文档
                   - 适用场景: 固定的过滤条件，不随查询变化
                
                2. 动态元数据过滤:
                   - 根据查询上下文动态生成过滤器
                   - 示例: 根据用户ID过滤，仅检索 userId = currentUserId 的文档
                   - 适用场景: 个性化搜索，权限控制
                
                3. LLM生成的元数据过滤:
                   - 使用LLM分析查询并生成SQL过滤器
                   - 示例: 用户查询"90年代的戏剧电影"，LLM生成过滤器: genre = 'drama' AND year BETWEEN 1990 AND 1999
                   - 适用场景: 复杂的自然语言过滤条件
                
                技术实现:
                - 使用 MetadataFilterBuilder 构建静态过滤器
                - 使用 Function<Query, Filter> 实现动态过滤器
                - 使用 LanguageModelSqlFilterBuilder 实现LLM生成的过滤器
                
                优势:
                1. 提高检索精度
                2. 支持个性化搜索
                3. 处理复杂过滤条件
                4. 减少不相关结果
                """;
    }
}
