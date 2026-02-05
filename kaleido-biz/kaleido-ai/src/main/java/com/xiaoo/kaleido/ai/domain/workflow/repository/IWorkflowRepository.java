package com.xiaoo.kaleido.ai.domain.workflow.repository;

import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;

import java.util.List;

/**
 * 工作流仓储接口
 * <p>
 * 定义工作流聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IWorkflowRepository {

    /**
     * 保存工作流聚合根
     * <p>
     * 保存工作流聚合根到数据库，如果是新工作流则插入，如果是已存在工作流则更新
     *
     * @param workflowAggregate 工作流聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void save(WorkflowAggregate workflowAggregate);

    /**
     * 更新工作流聚合根
     * <p>
     * 更新工作流聚合根信息到数据库
     *
     * @param workflowAggregate 工作流聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当更新失败或工作流不存在时抛出
     */
    void update(WorkflowAggregate workflowAggregate);

    /**
     * 根据ID查找工作流聚合根
     * <p>
     * 根据工作流ID查询工作流聚合根
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    WorkflowAggregate findById(String workflowId);

    /**
     * 根据编码查找工作流聚合根
     * <p>
     * 根据工作流编码查询工作流聚合根
     *
     * @param code 工作流编码，不能为空
     * @return 工作流聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    WorkflowAggregate findByCode(String code);

    /**
     * 根据ID查找工作流聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保工作流存在的场景，如果工作流不存在则抛出异常
     *
     * @param workflowId 工作流ID，不能为空
     * @return 工作流聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当工作流不存在时抛出
     */
    WorkflowAggregate findByIdOrThrow(String workflowId);

    /**
     * 根据编码查找工作流聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保工作流存在的场景，如果工作流不存在则抛出异常
     *
     * @param code 工作流编码，不能为空
     * @return 工作流聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当工作流不存在时抛出
     */
    WorkflowAggregate findByCodeOrThrow(String code);

    /**
     * 检查工作流编码是否唯一
     * <p>
     * 检查工作流编码是否唯一，用于工作流创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_code (code)
     *
     * @param code 工作流编码，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    boolean existsByCode(String code);

    /**
     * 查询所有未被删除的工作流
     * <p>
     * 查询所有未被删除的工作流列表
     *
     * @return 工作流聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    List<WorkflowAggregate> findAllNotDeleted();

}
