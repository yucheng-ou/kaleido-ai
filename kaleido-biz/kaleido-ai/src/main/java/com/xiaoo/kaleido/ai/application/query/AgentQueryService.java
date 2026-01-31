package com.xiaoo.kaleido.ai.application.query;

import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;

import java.util.List;

/**
 * Agent查询服务接口
 * <p>
 * Agent应用层查询服务，负责Agent相关的读操作，包括Agent信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface AgentQueryService {

    /**
     * 根据ID查询Agent信息
     * <p>
     * 根据Agent ID查询Agent详细信息，如果Agent不存在则返回null
     *
     * @param agentId Agent ID，不能为空
     * @return Agent信息响应，如果Agent不存在则返回null
     */
    AgentInfoResponse findById(String agentId);

    /**
     * 根据编码查询Agent信息
     * <p>
     * 根据Agent编码查询Agent详细信息，如果Agent不存在则返回null
     *
     * @param code Agent编码，不能为空
     * @return Agent信息响应，如果Agent不存在则返回null
     */
    AgentInfoResponse findByCode(String code);

    /**
     * 查询所有未被删除的Agent
     * <p>
     * 查询所有未被删除的Agent列表
     *
     * @return Agent信息响应列表，如果不存在则返回空列表
     */
    List<AgentInfoResponse> findAll();
}
