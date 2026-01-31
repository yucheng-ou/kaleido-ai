package com.xiaoo.kaleido.ai.domain.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流执行服务实现类
 * <p>
 * 实现工作流执行的核心业务逻辑，遵循DDD原则：
 * 1. service层包含参数校验与聚合根的修改
 * 2. 可以查询数据库进行参数校验
 * 3. 不能直接调用仓储层写入或更新数据库（通过聚合根的方法修改状态）
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowExecutionServiceImpl implements IWorkflowExecutionService {

    private final IWorkflowExecutionRepository workflowExecutionRepository;
    private final IWorkflowManagementService workflowManagementService;

    @Override
    public WorkflowExecutionAggregate createWorkflowExecution(String executionId, String workflowId, String inputData) {
        // 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
        // 这里校验业务规则，比如工作流是否存在且启用
        WorkflowAggregate workflow = workflowManagementService.findWorkflowByIdOrThrow(workflowId);
        
        if (!workflow.isEnabled()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流已禁用，无法执行: " + workflowId);
        }

        // 创建执行聚合根（聚合根本身包含最核心的业务逻辑，不包含参数校验）
        WorkflowExecutionAggregate execution = WorkflowExecutionAggregate.create(executionId, workflowId, inputData);

        log.info("创建工作流执行记录成功，执行ID: {}, 工作流ID: {}", executionId, workflowId);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate findWorkflowExecutionByIdOrThrow(String executionId) {
        // 参数校验
        if (StrUtil.isBlank(executionId)) {
            throw AiException.of(AiErrorCode.EXECUTION_ID_NOT_NULL, "执行ID不能为空");
        }

        // 查询数据库
        return workflowExecutionRepository.findByIdOrThrow(executionId);
    }

    @Override
    public WorkflowExecutionAggregate succeedWorkflowExecution(String executionId, String outputData) {
        // 参数校验
        if (StrUtil.isBlank(executionId)) {
            throw AiException.of(AiErrorCode.EXECUTION_ID_NOT_NULL, "执行ID不能为空");
        }

        // 查找执行记录
        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(executionId);

        // 更新执行状态为成功（聚合根本身包含最核心的业务逻辑）
        execution.succeed(outputData);

        log.info("工作流执行成功，执行ID: {}", executionId);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate failWorkflowExecution(String executionId, String errorMessage) {
        // 参数校验
        if (StrUtil.isBlank(executionId)) {
            throw AiException.of(AiErrorCode.EXECUTION_ID_NOT_NULL, "执行ID不能为空");
        }
        if (StrUtil.isBlank(errorMessage)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "错误信息不能为空");
        }

        // 查找执行记录
        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(executionId);

        // 更新执行状态为失败（聚合根本身包含最核心的业务逻辑）
        execution.fail(errorMessage);

        log.info("工作流执行失败，执行ID: {}, 错误信息: {}", executionId, errorMessage);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate updateWorkflowExecutionProgress(String executionId, String outputData) {
        // 参数校验
        if (StrUtil.isBlank(executionId)) {
            throw AiException.of(AiErrorCode.EXECUTION_ID_NOT_NULL, "执行ID不能为空");
        }

        // 查找执行记录
        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(executionId);

        // 更新执行进度（聚合根本身包含最核心的业务逻辑）
        execution.updateProgress(outputData);

        log.info("更新工作流执行进度，执行ID: {}", executionId);
        return execution;
    }

    @Override
    public List<WorkflowExecutionAggregate> findWorkflowExecutionsByWorkflowId(String workflowId) {
        // 参数校验
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        // 查询数据库进行参数校验，确保工作流存在
        workflowManagementService.findWorkflowByIdOrThrow(workflowId);

        // 查询执行记录
        return workflowExecutionRepository.findByWorkflowId(workflowId);
    }

    @Override
    public List<WorkflowExecutionAggregate> findWorkflowExecutionsByStatus(String status) {
        // 参数校验
        if (StrUtil.isBlank(status)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行状态不能为空");
        }

        // 查询数据库
        return workflowExecutionRepository.findByStatus(status);
    }
}
