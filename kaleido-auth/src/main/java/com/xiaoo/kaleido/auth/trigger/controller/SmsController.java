package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.auth.application.command.SmsCommandService;
import com.xiaoo.kaleido.auth.application.command.UserAuthCommandService;
import com.xiaoo.kaleido.base.result.Result;
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
    @PostMapping("/verify-code")
    public Result<SmsCodeResponse> sendSmsCode(
            @Valid @RequestBody SendSmsCodeCommand command) {
        
        log.info("接收发送短信验证码请求，手机号: {}", command.getMobile());
        SmsCodeResponse response = smsCommandService.sendSmsCode(command);
        return Result.success(response);
    }
}
