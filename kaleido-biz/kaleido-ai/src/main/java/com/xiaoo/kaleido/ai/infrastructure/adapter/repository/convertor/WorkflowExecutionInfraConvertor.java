package com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowExecutionPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 工作流执行基础设施层转换器
 * <p>
 * 负责WorkflowExecutionAggregate和WorkflowExecutionPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface WorkflowExecutionInfraConvertor {

    WorkflowExecutionInfraConvertor INSTANCE = Mappers.getMapper(WorkflowExecutionInfraConvertor.class);

    /**
     * WorkflowExecutionAggregate转换为WorkflowExecutionPO
     *
     * @param aggregate 工作流执行聚合根
     * @return 工作流执行持久化对象
     */
    WorkflowExecutionPO toPO(WorkflowExecutionAggregate aggregate);

    /**
     * WorkflowExecutionPO转换为WorkflowExecutionAggregate
     *
     * @param po 工作流执行持久化对象
     * @return 工作流执行聚合根
     */
    WorkflowExecutionAggregate toAggregate(WorkflowExecutionPO po);

    /**
     * WorkflowExecutionAggregate列表转换为WorkflowExecutionPO列表
     *
     * @param aggregates 工作流执行聚合根列表
     * @return 工作流执行持久化对象列表
     */
    List<WorkflowExecutionPO> toPOList(List<WorkflowExecutionAggregate> aggregates);

    /**
     * WorkflowExecutionPO列表转换为WorkflowExecutionAggregate列表
     *
     * @param pos 工作流执行持久化对象列表
     * @return 工作流执行聚合根列表
     */
    List<WorkflowExecutionAggregate> toAggregateList(List<WorkflowExecutionPO> pos);
}
