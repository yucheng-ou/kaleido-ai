package com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.ai.domain.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Agent基础设施层转换器
 * <p>
 * 负责AgentAggregate和AgentPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface AgentInfraConvertor {

    AgentInfraConvertor INSTANCE = Mappers.getMapper(AgentInfraConvertor.class);

    /**
     * AgentAggregate转换为AgentPO
     *
     * @param aggregate Agent聚合根
     * @return Agent持久化对象
     */
    AgentPO toPO(AgentAggregate aggregate);

    /**
     * AgentPO转换为AgentAggregate
     *
     * @param po Agent持久化对象
     * @return Agent聚合根
     */
    @Mapping(target = "tools", ignore = true)
    AgentAggregate toAggregate(AgentPO po);
}
