package com.xiaoo.kaleido.interview.types.config;

import com.xiaoo.kaleido.interview.domain.agent.*;
import com.xiaoo.kaleido.interview.domain.candidate.adapter.ai.IResumeExtractor;
import com.xiaoo.kaleido.interview.infrastructure.tools.EmailTools;
import com.xiaoo.kaleido.interview.infrastructure.tools.InterviewTools;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI服务配置类
 * <p>
 * 配置LangChain4j相关的Bean，包括AI服务、向量存储、聊天记忆等
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiConfig {

    private final OpenAiChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final InterviewTools interviewTools;
    private final EmailTools emailTools;
    private final RedisChatMemoryStore redisChatMemoryStore;

    @Value("${milvus.host:localhost}")
    private String milvusHost;

    @Value("${milvus.port:19530}")
    private Integer milvusPort;

    @Value("${milvus.collection-name:candidate_resumes}")
    private String collectionName;

    @Value("${milvus.dimension}")
    private Integer dimension;

    /**
     * Milvus向量存储
     * 动态获取嵌入模型维度，参考demo项目的最佳实践
     */
    @Bean
    public EmbeddingStore<TextSegment> milvusEmbeddingStore() {
        log.info("初始化Milvus向量存储，host: {}, port: {}, collection: {}",
                milvusHost, milvusPort, collectionName);

        // 动态获取嵌入模型的实际维度
        int actualDimension = getActualEmbeddingDimension();
        log.info("使用嵌入模型实际维度: {} (配置维度: {})", actualDimension, dimension);

        return MilvusEmbeddingStore.builder()
                .host(milvusHost)
                .port(milvusPort)
                .collectionName(collectionName)
                .dimension(actualDimension)
                .build();
    }

    /**
     * 动态获取嵌入模型的实际维度
     * 参考demo项目中的做法，通过嵌入一个测试文本来获取维度
     */
    private int getActualEmbeddingDimension() {
        try {
            // 创建一个简单的测试文本
            String testText = "test";
            Embedding embedding = embeddingModel.embed(testText).content();
            int actualDimension = embedding.dimension();
            log.info("嵌入模型实际维度检测为: {}", actualDimension);
            return actualDimension;
        } catch (Exception e) {
            log.warn("无法获取嵌入模型实际维度，使用配置维度: {}. 错误: {}", dimension, e.getMessage());
            return dimension != null ? dimension : 1536;
        }
    }

    /**
     * 内容检索器（用于RAG）
     */
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore) {
        log.info("初始化内容检索器");

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(3)
                .minScore(0.7)
                .build();
    }

    /**
     * Redis聊天记忆提供者
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        log.info("初始化Redis聊天记忆提供者");

        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20) // 保留最近20条消息
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }

    /**
     * 简历提取AI服务（Application层实现）
     */
    @Bean
    public IResumeExtractor applicationResumeExtractor() {
        log.info("初始化简历提取AI服务（Application层）");

        return AiServices.builder(IResumeExtractor.class)
                .chatModel(chatModel)
                .build();
    }

    /**
     * 意图识别 Agent
     */
    @Bean
    public IIntentAgent intentAgent() {
        log.info("初始化意图识别 Agent");
        return AiServices.builder(IIntentAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider())
                .build();
    }

    /**
     * 普通聊天 Agent
     */
    @Bean
    public IGeneralChatAgent generalChatAgent() {
        log.info("初始化普通聊天 Agent");
        return AiServices.builder(IGeneralChatAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider())
                .build();
    }

    /**
     * 候选人查询 Agent
     */
    @Bean
    public ICandidateAgent candidateAgent(ContentRetriever contentRetriever) {
        log.info("初始化候选人查询 Agent");
        return AiServices.builder(ICandidateAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider())
                .contentRetriever(contentRetriever)
                .tools(interviewTools)
                .build();
    }

    /**
     * 面试安排 Agent
     */
    @Bean
    public IInterviewAgent interviewAgent() {
        log.info("初始化面试安排 Agent");
        return AiServices.builder(IInterviewAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider())
                .tools(interviewTools)
                .build();
    }

    /**
     * Offer发送 Agent
     */
    @Bean
    public IOfferAgent offerAgent() {
        log.info("初始化Offer发送 Agent");
        return AiServices.builder(IOfferAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider())
                .tools(emailTools)
                .build();
    }
}
