package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.ai.domain.workflow.service.impl.WorkflowExecutionServiceImpl;
import com.xiaoo.kaleido.api.ai.response.WorkflowInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.WorkflowQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流API控制器
 * <p>
 * 整合了工作流查询、执行和管理功能，遵循一个控制器对应一个领域的原则
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
    private final WorkflowExecutionServiceImpl workflowExecutionService;

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

    /**
     * 执行工作流
     *
     * @param workflowId 工作流ID
     * @param inputData 输入数据
     * @return 执行结果
     */
    @PostMapping("/{workflowId}/execute")
    public Result<String> executeWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId,
            @RequestBody(required = false) String inputData) {
        
        String userId = StpUserUtil.getLoginId();
        log.info("用户请求执行工作流，用户ID: {}, 工作流ID: {}, 输入数据长度: {}", 
                userId, workflowId, inputData != null ? inputData.length() : 0);
        
        String result = workflowExecutionService.executeWorkflow(workflowId, inputData);
        
        log.info("工作流执行成功，用户ID: {}, 工作流ID: {}, 结果长度: {}", 
                userId, workflowId, result != null ? result.length() : 0);
        
        return Result.success(result);
    }

    /**
     * 注册工作流到工厂
     *
     * @param workflowId 工作流ID
     * @return 注册结果
     */
    @PostMapping("/{workflowId}/register")
    public Result<Void> registerWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        
        String userId = StpUserUtil.getLoginId();
        log.info("用户请求注册工作流到工厂，用户ID: {}, 工作流ID: {}", userId, workflowId);
        
        workflowExecutionService.registerWorkflow(workflowId);
        
        log.info("工作流注册成功，用户ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success();
    }

    /**
     * 注销工作流从工厂
     *
     * @param workflowId 工作流ID
     * @return 注销结果
     */
    @PostMapping("/{workflowId}/unregister")
    public Result<Void> unregisterWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        
        String userId = StpUserUtil.getLoginId();
        log.info("用户请求注销工作流从工厂，用户ID: {}, 工作流ID: {}", userId, workflowId);
        
        workflowExecutionService.unregisterWorkflow(workflowId);
        
        log.info("工作流注销成功，用户ID: {}, 工作流ID: {}", userId, workflowId);
        return Result.success();
    }

    /**
     * 检查工作流是否已注册到工厂
     *
     * @param workflowId 工作流ID
     * @return 检查结果
     */
    @GetMapping("/{workflowId}/registered")
    public Result<Boolean> isWorkflowRegistered(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        
        String userId = StpUserUtil.getLoginId();
        log.debug("用户请求检查工作流注册状态，用户ID: {}, 工作流ID: {}", userId, workflowId);
        
        boolean isRegistered = workflowExecutionService.isWorkflowRegistered(workflowId);
        
        log.debug("工作流注册状态检查完成，用户ID: {}, 工作流ID: {}, 已注册: {}", 
                userId, workflowId, isRegistered);
        
        return Result.success(isRegistered);
    }

    /**
     * 测试工作流执行（简单测试）
     *
     * @param workflowId 工作流ID
     * @return 测试结果
     */
    @PostMapping("/{workflowId}/test")
    public Result<String> testWorkflow(
            @NotBlank(message = "工作流ID不能为空")
            @PathVariable String workflowId) {
        
        String userId = StpUserUtil.getLoginId();
        log.info("用户请求测试工作流，用户ID: {}, 工作流ID: {}", userId, workflowId);
        
        // 使用默认输入数据
        String testInput = "这是一个测试输入，用于验证工作流执行功能。";
        String result = workflowExecutionService.executeWorkflow(workflowId, testInput);
        
        log.info("工作流测试成功，用户ID: {}, 工作流ID: {}, 结果长度: {}", 
                userId, workflowId, result != null ? result.length() : 0);
        
        return Result.success(result);
    }
}
