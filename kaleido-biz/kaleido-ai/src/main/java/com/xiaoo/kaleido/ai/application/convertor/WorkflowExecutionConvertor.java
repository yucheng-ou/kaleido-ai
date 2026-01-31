package com.xiaoo.kaleido.ai.application.convertor;

import com.xiaoo.kaleido.api.ai.response.WorkflowExecutionInfoResponse;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 工作流执行转换器
 * <p>
 * 工作流执行应用层转换器，负责工作流执行领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper(componentModel = "spring")
public interface WorkflowExecutionConvertor {

    WorkflowExecutionConvertor INSTANCE = Mappers.getMapper(WorkflowExecutionConvertor.class);

    /**
     * 将工作流执行聚合根转换为工作流执行信息响应
     * <p>
     * 将领域层的工作流执行聚合根转换为应用层的工作流执行信息响应DTO
     *
     * @param executionAggregate 工作流执行聚合根，不能为空
     * @return 工作流执行信息响应，包含执行的基本信息和状态
     */
    @Mapping(source = "id", target = "executionId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "errorMessage", target = "errorMessage")
    WorkflowExecutionInfoResponse toResponse(WorkflowExecutionAggregate executionAggregate);

    /**
     * 将工作流执行聚合根列表转换为工作流执行信息响应列表
     *
     * @param executionAggregates 工作流执行聚合根列表，不能为空
     * @return 工作流执行信息响应列表
     */
    List<WorkflowExecutionInfoResponse> toResponses(List<WorkflowExecutionAggregate> executionAggregates);
}
