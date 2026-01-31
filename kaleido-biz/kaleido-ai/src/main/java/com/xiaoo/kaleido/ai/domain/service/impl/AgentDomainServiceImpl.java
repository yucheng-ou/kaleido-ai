package com.xiaoo.kaleido.ai.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.ai.domain.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.model.entity.AgentTool;
import com.xiaoo.kaleido.api.ai.enums.ToolType;
import com.xiaoo.kaleido.ai.domain.service.IAgentDomainService;
import com.xiaoo.kaleido.ai.domain.adapter.repository.IAgentRepository;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Agent领域服务实现类
 * <p>
 * 实现Agent领域服务的核心业务逻辑，遵循DDD原则：
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
public class AgentDomainServiceImpl implements IAgentDomainService {

    private final IAgentRepository agentRepository;

    @Override
    public AgentAggregate createAgent(
            String code,
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens) {

        // 校验编码唯一性
        if (!isAgentCodeUnique(code)) {
            throw AiException.of(AiErrorCode.AGENT_CODE_EXISTS, "Agent编码已存在: " + code);
        }

        // 创建聚合根
        AgentAggregate agent = AgentAggregate.create(
                code, name, description, systemPrompt, modelName, temperature, maxTokens
        );

        log.info("创建Agent成功，Agent ID: {}, 编码: {}", agent.getId(), code);
        return agent;
    }

    @Override
    public AgentAggregate findByIdOrThrow(String agentId) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }

        // 查询数据库
        return agentRepository.findByIdOrThrow(agentId);
    }

    @Override
    public AgentAggregate findByCodeOrThrow(String code) {
        // 参数校验
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.AGENT_CODE_EMPTY, "Agent编码不能为空");
        }

        // 查询数据库
        return agentRepository.findByCodeOrThrow(code);
    }

    @Override
    public AgentAggregate updateAgent(
            String agentId,
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }

        // 查找Agent
        AgentAggregate agent = agentRepository.findByIdOrThrow(agentId);

        // 更新聚合根信息（聚合根本身包含最核心的业务逻辑）
        agent.updateInfo(name, description, systemPrompt, modelName, temperature, maxTokens);

        log.info("更新Agent成功，Agent ID: {}, 新名称: {}", agentId, name);
        return agent;
    }

    @Override
    public AgentAggregate enableAgent(String agentId) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }

        // 查找Agent
        AgentAggregate agent = agentRepository.findByIdOrThrow(agentId);

        // 启用Agent（聚合根本身包含最核心的业务逻辑）
        agent.enable();

        log.info("启用Agent成功，Agent ID: {}", agentId);
        return agent;
    }

    @Override
    public AgentAggregate disableAgent(String agentId) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }

        // 查找Agent
        AgentAggregate agent = agentRepository.findByIdOrThrow(agentId);

        // 禁用Agent（聚合根本身包含最核心的业务逻辑）
        agent.disable();

        log.info("禁用Agent成功，Agent ID: {}", agentId);
        return agent;
    }

    @Override
    public AgentTool addTool(
            String agentId,
            String toolCode,
            String toolName,
            ToolType toolType,
            String toolConfig) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }
        if (StrUtil.isBlank(toolCode)) {
            throw AiException.of(AiErrorCode.TOOL_CODE_EMPTY, "工具编码不能为空");
        }
        if (StrUtil.isBlank(toolName)) {
            throw AiException.of(AiErrorCode.TOOL_NAME_EMPTY, "工具名称不能为空");
        }
        if (toolType == null) {
            throw AiException.of(AiErrorCode.TOOL_TYPE_EMPTY, "工具类型不能为空");
        }

        // 查找Agent
        AgentAggregate agent = agentRepository.findByIdOrThrow(agentId);

        // 创建AgentTool对象
        AgentTool agentTool = AgentTool.create(agentId, toolCode, toolName, toolType, toolConfig);

        // 添加工具到Agent（聚合根本身包含最核心的业务逻辑）
        agent.addTool(agentTool);

        log.info("添加工具到Agent成功，Agent ID: {}, 工具编码: {}", agentId, toolCode);
        return agentTool;
    }

    @Override
    public AgentAggregate removeTool(String agentId, String toolCode) {
        // 参数校验
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }
        if (StrUtil.isBlank(toolCode)) {
            throw AiException.of(AiErrorCode.TOOL_CODE_EMPTY, "工具编码不能为空");
        }

        // 查找Agent
        AgentAggregate agent = agentRepository.findByIdOrThrow(agentId);

        // 从Agent移除工具（聚合根本身包含最核心的业务逻辑）
        agent.removeTool(toolCode);

        log.info("从Agent移除工具成功，Agent ID: {}, 工具编码: {}", agentId, toolCode);
        return agent;
    }

    @Override
    public boolean isAgentCodeUnique(String code) {
        // 参数校验
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.AGENT_CODE_EMPTY, "Agent编码不能为空");
        }

        // 查询数据库进行参数校验
        return !agentRepository.existsByCode(code);
    }
}
