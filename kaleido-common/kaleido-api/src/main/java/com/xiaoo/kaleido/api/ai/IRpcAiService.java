package com.xiaoo.kaleido.api.ai;

import com.xiaoo.kaleido.api.ai.command.CreateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateAgentCommand;
import com.xiaoo.kaleido.api.ai.command.AddAgentToolCommand;
import com.xiaoo.kaleido.api.ai.command.CreateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.command.UpdateWorkflowCommand;
import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * AI RPC服务接口
 * <p>
 * 提供AI相关的RPC服务，供管理后台调用
 *
 * @author ouyucheng
 * @date 2026/1/30
 * @dubbo
 */
public interface IRpcAiService {

    /**
     * 创建Agent
     *
     * @param userId  用户ID（管理员ID）
     * @param command 创建Agent命令
     * @return Agent ID
     */
    Result<String> createAgent(@NotBlank String userId, @Valid CreateAgentCommand command);

    /**
     * 更新Agent
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     * @param command 更新Agent命令
     */
    Result<Void> updateAgent(@NotBlank String userId, @NotBlank String agentId, @Valid UpdateAgentCommand command);

    /**
     * 启用Agent
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     */
    Result<Void> enableAgent(@NotBlank String userId, @NotBlank String agentId);

    /**
     * 禁用Agent
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     */
    Result<Void> disableAgent(@NotBlank String userId, @NotBlank String agentId);

    /**
     * 添加工具到Agent
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     * @param command 添加工具命令
     */
    Result<Void> addAgentTool(@NotBlank String userId, @NotBlank String agentId, @Valid AddAgentToolCommand command);

    /**
     * 从Agent移除工具
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     * @param toolCode 工具编码
     */
    Result<Void> removeAgentTool(@NotBlank String userId, @NotBlank String agentId, @NotBlank String toolCode);

    /**
     * 创建工作流
     *
     * @param userId  用户ID（管理员ID）
     * @param command 创建工作流命令
     * @return 工作流ID
     */
    Result<String> createWorkflow(@NotBlank String userId, @Valid CreateWorkflowCommand command);

    /**
     * 更新工作流
     *
     * @param userId     用户ID（管理员ID）
     * @param workflowId 工作流ID
     * @param command    更新工作流命令
     */
    Result<Void> updateWorkflow(@NotBlank String userId, @NotBlank String workflowId, @Valid UpdateWorkflowCommand command);

    /**
     * 启用工作流
     *
     * @param userId     用户ID（管理员ID）
     * @param workflowId 工作流ID
     */
    Result<Void> enableWorkflow(@NotBlank String userId, @NotBlank String workflowId);

    /**
     * 禁用工作流
     *
     * @param userId     用户ID（管理员ID）
     * @param workflowId 工作流ID
     */
    Result<Void> disableWorkflow(@NotBlank String userId, @NotBlank String workflowId);

    /**
     * 根据ID查询Agent详情
     *
     * @param userId  用户ID（管理员ID）
     * @param agentId Agent ID
     * @return Agent信息响应
     */
    Result<AgentInfoResponse> getAgentById(@NotBlank String userId, @NotBlank String agentId);

    /**
     * 根据编码查询Agent
     *
     * @param userId 用户ID（管理员ID）
     * @param code   Agent编码
     * @return Agent信息响应
     */
    Result<AgentInfoResponse> getAgentByCode(@NotBlank String userId, @NotBlank String code);

    /**
     * 查询Agent列表
     *
     * @param userId 用户ID（管理员ID）
     * @return Agent信息响应列表
     */
    Result<List<AgentInfoResponse>> listAgents(@NotBlank String userId);

    /**
     * 根据ID查询工作流详情
     *
     * @param userId     用户ID（管理员ID）
     * @param workflowId 工作流ID
     * @return 工作流信息响应
     */
    Result<WorkflowInfoResponse> getWorkflowById(@NotBlank String userId, @NotBlank String workflowId);

    /**
     * 根据编码查询工作流
     *
     * @param userId 用户ID（管理员ID）
     * @param code   工作流编码
     * @return 工作流信息响应
     */
    Result<WorkflowInfoResponse> getWorkflowByCode(@NotBlank String userId, @NotBlank String code);

    /**
     * 查询工作流列表
     *
     * @param userId 用户ID（管理员ID）
     * @return 工作流信息响应列表
     */
    Result<List<WorkflowInfoResponse>> listWorkflows(@NotBlank String userId);
}
