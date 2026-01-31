package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
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
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class AgentChatController {

    private final IChatService chatService;

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

        return chatService.chatWithAgent(agentId, message, conversationId);
    }

    /**
     * 默认聊天 不三使用agent
     *
     * @param message        用户消息
     * @param conversationId 会话ID（可选）
     * @return 聊天响应流
     */
    @GetMapping(value = "/chat")
    public Flux<String> chat(
            @RequestParam("message") String message,
            @RequestParam(required = false) String conversationId) {

        String userId = StpUserUtil.getLoginId();

        // 使用ChatService的默认聊天方法（带用户ID过滤）
        return chatService.chatWithDefault(message, conversationId, userId);
    }
}
