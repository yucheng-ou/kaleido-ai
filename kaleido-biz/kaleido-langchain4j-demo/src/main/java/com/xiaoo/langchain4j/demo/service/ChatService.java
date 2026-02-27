package com.xiaoo.langchain4j.demo.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

/**
 * Langchain4j 聊天服务接口
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@AiService
public interface ChatService {

    /**
     * 普通聊天方法
     *
     * @param userMessage 用户消息
     * @return AI 回复
     */
    String chat(String userMessage);

    /**
     * 流式聊天方法
     *
     * @param userMessage 用户消息
     * @return 流式 AI 回复
     */
    Flux<String> chatStream(String userMessage);

    /**
     * 使用系统消息模板的聊天方法
     *
     * @param userMessage 用户消息
     * @return AI 回复
     */
    @SystemMessage("你是一名Java架构师，只回答Java架构方面的问题")
    String chatTemplate(String userMessage);
}
