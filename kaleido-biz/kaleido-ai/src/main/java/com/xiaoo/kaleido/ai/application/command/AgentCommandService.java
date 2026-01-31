package com.xiaoo.kaleido.ai.application.command;

import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.ai.domain.agent.model.vo.AgentStatus;
import com.xiaoo.kaleido.ai.domain.agent.service.IAgentDomainService;
import com.xiaoo.kaleido.ai.domain.agent.adapter.repository.IAgentRepository;
import com.xiaoo.kaleido.api.ai.command.CreateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.AddAgentToolCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Agent命令服务
 * <p>
 * 负责编排Agent相关的命令操作，包括创建、更新、启用、禁用、添加工具等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentCommandService {

    private final IAgentDomainService agentDomainService;
    private final IAgentRepository agentRepository;
    private final AgentFactory agentFactory;

    /**
     * 创建Agent
     *
     * @param command 创建Agent命令
     * @return Agent ID
     */
    public String createAgent(CreateAgentCommand command) {
        // 1.调用领域服务创建Agent
        AgentAggregate agent = agentDomainService.createAgent(
                command.getCode(),
                command.getName(),
                command.getDescription(),
                command.getSystemPrompt(),
                command.getModelName(),
                command.getTemperature(),
                command.getMaxTokens()
        );

        // 2.保存Agent
        agentRepository.save(agent);

        // 3.如果Agent状态为NORMAL，则注册到注册中心
        if (agent.getStatus() == AgentStatus.NORMAL) {
            agentFactory.registerAgent(agent.getId());
        }

        // 4.记录日志
        log.info("Agent创建成功，Agent ID: {}, 编码: {}", agent.getId(), command.getCode());
        
        return agent.getId();
    }

    /**
     * 更新Agent
     *
     * @param agentId Agent ID
     * @param command 更新Agent命令
     */
    public void updateAgent(String agentId, UpdateAgentCommand command) {
        // 1.获取旧的Agent状态
        AgentAggregate oldAgent = agentRepository.findByIdOrThrow(agentId);
        AgentStatus oldStatus = oldAgent.getStatus();

        // 2.调用领域服务更新Agent
        AgentAggregate agent = agentDomainService.updateAgent(
                agentId,
                command.getName(),
                command.getDescription(),
                command.getSystemPrompt(),
                command.getModelName(),
                command.getTemperature(),
                command.getMaxTokens()
        );

        // 3.保存Agent
        agentRepository.update(agent);

        // 4.处理注册状态变化
        handleRegistrationStatusChange(agentId, oldStatus, agent.getStatus());

        // 5.记录日志
        log.info("Agent更新成功，Agent ID: {}, 新名称: {}", agentId, command.getName());
    }

    /**
     * 启用Agent
     *
     * @param agentId Agent ID
     */
    public void enableAgent(String agentId) {
        // 1.调用领域服务启用Agent
        AgentAggregate agent = agentDomainService.enableAgent(agentId);

        // 2.保存Agent
        agentRepository.update(agent);

        // 3.注册Agent到注册中心
        agentFactory.registerAgent(agentId);

        // 4.记录日志
        log.info("Agent启用成功，Agent ID: {}", agentId);
    }

    /**
     * 禁用Agent
     *
     * @param agentId Agent ID
     */
    public void disableAgent(String agentId) {
        // 1.调用领域服务禁用Agent
        AgentAggregate agent = agentDomainService.disableAgent(agentId);

        // 2.保存Agent
        agentRepository.update(agent);

        // 3.从注册中心注销Agent
        agentFactory.unregisterAgent(agentId);

        // 4.记录日志
        log.info("Agent禁用成功，Agent ID: {}", agentId);
    }

    /**
     * 添加工具到Agent
     *
     * @param agentId Agent ID
     * @param command 添加工具命令
     */
    public void addAgentTool(String agentId, AddAgentToolCommand command) {
        // 1.调用领域服务添加工具，直接返回创建的AgentTool对象
        AgentTool newTool = agentDomainService.addTool(
                agentId,
                command.getToolCode(),
                command.getToolName(),
                command.getToolType(),
                command.getToolConfig()
        );

        // 2.保存工具到数据库
        agentRepository.addTool(newTool);

        // 3.记录日志
        log.info("工具添加到Agent成功，Agent ID: {}, 工具编码: {}", agentId, command.getToolCode());
    }

    /**
     * 从Agent移除工具
     *
     * @param agentId  Agent ID
     * @param toolCode 工具编码
     */
    public void removeAgentTool(String agentId, String toolCode) {
        // 1.调用领域服务移除工具（验证工具存在并执行业务逻辑）
        AgentAggregate agent = agentDomainService.removeTool(agentId, toolCode);

        // 2.从数据库中删除工具
        agentRepository.removeTool(agentId, toolCode);

        // 3.刷新Agent配置（因为工具配置发生了变化）
        if (agent.getStatus() == AgentStatus.NORMAL) {
            agentFactory.refreshAgent(agentId);
        }

        // 4.记录日志
        log.info("工具从Agent移除成功，Agent ID: {}, 工具编码: {}", agentId, toolCode);
    }

    /**
     * 处理注册状态变化
     *
     * @param agentId   Agent ID
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     */
    private void handleRegistrationStatusChange(String agentId, AgentStatus oldStatus, AgentStatus newStatus) {
        if (oldStatus == newStatus) {
            // 状态未变化，如果状态为NORMAL则刷新配置
            if (newStatus == AgentStatus.NORMAL) {
                agentFactory.refreshAgent(agentId);
            }
            return;
        }

        // 状态发生变化
        if (newStatus == AgentStatus.NORMAL) {
            // 从其他状态变为NORMAL，注册Agent
            agentFactory.registerAgent(agentId);
        } else if (oldStatus == AgentStatus.NORMAL) {
            // 从NORMAL变为其他状态，注销Agent
            agentFactory.unregisterAgent(agentId);
        }
    }
}
