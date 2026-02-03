package com.xiaoo.kaleido.ai.application.query.impl;

import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;
import com.xiaoo.kaleido.ai.application.convertor.ConversationConvertor;
import com.xiaoo.kaleido.ai.application.query.ConversationQueryService;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.repository.mongo.MongoChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 会话查询服务实现
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationQueryServiceImpl implements ConversationQueryService {

    private final IConversationRepository conversationRepository;
    private final ConversationConvertor conversationConvertor;
    private final MongoChatMemoryRepository mongoChatMemoryRepository;

    @Override
    public ConversationInfoResponse findById(String conversationId) {
        
        // 1.查询会话
        ConversationAggregate conversation = conversationRepository.findById(conversationId);
        if (conversation == null) {
            return null;
        }

        // 2.查询消息
        List<org.springframework.ai.chat.messages.Message> aiMessages = mongoChatMemoryRepository.findByConversationId(conversationId);

        // 3.转换为响应对象
        ConversationInfoResponse response = conversationConvertor.toResponse(conversation);

        // 4.转换消息并设置到响应中
        List<ConversationInfoResponse.Message> messages = aiMessages.stream()
                .map(msg -> ConversationInfoResponse.Message.builder()
                        .role(msg.getMessageType().name())
                        .content(msg.getText())
                        .build())
                .collect(Collectors.toList());
        
        response.setMessages(messages);

        return response;
    }

    @Override
    public List<ConversationInfoResponse> findByUserId(String userId) {
        
        // 1.查询会话列表
        List<ConversationAggregate> conversations = conversationRepository.findByUserId(userId);
        
        // 2.转换为响应对象列表
        return conversations.stream()
                .map(conversationConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
