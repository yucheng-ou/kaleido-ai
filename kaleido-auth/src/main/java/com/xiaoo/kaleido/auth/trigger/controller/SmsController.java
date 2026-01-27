package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.auth.application.command.SmsCommandService;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.result.Result;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xiaoo.kaleido.limiter.annotation.RateLimit;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码API
 *
 * @author ouyucheng
 * @date 2025/1/13
 */
@Slf4j
@RestController
@RequestMapping("/public/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsCommandService smsCommandService;

    /**
     * 发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 短信验证码响应
     */
    @SentinelResource(
            value = "sendSmsCode",
            blockHandler = "sendSmsCodeBlockHandler",
            fallback = "sendSmsCodeFallback",
            exceptionsToIgnore = {IllegalArgumentException.class, BizException.class}
    )
    @RateLimit(key = "'sms:mobile:' + #command.mobile", limit = 1, window = 60,message = "请求频繁，请稍后再试")
    @PostMapping("/verify-code")
    public Result<SmsCodeResponse> sendSmsCode(
            @Valid @RequestBody SendSmsCodeCommand command) {

        log.info("接收发送短信验证码请求，手机号: {}", command.getMobile());
        SmsCodeResponse response = smsCommandService.sendSmsCode(command);
        return Result.success(response);
    }

    /**
     * 发送短信验证码限流处理函数
     *
     * @param command 发送短信验证码命令
     * @param ex      限流异常
     * @return 限流响应
     */
    public Result<SmsCodeResponse> sendSmsCodeBlockHandler(
            SendSmsCodeCommand command, BlockException ex) {
        log.warn("短信验证码发送接口触发限流，手机号: {}, 异常: {}", command.getMobile(), ex.getClass().getSimpleName());
        return Result.error(BizErrorCode.SYSTEM_BUSY, "请求过于频繁，请1分钟后再试");
    }

    /**
     * 发送短信验证码降级处理函数
     *
     * @param command   发送短信验证码命令
     * @param throwable 降级异常
     * @return 降级响应
     */
    public Result<SmsCodeResponse> sendSmsCodeFallback(
            SendSmsCodeCommand command, Throwable throwable) {
        log.error("短信验证码发送接口触发降级，手机号: {}, 异常: {}", command.getMobile(), throwable.getMessage(), throwable);
        return Result.error(BizErrorCode.SYSTEM_BUSY, "服务暂时不可用，请稍后重试");
    }
}
