package com.xiaoo.langchain4j.demo.config;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Langchain4j 演示配置类
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Configuration
class MyConfiguration {

    /**
     * 自定义 ChatModel 监听器 Bean
     *
     * @return ChatModelListener 实例
     */
    @Bean
    ChatModelListener chatModelListener() {
        return new CustomChatModelListener();
    }

    /**
     * 自定义 ChatModel 监听器实现类
     */
    private static class CustomChatModelListener implements ChatModelListener {

        private static final Logger LOG = LoggerFactory.getLogger(CustomChatModelListener.class);

        @Override
        public void onRequest(ChatModelRequestContext requestContext) {
            LOG.info("onRequest(): {}", requestContext.chatRequest());
        }

        @Override
        public void onResponse(ChatModelResponseContext responseContext) {
            LOG.info("onResponse(): {}", responseContext.chatResponse());
        }

        @Override
        public void onError(ChatModelErrorContext errorContext) {
            LOG.info("onError(): {}", errorContext.error().getMessage());
        }
    }
}
