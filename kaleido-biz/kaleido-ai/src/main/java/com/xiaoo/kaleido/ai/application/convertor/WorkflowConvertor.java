package com.xiaoo.kaleido.ai.application.convertor;

import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 工作流转换器
 * <p>
 * 工作流应用层转换器，负责工作流领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper(componentModel = "spring")
public interface WorkflowConvertor {

    WorkflowConvertor INSTANCE = Mappers.getMapper(WorkflowConvertor.class);

    /**
     * 将工作流聚合根转换为工作流信息响应
     * <p>
     * 将领域层的工作流聚合根转换为应用层的工作流信息响应DTO
     *
     * @param workflowAggregate 工作流聚合根，不能为空
     * @return 工作流信息响应，包含工作流的基本信息和状态
     */
    @Mapping(source = "id", target = "workflowId")
    WorkflowInfoResponse toResponse(WorkflowAggregate workflowAggregate);

    /**
     * 将工作流聚合根列表转换为工作流信息响应列表
     *
     * @param workflowAggregates 工作流聚合根列表，不能为空
     * @return 工作流信息响应列表
     */
    List<WorkflowInfoResponse> toResponses(List<WorkflowAggregate> workflowAggregates);
}
