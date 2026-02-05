package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.application.command.executor.GenericWorkflowExecutor;
import com.xiaoo.kaleido.ai.application.command.executor.OutfitRecommendWorkflowExecutor;
import com.xiaoo.kaleido.ai.application.command.executor.WorkflowExecutorFactory;
import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.IWorkflowEventPublisher;
import com.xiaoo.kaleido.ai.domain.workflow.armory.WorkflowFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowRepository;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.ExecuteWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 工作流命令服务
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowCommandService {

    private final IWorkflowManagementService workflowManagementService;
    private final IWorkflowRepository workflowRepository;
    private final IWorkflowExecutionService workflowExecutionService;
    private final IWorkflowExecutionRepository workflowExecutionRepository;
    private final WorkflowFactory workflowFactory;
    private final IWorkflowEventPublisher workflowEventPublisher;
    private final WorkflowExecutorFactory executorFactory;

    /**
     * 创建工作流
     *
     * @param command 创建工作流命令
     * @return 工作流ID
     */
    public String createWorkflow(CreateWorkflowCommand command) {
        // 1.调用领域服务创建工作流
        WorkflowAggregate workflow = workflowManagementService.createWorkflow(
                command.getCode(),
                command.getName(),
                command.getDescription(),
                command.getDefinition()
        );

        // 2.保存工作流
        workflowRepository.save(workflow);

        // 3.记录日志
        log.info("工作流创建成功，工作流ID: {}, 编码: {}", workflow.getId(), command.getCode());
        
        return workflow.getId();
    }

    /**
     * 更新工作流
     *
     * @param workflowId 工作流ID
     * @param command 更新工作流命令
     */
    public void updateWorkflow(String workflowId, UpdateWorkflowCommand command) {
        // 1.调用领域服务更新工作流
        WorkflowAggregate workflow = workflowManagementService.updateWorkflow(
                workflowId,
                command.getName(),
                command.getDescription(),
                command.getDefinition()
        );

        // 2.保存工作流
        workflowRepository.update(workflow);

        // 3.记录日志
        log.info("工作流更新成功，工作流ID: {}, 新名称: {}", workflowId, command.getName());
    }

    /**
     * 启用工作流
     *
     * @param workflowId 工作流ID
     */
    public void enableWorkflow(String workflowId) {
        // 1.调用领域服务启用工作流
        WorkflowAggregate workflow = workflowManagementService.enableWorkflow(workflowId);

        // 2.保存工作流
        workflowRepository.update(workflow);

        // 3.记录日志
        log.info("工作流启用成功，工作流ID: {}", workflowId);
    }

    /**
     * 禁用工作流
     *
     * @param workflowId 工作流ID
     */
    public void disableWorkflow(String workflowId) {
        // 1.调用领域服务禁用工作流
        WorkflowAggregate workflow = workflowManagementService.disableWorkflow(workflowId);

        // 2.保存工作流
        workflowRepository.update(workflow);

        // 3.记录日志
        log.info("工作流禁用成功，工作流ID: {}", workflowId);
    }

    /**
     * 执行工作流（异步）
     * <p>
     * 负责编排工作流执行，包括：
     * 1. 创建工作流执行记录
     * 2. 异步调用工作流工厂执行工作流
     * 3. 立即返回执行ID，工作流在后台执行
     * 4. 记录宏观操作日志
     *
     * @param command 执行工作流命令
     * @param userId 用户ID
     * @return 执行记录ID
     */
    public String executeWorkflow(ExecuteWorkflowCommand command, String userId) {
        // 使用模板方法模式，通过执行器工厂创建通用工作流执行器
        GenericWorkflowExecutor executor = executorFactory.createGenericExecutor(command, userId);
        return executor.execute();
    }

    /**
     * 执行服装推荐工作流（异步）
     * <p>
     * 专门用于执行服装推荐工作流，使用固定的工作流编码"OUTFIT_RECOMMEND"
     * 包含事件发布逻辑，用于触发后续的推荐记录处理
     *
     * @param prompt 用户输入的推荐需求提示词
     * @param userId 用户ID
     * @return 执行记录ID
     */
    public String executeOutfitRecommendWorkflow(String prompt, String userId) {
        // 使用模板方法模式，通过执行器工厂创建服装推荐工作流执行器
        OutfitRecommendWorkflowExecutor executor = executorFactory.createOutfitRecommendExecutor(prompt, userId);
        return executor.execute();
    }
}
