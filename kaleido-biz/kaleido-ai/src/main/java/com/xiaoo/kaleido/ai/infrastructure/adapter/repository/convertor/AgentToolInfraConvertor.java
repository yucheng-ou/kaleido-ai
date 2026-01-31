package com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentToolPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Agent工具基础设施层转换器
 * <p>
 * 负责AgentTool和AgentToolPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface AgentToolInfraConvertor {

    AgentToolInfraConvertor INSTANCE = Mappers.getMapper(AgentToolInfraConvertor.class);

    /**
     * AgentTool转换为AgentToolPO
     *
     * @param tool Agent工具实体
     * @return Agent工具持久化对象
     */
    AgentToolPO toPO(AgentTool tool);

    /**
     * AgentToolPO转换为AgentTool
     *
     * @param po Agent工具持久化对象
     * @return Agent工具实体
     */
    AgentTool toEntity(AgentToolPO po);

    /**
     * AgentTool列表转换为AgentToolPO列表
     *
     * @param tools Agent工具实体列表
     * @return Agent工具持久化对象列表
     */
    List<AgentToolPO> toPOList(List<AgentTool> tools);

    /**
     * AgentToolPO列表转换为AgentTool列表
     *
     * @param pos Agent工具持久化对象列表
     * @return Agent工具实体列表
     */
    List<AgentTool> toEntityList(List<AgentToolPO> pos);
}
