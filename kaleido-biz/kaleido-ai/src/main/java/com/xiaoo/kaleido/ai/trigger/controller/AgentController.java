package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.AgentQueryService;
import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Agent API控制器
 * <p>
 * 整合了Agent查询和聊天功能，遵循一个控制器对应一个领域的原则
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ai/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentQueryService agentQueryService;
    private final IChatService chatService;

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

    /**
     * 与Agent进行聊天
     *
     * @param agentId        Agent ID
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    @PostMapping("/{agentId}/chat")
    public Flux<String> chatWithAgent(
            @PathVariable String agentId,
            @RequestParam String message,
            @RequestParam(required = false) String conversationId) {

        log.info("收到Agent聊天请求，Agent ID: {}, 会话ID: {}, 消息长度: {}",
                agentId, conversationId, message.length());

        return chatService.chatWithAgent(agentId, message, conversationId);
    }

    /**
     * 默认聊天（不使用特定Agent）
     *
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    @GetMapping("/chat")
    public Flux<String> chat(
            @RequestParam("message") String message,
            @RequestParam(required = false) String conversationId) {

        String userId = StpUserUtil.getLoginId();

        // 使用ChatService的默认聊天方法（带用户ID过滤）
        return chatService.chatWithDefault(message, conversationId, userId);
    }
}
