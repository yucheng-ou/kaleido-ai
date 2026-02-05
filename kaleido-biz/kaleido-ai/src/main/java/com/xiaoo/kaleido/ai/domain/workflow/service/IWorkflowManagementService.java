package com.xiaoo.kaleido.ai.domain.workflow.service;

import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;

import java.util.List;

/**
 * 工作流管理服务接口
 * <p>
 * 定义工作流管理的核心业务逻辑，处理工作流的创建、更新、状态管理等操作
 * 遵循DDD原则，service层包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IWorkflowManagementService {

    /**
     * 创建工作流
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param code        工作流编码，不能为空
     * @param name        工作流名称，不能为空
     * @param description 工作流描述，可为空
     * @param definition  工作流DSL定义，不能为空
     * @return 工作流聚合根
     */
    WorkflowAggregate createWorkflow(
            String code,
            String name,
            String description,
            String definition);

    /**
     * 根据ID查找工作流
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流聚合根
     */
    WorkflowAggregate findWorkflowByIdOrThrow(String workflowId);

    /**
     * 根据编码查找工作流
     *
     * @param code 工作流编码，不能为空
     * @return 工作流聚合根
     */
    WorkflowAggregate findWorkflowByCodeOrThrow(String code);

    /**
     * 更新工作流信息
     * <p>
     * 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
     *
     * @param workflowId  工作流ID，不能为空
     * @param name        新工作流名称，不能为空
     * @param description 新工作流描述，可为空
     * @param definition  新工作流定义，不能为空
     * @return 更新后的工作流聚合根
     */
    WorkflowAggregate updateWorkflow(
            String workflowId,
            String name,
            String description,
            String definition);

    /**
     * 启用工作流
     *
     * @param workflowId 工作流ID，不能为空
     * @return 启用后的工作流聚合根
     */
    WorkflowAggregate enableWorkflow(String workflowId);

    /**
     * 禁用工作流
     *
     * @param workflowId 工作流ID，不能为空
     * @return 禁用后的工作流聚合根
     */
    WorkflowAggregate disableWorkflow(String workflowId);

    /**
     * 检查工作流编码是否唯一
     * <p>
     * 可以查询数据库进行参数校验
     *
     * @param code 工作流编码，不能为空
     * @return 如果工作流编码唯一返回true，否则返回false
     */
    boolean isWorkflowCodeUnique(String code);

    /**
     * 验证工作流定义格式
     * <p>
     * 业务规则验证，可以查询数据库进行参数校验
     *
     * @param definition 工作流定义，不能为空
     * @return 如果工作流定义格式有效返回true，否则返回false
     */
    boolean validateWorkflowDefinition(String definition);

}
