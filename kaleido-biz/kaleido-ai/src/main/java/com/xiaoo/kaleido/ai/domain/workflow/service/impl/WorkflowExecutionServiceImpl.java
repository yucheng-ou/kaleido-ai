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
    public WorkflowExecutionAggregate createWorkflowExecution(String workflowId, String inputData, String userId) {
        WorkflowAggregate workflow = workflowManagementService.findWorkflowByIdOrThrow(workflowId);
        
        if (!workflow.isEnabled()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流已禁用，无法执行: " + workflowId);
        }

        WorkflowExecutionAggregate execution = WorkflowExecutionAggregate.create(workflowId, inputData, userId);

        log.info("创建工作流执行记录成功，工作流ID: {}, 用户ID: {}", workflowId, userId);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate findWorkflowExecutionByIdOrThrow(String id) {
        if (StrUtil.isBlank(id)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行记录ID不能为空");
        }

        return workflowExecutionRepository.findByIdOrThrow(id);
    }

    @Override
    public WorkflowExecutionAggregate succeedWorkflowExecution(String id, String outputData) {
        if (StrUtil.isBlank(id)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行记录ID不能为空");
        }

        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(id);
        execution.succeed(outputData);

        log.info("工作流执行成功，执行记录ID: {}", id);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate failWorkflowExecution(String id, String errorMessage) {
        if (StrUtil.isBlank(id)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行记录ID不能为空");
        }
        if (StrUtil.isBlank(errorMessage)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "错误信息不能为空");
        }

        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(id);
        execution.fail(errorMessage);

        log.info("工作流执行失败，执行记录ID: {}, 错误信息: {}", id, errorMessage);
        return execution;
    }

    @Override
    public WorkflowExecutionAggregate updateWorkflowExecutionProgress(String id, String outputData) {
        if (StrUtil.isBlank(id)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行记录ID不能为空");
        }

        WorkflowExecutionAggregate execution = workflowExecutionRepository.findByIdOrThrow(id);
        execution.updateProgress(outputData);

        log.info("更新工作流执行进度，执行记录ID: {}", id);
        return execution;
    }

    @Override
    public List<WorkflowExecutionAggregate> findWorkflowExecutionsByWorkflowId(String workflowId) {
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        workflowManagementService.findWorkflowByIdOrThrow(workflowId);

        return workflowExecutionRepository.findByWorkflowId(workflowId);
    }

    @Override
    public List<WorkflowExecutionAggregate> findWorkflowExecutionsByStatus(String status) {
        if (StrUtil.isBlank(status)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "执行状态不能为空");
        }

        return workflowExecutionRepository.findByStatus(status);
    }
}
