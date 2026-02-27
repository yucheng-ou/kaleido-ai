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
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 查询路由高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含查询路由技术
 * 说白了就是可能有很多向量库 不可能一一去检索 那么就给每个向量库加一个描述 由大模型去判断应该使用哪个
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/query-routing-rag")
@RestController
@RequiredArgsConstructor
public class QueryRoutingAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String BIOGRAPHY_DOCUMENT_PATH = "./documents/biography-of-john-doe.txt";
    private static final String TERMS_OF_USE_DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.6;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 查询路由高级 RAG 聊天接口 - 基于文档检索的聊天（包含查询路由）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String queryRoutingRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到查询路由高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有查询路由高级 RAG 功能的聊天服务
            ChatService queryRoutingRagChatService = createQueryRoutingRagChatService();
            
            // 使用查询路由高级 RAG 服务进行聊天
            String response = queryRoutingRagChatService.chat(userMessage);
            log.info("查询路由高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("查询路由高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "查询路由高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有查询路由高级 RAG 功能的聊天服务
     *
     * @return 配置了查询路由高级 RAG 的聊天服务
     */
    private ChatService createQueryRoutingRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createQueryRoutingRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建查询路由检索增强器
     *
     * @return 查询路由检索增强器
     */
    private RetrievalAugmentor createQueryRoutingRetrievalAugmentor() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 为传记创建单独的嵌入存储
        EmbeddingStore<TextSegment> biographyEmbeddingStore =
                embed(Path.of(BIOGRAPHY_DOCUMENT_PATH), embeddingModel);
        ContentRetriever biographyContentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(biographyEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();

        // 为使用条款创建单独的嵌入存储
        EmbeddingStore<TextSegment> termsOfUseEmbeddingStore =
                embed(Path.of(TERMS_OF_USE_DOCUMENT_PATH), embeddingModel);
        ContentRetriever termsOfUseContentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(termsOfUseEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();

        // 创建查询路由器
        Map<ContentRetriever, String> retrieverToDescription = new HashMap<>();
        retrieverToDescription.put(biographyContentRetriever, "biography of John Doe");
        retrieverToDescription.put(termsOfUseContentRetriever, "terms of use of car rental company");
        QueryRouter queryRouter = new LanguageModelQueryRouter(chatModel, retrieverToDescription);

        log.info("创建 LanguageModelQueryRouter 进行查询路由");

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
     * 获取查询路由示例
     *
     * @return 查询路由示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                查询路由高级 RAG 示例说明:
                
                场景: 私有数据分布在多个来源和格式中
                
                数据源示例:
                1. Confluence 上的内部公司文档
                2. Git 仓库中的项目代码
                3. 包含用户数据的关系数据库
                4. 包含所售产品的搜索引擎
                
                问题: 在利用多个数据源的 RAG 流程中，您可能会有多个内容检索器。
                      虽然可以将每个用户查询路由到所有可用的内容检索器，
                      但这种方法可能效率低下且适得其反。
                
                解决方案: 查询路由技术
                - 将查询定向到最合适的内容检索器（或多个）
                - 路由可以通过多种方式实现:
                  1. 使用规则（例如，根据用户的权限、位置等）
                  2. 使用关键词（例如，如果查询包含特定单词）
                  3. 使用语义相似性
                  4. 使用 LLM 做出路由决策（本示例使用此方法）
                
                优势: 提高检索效率，减少不相关结果
                代价: 增加路由决策的计算开销
                """;
    }
}
