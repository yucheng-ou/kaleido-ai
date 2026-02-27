package com.xiaoo.langchain4j.demo.controller.rag.advanced;

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
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.Result;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 返回来源高级 RAG (检索增强生成) 控制器
 * 返回检索来源
 *
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/return-sources-rag")
@RestController
@RequiredArgsConstructor
public class ReturnSourcesAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "./documents/miles-of-smiles-terms-of-use.txt";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int MAX_RESULTS = 2;
    private static final double MIN_SCORE = 0.6;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 返回来源高级 RAG 聊天接口 - 基于文档检索的聊天（包含返回检索来源）
     *
     * @param userMessage 用户消息，不能为空
     * @return 包含回答和检索来源的响应
     */
    @PostMapping("/chat")
    public Result<String> returnSourcesRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到返回来源高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有返回来源功能的助手
            ReturnSourcesAssistant assistant = createReturnSourcesAssistant();
            
            // 使用助手进行聊天
            return assistant.answer(userMessage);
        } catch (Exception e) {
            log.error("返回来源高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 创建带有返回来源功能的助手
     *
     * @return 配置了返回来源功能的助手
     */
    private ReturnSourcesAssistant createReturnSourcesAssistant() {
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

        return AiServices.builder(ReturnSourcesAssistant.class)
                .chatModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
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
     * 获取返回来源示例
     *
     * @return 返回来源示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                返回来源高级 RAG 示例说明:
                
                场景: 需要向用户展示回答所依据的文档来源
                
                问题: 在基础RAG中，用户只能看到最终回答，
                      但不知道回答是基于哪些文档内容生成的。
                      这降低了回答的可信度和可追溯性。
                
                解决方案: 返回来源技术
                - 在响应中包含检索到的文档内容
                - 显示每个来源的元数据（如文件名、位置等）
                - 显示每个来源的相关性分数
                
                技术实现:
                1. 使用Result<String>作为返回类型，而不是简单的String
                2. Result对象包含content（回答）和sources（来源）
                3. 每个Source包含文本内容、元数据和相关性分数
                4. 将Result转换为自定义的响应格式
                
                响应格式示例:
                {
                  "answer": "根据租车公司的使用条款，取消政策规定...",
                  "sources": [
                    {
                      "content": "取消政策: 用户可在预订后24小时内免费取消...",
                      "metadata": {
                        "file_name": "miles-of-smiles-terms-of-use.txt",
                        "index": 3
                      },
                      "score": 0.85
                    },
                    {
                      "content": "退款政策: 取消后退款将在7-10个工作日内处理...",
                      "metadata": {
                        "file_name": "miles-of-smiles-terms-of-use.txt", 
                        "index": 4
                      },
                      "score": 0.72
                    }
                  ]
                }
                
                优势:
                1. 提高回答的可信度
                2. 支持用户验证信息来源
                3. 增强系统的透明度
                4. 便于调试和优化检索过程
                
                适用场景:
                1. 法律、医疗等需要严格引用的领域
                2. 学术研究和文献综述
                3. 企业知识库问答系统
                4. 需要高可信度的客户服务
                """;
    }

    /**
     * 返回来源助手接口
     */
    interface ReturnSourcesAssistant {
        Result<String> answer(String query);
    }

    /**
     * 返回来源响应类
     */
    @Data
    public static class ReturnSourcesResponse {
        private final String answer;
        private final List<SourceInfo> sources;
        
        public ReturnSourcesResponse(String answer, List<SourceInfo> sources) {
            this.answer = answer;
            this.sources = sources;
        }
    }

    /**
     * 来源信息类
     */
    @Data
    public static class SourceInfo {
        private String content;
        private Object metadata;
        private Double score;
    }
}
