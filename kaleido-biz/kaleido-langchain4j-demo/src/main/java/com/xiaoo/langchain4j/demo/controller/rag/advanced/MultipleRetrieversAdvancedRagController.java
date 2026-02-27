package com.xiaoo.langchain4j.demo.controller.rag.advanced;

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
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.rag.query.router.QueryRouter;
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
 * 多检索器高级 RAG (检索增强生成) 控制器
 * 同时检索多个向量库
 *
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/multiple-retrievers-rag")
@RestController
@RequiredArgsConstructor
public class MultipleRetrieversAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String TERMS_OF_USE_DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final String BIOGRAPHY_DOCUMENT_PATH = "./documents/biography-of-john-doe.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.6;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 多检索器高级 RAG 聊天接口 - 基于文档检索的聊天（包含多个检索器）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String multipleRetrieversRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到多检索器高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有多检索器高级 RAG 功能的聊天服务
            ChatService multipleRetrieversRagChatService = createMultipleRetrieversRagChatService();
            
            // 使用多检索器高级 RAG 服务进行聊天
            String response = multipleRetrieversRagChatService.chat(userMessage);
            log.info("多检索器高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("多检索器高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "多检索器高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有多检索器高级 RAG 功能的聊天服务
     *
     * @return 配置了多检索器高级 RAG 的聊天服务
     */
    private ChatService createMultipleRetrieversRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createMultipleRetrieversRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建多检索器检索增强器
     *
     * @return 多检索器检索增强器
     */
    private RetrievalAugmentor createMultipleRetrieversRetrievalAugmentor() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 创建第一个内容检索器（使用条款文档）
        EmbeddingStore<TextSegment> embeddingStore1 = embed(Path.of(TERMS_OF_USE_DOCUMENT_PATH), embeddingModel);
        ContentRetriever contentRetriever1 = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore1)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();

        log.info("创建第一个内容检索器（使用条款文档），最大结果: {}，最小分数: {}", MAX_RESULTS, MIN_SCORE);

        // 创建第二个内容检索器（传记文档）
        EmbeddingStore<TextSegment> embeddingStore2 = embed(Path.of(BIOGRAPHY_DOCUMENT_PATH), embeddingModel);
        ContentRetriever contentRetriever2 = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore2)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();

        log.info("创建第二个内容检索器（传记文档），最大结果: {}，最小分数: {}", MAX_RESULTS, MIN_SCORE);

        // 创建查询路由器，将每个查询路由到两个检索器
        QueryRouter queryRouter = new DefaultQueryRouter(contentRetriever1, contentRetriever2);

        log.info("创建 DefaultQueryRouter，将查询路由到两个检索器");

        // 创建检索增强器
        return DefaultRetrievalAugmentor.builder()
                .queryRouter(queryRouter)
                .build();
    }

    /**
     * 嵌入文档到嵌入存储
     *
     * @param documentPath 文档路径
     * @param embeddingModel 嵌入模型
     * @return 嵌入存储
     */
    private EmbeddingStore<TextSegment> embed(Path documentPath, EmbeddingModel embeddingModel) {
        DocumentParser documentParser = new TextDocumentParser();
        Document document = loadDocument(documentPath, documentParser);

        DocumentSplitter splitter = DocumentSplitters.recursive(SEGMENT_SIZE, SEGMENT_OVERLAP);
        List<TextSegment> segments = splitter.split(document);

        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);
        
        log.info("已将文档 {} 嵌入到存储中，段数: {}", documentPath.getFileName(), segments.size());
        
        return embeddingStore;
    }
    
    /**
     * 获取多检索器示例
     *
     * @return 多检索器示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                多检索器高级 RAG 示例说明:
                
                场景: 需要从多个数据源检索信息
                
                问题: 在实际应用中，相关信息可能分布在不同的文档、数据库或系统中。
                      单一检索器无法覆盖所有相关数据源。
                
                解决方案: 多检索器技术
                - 为每个数据源创建独立的检索器
                - 使用查询路由器将查询路由到所有相关检索器
                - 合并来自多个检索器的结果
                
                本示例使用两个检索器:
                1. 使用条款文档检索器: 包含租车公司的使用条款
                2. 传记文档检索器: 包含John Doe的传记信息
                
                技术实现:
                - 为每个文档创建独立的 EmbeddingStore
                - 为每个 EmbeddingStore 创建 ContentRetriever
                - 使用 DefaultQueryRouter 将查询路由到所有检索器
                - 检索器并行执行，结果自动合并
                
                优势:
                1. 覆盖更广泛的数据源
                2. 提高检索的全面性
                3. 支持异构数据源
                4. 灵活的数据源管理
                
                适用场景:
                1. 企业内部多个知识库
                2. 不同格式的文档（PDF、Word、HTML等）
                3. 结构化数据和非结构化数据混合
                4. 实时数据和历史数据结合
                """;
    }
}
