package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.ai.application.command.WorkflowCommandService;
import com.xiaoo.kaleido.ai.application.query.WorkflowExecutionQueryService;
import com.xiaoo.kaleido.api.ai.command.ExecuteWorkflowCommand;
import com.xiaoo.kaleido.api.ai.response.WorkflowExecutionInfoResponse;
import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.WorkflowQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流API控制器
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ai/workflow")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowQueryService workflowQueryService;
    private final WorkflowCommandService workflowCommandService;
    private final WorkflowExecutionQueryService workflowExecutionQueryService;

    /**
     * 查询工作流详情
     *
     * @param workflowId 工作流ID
     * @return 工作流信息响应
     */
    @GetMapping("/{workflowId}")
    public Result<WorkflowInfoResponse> getWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        String userId = StpUserUtil.getLoginId();
        WorkflowInfoResponse workflow = workflowQueryService.findById(workflowId);
        log.info("用户查询工作流详情成功，用户ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success(workflow);
    }

    /**
     * 执行工作流
     *
     * @param command 执行工作流命令
     * @return 执行结果
     */
    @PostMapping("/execute")
    public Result<String> executeWorkflow(
            @RequestBody @Valid ExecuteWorkflowCommand command) {
        String userId = StpUserUtil.getLoginId();
        String result = workflowCommandService.executeWorkflow(command, userId);
        return Result.success(result);
    }

    /**
     * 查询用户的工作流执行记录
     *
     * @return 工作流执行信息响应列表
     */
    @GetMapping("/executions/my")
    public Result<List<WorkflowExecutionInfoResponse>> getMyWorkflowExecutions() {
        String userId = StpUserUtil.getLoginId();
        List<WorkflowExecutionInfoResponse> executions = workflowExecutionQueryService.findByUserId(userId);
        log.info("用户查询工作流执行记录成功，用户ID: {}, 记录数量: {}", userId, executions.size());
        return Result.success(executions);
    }
}
