package com.xiaoo.kaleido.ai.application.convertor;

import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.ai.domain.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.model.entity.AgentTool;
import com.xiaoo.kaleido.api.ai.response.AgentToolResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Agent转换器
 * <p>
 * Agent应用层转换器，负责Agent领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper(componentModel = "spring")
public interface AgentConvertor {

    AgentConvertor INSTANCE = Mappers.getMapper(AgentConvertor.class);

    /**
     * 将Agent聚合根转换为Agent信息响应
     * <p>
     * 将领域层的Agent聚合根转换为应用层的Agent信息响应DTO
     *
     * @param agentAggregate Agent聚合根，不能为空
     * @return Agent信息响应，包含Agent的基本信息和状态
     */
    @Mapping(source = "id", target = "agentId")
    @Mapping(source = "status", target = "status")
    AgentInfoResponse toResponse(AgentAggregate agentAggregate);

    /**
     * 将Agent工具实体转换为Agent工具响应
     *
     * @param agentTool Agent工具实体，不能为空
     * @return Agent工具响应
     */
    AgentToolResponse toToolResponse(AgentTool agentTool);

    /**
     * 将Agent工具实体列表转换为Agent工具响应列表
     *
     * @param agentTools Agent工具实体列表，不能为空
     * @return Agent工具响应列表
     */
    List<AgentToolResponse> toToolResponses(List<AgentTool> agentTools);
}
