package com.xiaoo.langchain4j.demo.controller.rag.advanced;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 跳过检索高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含有条件跳过检索技术
 * 根据用户的问题判断是否需要检索 比如用户说了hello 是没必要进行检索的
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/skip-retrieval-rag")
@RestController
@RequiredArgsConstructor
public class SkipRetrievalAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.6;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 跳过检索高级 RAG 聊天接口 - 基于文档检索的聊天（包含有条件跳过检索）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String skipRetrievalRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到跳过检索高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有跳过检索高级 RAG 功能的聊天服务
            ChatService skipRetrievalRagChatService = createSkipRetrievalRagChatService();
            
            // 使用跳过检索高级 RAG 服务进行聊天
            String response = skipRetrievalRagChatService.chat(userMessage);
            log.info("跳过检索高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("跳过检索高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "跳过检索高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有跳过检索高级 RAG 功能的聊天服务
     *
     * @return 配置了跳过检索高级 RAG 的聊天服务
     */
    private ChatService createSkipRetrievalRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createSkipRetrievalRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建跳过检索检索增强器
     *
     * @return 跳过检索检索增强器
     */
    private RetrievalAugmentor createSkipRetrievalRetrievalAugmentor() {
        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // 嵌入文档到嵌入存储
        EmbeddingStore<TextSegment> embeddingStore = embed(Path.of(DOCUMENT_PATH), embeddingModel);

        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)
                .minScore(MIN_SCORE)
                .build();

        log.info("创建内容检索器，最大结果: {}，最小分数: {}", MAX_RESULTS, MIN_SCORE);

        // 创建自定义查询路由器
        QueryRouter queryRouter = new QueryRouter() {
            private final PromptTemplate PROMPT_TEMPLATE = PromptTemplate.from(
                    "Is the following query related to the business of the car rental company? " +
                            "Answer only 'yes', 'no' or 'maybe'. " +
                            "Query: {{it}}"
            );

            @Override
            public Collection<ContentRetriever> route(Query query) {
                Prompt prompt = PROMPT_TEMPLATE.apply(query.text());
                AiMessage aiMessage = chatModel.chat(prompt.toUserMessage()).aiMessage();
                
                log.info("LLM决定是否跳过检索: {}", aiMessage.text());
                
                if (aiMessage.text().toLowerCase().contains("no")) {
                    log.info("查询与租车公司业务无关，跳过检索");
                    return Collections.emptyList();
                }
                
                log.info("查询与租车公司业务相关，进行检索");
                return Collections.singletonList(contentRetriever);
            }
        };

        log.info("创建自定义 QueryRouter，使用LLM决定是否跳过检索");

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
     * 获取跳过检索示例
     *
     * @return 跳过检索示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                跳过检索高级 RAG 示例说明:
                
                场景: 有时检索是不必要的，例如当用户只是打招呼或询问与业务无关的问题时
                
                问题: 对于某些查询（如"Hi"、"Hello"、"How are you?"），
                      进行文档检索是浪费资源且可能降低响应质量。
                
                解决方案: 有条件跳过检索技术
                - 使用LLM分析查询是否与业务相关
                - 如果查询与业务无关，则跳过检索阶段
                - 如果查询与业务相关，则进行正常检索
                
                决策方法:
                1. 使用LLM分析查询相关性（本示例使用此方法）
                2. 使用规则（例如，根据关键词判断）
                3. 使用语义相似性
                4. 使用分类器
                
                工作流程:
                1. 用户发送查询
                2. LLM分析查询是否与业务相关
                3. 如果相关: 进行文档检索 → 生成回答
                4. 如果不相关: 跳过检索 → 直接生成回答
                
                示例:
                - 查询"Hi" → LLM判断"no" → 跳过检索 → 直接生成问候回复
                - 查询"Can I cancel my reservation?" → LLM判断"yes" → 进行检索 → 基于文档生成回答
                
                优势:
                1. 减少不必要的检索开销
                2. 提高响应速度
                3. 避免不相关检索结果干扰
                4. 改善用户体验
                """;
    }
}
