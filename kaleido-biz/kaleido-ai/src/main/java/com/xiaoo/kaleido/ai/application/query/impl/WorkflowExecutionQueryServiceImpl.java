package com.xiaoo.kaleido.ai.application.query.impl;

import com.xiaoo.kaleido.api.ai.response.WorkflowExecutionInfoResponse;
import com.xiaoo.kaleido.ai.application.convertor.WorkflowExecutionConvertor;
import com.xiaoo.kaleido.ai.application.query.WorkflowExecutionQueryService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowExecutionRepository;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowExecutionAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工作流执行查询服务实现
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowExecutionQueryServiceImpl implements WorkflowExecutionQueryService {

    private final IWorkflowExecutionRepository workflowExecutionRepository;
    private final WorkflowExecutionConvertor workflowExecutionConvertor;

    @Override
    public WorkflowExecutionInfoResponse findById(String executionId) {
        // 1.参数校验
        Objects.requireNonNull(executionId, "executionId不能为空");
        
        // 2.查询工作流执行
        WorkflowExecutionAggregate execution = workflowExecutionRepository.findById(executionId);
        
        // 3.转换为响应对象
        return execution != null ? workflowExecutionConvertor.toResponse(execution) : null;
    }

    @Override
    public List<WorkflowExecutionInfoResponse> findByWorkflowId(String workflowId) {
        // 1.参数校验
        Objects.requireNonNull(workflowId, "workflowId不能为空");
        
        // 2.查询工作流执行列表
        List<WorkflowExecutionAggregate> executions = workflowExecutionRepository.findByWorkflowId(workflowId);
        
        // 3.转换为响应对象列表
        return executions.stream()
                .map(workflowExecutionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkflowExecutionInfoResponse> findByStatus(String status) {
        // 1.参数校验
        Objects.requireNonNull(status, "status不能为空");
        
        // 2.查询工作流执行列表
        List<WorkflowExecutionAggregate> executions = workflowExecutionRepository.findByStatus(status);
        
        // 3.转换为响应对象列表
        return executions.stream()
                .map(workflowExecutionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkflowExecutionInfoResponse> findByIds(List<String> executionIds) {
        // 1.参数校验
        Objects.requireNonNull(executionIds, "executionIds不能为空");
        
        // 2.如果列表为空，直接返回空列表
        if (CollectionUtils.isEmpty(executionIds)) {
            return Collections.emptyList();
        }
        
        // 3.查询工作流执行列表
        // 注意：IWorkflowExecutionRepository 没有直接的 findByIds 方法，需要实现
        // 这里暂时使用循环查询，实际项目中应该实现批量查询方法
        List<WorkflowExecutionAggregate> executions = executionIds.stream()
                .map(workflowExecutionRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
        // 4.转换为响应对象列表
        return executions.stream()
                .map(workflowExecutionConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
