package com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.ai.domain.chat.model.aggregate.ConversationAggregate;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.ConversationPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 会话基础设施层转换器
 * <p>
 * 负责ConversationAggregate和ConversationPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface ConversationInfraConvertor {

    ConversationInfraConvertor INSTANCE = Mappers.getMapper(ConversationInfraConvertor.class);

    /**
     * ConversationAggregate转换为ConversationPO
     *
     * @param aggregate 会话聚合根
     * @return 会话持久化对象
     */
    ConversationPO toPO(ConversationAggregate aggregate);

    /**
     * ConversationPO转换为ConversationAggregate
     *
     * @param po 会话持久化对象
     * @return 会话聚合根
     */
    ConversationAggregate toAggregate(ConversationPO po);
}
