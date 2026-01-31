package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.WorkflowQueryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流查询API控制器
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ai/workflow")
@RequiredArgsConstructor
public class WorkflowQueryController {

    private final WorkflowQueryService workflowQueryService;

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
     * 根据编码查询工作流
     *
     * @param code 工作流编码
     * @return 工作流信息响应
     */
    @GetMapping("/by-code/{code}")
    public Result<WorkflowInfoResponse> getWorkflowByCode(
            @NotBlank(message = "工作流编码不能为空")
            @PathVariable String code) {
        String userId = StpUserUtil.getLoginId();
        WorkflowInfoResponse workflow = workflowQueryService.findByCode(code);
        log.info("用户根据编码查询工作流成功，用户ID: {}, 工作流编码: {}", userId, code);
        return Result.success(workflow);
    }

    /**
     * 查询启用的工作流列表
     *
     * @return 工作流信息响应列表
     */
    @GetMapping
    public Result<List<WorkflowInfoResponse>> listWorkflows() {
        String userId = StpUserUtil.getLoginId();
        List<WorkflowInfoResponse> workflows = workflowQueryService.findAllEnabled();
        log.info("用户查询工作流列表成功，用户ID: {}, 工作流数量: {}", userId, workflows.size());
        return Result.success(workflows);
    }
}
