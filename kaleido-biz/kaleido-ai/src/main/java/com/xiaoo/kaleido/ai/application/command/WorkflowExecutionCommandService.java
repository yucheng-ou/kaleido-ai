package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowExecutionService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowExecutionCommand;
import com.xiaoo.kaleido.api.ai.command.SucceedWorkflowExecutionCommand;
import com.xiaoo.kaleido.api.ai.command.FailWorkflowExecutionCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 工作流执行命令服务
 * <p>
 * 负责编排工作流执行相关的命令操作，包括创建执行记录、更新执行状态等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowExecutionCommandService {

    private final IWorkflowExecutionService workflowExecutionService;
    private final IWorkflowExecutionRepository workflowExecutionRepository;

    /**
     * 创建工作流执行记录
     *
     * @param command 创建工作流执行命令
     * @return 执行ID
     */
    public String createWorkflowExecution(CreateWorkflowExecutionCommand command) {
        // 1.调用领域服务创建工作流执行记录
        WorkflowExecutionAggregate execution = workflowExecutionService.createWorkflowExecution(
                command.getExecutionId(),
                command.getWorkflowId(),
                command.getInputData()
        );

        // 2.保存执行记录
        workflowExecutionRepository.save(execution);

        // 3.记录日志
        log.info("工作流执行记录创建成功，执行ID: {}, 工作流ID: {}", 
                command.getExecutionId(), command.getWorkflowId());
        
        return execution.getId();
    }

    /**
     * 工作流执行成功
     *
     * @param executionId 执行ID
     * @param command 执行成功命令
     */
    public void succeedWorkflowExecution(String executionId, SucceedWorkflowExecutionCommand command) {
        // 1.调用领域服务更新执行状态为成功
        WorkflowExecutionAggregate execution = workflowExecutionService.succeedWorkflowExecution(
                executionId,
                command.getOutputData()
        );

        // 2.保存执行记录
        workflowExecutionRepository.update(execution);

        // 3.记录日志
        log.info("工作流执行成功，执行ID: {}", executionId);
    }

    /**
     * 工作流执行失败
     *
     * @param executionId 执行ID
     * @param command 执行失败命令
     */
    public void failWorkflowExecution(String executionId, FailWorkflowExecutionCommand command) {
        // 1.调用领域服务更新执行状态为失败
        WorkflowExecutionAggregate execution = workflowExecutionService.failWorkflowExecution(
                executionId,
                command.getErrorMessage()
        );

        // 2.保存执行记录
        workflowExecutionRepository.update(execution);

        // 3.记录日志
        log.info("工作流执行失败，执行ID: {}, 错误信息: {}", executionId, command.getErrorMessage());
    }

    /**
     * 更新工作流执行进度
     *
     * @param executionId 执行ID
     * @param outputData 输出数据
     */
    public void updateWorkflowExecutionProgress(String executionId, String outputData) {
        // 1.调用领域服务更新执行进度
        WorkflowExecutionAggregate execution = workflowExecutionService.updateWorkflowExecutionProgress(
                executionId,
                outputData
        );

        // 2.保存执行记录
        workflowExecutionRepository.update(execution);

        // 3.记录日志
        log.info("工作流执行进度更新，执行ID: {}", executionId);
    }
}
