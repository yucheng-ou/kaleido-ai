package com.xiaoo.kaleido.ai.application.query.impl;

import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;
import com.xiaoo.kaleido.ai.application.convertor.ConversationConvertor;
import com.xiaoo.kaleido.ai.application.query.ConversationQueryService;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public ConversationInfoResponse findById(String conversationId) {
        // 1.参数校验
        Objects.requireNonNull(conversationId, "conversationId不能为空");
        
        // 2.查询会话
        ConversationAggregate conversation = conversationRepository.findById(conversationId);
        
        // 3.转换为响应对象
        return conversation != null ? conversationConvertor.toResponse(conversation) : null;
    }

    @Override
    public List<ConversationInfoResponse> findByUserId(String userId) {
        // 1.参数校验
        Objects.requireNonNull(userId, "userId不能为空");
        
        // 2.查询会话列表
        List<ConversationAggregate> conversations = conversationRepository.findByUserId(userId);
        
        // 3.转换为响应对象列表
        return conversations.stream()
                .map(conversationConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConversationInfoResponse> findActiveConversationsByUserId(String userId) {
        // 1.参数校验
        Objects.requireNonNull(userId, "userId不能为空");
        
        // 2.查询活跃会话列表
        List<ConversationAggregate> conversations = conversationRepository.findActiveConversationsByUserId(userId);
        
        // 3.转换为响应对象列表
        return conversations.stream()
                .map(conversationConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConversationInfoResponse> findIdleConversationsByUserId(String userId, int maxIdleDays) {
        // 1.参数校验
        Objects.requireNonNull(userId, "userId不能为空");
        if (maxIdleDays < 0) {
            throw new IllegalArgumentException("maxIdleDays不能小于0");
        }
        
        // 2.查询闲置会话列表
        List<ConversationAggregate> conversations = conversationRepository.findIdleConversationsByUserId(userId, maxIdleDays);
        
        // 3.转换为响应对象列表
        return conversations.stream()
                .map(conversationConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
