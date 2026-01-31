package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.ai.application.service.AgentChatService;
import com.xiaoo.kaleido.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Agent聊天控制器
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@RestController
@RequestMapping("/ai/agent")
@RequiredArgsConstructor
public class AgentChatController {

    private final AgentChatService agentChatService;

    /**
     * 与Agent进行聊天
     *
     * @param agentId        Agent ID
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    @PostMapping("/{agentId}/chat")
    public Flux<String> chat(
            @PathVariable String agentId,
            @RequestParam String message,
            @RequestParam(required = false) String conversationId) {

        log.info("收到Agent聊天请求，Agent ID: {}, 会话ID: {}, 消息长度: {}",
                agentId, conversationId, message.length());

        return agentChatService.chat(agentId, message, conversationId);
    }

    /**
     * 检查Agent是否可用
     *
     * @param agentId Agent ID
     * @return 可用性检查结果
     */
    @GetMapping("/{agentId}/available")
    public Result<Boolean> checkAvailable(@PathVariable String agentId) {
        log.debug("检查Agent可用性，Agent ID: {}", agentId);

        boolean available = agentChatService.isAgentAvailable(agentId);
        return Result.success(available);
    }

    /**
     * 获取Agent状态
     *
     * @param agentId Agent ID
     * @return Agent状态信息
     */
    @GetMapping("/{agentId}/status")
    public Result<String> getStatus(@PathVariable String agentId) {
        log.debug("获取Agent状态，Agent ID: {}", agentId);

        String status = agentChatService.getAgentStatus(agentId);
        return Result.success(status);
    }
}
