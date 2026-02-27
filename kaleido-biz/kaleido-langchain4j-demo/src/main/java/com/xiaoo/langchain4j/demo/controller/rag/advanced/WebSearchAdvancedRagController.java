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
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.rag.query.router.QueryRouter;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 网络搜索高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含网络搜索集成技术
 * 所有配置值均已硬编码
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/web-search-rag")
@RestController
@RequiredArgsConstructor
public class WebSearchAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int EMBEDDING_MAX_RESULTS = 2;
    private static final double EMBEDDING_MIN_SCORE = 0.6;
    private static final int WEB_SEARCH_MAX_RESULTS = 3;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 网络搜索高级 RAG 聊天接口 - 基于文档检索的聊天（包含网络搜索集成）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String webSearchRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到网络搜索高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有网络搜索高级 RAG 功能的聊天服务
            ChatService webSearchRagChatService = createWebSearchRagChatService();
            
            // 使用网络搜索高级 RAG 服务进行聊天
            String response = webSearchRagChatService.chat(userMessage);
            log.info("网络搜索高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("网络搜索高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "网络搜索高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有网络搜索高级 RAG 功能的聊天服务
     *
     * @return 配置了网络搜索高级 RAG 的聊天服务
     */
    private ChatService createWebSearchRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createWebSearchRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建网络搜索检索增强器
     *
     * @return 网络搜索检索增强器
     */
    private RetrievalAugmentor createWebSearchRetrievalAugmentor() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 嵌入文档到嵌入存储
        EmbeddingStore<TextSegment> embeddingStore = embed(Path.of(DOCUMENT_PATH), embeddingModel);

        // 创建嵌入存储内容检索器
        ContentRetriever embeddingStoreContentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(EMBEDDING_MAX_RESULTS)
                .minScore(EMBEDDING_MIN_SCORE)
                .build();

        log.info("创建嵌入存储内容检索器，最大结果: {}，最小分数: {}", 
                EMBEDDING_MAX_RESULTS, EMBEDDING_MIN_SCORE);

        // 创建网络搜索内容检索器
        // 注意：需要 TAVILY_API_KEY 环境变量
        WebSearchEngine webSearchEngine = TavilyWebSearchEngine.builder()
                .apiKey(System.getenv("TAVILY_API_KEY")) // 获取免费密钥: https://app.tavily.com/sign-in
                .build();

        ContentRetriever webSearchContentRetriever = WebSearchContentRetriever.builder()
                .webSearchEngine(webSearchEngine)
                .maxResults(WEB_SEARCH_MAX_RESULTS)
                .build();

        log.info("创建网络搜索内容检索器，最大结果: {}，需要TAVILY_API_KEY环境变量", WEB_SEARCH_MAX_RESULTS);

        // 创建查询路由器，将每个查询路由到两个检索器
        QueryRouter queryRouter = new DefaultQueryRouter(embeddingStoreContentRetriever, webSearchContentRetriever);

        log.info("创建 DefaultQueryRouter，将查询路由到嵌入存储检索器和网络搜索检索器");

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
     * 获取网络搜索示例
     *
     * @return 网络搜索示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                网络搜索高级 RAG 示例说明:
                
                场景: 需要结合内部文档和外部网络信息
                
                问题: 内部文档可能不包含最新信息或特定主题的详细信息，
                      而网络搜索可以提供实时、广泛的信息。
                
                解决方案: 网络搜索集成技术
                - 结合内部文档检索器和网络搜索检索器
                - 内部检索器: 检索公司内部文档、知识库
                - 网络检索器: 检索互联网上的最新信息
                
                技术实现:
                1. 嵌入存储内容检索器: 基于内部文档的向量检索
                2. 网络搜索内容检索器: 基于Tavily搜索引擎的网络检索
                3. 查询路由器: 将查询同时路由到两个检索器
                4. 结果合并: 自动合并来自两个来源的结果
                
                依赖要求:
                - Maven依赖: langchain4j-web-search-engine-tavily
                - API密钥: TAVILY_API_KEY环境变量（免费注册: https://app.tavily.com）
                
                工作流程:
                1. 用户发送查询
                2. 查询被路由到两个检索器
                3. 内部检索器: 从公司文档中检索相关信息
                4. 网络检索器: 从互联网检索最新信息
                5. 结果合并后提供给LLM生成回答
                
                优势:
                1. 结合内部知识和外部信息
                2. 获取最新、实时的信息
                3. 覆盖更广泛的主题
                4. 提高回答的准确性和时效性
                
                适用场景:
                1. 需要最新市场信息的商业分析
                2. 技术问题的解决方案搜索
                3. 竞争对手分析
                4. 行业趋势研究
                """;
    }
}
