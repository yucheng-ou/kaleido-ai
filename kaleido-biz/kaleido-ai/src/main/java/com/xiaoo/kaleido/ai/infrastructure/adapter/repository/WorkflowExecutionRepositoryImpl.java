package com.xiaoo.kaleido.ai.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.ai.domain.adapter.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.model.aggregate.WorkflowExecutionAggregate;
import com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor.WorkflowExecutionInfraConvertor;
import com.xiaoo.kaleido.ai.infrastructure.dao.WorkflowExecutionDao;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowExecutionPO;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 工作流执行仓储实现（基础设施层）
 * <p>
 * 工作流执行仓储接口的具体实现，负责工作流执行聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class WorkflowExecutionRepositoryImpl implements IWorkflowExecutionRepository {

    private final WorkflowExecutionDao workflowExecutionDao;

    @Override
    public void save(WorkflowExecutionAggregate executionAggregate) {
        // 1.转换WorkflowExecutionAggregate为WorkflowExecutionPO
        WorkflowExecutionPO executionPO = WorkflowExecutionInfraConvertor.INSTANCE.toPO(executionAggregate);

        // 2.保存工作流执行基本信息
        workflowExecutionDao.insert(executionPO);

        log.info("工作流执行保存成功，执行ID: {}, 工作流ID: {}, 状态: {}",
                executionAggregate.getExecutionId(), executionAggregate.getWorkflowId(), executionAggregate.getStatus());
    }

    @Override
    public void update(WorkflowExecutionAggregate executionAggregate) {
        // 1.转换WorkflowExecutionAggregate为WorkflowExecutionPO
        WorkflowExecutionPO executionPO = WorkflowExecutionInfraConvertor.INSTANCE.toPO(executionAggregate);

        // 2.更新工作流执行基本信息
        workflowExecutionDao.updateById(executionPO);

        log.info("工作流执行更新成功，执行ID: {}, 工作流ID: {}, 状态: {}",
                executionAggregate.getExecutionId(), executionAggregate.getWorkflowId(), executionAggregate.getStatus());
    }

    @Override
    public WorkflowExecutionAggregate findById(String executionId) {
        try {
            // 1.查询工作流执行基本信息
            WorkflowExecutionPO executionPO = workflowExecutionDao.findByExecutionId(executionId);
            if (executionPO == null) {
                return null;
            }

            // 2.转换为WorkflowExecutionAggregate
            WorkflowExecutionAggregate executionAggregate = WorkflowExecutionInfraConvertor.INSTANCE.toAggregate(executionPO);

            return executionAggregate;
        } catch (Exception e) {
            log.error("查询工作流执行失败，执行ID: {}, 原因: {}", executionId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_EXECUTION_QUERY_FAIL);
        }
    }

    @Override
    public WorkflowExecutionAggregate findByIdOrThrow(String executionId) {
        WorkflowExecutionAggregate execution = findById(executionId);
        if (execution == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return execution;
    }

    @Override
    public List<WorkflowExecutionAggregate> findByWorkflowId(String workflowId) {
        try {
            // 1.查询工作流执行基本信息列表
            List<WorkflowExecutionPO> executionPOs = workflowExecutionDao.findByWorkflowId(workflowId);

            // 2.转换为WorkflowExecutionAggregate列表
            return WorkflowExecutionInfraConvertor.INSTANCE.toAggregateList(executionPOs);
        } catch (Exception e) {
            log.error("查询工作流执行记录失败，工作流ID: {}, 原因: {}", workflowId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_EXECUTION_QUERY_FAIL);
        }
    }

    @Override
    public List<WorkflowExecutionAggregate> findByStatus(String status) {
        try {
            // 1.查询工作流执行基本信息列表
            List<WorkflowExecutionPO> executionPOs = workflowExecutionDao.findByStatus(status);

            // 2.转换为WorkflowExecutionAggregate列表
            return WorkflowExecutionInfraConvertor.INSTANCE.toAggregateList(executionPOs);
        } catch (Exception e) {
            log.error("根据状态查询工作流执行记录失败，状态: {}, 原因: {}", status, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_EXECUTION_QUERY_FAIL);
        }
    }



}
