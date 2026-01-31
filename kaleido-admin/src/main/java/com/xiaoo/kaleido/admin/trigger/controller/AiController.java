package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaoo.kaleido.api.ai.IRpcAiService;
import com.xiaoo.kaleido.api.ai.command.CreateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.AddAgentToolCommand;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI管理API
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/ai")
@RequiredArgsConstructor
public class AiController {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAiService rpcAiService;

    /**
     * 创建Agent
     *
     * @param command 创建Agent命令
     * @return Agent ID
     */
    @SaCheckPermission(value = "admin:ai:create", type = StpAdminUtil.TYPE)
    @PostMapping("/agent")
    public Result<String> createAgent(@Valid @RequestBody CreateAgentCommand command) {
        String adminId = StpAdminUtil.getLoginId();
        String agentId = rpcAiService.createAgent(adminId, command).getData();
        log.info("管理员创建Agent成功，管理员ID: {}, Agent ID: {}", adminId, agentId);
        return Result.success(agentId);
    }

    /**
     * 更新Agent
     *
     * @param agentId Agent ID
     * @param command 更新Agent命令
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:ai:update", type = StpAdminUtil.TYPE)
    @PutMapping("/agent/{agentId}")
    public Result<Void> updateAgent(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId,
            @Valid @RequestBody UpdateAgentCommand command) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.updateAgent(adminId, agentId, command);
        log.info("管理员更新Agent成功，管理员ID: {}, Agent ID: {}", adminId, agentId);
        return Result.success();
    }

    /**
     * 启用Agent
     *
     * @param agentId Agent ID
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:ai:enable", type = StpAdminUtil.TYPE)
    @PutMapping("/agent/{agentId}/enable")
    public Result<Void> enableAgent(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.enableAgent(adminId, agentId);
        log.info("管理员启用Agent成功，管理员ID: {}, Agent ID: {}", adminId, agentId);
        return Result.success();
    }

    /**
     * 禁用Agent
     *
     * @param agentId Agent ID
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:ai:disable", type = StpAdminUtil.TYPE)
    @PutMapping("/agent/{agentId}/disable")
    public Result<Void> disableAgent(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.disableAgent(adminId, agentId);
        log.info("管理员禁用Agent成功，管理员ID: {}, Agent ID: {}", adminId, agentId);
        return Result.success();
    }

    /**
     * 添加工具到Agent
     *
     * @param agentId Agent ID
     * @param command 添加工具命令
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:ai:update", type = StpAdminUtil.TYPE)
    @PostMapping("/agent/{agentId}/tools")
    public Result<Void> addAgentTool(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId,
            @Valid @RequestBody AddAgentToolCommand command) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.addAgentTool(adminId, agentId, command);
        log.info("管理员添加Agent工具成功，管理员ID: {}, Agent ID: {}, 工具编码: {}", adminId, agentId, command.getToolCode());
        return Result.success();
    }

    /**
     * 从Agent移除工具
     *
     * @param agentId  Agent ID
     * @param toolCode 工具编码
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:ai:update", type = StpAdminUtil.TYPE)
    @DeleteMapping("/agent/{agentId}/tools/{toolCode}")
    public Result<Void> removeAgentTool(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId,
            @NotBlank(message = "工具编码不能为空")
            @PathVariable String toolCode) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.removeAgentTool(adminId, agentId, toolCode);
        log.info("管理员移除Agent工具成功，管理员ID: {}, Agent ID: {}, 工具编码: {}", adminId, agentId, toolCode);
        return Result.success();
    }

    /**
     * 查询Agent详情
     *
     * @param agentId Agent ID
     * @return Agent信息响应
     */
    @SaCheckPermission(value = "admin:ai:read", type = StpAdminUtil.TYPE)
    @GetMapping("/agent/{agentId}")
    public Result<AgentInfoResponse> getAgentById(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId) {
        String adminId = StpAdminUtil.getLoginId();
        AgentInfoResponse agent = rpcAiService.getAgentById(adminId, agentId).getData();
        log.info("管理员查询Agent详情成功，管理员ID: {}, Agent ID: {}", adminId, agentId);
        return Result.success(agent);
    }

    /**
     * 根据编码查询Agent
     *
     * @param code Agent编码
     * @return Agent信息响应
     */
    @SaCheckPermission(value = "admin:ai:read", type = StpAdminUtil.TYPE)
    @GetMapping("/agent/by-code/{code}")
    public Result<AgentInfoResponse> getAgentByCode(
            @NotBlank(message = "Agent编码不能为空")
            @PathVariable String code) {
        String adminId = StpAdminUtil.getLoginId();
        AgentInfoResponse agent = rpcAiService.getAgentByCode(adminId, code).getData();
        log.info("管理员根据编码查询Agent成功，管理员ID: {}, Agent编码: {}", adminId, code);
        return Result.success(agent);
    }

    /**
     * 查询Agent列表
     *
     * @return Agent信息响应列表
     */
    @SaCheckPermission(value = "admin:ai:read", type = StpAdminUtil.TYPE)
    @GetMapping("/agent")
    public Result<List<AgentInfoResponse>> listAgents() {
        String adminId = StpAdminUtil.getLoginId();
        List<AgentInfoResponse> agents = rpcAiService.listAgents(adminId).getData();
        log.info("管理员查询Agent列表成功，管理员ID: {}, Agent数量: {}", adminId, agents.size());
        return Result.success(agents);
    }

    /**
     * 创建工作流
     *
     * @param command 创建工作流命令
     * @return 工作流ID
     */
    @SaCheckPermission(value = "admin:workflow:create", type = StpAdminUtil.TYPE)
    @PostMapping("/workflow")
    public Result<String> createWorkflow(@Valid @RequestBody CreateWorkflowCommand command) {
        String adminId = StpAdminUtil.getLoginId();
        String workflowId = rpcAiService.createWorkflow(adminId, command).getData();
        log.info("管理员创建工作流成功，管理员ID: {}, 工作流ID: {}", adminId, workflowId);
        return Result.success(workflowId);
    }

    /**
     * 更新工作流
     *
     * @param workflowId 工作流ID
     * @param command    更新工作流命令
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:workflow:update", type = StpAdminUtil.TYPE)
    @PutMapping("/workflow/{workflowId}")
    public Result<Void> updateWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId,
            @Valid @RequestBody UpdateWorkflowCommand command) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.updateWorkflow(adminId, workflowId, command);
        log.info("管理员更新工作流成功，管理员ID: {}, 工作流ID: {}", adminId, workflowId);
        return Result.success();
    }

    /**
     * 启用工作流
     *
     * @param workflowId 工作流ID
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:workflow:enable", type = StpAdminUtil.TYPE)
    @PutMapping("/workflow/{workflowId}/enable")
    public Result<Void> enableWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.enableWorkflow(adminId, workflowId);
        log.info("管理员启用工作流成功，管理员ID: {}, 工作流ID: {}", adminId, workflowId);
        return Result.success();
    }

    /**
     * 禁用工作流
     *
     * @param workflowId 工作流ID
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:workflow:disable", type = StpAdminUtil.TYPE)
    @PutMapping("/workflow/{workflowId}/disable")
    public Result<Void> disableWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        String adminId = StpAdminUtil.getLoginId();
        rpcAiService.disableWorkflow(adminId, workflowId);
        log.info("管理员禁用工作流成功，管理员ID: {}, 工作流ID: {}", adminId, workflowId);
        return Result.success();
    }

    /**
     * 查询工作流详情
     *
     * @param workflowId 工作流ID
     * @return 工作流信息响应
     */
    @SaCheckPermission(value = "admin:workflow:read", type = StpAdminUtil.TYPE)
    @GetMapping("/workflow/{workflowId}")
    public Result<WorkflowInfoResponse> getWorkflowById(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        String adminId = StpAdminUtil.getLoginId();
        WorkflowInfoResponse workflow = rpcAiService.getWorkflowById(adminId, workflowId).getData();
        log.info("管理员查询工作流详情成功，管理员ID: {}, 工作流ID: {}", adminId, workflowId);
        return Result.success(workflow);
    }

    /**
     * 根据编码查询工作流
     *
     * @param code 工作流编码
     * @return 工作流信息响应
     */
    @SaCheckPermission(value = "admin:workflow:read", type = StpAdminUtil.TYPE)
    @GetMapping("/workflow/by-code/{code}")
    public Result<WorkflowInfoResponse> getWorkflowByCode(
            @NotBlank(message = "工作流编码不能为空")
            @PathVariable String code) {
        String adminId = StpAdminUtil.getLoginId();
        WorkflowInfoResponse workflow = rpcAiService.getWorkflowByCode(adminId, code).getData();
        log.info("管理员根据编码查询工作流成功，管理员ID: {}, 工作流编码: {}", adminId, code);
        return Result.success(workflow);
    }

    /**
     * 查询工作流列表
     *
     * @return 工作流信息响应列表
     */
    @SaCheckPermission(value = "admin:workflow:read", type = StpAdminUtil.TYPE)
    @GetMapping("/workflow")
    public Result<List<WorkflowInfoResponse>> listWorkflows() {
        String adminId = StpAdminUtil.getLoginId();
        List<WorkflowInfoResponse> workflows = rpcAiService.listWorkflows(adminId).getData();
        log.info("管理员查询工作流列表成功，管理员ID: {}, 工作流数量: {}", adminId, workflows.size());
        return Result.success(workflows);
    }
}
