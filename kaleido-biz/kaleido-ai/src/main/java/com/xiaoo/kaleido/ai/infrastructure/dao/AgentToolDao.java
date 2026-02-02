package com.xiaoo.kaleido.ai.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentToolPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Agent工具数据访问接口
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface AgentToolDao extends BaseMapper<AgentToolPO> {

    /**
     * 根据Agent ID查询工具列表
     *
     * @param agentId Agent ID
     * @return 工具持久化对象列表
     */
    List<AgentToolPO> findByAgentId(@Param("agentId") String agentId);

    /**
     * 根据Agent ID和工具编码查询工具
     *
     * @param agentId  Agent ID
     * @param toolCode 工具编码
     * @return 工具持久化对象
     */
    AgentToolPO findByAgentIdAndToolCode(@Param("agentId") String agentId, @Param("toolCode") String toolCode);

    /**
     * 根据Agent ID删除所有工具
     *
     * @param agentId Agent ID
     * @return 删除的记录数
     */
    int deleteByAgentId(@Param("agentId") String agentId);

    /**
     * 根据Agent ID和工具编码删除工具
     *
     * @param agentId  Agent ID
     * @param toolCode 工具编码
     * @return 删除的记录数
     */
    int deleteByAgentIdAndToolCode(@Param("agentId") String agentId, @Param("toolCode") String toolCode);

    /**
     * 批量插入工具
     *
     * @param tools 工具列表
     * @return 插入的记录数
     */
    int batchInsert(@Param("tools") List<AgentToolPO> tools);
}
