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
        // 这里校验业务规则，比如编码唯一性
        if (!isWorkflowCodeUnique(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EXISTS, "工作流编码已存在: " + code);
        }

        // 创建聚合根（聚合根本身包含最核心的业务逻辑，不包含参数校验）
        WorkflowAggregate workflow = WorkflowAggregate.create(code, name, description, definition);

        log.info("创建工作流成功，工作流ID: {}, 编码: {}", workflow.getId(), code);
        return workflow;
    }

    @Override
    public WorkflowAggregate findWorkflowByIdOrThrow(String workflowId) {
        // 参数校验
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        // 查询数据库
        return workflowRepository.findByIdOrThrow(workflowId);
    }

    @Override
    public WorkflowAggregate findWorkflowByCodeOrThrow(String code) {
        // 参数校验
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EMPTY, "工作流编码不能为空");
        }

        // 查询数据库
        return workflowRepository.findByCodeOrThrow(code);
    }

    @Override
    public WorkflowAggregate updateWorkflow(String workflowId, String name, String description, String definition) {
        // 参数校验
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        // 查找工作流
        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);

        // 更新聚合根信息（聚合根本身包含最核心的业务逻辑）
        workflow.updateInfo(name, description, definition);

        log.info("更新工作流成功，工作流ID: {}, 名称: {}", workflowId, name);
        return workflow;
    }

    @Override
    public WorkflowAggregate enableWorkflow(String workflowId) {
        // 参数校验
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        // 查找工作流
        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);

        // 启用工作流（聚合根本身包含最核心的业务逻辑）
        workflow.enable();

        log.info("启用工作流成功，工作流ID: {}", workflowId);
        return workflow;
    }

    @Override
    public WorkflowAggregate disableWorkflow(String workflowId) {
        // 参数校验
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        // 查找工作流
        WorkflowAggregate workflow = workflowRepository.findByIdOrThrow(workflowId);

        // 禁用工作流（聚合根本身包含最核心的业务逻辑）
        workflow.disable();

        log.info("禁用工作流成功，工作流ID: {}", workflowId);
        return workflow;
    }

    @Override
    public boolean isWorkflowCodeUnique(String code) {
        // 参数校验
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EMPTY, "工作流编码不能为空");
        }

        // 查询数据库进行参数校验
        return !workflowRepository.existsByCode(code);
    }

    @Override
    public List<WorkflowAggregate> findAllEnabledWorkflows() {
        // 直接查询数据库
        return workflowRepository.findAllEnabled();
    }

    @Override
    public boolean validateWorkflowDefinition(String definition) {
        // 参数校验
        if (StrUtil.isBlank(definition)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义不能为空");
        }

        // 创建临时聚合根进行验证
        WorkflowAggregate tempWorkflow = WorkflowAggregate.builder()
                .definition(definition)
                .build();

        // 使用聚合根的方法进行验证（聚合根本身包含最核心的业务逻辑）
        return tempWorkflow.isValidDefinition();
    }
}
