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
    public ConversationAggregate createConversation(
            String conversationId,
            String userId,
            String title) {
        // 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
        // 这里校验业务规则，比如会话ID唯一性
        if (conversationRepository.existsById(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_EXISTS, "会话ID已存在: " + conversationId);
        }

        // 创建聚合根（聚合根本身包含最核心的业务逻辑，不包含参数校验）
        ConversationAggregate conversation = ConversationAggregate.create(conversationId, userId, title);

        log.info("创建会话成功，会话ID: {}, 用户ID: {}", conversationId, userId);
        return conversation;
    }

    @Override
    public ConversationAggregate findConversationByIdOrThrow(String conversationId) {
        // 参数校验
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }

        // 查询数据库
        return conversationRepository.findByIdOrThrow(conversationId);
    }

    @Override
    public List<ConversationAggregate> findConversationsByUserId(String userId) {
        // 参数校验
        if (StrUtil.isBlank(userId)) {
            throw AiException.of(AiErrorCode.USER_ID_NOT_NULL, "用户ID不能为空");
        }

        // 查询数据库
        return conversationRepository.findByUserId(userId);
    }

    @Override
    public ConversationAggregate updateConversationTitle(String conversationId, String title) {
        // 参数校验
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }
        if (StrUtil.isBlank(title)) {
            throw AiException.of(AiErrorCode.CONVERSATION_TITLE_EMPTY, "会话标题不能为空");
        }

        // 查找会话
        ConversationAggregate conversation = conversationRepository.findByIdOrThrow(conversationId);

        // 更新会话标题（聚合根本身包含最核心的业务逻辑）
        conversation.updateTitle(title);

        log.info("更新会话标题成功，会话ID: {}, 新标题: {}", conversationId, title);
        return conversation;
    }

    @Override
    public ConversationAggregate updateConversationLastMessageTime(String conversationId) {
        // 参数校验
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }

        // 查找会话
        ConversationAggregate conversation = conversationRepository.findByIdOrThrow(conversationId);

        // 更新最后消息时间（聚合根本身包含最核心的业务逻辑）
        conversation.updateLastMessageTime();

        log.info("更新会话最后消息时间成功，会话ID: {}", conversationId);
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

        // 删除会话（聚合根本身包含最核心的业务逻辑）
        conversation.markAsDeleted();

        log.info("删除会话成功，会话ID: {}, 用户ID: {}", conversationId, userId);
    }
}
