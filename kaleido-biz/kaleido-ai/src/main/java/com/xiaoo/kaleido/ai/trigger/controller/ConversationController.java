package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.api.ai.command.UpdateConversationTitleCommand;
import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.ai.application.command.ConversationCommandService;
import com.xiaoo.kaleido.ai.application.query.ConversationQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会话API控制器
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ai/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationCommandService conversationCommandService;
    private final ConversationQueryService conversationQueryService;

    /**
     * 创建会话
     *
     * @return 会话信息响应（包含会话ID和标题）
     */
    @PostMapping
    public Result<ConversationInfoResponse> createConversation() {
        String userId = StpUserUtil.getLoginId();
        return Result.success(conversationCommandService.createConversation(userId));
    }

    /**
     * 更新会话标题
     *
     * @param conversationId 会话ID
     * @param command 更新会话标题命令
     * @return 空响应
     */
    @PutMapping("/{conversationId}/title")
    public Result<Void> updateConversationTitle(
            @NotBlank(message = "会话ID不能为空")
            @PathVariable String conversationId,
            @Valid @RequestBody UpdateConversationTitleCommand command) {
        String userId = StpUserUtil.getLoginId();
        conversationCommandService.updateConversationTitle(conversationId, command, userId);
        return Result.success();
    }

    /**
     * 查询会话详情
     *
     * @param conversationId 会话ID
     * @return 会话信息响应
     */
    @GetMapping("/{conversationId}")
    public Result<ConversationInfoResponse> getConversation(
            @NotBlank(message = "会话ID不能为空")
            @PathVariable String conversationId) {
        String userId = StpUserUtil.getLoginId();
        ConversationInfoResponse conversation = conversationQueryService.findById(conversationId);
        return Result.success(conversation);
    }

    /**
     * 查询用户会话列表
     *
     * @return 会话信息响应列表
     */
    @GetMapping
    public Result<List<ConversationInfoResponse>> listConversations() {
        String userId = StpUserUtil.getLoginId();
        List<ConversationInfoResponse> conversations = conversationQueryService.findByUserId(userId);
        return Result.success(conversations);
    }

    /**
     * 删除会话
     *
     * @param conversationId 会话ID
     * @return 空响应
     */
    @DeleteMapping("/{conversationId}")
    public Result<Void> deleteConversation(
            @NotBlank(message = "会话ID不能为空")
            @PathVariable String conversationId) {
        String userId = StpUserUtil.getLoginId();
        conversationCommandService.deleteConversation(conversationId, userId);
        return Result.success();
    }
}
