package com.xiaoo.langchain4j.demo.controller.rag;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

/**
 * RAG (检索增强生成) 控制器
 * 提供基于文档检索的聊天功能
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/easy-rag")
@RestController
@RequiredArgsConstructor
public class EasyRagController {

    private final OpenAiChatModel chatModel;
    
    private static final String DOCUMENT_PATH ="C:\\Users\\ouyucheng\\Desktop\\rag";

    /**
     * RAG 聊天接口 - 基于文档检索的聊天
     *
     * @param userMessage 用户消息，不能为空
     * @return 基于文档检索的 AI 回复
     */
    @PostMapping("/chat")
    public String ragChat(@RequestParam @NotBlank String userMessage) {
        log.info("收到 RAG 聊天请求: {}", userMessage);
        
        try {
            // 加载文档
            List<Document> documents = loadDocuments(DOCUMENT_PATH);
            log.info("从路径 {} 加载了 {} 个文档", DOCUMENT_PATH, documents.size());
            
            if (documents.isEmpty()) {
                log.warn("文档目录为空，将使用普通聊天模式");
                return chatWithBasicAiService(userMessage);
            }
            
            // 创建带有 RAG 功能的聊天服务
            ChatService ragChatService = createRagChatService(documents);
            
            // 使用 RAG 服务进行聊天
            String response = ragChatService.chat(userMessage);
            log.info("RAG 聊天完成，响应长度: {}", response.length());
            
            return response;
        } catch (Exception e) {
            log.error("RAG 聊天处理失败: {}", e.getMessage(), e);
            return "RAG 聊天处理失败: " + e.getMessage();
        }
    }

    /**
     * 创建带有 RAG 功能的聊天服务
     *
     * @param documents 文档列表
     * @return 配置了 RAG 的聊天服务
     */
    private ChatService createRagChatService(List<Document> documents) {
        ContentRetriever contentRetriever = createContentRetriever(documents);
        
        return AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(contentRetriever)
                .build();
    }

    /**
     * 创建内容检索器
     *
     * @param documents 文档列表
     * @return 内容检索器
     */
    private ContentRetriever createContentRetriever(List<Document> documents) {
        // 创建内存嵌入存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        
        // 将文档摄入到嵌入存储中
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        log.info("已将 {} 个文档摄入到嵌入存储中", documents.size());
        
        // 创建基于嵌入存储的内容检索器
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    /**
     * 使用基本 AI 服务进行聊天（无 RAG）
     *
     * @param userMessage 用户消息
     * @return AI 回复
     */
    private String chatWithBasicAiService(String userMessage) {
        ChatService basicChatService = AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
        
        return basicChatService.chat(userMessage);
    }
}
