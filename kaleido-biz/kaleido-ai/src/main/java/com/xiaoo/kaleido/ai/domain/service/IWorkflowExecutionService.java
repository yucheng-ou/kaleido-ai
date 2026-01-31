package com.xiaoo.kaleido.ai.domain.service;

import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowExecutionAggregate;

import java.util.List;

/**
 * 工作流执行服务接口
 * <p>
 * 定义工作流执行的核心业务逻辑，处理工作流执行的创建、状态更新、查询等操作
 * 遵循DDD原则，service层包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IWorkflowExecutionService {

    /**
     * 创建工作流执行记录
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param executionId 执行ID（业务唯一），不能为空
     * @param workflowId  工作流ID，不能为空
     * @param inputData   输入数据，可为空
     * @return 工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当工作流不存在时抛出
     */
    WorkflowExecutionAggregate createWorkflowExecution(
            String executionId,
            String workflowId,
            String inputData);

    /**
     * 根据执行ID查找工作流执行记录
     *
     * @param executionId 执行ID（业务唯一），不能为空
     * @return 工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当执行记录不存在时抛出
     */
    WorkflowExecutionAggregate findWorkflowExecutionByIdOrThrow(String executionId);

    /**
     * 更新工作流执行状态为成功
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param executionId 执行ID（业务唯一），不能为空
     * @param outputData  输出数据，可为空
     * @return 更新后的工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当执行记录不存在时抛出
     */
    WorkflowExecutionAggregate succeedWorkflowExecution(String executionId, String outputData);

    /**
     * 更新工作流执行状态为失败
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param executionId  执行ID（业务唯一），不能为空
     * @param errorMessage 错误信息，不能为空
     * @return 更新后的工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当执行记录不存在时抛出
     */
    WorkflowExecutionAggregate failWorkflowExecution(String executionId, String errorMessage);

    /**
     * 更新工作流执行进度
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param executionId 执行ID（业务唯一），不能为空
     * @param outputData  输出数据，可为空
     * @return 更新后的工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当执行记录不存在时抛出
     */
    WorkflowExecutionAggregate updateWorkflowExecutionProgress(String executionId, String outputData);

    /**
     * 根据工作流ID查找执行记录
     * <p>
     * 可以查询数据库进行参数校验
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流执行聚合根列表
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    List<WorkflowExecutionAggregate> findWorkflowExecutionsByWorkflowId(String workflowId);

    /**
     * 根据状态查找工作流执行记录
     * <p>
     * 可以查询数据库进行参数校验
     *
     * @param status 执行状态，不能为空
     * @return 工作流执行聚合根列表
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    List<WorkflowExecutionAggregate> findWorkflowExecutionsByStatus(String status);


}
