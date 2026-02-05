package com.xiaoo.kaleido.ai.application.command.executor;

import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.IWorkflowEventPublisher;
import com.xiaoo.kaleido.ai.domain.workflow.armory.WorkflowFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 服装推荐工作流执行器
 * 发布事件，用于触发后续的推荐记录处理
 * 
 * @author ouyucheng
 * @date 2026/2/5
 */
@Getter
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OutfitRecommendWorkflowExecutor extends AbstractWorkflowExecutor {
    
    private String prompt;
    
    private String userId;
    
    @Autowired
    public OutfitRecommendWorkflowExecutor(
            IWorkflowManagementService workflowManagementService,
            WorkflowFactory workflowFactory,
            IWorkflowExecutionService workflowExecutionService,
            IWorkflowExecutionRepository workflowExecutionRepository,
            IWorkflowEventPublisher workflowEventPublisher) {
        super(workflowManagementService, workflowFactory, workflowExecutionService, 
              workflowExecutionRepository, workflowEventPublisher);
    }
    
    /**
     * 设置执行参数
     */
    public void setExecutionParams(String prompt, String userId) {
        this.prompt = prompt;
        this.userId = userId;
    }
    
    @Override
    protected String getWorkflowIdentifier() {
        return "OUTFIT_RECOMMEND";
    }
    
    @Override
    protected String getInputData() {
        return prompt;
    }
    
    @Override
    protected String getUserId() {
        return userId;
    }
    
    @Override
    protected WorkflowAggregate findWorkflow(String workflowCode) {
        return workflowManagementService.findWorkflowByCodeOrThrow(workflowCode);
    }
    
    @Override
    protected void onSuccess(String executionId, String workflowId, String userId, String result) {
        // 发布穿搭推荐执行成功事件
        workflowEventPublisher.publishOutfitRecommendCompletedEvent(
                executionId,
                workflowId,
                userId,
                WorkflowExecutionStatusEnum.SUCCESS,
                result,
                null
        );
    }
    
    @Override
    protected void onFailure(String executionId, String workflowId, String userId, String errorMessage) {
        // 发布穿搭推荐执行失败事件
        workflowEventPublisher.publishOutfitRecommendCompletedEvent(
                executionId,
                workflowId,
                userId,
                WorkflowExecutionStatusEnum.FAILED,
                null,
                errorMessage
        );
    }
    
    @Override
    protected void logStartExecution(String executionId, WorkflowAggregate workflow) {
        log.info("服装推荐工作流开始异步执行，执行记录ID: {}, 工作流Code: {}, 用户ID: {}",
                executionId, "OUTFIT_RECOMMEND", getUserId());
    }
    
    @Override
    protected void logSuccess(String executionId, WorkflowAggregate workflow) {
        log.info("服装推荐工作流执行成功，执行记录ID: {}, 工作流ID: {}, 用户ID: {}",
                executionId, workflow.getId(), getUserId());
    }
    
    @Override
    protected void logFailure(String executionId, WorkflowAggregate workflow, Exception e) {
        log.error("服装推荐工作流执行失败，执行记录ID: {}, 工作流ID: {}, 用户ID: {}, 错误: {}",
                executionId, workflow.getId(), getUserId(), e.getMessage(), e);
    }
}
