package com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 工作流基础设施层转换器
 * <p>
 * 负责WorkflowAggregate和WorkflowPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface WorkflowInfraConvertor {

    WorkflowInfraConvertor INSTANCE = Mappers.getMapper(WorkflowInfraConvertor.class);

    /**
     * WorkflowAggregate转换为WorkflowPO
     *
     * @param aggregate 工作流聚合根
     * @return 工作流持久化对象
     */
    WorkflowPO toPO(WorkflowAggregate aggregate);

    /**
     * WorkflowPO转换为WorkflowAggregate
     *
     * @param po 工作流持久化对象
     * @return 工作流聚合根
     */
    WorkflowAggregate toAggregate(WorkflowPO po);
}
