package com.xiaoo.langchain4j.demo.controller.memory;

import com.xiaoo.langchain4j.demo.config.RedisChatMemoryStore;
import com.xiaoo.langchain4j.demo.service.MemoryChatService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Langchain4j 记忆聊天控制器
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/memory")
@RestController
@RequiredArgsConstructor
public class ChatMemoryController implements InitializingBean {

    private final OpenAiChatModel chatModel;
    private final RedisChatMemoryStore redisChatMemoryStore;
    private MemoryChatService memoryChatService;

    @Override
    public void afterPropertiesSet() {
        memoryChatService = AiServices.builder(MemoryChatService.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .chatMemoryStore(redisChatMemoryStore)
                        .maxMessages(10)
                        .id(memoryId)
                        .build())
                .build();
    }

    /**
     * 带记忆的聊天接口
     *
     * @param userMessage 用户消息，不能为空
     * @param conversationId 会话ID，用于区分不同会话，不能为空
     * @return 流式 AI 回复
     */
    @GetMapping("/chat")
    public Flux<String> chat(
            @RequestParam @NotBlank String userMessage,
            @RequestParam @NotBlank String conversationId) {
        log.info("收到带记忆聊天请求，会话ID: {}, 消息: {}", conversationId, userMessage);
        return memoryChatService.memoryChat(conversationId, userMessage);
    }
}
