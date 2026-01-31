package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.AgentQueryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Agent查询API控制器
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ai/agent")
@RequiredArgsConstructor
public class AgentQueryController {

    private final AgentQueryService agentQueryService;

    /**
     * 查询Agent详情
     *
     * @param agentId Agent ID
     * @return Agent信息响应
     */
    @GetMapping("/{agentId}")
    public Result<AgentInfoResponse> getAgent(
            @NotBlank(message = "Agent ID不能为空")
            @PathVariable String agentId) {
        String userId = StpUserUtil.getLoginId();
        AgentInfoResponse agent = agentQueryService.findById(agentId);
        log.info("用户查询Agent详情成功，用户ID: {}, Agent ID: {}", userId, agentId);
        return Result.success(agent);
    }

    /**
     * 根据编码查询Agent
     *
     * @param code Agent编码
     * @return Agent信息响应
     */
    @GetMapping("/by-code/{code}")
    public Result<AgentInfoResponse> getAgentByCode(
            @NotBlank(message = "Agent编码不能为空")
            @PathVariable String code) {
        String userId = StpUserUtil.getLoginId();
        AgentInfoResponse agent = agentQueryService.findByCode(code);
        log.info("用户根据编码查询Agent成功，用户ID: {}, Agent编码: {}", userId, code);
        return Result.success(agent);
    }

    /**
     * 查询Agent列表
     *
     * @return Agent信息响应列表
     */
    @GetMapping
    public Result<List<AgentInfoResponse>> listAgents() {
        String userId = StpUserUtil.getLoginId();
        List<AgentInfoResponse> agents = agentQueryService.findAll();
        log.info("用户查询Agent列表成功，用户ID: {}, Agent数量: {}", userId, agents.size());
        return Result.success(agents);
    }
}
