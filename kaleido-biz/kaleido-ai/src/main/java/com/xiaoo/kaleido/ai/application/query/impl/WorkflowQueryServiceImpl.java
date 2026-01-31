package com.xiaoo.kaleido.ai.application.query.impl;

import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.ai.application.convertor.WorkflowConvertor;
import com.xiaoo.kaleido.ai.application.query.WorkflowQueryService;
import com.xiaoo.kaleido.ai.domain.workflow.repository.IWorkflowRepository;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工作流查询服务实现
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowQueryServiceImpl implements WorkflowQueryService {

    private final IWorkflowRepository workflowRepository;
    private final WorkflowConvertor workflowConvertor;

    @Override
    public WorkflowInfoResponse findById(String workflowId) {
        // 1.参数校验
        Objects.requireNonNull(workflowId, "workflowId不能为空");
        
        // 2.查询工作流
        WorkflowAggregate workflow = workflowRepository.findById(workflowId);
        
        // 3.转换为响应对象
        return workflow != null ? workflowConvertor.toResponse(workflow) : null;
    }

    @Override
    public WorkflowInfoResponse findByCode(String code) {
        // 1.参数校验
        Objects.requireNonNull(code, "code不能为空");
        
        // 2.查询工作流
        WorkflowAggregate workflow = workflowRepository.findByCode(code);
        
        // 3.转换为响应对象
        return workflow != null ? workflowConvertor.toResponse(workflow) : null;
    }

    @Override
    public List<WorkflowInfoResponse> findAllEnabled() {
        // 1.查询所有启用的工作流
        List<WorkflowAggregate> workflows = workflowRepository.findAllEnabled();
        
        // 2.转换为响应对象列表
        return workflows.stream()
                .map(workflowConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkflowInfoResponse> findByIds(List<String> workflowIds) {
        // 1.参数校验
        Objects.requireNonNull(workflowIds, "workflowIds不能为空");
        
        // 2.如果列表为空，直接返回空列表
        if (CollectionUtils.isEmpty(workflowIds)) {
            return Collections.emptyList();
        }
        
        // 3.查询工作流列表
        // 注意：IWorkflowRepository 没有直接的 findByIds 方法，需要实现
        // 这里暂时使用循环查询，实际项目中应该实现批量查询方法
        List<WorkflowAggregate> workflows = workflowIds.stream()
                .map(workflowRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
        // 4.转换为响应对象列表
        return workflows.stream()
                .map(workflowConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
