package com.xiaoo.langchain4j.demo.controller.vector;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Naive RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，实现基本的 naive RAG 流程
 * 所有配置值均已硬编码
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/milvus/rag")
@RestController
@RequiredArgsConstructor
public class MilvusRagController {

    private final OpenAiChatModel chatModel;
    
    // Milvus 配置
    @Value("${milvus.host}")
    private String milvusHost;
    
    @Value("${milvus.port}")
    private String milvusPort;
    
    @Value("${milvus.username}")
    private String milvusUsername;
    
    @Value("${milvus.password}")
    private String milvusPassword;
    
    @Value("${milvus.database-name}")
    private String milvusDatabaseName;
    
    @Value("${milvus.collection-name}")
    private String milvusCollectionName;
    
    @Value("${milvus.dimension}")
    private Integer milvusDimension;
    
    // 配置值
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.5;
    private static final int CHAT_MEMORY_SIZE = 10;
    
    // 共享组件
    private EmbeddingStore<TextSegment> embeddingStore;
    private EmbeddingModel embeddingModel;
    private boolean isDocumentUploaded = false;
    

    /**
     * 上传文档接口 - 将文档上传到 Milvus 向量数据库
     *
     * @param file 上传的文档文件
     * @return 上传结果信息
     */
    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file) {
        log.info("收到文档上传请求，文件名: {}, 大小: {} bytes", 
                file.getOriginalFilename(), file.getSize());
        
        if (file.isEmpty()) {
            return "上传的文件为空，请选择有效的文档文件。";
        }
        
        if (isDocumentUploaded) {
            return "文档已上传，无需重复上传。可以直接使用 /chat 接口进行聊天。";
        }
        
        try {
            // 处理上传的文件流
            uploadDocumentToMilvus(file);
            
            isDocumentUploaded = true;
            return String.format("文档上传成功！文件 '%s' 已处理并存储到 Milvus 向量数据库。现在可以使用 /chat 接口进行基于文档的聊天。",
                    file.getOriginalFilename());
        } catch (Exception e) {
            log.error("文档上传失败: {}", e.getMessage(), e);
            return "文档上传失败: " + e.getMessage();
        }
    }

    /**
     * Naive RAG 聊天接口 - 基于文档检索的聊天
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String naiveRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到 Naive RAG 聊天请求: {}", userMessage);
        
        try {
            // 检查文档是否已上传
            if (!isDocumentUploaded) {
                return "请先调用 /upload 接口上传文档，然后再使用 /chat 接口进行聊天。";
            }
            
            // 创建带有 Naive RAG 功能的聊天服务
            ChatService naiveRagChatService = createNaiveRagChatService();
            
            // 使用 Naive RAG 服务进行聊天
            String response = naiveRagChatService.chat(userMessage);
            log.info("Naive RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("Naive RAG 聊天处理失败: {}", e.getMessage(), e);
            return "Naive RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有 Naive RAG 功能的聊天服务
     *
     * @return 配置了 Naive RAG 的聊天服务
     */
    private ChatService createNaiveRagChatService() {
        // 加载并处理文档
        ContentRetriever contentRetriever = createNaiveContentRetriever();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .contentRetriever(contentRetriever)
                .build();
    }

    /**
     * 上传文档到 Milvus 向量数据库
     */
    private void uploadDocumentToMilvus(MultipartFile file) throws Exception {
        // 直接从文件流解析文档
        Document document;
        try (InputStream inputStream = file.getInputStream()) {
            ApacheTikaDocumentParser parser = new ApacheTikaDocumentParser();
            document = parser.parse(inputStream);
        }
        
        // 记录文档信息
        log.info("文档解析成功，文件名: {}, 文档文本长度: {}", 
                file.getOriginalFilename(), document.text().length());

        // 分割文档为小段
        DocumentSplitter splitter = DocumentSplitters.recursive(SEGMENT_SIZE, SEGMENT_OVERLAP);
        List<TextSegment> segments = splitter.split(document);
        log.info("将文档分割为 {} 个段，每段大小: {}，重叠: {}", 
                segments.size(), SEGMENT_SIZE, SEGMENT_OVERLAP);
        
        // 创建嵌入模型
        embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
        log.info("使用 BgeSmallEnV15QuantizedEmbeddingModel 嵌入模型");
        
        // 嵌入所有段
        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
        log.info("已为 {} 个段生成嵌入向量", embeddings.size());
        
        // 动态获取嵌入维度
        int actualDimension = !embeddings.isEmpty() ? embeddings.get(0).dimension() : milvusDimension;
        log.info("嵌入向量维度: {} (配置文件维度: {})", actualDimension, milvusDimension);
        
        // 创建 Milvus 嵌入存储
        String uri = "http://" + milvusHost + ":" + milvusPort;
        log.info("连接 Milvus: {} (数据库: {}, 集合: {})", 
                uri, milvusDatabaseName, milvusCollectionName);
        
        embeddingStore = MilvusEmbeddingStore.builder()
                .uri(uri)
                .username(milvusUsername)
                .password(milvusPassword)
                .databaseName(milvusDatabaseName)
                .collectionName(milvusCollectionName)
                .dimension(actualDimension)
                .build();
        
        // 添加嵌入向量和段到 Milvus
        embeddingStore.addAll(embeddings, segments);
        log.info("已将 {} 个嵌入向量和段添加到 Milvus 存储中", embeddings.size());
    }

    /**
     * 创建 Naive 内容检索器（基于已上传的文档）
     *
     * @return Naive 内容检索器
     */
    private ContentRetriever createNaiveContentRetriever() {
        if (embeddingStore == null || embeddingModel == null) {
            throw new IllegalStateException("文档尚未上传，请先调用 /upload 接口");
        }
        
        // 创建 Naive 内容检索器
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();
    }
}
