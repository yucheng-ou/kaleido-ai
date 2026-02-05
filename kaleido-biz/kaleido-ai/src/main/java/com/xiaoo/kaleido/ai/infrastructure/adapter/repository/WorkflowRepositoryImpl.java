package com.xiaoo.kaleido.ai.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowRepository;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor.WorkflowInfraConvertor;
import com.xiaoo.kaleido.ai.infrastructure.dao.WorkflowDao;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowPO;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作流仓储实现（基础设施层）
 * <p>
 * 工作流仓储接口的具体实现，负责工作流聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class WorkflowRepositoryImpl implements IWorkflowRepository {

    private final WorkflowDao workflowDao;

    @Override
    public void save(WorkflowAggregate workflowAggregate) {
        // 1.转换WorkflowAggregate为WorkflowPO
        WorkflowPO workflowPO = WorkflowInfraConvertor.INSTANCE.toPO(workflowAggregate);

        // 2.保存工作流基本信息
        workflowDao.insert(workflowPO);

        log.info("工作流保存成功，工作流ID: {}, 工作流编码: {}, 工作流名称: {}",
                workflowAggregate.getId(), workflowAggregate.getCode(), workflowAggregate.getName());
    }

    @Override
    public void update(WorkflowAggregate workflowAggregate) {
        // 1.转换WorkflowAggregate为WorkflowPO
        WorkflowPO workflowPO = WorkflowInfraConvertor.INSTANCE.toPO(workflowAggregate);

        // 2.更新工作流基本信息
        workflowDao.updateById(workflowPO);

        log.info("工作流更新成功，工作流ID: {}, 工作流编码: {}, 工作流名称: {}",
                workflowAggregate.getId(), workflowAggregate.getCode(), workflowAggregate.getName());
    }

    @Override
    public WorkflowAggregate findById(String workflowId) {
        try {
            // 1.查询工作流基本信息
            WorkflowPO workflowPO = workflowDao.findById(workflowId);
            if (workflowPO == null) {
                return null;
            }

            // 2.转换为WorkflowAggregate
            return WorkflowInfraConvertor.INSTANCE.toAggregate(workflowPO);
        } catch (Exception e) {
            log.error("查询工作流失败，工作流ID: {}, 原因: {}", workflowId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_QUERY_FAIL);
        }
    }

    @Override
    public WorkflowAggregate findByCode(String code) {
        try {
            // 1.查询工作流基本信息
            WorkflowPO workflowPO = workflowDao.findByCode(code);
            if (workflowPO == null) {
                return null;
            }

            // 2.转换为WorkflowAggregate
            return WorkflowInfraConvertor.INSTANCE.toAggregate(workflowPO);
        } catch (Exception e) {
            log.error("查询工作流失败，工作流编码: {}, 原因: {}", code, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_QUERY_FAIL);
        }
    }

    @Override
    public WorkflowAggregate findByIdOrThrow(String workflowId) {
        WorkflowAggregate workflow = findById(workflowId);
        if (workflow == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return workflow;
    }

    @Override
    public WorkflowAggregate findByCodeOrThrow(String code) {
        WorkflowAggregate workflow = findByCode(code);
        if (workflow == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return workflow;
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            return workflowDao.existsByCode(code);
        } catch (Exception e) {
            log.error("检查工作流编码唯一性失败，工作流编码: {}, 原因: {}", code, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_QUERY_FAIL);
        }
    }

    @Override
    public List<WorkflowAggregate> findAllNotDeleted() {
        try {
            // 1.查询所有未被删除的工作流基本信息
            List<WorkflowPO> workflowPOs = workflowDao.findAllNotDeleted();

            // 2.转换为WorkflowAggregate列表
            return workflowPOs.stream()
                    .map(WorkflowInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询所有未被删除的工作流失败，原因: {}", e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_QUERY_FAIL);
        }
    }
}
