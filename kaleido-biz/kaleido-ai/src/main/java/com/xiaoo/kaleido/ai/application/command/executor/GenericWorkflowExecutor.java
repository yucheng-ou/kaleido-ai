package com.xiaoo.kaleido.ai.application.command.executor;

import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.IWorkflowEventPublisher;
import com.xiaoo.kaleido.ai.domain.workflow.armory.WorkflowFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.api.ai.command.ExecuteWorkflowCommand;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 通用工作流执行器
 * 不发布事件，适用于大多数工作流执行场景
 * 
 * @author ouyucheng
 * @date 2026/2/5
 */
@Getter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GenericWorkflowExecutor extends AbstractWorkflowExecutor {
    
    private ExecuteWorkflowCommand command;
    
    private String userId;
    
    @Autowired
    public GenericWorkflowExecutor(
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
    public void setExecutionParams(ExecuteWorkflowCommand command, String userId) {
        this.command = command;
        this.userId = userId;
    }
    
    @Override
    protected String getWorkflowIdentifier() {
        return command.getWorkflowId();
    }
    
    @Override
    protected String getInputData() {
        return command.getInputData();
    }
    
    @Override
    protected String getUserId() {
        return userId;
    }
    
    @Override
    protected WorkflowAggregate findWorkflow(String workflowId) {
        return workflowManagementService.findWorkflowByIdOrThrow(workflowId);
    }
    
    // 不重写onSuccess和onFailure方法，使用父类的空实现（不发布事件）
}
