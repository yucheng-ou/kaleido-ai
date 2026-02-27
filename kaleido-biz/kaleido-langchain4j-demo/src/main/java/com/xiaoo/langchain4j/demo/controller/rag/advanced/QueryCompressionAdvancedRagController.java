package com.xiaoo.langchain4j.demo.controller.rag.advanced;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
import dev.langchain4j.rag.query.transformer.QueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 查询压缩高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含查询压缩技术
 * 检索过程中可以对上下文进行压缩 示例见下文 getExample方法
 *
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/query-compression-rag")
@RestController
@RequiredArgsConstructor
public class QueryCompressionAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "C:\\Users\\ouyucheng\\Desktop\\rag\\temp.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.6;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 查询压缩高级 RAG 聊天接口 - 基于文档检索的聊天（包含查询压缩）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String queryCompressionRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到查询压缩高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有查询压缩高级 RAG 功能的聊天服务
            ChatService queryCompressionRagChatService = createQueryCompressionRagChatService();
            
            // 使用查询压缩高级 RAG 服务进行聊天
            String response = queryCompressionRagChatService.chat(userMessage);
            log.info("查询压缩高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("查询压缩高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "查询压缩高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有查询压缩高级 RAG 功能的聊天服务
     *
     * @return 配置了查询压缩高级 RAG 的聊天服务
     */
    private ChatService createQueryCompressionRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createQueryCompressionRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建查询压缩检索增强器
     *
     * @return 查询压缩检索增强器
     */
    private RetrievalAugmentor createQueryCompressionRetrievalAugmentor() {
        // 加载文档
        Document document = loadDocument(Path.of(DOCUMENT_PATH), new TextDocumentParser());

        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
        log.info("使用 BgeSmallEnV15QuantizedEmbeddingModel 嵌入模型");
        
        // 创建嵌入存储
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        
        // 使用 EmbeddingStoreIngestor 简化文档摄入过程
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(SEGMENT_SIZE, SEGMENT_OVERLAP))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        
        // 摄入文档
        ingestor.ingest(document);
        log.info("已使用 EmbeddingStoreIngestor 摄入文档，段大小: {}，重叠: {}", 
                SEGMENT_SIZE, SEGMENT_OVERLAP);
        
        // 创建查询转换器（查询压缩）
        QueryTransformer queryTransformer = new CompressingQueryTransformer(chatModel);
        log.info("使用 CompressingQueryTransformer 进行查询压缩");
        
        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();
        
        log.info("创建内容检索器，最大结果: {}，最小分数: {}", MAX_RESULTS, MIN_SCORE);
        
        // 创建检索增强器
        return DefaultRetrievalAugmentor.builder()
                .queryTransformer(queryTransformer)
                .contentRetriever(contentRetriever)
                .build();
    }

    /**
     * 获取查询压缩示例
     *
     * @return 查询压缩示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                查询压缩高级 RAG 示例说明:
                
                场景: 多轮对话中的上下文引用问题
                
                第一轮对话:
                用户: "What is the legacy of John Doe?"
                AI: "John Doe was a famous scientist who..."
                
                第二轮对话:
                用户: "When was he born?"
                
                问题: 在基础 RAG 中，查询 "When was he born?" 可能无法找到关于 John Doe 的文章，
                      因为查询中不包含 "John Doe"。
                
                解决方案: 查询压缩技术
                - 系统会获取用户查询和之前的对话历史
                - 使用 LLM 将其压缩为独立的查询
                - 生成类似 "When was John Doe born?" 的查询
                - 使用压缩后的查询进行文档检索
                
                优势: 显著提高多轮对话中 RAG 的质量
                代价: 增加少量延迟和成本
                """;
    }
}
