package com.xiaoo.langchain4j.demo.config;

import com.xiaoo.kaleido.redis.service.RedissonService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Redis 聊天记忆存储实现
 * 使用 Redis 持久化存储 LangChain4j 的聊天消息
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
@Component
public class RedisChatMemoryStore implements ChatMemoryStore {

    private final RedissonService redissonService;


    public RedisChatMemoryStore(RedissonService redissonService) {
        this.redissonService = redissonService;
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String key = getKey(memoryId);
        String json = redissonService.getValue(key);

        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }

        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String key = getKey(memoryId);
        redissonService.setValue(key, messages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        redissonService.remove(getKey(memoryId));
    }

    private String getKey(Object memoryId) {
        return "langchain4j:demo:chat-memory:" + memoryId;
    }
}
