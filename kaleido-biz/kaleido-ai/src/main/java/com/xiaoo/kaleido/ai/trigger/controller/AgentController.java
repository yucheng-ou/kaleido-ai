package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.api.ai.command.ChatCommand;
import com.xiaoo.kaleido.api.ai.command.ChatWithAgentCommand;
import com.xiaoo.kaleido.api.ai.response.AgentInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.query.AgentQueryService;
import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Agent API控制器
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
     * @param agentId Agent ID
     * @param command 聊天命令
     * @return 聊天响应流
     */
    @PostMapping("/{agentId}/chat")
    public Flux<String> chatWithAgent(
            @PathVariable String agentId,
            @Valid @RequestBody ChatWithAgentCommand command) {

        String userId = StpUserUtil.getLoginId();
        return chatService.chatWithAgent(agentId, command.getMessage(), command.getConversationId(), userId);
    }

    /**
     * 默认聊天（不使用特定Agent）
     *
     * @param command 聊天命令
     * @return 聊天响应流
     */
    @PostMapping("/chat")
    public Flux<String> chat(
            @Valid @RequestBody ChatCommand command) {

        String userId = StpUserUtil.getLoginId();
        return chatService.chatWithDefault(command.getMessage(), command.getConversationId(), userId);
    }
}
