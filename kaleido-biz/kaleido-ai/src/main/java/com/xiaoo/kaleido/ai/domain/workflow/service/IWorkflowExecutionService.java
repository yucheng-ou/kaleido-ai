package com.xiaoo.kaleido.ai.domain.workflow.service;

import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.api.ai.command.ExecuteWorkflowCommand;

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
     * @param workflowId  工作流ID，不能为空
     * @param inputData   输入数据，可为空
     * @param userId      用户ID，不能为空
     * @return 工作流执行聚合根
     */
    WorkflowExecutionAggregate createWorkflowExecution(
            String workflowId,
            String inputData,
            String userId);

    /**
     * 根据ID查找工作流执行记录
     *
     * @param id 执行记录ID，不能为空
     * @return 工作流执行聚合根
     */
    WorkflowExecutionAggregate findWorkflowExecutionByIdOrThrow(String id);

    /**
     * 更新工作流执行状态为成功
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param id         执行记录ID，不能为空
     * @param outputData 输出数据，可为空
     * @return 更新后的工作流执行聚合根
     */
    WorkflowExecutionAggregate succeedWorkflowExecution(String id, String outputData);

    /**
     * 更新工作流执行状态为失败
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param id           执行记录ID，不能为空
     * @param errorMessage 错误信息，不能为空
     * @return 更新后的工作流执行聚合根
     */
    WorkflowExecutionAggregate failWorkflowExecution(String id, String errorMessage);

    /**
     * 更新工作流执行进度
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param id         执行记录ID，不能为空
     * @param outputData 输出数据，可为空
     * @return 更新后的工作流执行聚合根
     */
    WorkflowExecutionAggregate updateWorkflowExecutionProgress(String id, String outputData);

    /**
     * 根据工作流ID查找执行记录
     * <p>
     * 可以查询数据库进行参数校验
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流执行聚合根列表
     */
    List<WorkflowExecutionAggregate> findWorkflowExecutionsByWorkflowId(String workflowId);

    /**
     * 根据状态查找工作流执行记录
     * <p>
     * 可以查询数据库进行参数校验
     *
     * @param status 执行状态，不能为空
     * @return 工作流执行聚合根列表
     */
    List<WorkflowExecutionAggregate> findWorkflowExecutionsByStatus(String status);


}
