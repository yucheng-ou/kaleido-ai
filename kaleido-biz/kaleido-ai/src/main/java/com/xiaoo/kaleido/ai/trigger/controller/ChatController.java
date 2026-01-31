package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import io.modelcontextprotocol.client.McpSyncClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;
import org.springframework.web.bind.annotation.*;
import com.xiaoo.kaleido.ai.trigger.dto.ClothingDocumentDto;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiApi openAiApi;
    private final ChatMemory myChatMemory;
    private final List<McpSyncClient> mcpSyncClients;
    private final MilvusVectorStore vectorStore;

    @GetMapping(value = "/chat")
    public Flux<String> chat(
            @RequestParam("message") String message,
            @RequestParam("conversationId") String conversationId) {

        String userId = StpUserUtil.getLoginId();

        // 自动生成会话ID（如果未提供）
        if (conversationId == null || conversationId.isEmpty()) {
            conversationId = UUID.randomUUID().toString();
        }
        String memoryId = conversationId;

        // 初始化 ChatModel
        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-v3")
                        .build())
                .build();

        SyncMcpToolCallbackProvider callbackProvider = SyncMcpToolCallbackProvider.builder().mcpClients(mcpSyncClients).build();

        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(myChatMemory).build())
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder().build()).build())
                .defaultToolCallbacks(callbackProvider)
                .build();


        return chatClient
                .prompt("你是一名搭配能手 根据当前的衣物信息与天气状况 搭配出最合适的穿搭")
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, memoryId))
                .advisors(advisorSpec -> advisorSpec.param(QuestionAnswerAdvisor.FILTER_EXPRESSION, String.format("userId == '%s'", userId)))
                .user(message)
                .stream().content();
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody List<ClothingDocumentDto> clothingList) {
        log.info("开始保存 {} 件衣服信息到Milvus", clothingList.size());

        List<Document> documents = clothingList.stream()
                .map(this::convertToDocument)
                .toList();

        vectorStore.add(documents);

        log.info("成功保存 {} 件衣服信息到Milvus", documents.size());
    }

    /**
     * 将 ClothingDocumentDto 转换为 Spring AI Document
     */
    private Document convertToDocument(ClothingDocumentDto dto) {
        // 创建描述性文本内容
        String content = String.format(
                "这是一件%s，类型为%s，颜色为%s，适合%s季节。尺码为%s，品牌为%s。%s",
                dto.getName(),
                dto.getTypeCode(),
                dto.getColorCode(),
                dto.getSeasonCode(),
                dto.getSize(),
                dto.getBrandName() != null ? dto.getBrandName() : "未知品牌",
                dto.getDescription() != null ? "描述：" + dto.getDescription() : ""
        );

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("clothingId", dto.getClothingId());
        metadata.put("userId", dto.getUserId());
        metadata.put("name", dto.getName());
        metadata.put("typeCode", dto.getTypeCode());
        metadata.put("colorCode", dto.getColorCode());
        metadata.put("seasonCode", dto.getSeasonCode());
        metadata.put("brandName", dto.getBrandName());
        metadata.put("size", dto.getSize());
        metadata.put("price", dto.getPrice());
        metadata.put("description", dto.getDescription() != null ? dto.getDescription() : "");

        return new Document(content, metadata);
    }
}
