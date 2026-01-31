package com.xiaoo.kaleido.ai.application.query.impl;

import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.ai.application.convertor.AgentConvertor;
import com.xiaoo.kaleido.ai.application.query.AgentQueryService;
import com.xiaoo.kaleido.ai.domain.adapter.repository.IAgentRepository;
import com.xiaoo.kaleido.ai.domain.model.aggregate.AgentAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Agent查询服务实现
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentQueryServiceImpl implements AgentQueryService {

    private final IAgentRepository agentRepository;
    private final AgentConvertor agentConvertor;

    @Override
    public AgentInfoResponse findById(String agentId) {
        // 1.参数校验
        Objects.requireNonNull(agentId, "agentId不能为空");
        
        // 2.查询Agent
        AgentAggregate agentAggregate = agentRepository.findById(agentId);

        //3.数据转换
        return agentConvertor.toResponse(agentAggregate);
    }

    @Override
    public AgentInfoResponse findByCode(String code) {
        // 1.参数校验
        Objects.requireNonNull(code, "code不能为空");
        
        // 2.查询Agent
        AgentAggregate agentAggregate = agentRepository.findByCode(code);

        //3.数据转换
        return agentConvertor.toResponse(agentAggregate);
    }

    @Override
    public List<AgentInfoResponse> findAll() {
        // 1.查询所有未被删除的Agent列表
        List<AgentAggregate> agentAggregates = agentRepository.findAllNotDeleted();

        // 2.转换为响应对象
        return agentAggregates.stream()
                .map(agentConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
