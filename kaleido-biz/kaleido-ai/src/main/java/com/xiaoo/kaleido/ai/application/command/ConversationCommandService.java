package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import com.xiaoo.kaleido.ai.domain.chat.service.IConversationDomainService;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.api.ai.command.CreateConversationCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateConversationTitleCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 聊天命令服务
 * <p>
 * 负责编排聊天相关的命令操作，包括创建会话、更新会话标题、删除会话等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationCommandService {

    private final IConversationDomainService chatDomainService;
    private final IConversationRepository conversationRepository;

    /**
     * 创建会话
     *
     * @param command 创建会话命令
     * @return 会话ID
     */
    public String createConversation(String userId, CreateConversationCommand command) {
        // 1.调用领域服务创建会话
        ConversationAggregate conversation = chatDomainService.createConversation(
                command.getConversationId(),
                userId,
                command.getTitle()
        );

        // 2.保存会话
        conversationRepository.save(conversation);

        // 3.记录日志
        log.info("会话创建成功，会话ID: {}, 用户ID: {}", command.getConversationId(), userId);
        
        return conversation.getId();
    }

    /**
     * 更新会话标题
     *
     * @param conversationId 会话ID
     * @param command 更新会话标题命令
     */
    public void updateConversationTitle(String conversationId, UpdateConversationTitleCommand command) {
        // 1.调用领域服务更新会话标题
        ConversationAggregate conversation = chatDomainService.updateConversationTitle(
                conversationId,
                command.getTitle()
        );

        // 2.保存会话
        conversationRepository.update(conversation);

        // 3.记录日志
        log.info("会话标题更新成功，会话ID: {}, 新标题: {}", conversationId, command.getTitle());
    }

    /**
     * 更新会话最后消息时间
     *
     * @param conversationId 会话ID
     */
    public void updateConversationLastMessageTime(String conversationId) {
        // 1.调用领域服务更新会话最后消息时间
        ConversationAggregate conversation = chatDomainService.updateConversationLastMessageTime(conversationId);

        // 2.保存会话
        conversationRepository.update(conversation);

        // 3.记录日志
        log.info("会话最后消息时间更新成功，会话ID: {}", conversationId);
    }

    /**
     * 删除会话
     *
     * @param conversationId 会话ID
     * @param userId 用户ID
     */
    public void deleteConversation(String conversationId, String userId) {
        // 1.调用领域服务删除会话
        chatDomainService.deleteConversation(conversationId, userId);

        // 2.保存会话（领域服务已经标记为删除，这里需要更新）
        ConversationAggregate conversation = conversationRepository.findByIdOrThrow(conversationId);
        conversationRepository.update(conversation);

        // 3.记录日志
        log.info("会话删除成功，会话ID: {}, 用户ID: {}", conversationId, userId);
    }
}
