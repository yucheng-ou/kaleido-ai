package com.xiaoo.kaleido.ai.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.ai.domain.chat.adapter.repository.IConversationRepository;
import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor.ConversationInfraConvertor;
import com.xiaoo.kaleido.ai.infrastructure.dao.ConversationDao;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.ConversationPO;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会话仓储实现（基础设施层）
 * <p>
 * 会话仓储接口的具体实现，负责会话聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ConversationRepositoryImpl implements IConversationRepository {

    private final ConversationDao conversationDao;

    @Override
    public void save(ConversationAggregate conversationAggregate) {
        // 1.转换ConversationAggregate为ConversationPO
        ConversationPO conversationPO = ConversationInfraConvertor.INSTANCE.toPO(conversationAggregate);

        // 2.保存会话基本信息
        conversationDao.insert(conversationPO);

        log.info("会话保存成功，会话ID: {}, 用户ID: {}, 标题: {}", conversationAggregate.getConversationId(), conversationAggregate.getUserId(), conversationAggregate.getTitle());
    }

    @Override
    public void update(ConversationAggregate conversationAggregate) {
        // 1.转换ConversationAggregate为ConversationPO
        ConversationPO conversationPO = ConversationInfraConvertor.INSTANCE.toPO(conversationAggregate);

        // 2.更新会话基本信息
        conversationDao.updateById(conversationPO);

        log.info("会话更新成功，会话ID: {}, 用户ID: {}, 标题: {}", conversationAggregate.getConversationId(), conversationAggregate.getUserId(), conversationAggregate.getTitle());
    }

    @Override
    public ConversationAggregate findById(String conversationId) {
        try {
            // 1.查询会话基本信息
            ConversationPO conversationPO = conversationDao.findByConversationId(conversationId);
            if (conversationPO == null) {
                return null;
            }

            // 2.转换为ConversationAggregate
            return ConversationInfraConvertor.INSTANCE.toAggregate(conversationPO);
        } catch (Exception e) {
            log.error("查询会话失败，会话ID: {}, 原因: {}", conversationId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.CONVERSATION_QUERY_FAIL);
        }
    }

    @Override
    public ConversationAggregate findByIdOrThrow(String conversationId) {
        ConversationAggregate conversation = findById(conversationId);
        if (conversation == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return conversation;
    }

    @Override
    public List<ConversationAggregate> findByUserId(String userId) {
        try {
            // 1.查询用户的所有会话
            List<ConversationPO> conversationPOs = conversationDao.findByUserId(userId);

            // 2.转换为ConversationAggregate列表
            return conversationPOs.stream().map(ConversationInfraConvertor.INSTANCE::toAggregate).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户会话失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.CONVERSATION_QUERY_FAIL);
        }
    }

    @Override
    public boolean existsById(String conversationId) {
        try {
            // 1.查询会话基本信息
            ConversationPO conversationPO = conversationDao.findByConversationId(conversationId);

            // 2.返回是否存在
            return conversationPO != null;
        } catch (Exception e) {
            log.error("检查会话是否存在失败，会话ID: {}, 原因: {}", conversationId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.CONVERSATION_QUERY_FAIL);
        }
    }

    @Override
    public void delete(String conversationId) {
        conversationDao.deleteById(conversationId);
    }
}
