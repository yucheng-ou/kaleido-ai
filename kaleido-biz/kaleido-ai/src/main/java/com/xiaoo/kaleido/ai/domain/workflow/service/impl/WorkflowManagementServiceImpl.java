package com.xiaoo.kaleido.ai.domain.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.service.IWorkflowManagementService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowRepository;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流管理服务实现类
 * <p>
 * 实现工作流管理的核心业务逻辑，遵循DDD原则：
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
public class WorkflowManagementServiceImpl implements IWorkflowManagementService {

    private final IWorkflowRepository workflowRepository;

    @Override
    public WorkflowAggregate createWorkflow(String code, String name, String description, String definition) {
        // 注意：controller层与rpc层已经有注解的参数校验了，service层只需要校验没有被校验过的部分
        if (!isWorkflowCodeUnique(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EXISTS, "工作流编码已存在: " + code);
        }

        WorkflowAggregate workflow = WorkflowAggregate.create(code, name, description, definition);

        log.info("创建工作流成功，工作流ID: {}, 编码: {}", workflow.getId(), code);
        return workflow;
    }

    @Override
    public WorkflowAggregate findWorkflowByIdOrThrow(String workflowId) {
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        return workflowRepository.findByIdOrThrow(workflowId);
    }

    @Override
    public WorkflowAggregate findWorkflowByCodeOrThrow(String code) {
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EMPTY, "工作流编码不能为空");
        }

        return workflowRepository.findByCodeOrThrow(code);
    }

    @Override
    public WorkflowAggregate updateWorkflow(String workflowId, String name, String description, String definition) {
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);
        workflow.updateInfo(name, description, definition);

        log.info("更新工作流成功，工作流ID: {}, 名称: {}", workflowId, name);
        return workflow;
    }

    @Override
    public WorkflowAggregate enableWorkflow(String workflowId) {
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);
        workflow.enable();

        log.info("启用工作流成功，工作流ID: {}", workflowId);
        return workflow;
    }

    @Override
    public WorkflowAggregate disableWorkflow(String workflowId) {
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);
        workflow.disable();

        log.info("禁用工作流成功，工作流ID: {}", workflowId);
        return workflow;
    }

    @Override
    public boolean isWorkflowCodeUnique(String code) {
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EMPTY, "工作流编码不能为空");
        }

        return !workflowRepository.existsByCode(code);
    }


    @Override
    public boolean validateWorkflowDefinition(String definition) {
        if (StrUtil.isBlank(definition)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义不能为空");
        }

        WorkflowAggregate tempWorkflow = WorkflowAggregate.builder()
                .definition(definition)
                .build();

        return tempWorkflow.isValidDefinition();
    }
}
