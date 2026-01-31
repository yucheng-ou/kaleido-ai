package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.service.IWorkflowManagementService;
import com.xiaoo.kaleido.ai.domain.adapter.repository.IWorkflowRepository;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateWorkflowCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRotY;
import org.springframework.stereotype.Service;

/**
 * 工作流命令服务
 * <p>
 * 负责编排工作流相关的命令操作，包括创建、更新、启用、禁用等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
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
}
