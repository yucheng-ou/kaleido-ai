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
        // 1.查询工作流
        WorkflowAggregate workflow = workflowRepository.findById(workflowId);
        
        // 2.转换为响应对象
        return workflow != null ? workflowConvertor.toResponse(workflow) : null;
    }

    @Override
    public WorkflowInfoResponse findByCode(String code) {
        // 1.查询工作流
        WorkflowAggregate workflow = workflowRepository.findByCode(code);
        
        // 2.转换为响应对象
        return workflow != null ? workflowConvertor.toResponse(workflow) : null;
    }

    @Override
    public List<WorkflowInfoResponse> findAll() {
        // 1.查询所有未被删除的工作流列表
        List<WorkflowAggregate> workflowAggregates = workflowRepository.findAllNotDeleted();

        // 2.转换为响应对象
        return workflowAggregates.stream()
                .map(workflowConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
