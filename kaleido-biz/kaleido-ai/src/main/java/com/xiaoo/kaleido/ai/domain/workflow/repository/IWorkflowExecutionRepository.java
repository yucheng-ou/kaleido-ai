package com.xiaoo.kaleido.ai.domain.workflow.repository;

import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;

import java.util.List;

/**
 * 工作流执行仓储接口
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IWorkflowExecutionRepository {

    /**
     * 保存工作流执行聚合根
     * <p>
     * 保存工作流执行聚合根到数据库，如果是新执行记录则插入，如果是已存在执行记录则更新
     *
     * @param executionAggregate 工作流执行聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void save(WorkflowExecutionAggregate executionAggregate);

    /**
     * 更新工作流执行聚合根
     * <p>
     * 更新工作流执行聚合根信息到数据库
     *
     * @param executionAggregate 工作流执行聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当更新失败或执行记录不存在时抛出
     */
    void update(WorkflowExecutionAggregate executionAggregate);

    /**
     * 根据ID查找工作流执行聚合根
     * <p>
     * 根据ID查询工作流执行聚合根
     *
     * @param id 执行记录ID，不能为空
     * @return 工作流执行聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    WorkflowExecutionAggregate findById(String id);

    /**
     * 根据ID查找工作流执行聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保执行记录存在的场景，如果执行记录不存在则抛出异常
     *
     * @param id 执行记录ID，不能为空
     * @return 工作流执行聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当执行记录不存在时抛出
     */
    WorkflowExecutionAggregate findByIdOrThrow(String id);

    /**
     * 根据工作流ID查找执行记录
     * <p>
     * 查询指定工作流的所有执行记录
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流执行聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<WorkflowExecutionAggregate> findByWorkflowId(String workflowId);

    /**
     * 根据状态查找工作流执行记录
     * <p>
     * 查询指定状态的所有执行记录
     *
     * @param status 执行状态，不能为空
     * @return 工作流执行聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<WorkflowExecutionAggregate> findByStatus(String status);

    /**
     * 根据用户ID查找工作流执行记录
     * <p>
     * 查询指定用户的所有执行记录
     *
     * @param userId 用户ID，不能为空
     * @return 工作流执行聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<WorkflowExecutionAggregate> findByUserId(String userId);

}
