package com.xiaoo.kaleido.ai.application.query;

import com.xiaoo.kaleido.api.ai.response.WorkflowExecutionInfoResponse;

import java.util.List;

/**
 * 工作流执行查询服务接口
 * <p>
 * 工作流执行应用层查询服务，负责工作流执行相关的读操作，包括执行信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface WorkflowExecutionQueryService {

    /**
     * 根据ID查询工作流执行信息
     * <p>
     * 根据执行ID查询工作流执行详细信息，如果执行不存在则返回null
     *
     * @param executionId 执行ID，不能为空
     * @return 工作流执行信息响应，如果执行不存在则返回null
     */
    WorkflowExecutionInfoResponse findById(String executionId);

    /**
     * 根据工作流ID查询执行记录列表
     * <p>
     * 查询指定工作流的所有执行记录
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流执行信息响应列表，如果不存在则返回空列表
     */
    List<WorkflowExecutionInfoResponse> findByWorkflowId(String workflowId);

    /**
     * 根据状态查询工作流执行记录
     * <p>
     * 查询指定状态的所有执行记录
     *
     * @param status 执行状态，不能为空
     * @return 工作流执行信息响应列表，如果不存在则返回空列表
     */
    List<WorkflowExecutionInfoResponse> findByStatus(String status);

    /**
     * 根据ID列表查询工作流执行信息列表
     * <p>
     * 根据执行ID列表查询对应的工作流执行信息列表
     *
     * @param executionIds 执行ID列表，不能为空
     * @return 工作流执行信息响应列表，如果不存在则返回空列表
     */
    List<WorkflowExecutionInfoResponse> findByIds(List<String> executionIds);
}
