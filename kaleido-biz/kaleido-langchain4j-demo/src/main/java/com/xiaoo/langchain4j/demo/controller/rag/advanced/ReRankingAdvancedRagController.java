package com.xiaoo.langchain4j.demo.controller.rag.advanced;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.cohere.CohereScoringModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.scoring.ScoringModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.aggregator.ContentAggregator;
import dev.langchain4j.rag.content.aggregator.ReRankingContentAggregator;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
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
 * 重排序高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含重排序技术
 * 对于出没的检索结果 使用重排模型进行重排序 将真正相关有价值的内容放在前面
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/re-ranking-rag")
@RestController
@RequiredArgsConstructor
public class ReRankingAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 5;
    private static final double MIN_SCORE = 0.8;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 重排序高级 RAG 聊天接口 - 基于文档检索的聊天（包含重排序）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String reRankingRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到重排序高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有重排序高级 RAG 功能的聊天服务
            ChatService reRankingRagChatService = createReRankingRagChatService();
            
            // 使用重排序高级 RAG 服务进行聊天
            String response = reRankingRagChatService.chat(userMessage);
            log.info("重排序高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("重排序高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "重排序高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有重排序高级 RAG 功能的聊天服务
     *
     * @return 配置了重排序高级 RAG 的聊天服务
     */
    private ChatService createReRankingRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createReRankingRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建重排序检索增强器
     *
     * @return 重排序检索增强器
     */
    private RetrievalAugmentor createReRankingRetrievalAugmentor() {
        // 加载文档
        Document document = loadDocument(Path.of(DOCUMENT_PATH), new TextDocumentParser());

        // 创建嵌入模型
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

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

        // 创建内容检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS) // 获取更多结果进行重排序
                .build();

        log.info("创建内容检索器，最大结果: {}", MAX_RESULTS);

        // 创建评分模型（Cohere Rerank）
        // 注意：需要 COHERE_API_KEY 环境变量
        ScoringModel scoringModel = CohereScoringModel.builder()
                .apiKey(System.getenv("COHERE_API_KEY"))
                .modelName("rerank-multilingual-v3.0")
                .build();

        log.info("使用 CohereScoringModel 进行重排序");

        // 创建重排序内容聚合器
        ContentAggregator contentAggregator = ReRankingContentAggregator.builder()
                .scoringModel(scoringModel)
                .minScore(MIN_SCORE) // 仅向 LLM 呈现与用户查询真正相关的片段
                .build();

        log.info("创建 ReRankingContentAggregator，最小分数: {}", MIN_SCORE);

        // 创建检索增强器
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentAggregator(contentAggregator)
                .build();
    }
    
    /**
     * 获取重排序示例
     *
     * @return 重排序示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                重排序高级 RAG 示例说明:
                
                场景: 检索到的结果并非都与用户查询真正相关
                
                问题: 在初始检索阶段，通常使用更快且更具成本效益的模型，
                      特别是在处理大量数据时。权衡之处在于检索质量可能较低。
                      向 LLM 提供不相关信息可能代价高昂，最坏情况下会导致幻觉。
                
                解决方案: 重排序技术
                - 第一阶段: 使用快速、成本效益高的模型进行初始检索
                - 第二阶段: 对第一阶段获得的结果进行重排序
                - 使用更高级的模型（例如 Cohere Rerank）消除不相关的结果
                
                工作流程:
                1. 初始检索获取多个结果（例如 5 个）
                2. 使用重排序模型对结果进行评分
                3. 过滤掉低分结果（例如分数 < 0.8）
                4. 仅将高质量结果提供给 LLM
                
                优势: 提高最终答案质量，减少幻觉
                代价: 增加重排序阶段的延迟和成本
                依赖: 需要 Cohere API 密钥（或其他重排序模型）
                """;
    }
}
