package com.xiaoo.kaleido.ai.trigger.rpc;

import com.xiaoo.kaleido.api.ai.IRpcAiService;
import com.xiaoo.kaleido.api.ai.command.CreateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.AddAgentToolCommand;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.ai.application.command.AgentCommandService;
import com.xiaoo.kaleido.ai.application.command.WorkflowCommandService;
import com.xiaoo.kaleido.ai.application.query.AgentQueryService;
import com.xiaoo.kaleido.ai.application.query.WorkflowQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * AI RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = "1.0.0")
public class RpcAiServiceImpl implements IRpcAiService {

    private final AgentCommandService agentCommandService;
    private final WorkflowCommandService workflowCommandService;
    private final AgentQueryService agentQueryService;
    private final WorkflowQueryService workflowQueryService;

    @Override
    public Result<String> createAgent(@NotBlank String userId, @Valid CreateAgentCommand command) {
        String agentId = agentCommandService.createAgent(command);
        log.info("RPC创建Agent成功，管理员ID: {}, Agent ID: {}", userId, agentId);
        return Result.success(agentId);
    }

    @Override
    public Result<Void> updateAgent(@NotBlank String userId, @NotBlank String agentId, @Valid UpdateAgentCommand command) {
        agentCommandService.updateAgent(agentId, command);
        log.info("RPC更新Agent成功，管理员ID: {}, Agent ID: {}", userId, agentId);
        return Result.success();
    }

    @Override
    public Result<Void> enableAgent(@NotBlank String userId, @NotBlank String agentId) {
        agentCommandService.enableAgent(agentId);
        log.info("RPC启用Agent成功，管理员ID: {}, Agent ID: {}", userId, agentId);
        return Result.success();
    }

    @Override
    public Result<Void> disableAgent(@NotBlank String userId, @NotBlank String agentId) {
        agentCommandService.disableAgent(agentId);
        log.info("RPC禁用Agent成功，管理员ID: {}, Agent ID: {}", userId, agentId);
        return Result.success();
    }

    @Override
    public Result<Void> addAgentTool(@NotBlank String userId, @NotBlank String agentId, @Valid AddAgentToolCommand command) {
        agentCommandService.addAgentTool(agentId, command);
        log.info("RPC添加Agent工具成功，管理员ID: {}, Agent ID: {}, 工具编码: {}", userId, agentId, command.getToolCode());
        return Result.success();
    }

    @Override
    public Result<Void> removeAgentTool(@NotBlank String userId, @NotBlank String agentId, @NotBlank String toolCode) {
        agentCommandService.removeAgentTool(agentId, toolCode);
        log.info("RPC移除Agent工具成功，管理员ID: {}, Agent ID: {}, 工具编码: {}", userId, agentId, toolCode);
        return Result.success();
    }

    @Override
    public Result<String> createWorkflow(@NotBlank String userId, @Valid CreateWorkflowCommand command) {
        String workflowId = workflowCommandService.createWorkflow(command);
        log.info("RPC创建工作流成功，管理员ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success(workflowId);
    }

    @Override
    public Result<Void> updateWorkflow(@NotBlank String userId, @NotBlank String workflowId, @Valid UpdateWorkflowCommand command) {
        workflowCommandService.updateWorkflow(workflowId, command);
        log.info("RPC更新工作流成功，管理员ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success();
    }

    @Override
    public Result<Void> enableWorkflow(@NotBlank String userId, @NotBlank String workflowId) {
        workflowCommandService.enableWorkflow(workflowId);
        log.info("RPC启用工作流成功，管理员ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success();
    }

    @Override
    public Result<Void> disableWorkflow(@NotBlank String userId, @NotBlank String workflowId) {
        workflowCommandService.disableWorkflow(workflowId);
        log.info("RPC禁用工作流成功，管理员ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success();
    }

    @Override
    public Result<AgentInfoResponse> getAgentById(@NotBlank String userId, @NotBlank String agentId) {
        AgentInfoResponse agent = agentQueryService.findById(agentId);
        log.info("RPC查询Agent详情成功，管理员ID: {}, Agent ID: {}", userId, agentId);
        return Result.success(agent);
    }

    @Override
    public Result<AgentInfoResponse> getAgentByCode(@NotBlank String userId, @NotBlank String code) {
        AgentInfoResponse agent = agentQueryService.findByCode(code);
        log.info("RPC根据编码查询Agent成功，管理员ID: {}, Agent编码: {}", userId, code);
        return Result.success(agent);
    }

    @Override
    public Result<List<AgentInfoResponse>> listAgents(@NotBlank String userId) {
        List<AgentInfoResponse> agents = agentQueryService.findAll();
        log.info("RPC查询Agent列表成功，管理员ID: {}, Agent数量: {}", userId, agents.size());
        return Result.success(agents);
    }

    @Override
    public Result<WorkflowInfoResponse> getWorkflowById(@NotBlank String userId, @NotBlank String workflowId) {
        WorkflowInfoResponse workflow = workflowQueryService.findById(workflowId);
        log.info("RPC查询工作流详情成功，管理员ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success(workflow);
    }

    @Override
    public Result<WorkflowInfoResponse> getWorkflowByCode(@NotBlank String userId, @NotBlank String code) {
        WorkflowInfoResponse workflow = workflowQueryService.findByCode(code);
        log.info("RPC根据编码查询工作流成功，管理员ID: {}, 工作流编码: {}", userId, code);
        return Result.success(workflow);
    }

    @Override
    public Result<List<WorkflowInfoResponse>> listWorkflows(@NotBlank String userId) {
//        List<WorkflowInfoResponse> workflows = workflowQueryService.findAllEnabled();
//        log.info("RPC查询工作流列表成功，管理员ID: {}, 工作流数量: {}", userId, workflows.size());
//        return Result.success(workflows);
        return null;
    }
}
