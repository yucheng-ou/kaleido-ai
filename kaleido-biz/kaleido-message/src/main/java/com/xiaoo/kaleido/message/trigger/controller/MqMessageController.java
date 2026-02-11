package com.xiaoo.kaleido.message.trigger.controller;

import com.xiaoo.kaleido.api.message.command.CreateMqMessageCommand;
import com.xiaoo.kaleido.api.message.response.MqMessageResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.message.application.command.MqMessageCommandService;
import com.xiaoo.kaleido.message.application.query.IMqMessageQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MQ消息API控制器
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/message/mq")
@RequiredArgsConstructor
public class MqMessageController {

    private final IMqMessageQueryService mqMessageQueryService;

    /**
     * 获取消息详情
     *
     * @param messageId 消息ID
     * @return 消息详情
     */
    @GetMapping("/{messageId}")
    public Result<MqMessageResponse> getMessage(
            @NotBlank(message = "消息ID不能为空")
            @PathVariable String messageId) {
        MqMessageResponse response = mqMessageQueryService.getMessage(messageId);
        return Result.success(response);
    }

    /**
     * 获取当前用户的消息列表
     *
     * @return 消息列表
     */
    @GetMapping("/list")
    public Result<List<MqMessageResponse>> listMyMessages() {
        String userId = StpUserUtil.getLoginId();
        List<MqMessageResponse> responses = mqMessageQueryService.listMessagesByUserId(userId);
        return Result.success(responses);
    }
}
