package com.xiaoo.kaleido.ai.application.query;

import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;

import java.util.List;

/**
 * 工作流查询服务接口
 * <p>
 * 工作流应用层查询服务，负责工作流相关的读操作，包括工作流信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface WorkflowQueryService {

    /**
     * 根据ID查询工作流信息
     * <p>
     * 根据工作流ID查询工作流详细信息，如果工作流不存在则返回null
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流信息响应，如果工作流不存在则返回null
     */
    WorkflowInfoResponse findById(String workflowId);

    /**
     * 根据编码查询工作流信息
     * <p>
     * 根据工作流编码查询工作流详细信息，如果工作流不存在则返回null
     *
     * @param code 工作流编码，不能为空
     * @return 工作流信息响应，如果工作流不存在则返回null
     */
    WorkflowInfoResponse findByCode(String code);
}
