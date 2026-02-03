package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.application.convertor.ConversationConvertor;
import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import com.xiaoo.kaleido.ai.domain.chat.service.IConversationDomainService;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.api.ai.command.UpdateConversationTitleCommand;
import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;
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
    private final ConversationConvertor conversationConvertor;

    /**
     * 创建会话
     *
     * @param userId 用户ID
     * @return 会话信息响应
     */
    public ConversationInfoResponse createConversation(String userId) {
        // 1.调用领域服务创建会话（传空值，让聚合根生成ID和设置默认标题）
        ConversationAggregate conversation = chatDomainService.createConversation(userId);

        // 2.保存会话
        conversationRepository.save(conversation);

        // 3.记录日志
        log.info("会话创建成功，会话ID: {}, 用户ID: {}", conversation.getConversationId(), userId);

        // 4.转换为响应对象
        return conversationConvertor.toResponse(conversation);
    }

    /**
     * 更新会话标题
     *
     * @param conversationId 会话ID
     * @param command        更新会话标题命令
     * @param userId         用户ID
     */
    public void updateConversationTitle(String conversationId, UpdateConversationTitleCommand command, String userId) {
        // 1.调用领域服务更新会话标题
        ConversationAggregate conversation = chatDomainService.updateConversationTitle(
                conversationId,
                command.getTitle(),
                userId
        );

        // 2.保存会话
        conversationRepository.update(conversation);

        // 3.记录日志
        log.info("会话标题更新成功，会话ID: {}, 新标题: {}, 用户ID: {}", conversationId, command.getTitle(), userId);
    }

    /**
     * 删除会话
     *
     * @param conversationId 会话ID
     * @param userId         用户ID
     */
    public void deleteConversation(String conversationId, String userId) {
        // 1.调用领域服务删除会话
        chatDomainService.deleteConversation(conversationId, userId);

        // 2.删除会话
        conversationRepository.delete(conversationId);

        // 3.记录日志
        log.info("会话删除成功，会话ID: {}, 用户ID: {}", conversationId, userId);
    }
}
