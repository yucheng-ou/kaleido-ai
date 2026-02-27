package com.xiaoo.langchain4j.demo.controller.rag;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
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
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

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
@RequestMapping("/langchain/naive-rag")
@RestController
@RequiredArgsConstructor
public class NaiveRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "C:\\Users\\ouyucheng\\Desktop\\rag\\temp.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.5;
    private static final int CHAT_MEMORY_SIZE = 10;

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
     * 创建 Naive 内容检索器
     *
     * @return Naive 内容检索器
     */
    private ContentRetriever createNaiveContentRetriever() {
        // 加载文档
        DocumentParser documentParser = new TextDocumentParser();
        Document document = loadDocument(Path.of(DOCUMENT_PATH), documentParser);

        // 分割文档为小段
        DocumentSplitter splitter = DocumentSplitters.recursive(SEGMENT_SIZE, SEGMENT_OVERLAP);
        List<TextSegment> segments = splitter.split(document);
        log.info("将文档分割为 {} 个段，每段大小: {}，重叠: {}", 
                segments.size(), SEGMENT_SIZE, SEGMENT_OVERLAP);
        
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
        log.info("使用 BgeSmallEnV15QuantizedEmbeddingModel 嵌入模型");
        
        // 嵌入所有段
        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
        log.info("已为 {} 个段生成嵌入向量", embeddings.size());
        
        // 创建嵌入存储并添加嵌入向量和段
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);
        log.info("已将嵌入向量和段添加到内存嵌入存储中");
        
        // 创建 Naive 内容检索器
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();
    }
}
