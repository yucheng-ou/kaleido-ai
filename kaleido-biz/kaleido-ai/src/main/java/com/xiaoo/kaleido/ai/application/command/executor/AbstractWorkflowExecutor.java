package com.xiaoo.kaleido.ai.application.command.executor;

import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.IWorkflowEventPublisher;
import com.xiaoo.kaleido.ai.domain.workflow.armory.WorkflowFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 工作流执行器抽象模板类
 * 使用模板方法模式定义工作流执行的算法骨架
 * 
 * @author ouyucheng
 * @date 2026/2/5
 */
@Slf4j
public abstract class AbstractWorkflowExecutor {
    
    // 依赖注入
    protected final IWorkflowManagementService workflowManagementService;
    protected final WorkflowFactory workflowFactory;
    protected final IWorkflowExecutionService workflowExecutionService;
    protected final IWorkflowExecutionRepository workflowExecutionRepository;
    protected final IWorkflowEventPublisher workflowEventPublisher;
    
    public AbstractWorkflowExecutor(
            IWorkflowManagementService workflowManagementService,
            WorkflowFactory workflowFactory,
            IWorkflowExecutionService workflowExecutionService,
            IWorkflowExecutionRepository workflowExecutionRepository,
            IWorkflowEventPublisher workflowEventPublisher) {
        this.workflowManagementService = workflowManagementService;
        this.workflowFactory = workflowFactory;
        this.workflowExecutionService = workflowExecutionService;
        this.workflowExecutionRepository = workflowExecutionRepository;
        this.workflowEventPublisher = workflowEventPublisher;
    }
    
    // 抽象方法 - 由子类实现
    protected abstract String getWorkflowIdentifier();
    protected abstract String getInputData();
    protected abstract String getUserId();
    protected abstract WorkflowAggregate findWorkflow(String workflowIdentifier);
    
    // 钩子方法 - 子类可以重写（默认空实现）
    protected void onSuccess(String executionId, String workflowId, String userId, String result) {
        // 默认不执行任何操作
    }
    
    protected void onFailure(String executionId, String workflowId, String userId, String errorMessage) {
        // 默认不执行任何操作
    }
    
    // 模板方法 - 定义算法骨架（final防止子类修改算法结构）
    public final String execute() {
        // 1. 获取工作流标识
        String workflowIdentifier = getWorkflowIdentifier();
        
        // 2. 查找工作流
        WorkflowAggregate workflow = findWorkflow(workflowIdentifier);
        
        // 3. 检查工作流是否启用
        validateWorkflowEnabled(workflow, workflowIdentifier);
        
        // 4. 确保工作流已注册
        ensureWorkflowRegistered(workflow);
        
        // 5. 创建工作流执行记录
        WorkflowExecutionAggregate execution = createWorkflowExecution(workflow);
        String executionId = execution.getId();
        
        // 6. 异步执行工作流
        executeAsync(workflow, executionId);
        
        // 7. 记录开始执行日志
        logStartExecution(executionId, workflow);
        
        return executionId;
    }
    
    // 具体步骤实现（protected允许子类访问）
    protected void validateWorkflowEnabled(WorkflowAggregate workflow, String workflowIdentifier) {
        if (!workflow.isEnabled()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                "工作流已禁用，无法执行: " + workflowIdentifier);
        }
    }
    
    protected void ensureWorkflowRegistered(WorkflowAggregate workflow) {
        if (!workflowFactory.isWorkflowRegistered(workflow.getId())) {
            workflowFactory.registerWorkflow(workflow);
        }
    }
    
    protected WorkflowExecutionAggregate createWorkflowExecution(WorkflowAggregate workflow) {
        WorkflowExecutionAggregate execution = workflowExecutionService.createWorkflowExecution(
                workflow.getId(),
                getInputData(),
                getUserId()
        );
        workflowExecutionRepository.save(execution);
        return execution;
    }
    
    protected void executeAsync(WorkflowAggregate workflow, String executionId) {
        CompletableFuture.runAsync(() -> {
            try {
                String result = workflowFactory.executeWorkflow(
                        workflow.getId(),
                        getInputData(),
                        getUserId()
                );
                
                // 更新执行状态为成功
                updateExecutionToSuccess(executionId, result);
                
                // 调用成功钩子方法
                onSuccess(executionId, workflow.getId(), getUserId(), result);
                
                logSuccess(executionId, workflow);
            } catch (Exception e) {
                // 更新执行状态为失败
                updateExecutionToFailure(executionId, e.getMessage());
                
                // 调用失败钩子方法
                onFailure(executionId, workflow.getId(), getUserId(), e.getMessage());
                
                logFailure(executionId, workflow, e);
            }
        });
    }
    
    // 辅助方法
    protected void updateExecutionToSuccess(String executionId, String result) {
        WorkflowExecutionAggregate execution = 
            workflowExecutionService.succeedWorkflowExecution(executionId, result);
        workflowExecutionRepository.update(execution);
    }
    
    protected void updateExecutionToFailure(String executionId, String errorMessage) {
        WorkflowExecutionAggregate execution = 
            workflowExecutionService.failWorkflowExecution(executionId, errorMessage);
        workflowExecutionRepository.update(execution);
    }
    
    protected void logStartExecution(String executionId, WorkflowAggregate workflow) {
        log.info("工作流开始异步执行，执行记录ID: {}, 工作流ID: {}, 用户ID: {}",
                executionId, workflow.getId(), getUserId());
    }
    
    protected void logSuccess(String executionId, WorkflowAggregate workflow) {
        log.info("工作流执行成功，执行记录ID: {}, 工作流ID: {}, 用户ID: {}",
                executionId, workflow.getId(), getUserId());
    }
    
    protected void logFailure(String executionId, WorkflowAggregate workflow, Exception e) {
        log.error("工作流执行失败，执行记录ID: {}, 工作流ID: {}, 用户ID: {}, 错误: {}",
                executionId, workflow.getId(), getUserId(), e.getMessage(), e);
    }
}
