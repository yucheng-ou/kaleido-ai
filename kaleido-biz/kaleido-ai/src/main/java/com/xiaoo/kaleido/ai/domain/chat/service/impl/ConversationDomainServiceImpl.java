package com.xiaoo.kaleido.ai.domain.chat.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import com.xiaoo.kaleido.ai.domain.chat.service.IConversationDomainService;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天领域服务实现类
 * <p>
 * 实现聊天领域服务的核心业务逻辑，遵循DDD原则：
 * 1. service层包含参数校验与聚合根的修改
 * 2. 可以查询数据库进行参数校验
 * 3. 不能直接调用仓储层写入或更新数据库（通过聚合根的方法修改状态）
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationDomainServiceImpl implements IConversationDomainService {

    private final IConversationRepository conversationRepository;

    @Override
    public ConversationAggregate createConversation(String userId) {
        // 创建聚合根（聚合根本身包含最核心的业务逻辑，包括ID生成和默认标题设置）
        ConversationAggregate conversation = ConversationAggregate.create(userId);

        // 如果conversationId不为空，检查唯一性
        if (conversationRepository.existsById(conversation.getConversationId())) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_EXISTS, "会话ID已存在: " + conversation.getConversationId());
        }

        log.info("创建会话成功，会话ID: {}, 用户ID: {}", conversation.getConversationId(), userId);
        return conversation;
    }

    @Override
    public ConversationAggregate updateConversationTitle(String conversationId, String title, String userId) {
        // 参数校验
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }
        if (StrUtil.isBlank(title)) {
            throw AiException.of(AiErrorCode.CONVERSATION_TITLE_EMPTY, "会话标题不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw AiException.of(AiErrorCode.USER_ID_NOT_NULL, "用户ID不能为空");
        }

        // 查找会话
        ConversationAggregate conversation = conversationRepository.findByIdOrThrow(conversationId);

        // 验证用户权限
        if (!conversation.isOwner(userId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ACCESS_DENIED, "用户无权修改此会话");
        }

        // 更新会话标题（聚合根本身包含最核心的业务逻辑）
        conversation.updateTitle(title);

        log.info("更新会话标题成功，会话ID: {}, 新标题: {}, 用户ID: {}", conversationId, title, userId);
        return conversation;
    }

    @Override
    public void deleteConversation(String conversationId, String userId) {
        // 参数校验
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw AiException.of(AiErrorCode.USER_ID_NOT_NULL, "用户ID不能为空");
        }

        // 查找会话
        ConversationAggregate conversation = conversationRepository.findByIdOrThrow(conversationId);

        // 验证用户权限
        if (!conversation.isOwner(userId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ACCESS_DENIED, "用户无权删除此会话");
        }
    }
}
