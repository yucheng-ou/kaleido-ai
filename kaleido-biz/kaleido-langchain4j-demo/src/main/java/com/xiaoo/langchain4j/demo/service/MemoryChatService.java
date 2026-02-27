package com.xiaoo.langchain4j.demo.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

/**
 * Langchain4j 记忆聊天服务接口
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@AiService
public interface MemoryChatService {

    /**
     * 带记忆的聊天方法
     *
     * @param memoryId 记忆ID，用于区分不同会话
     * @param userMessage 用户消息
     * @return 流式 AI 回复
     */
    Flux<String> memoryChat(@MemoryId String memoryId, @UserMessage String userMessage);
}
