package com.xiaoo.langchain4j.demo.controller.rag.advanced;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
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
import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * 元数据高级 RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能，包含元数据注入技术
 * 检索的时候查出元数据 方便给用户数据来源 可以指定列
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/advanced/metadata-rag")
@RestController
@RequiredArgsConstructor
public class MetadataAdvancedRagController {

    private final OpenAiChatModel chatModel;
    
    // 硬编码的配置值
    private static final String DOCUMENT_PATH = "C:\\Users\\ouyucheng\\Desktop\\rag\\Kaleido-AI教程（七）整洁架构实践详解.md";
    private static final int SEGMENT_SIZE = 300;
    private static final int SEGMENT_OVERLAP = 0;
    private static final int CHAT_MEMORY_SIZE = 10;

    /**
     * 元数据高级 RAG 聊天接口 - 基于文档检索的聊天（包含元数据注入）
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String metadataRagChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到元数据高级 RAG 聊天请求: {}", userMessage);
        
        try {
            // 创建带有元数据高级 RAG 功能的聊天服务
            ChatService metadataRagChatService = createMetadataRagChatService();
            
            // 使用元数据高级 RAG 服务进行聊天
            String response = metadataRagChatService.chat(userMessage);
            log.info("元数据高级 RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("元数据高级 RAG 聊天处理失败: {}", e.getMessage(), e);
            return "元数据高级 RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有元数据高级 RAG 功能的聊天服务
     *
     * @return 配置了元数据高级 RAG 的聊天服务
     */
    private ChatService createMetadataRagChatService() {
        // 加载并处理文档
        RetrievalAugmentor retrievalAugmentor = createMetadataRetrievalAugmentor();
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(CHAT_MEMORY_SIZE))
                .build();
    }

    /**
     * 创建元数据检索增强器
     *
     * @return 元数据检索增强器
     */
    private RetrievalAugmentor createMetadataRetrievalAugmentor() {
        // 加载文档
        Document document = loadDocument(Path.of(DOCUMENT_PATH), new ApacheTikaDocumentParser());

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
                .build();

        log.info("创建内容检索器");

        // 每个检索到的片段应在提示中包含"file_name"和"index"元数据值
        List<String> metadataKeysToInclude = Arrays.asList("file_name", "index");
        ContentInjector contentInjector = DefaultContentInjector.builder()
                // .promptTemplate(...) // 格式也可以更改
                .metadataKeysToInclude(metadataKeysToInclude)
                .build();

        log.info("创建 DefaultContentInjector，包含的元数据键: {}", metadataKeysToInclude);

        // 创建检索增强器
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector(contentInjector)
                .build();
    }
    
    /**
     * 获取元数据示例
     *
     * @return 元数据示例说明
     */
    @GetMapping("/example")
    public String getExample() {
        return """
                元数据高级 RAG 示例说明:
                
                场景: 需要将文档来源和其他元数据包含到 LLM 提示中
                
                问题: 在基础 RAG 中，LLM 可能不知道检索到的内容来自哪个文档或位置，
                      这限制了其引用来源和提供更准确回答的能力。
                
                解决方案: 元数据注入技术
                - 在文档处理阶段为每个文本段添加元数据
                - 元数据可以包括: 文件名、文档类型、创建日期、作者、索引位置等
                - 在检索时，将元数据与内容一起注入到 LLM 提示中
                
                示例元数据:
                - file_name: "miles-of-smiles-terms-of-use.txt"
                - index: 3 (表示这是文档中的第 3 个片段)
                - document_type: "terms_of_use"
                - created_date: "2024-01-15"
                
                优势:
                1. LLM 可以引用具体来源（例如"根据文件 X 的第 Y 节"）
                2. 提高回答的可信度和可追溯性
                3. 支持更复杂的查询（例如"取消政策在哪个文件中定义？"）
                
                实现: 使用 DefaultContentInjector 配置要包含的元数据键
                """;
    }
}
