package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.user.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.RegisterResponse;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.response.UserLoginResponse;
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
 * 普通用户认证API
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class UserAuthController {
    
    private final UserAuthCommandService userAuthCommandService;
    
    /**
     * 发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 短信验证码响应
     */
    @PostMapping("/sms-code")
    public Result<SmsCodeResponse> sendSmsCode(
            @Valid @RequestBody SendSmsCodeCommand command) {
        
        log.info("接收发送短信验证码请求，手机号: {}", command.getMobile());
        SmsCodeResponse response = userAuthCommandService.sendSmsCode(command);
        return Result.success(response);
    }
    
    /**
     * 用户注册
     *
     * @param command 注册命令
     * @return 注册响应
     */
    @PostMapping("/register")
    public Result<RegisterResponse> register(
            @Valid @RequestBody RegisterUserCommand command) {
        
        log.info("接收用户注册请求，手机号: {}", command.getTelephone());
        RegisterResponse response = userAuthCommandService.register(command);
        return Result.success(response);
    }
    
    /**
     * 用户登录
     *
     * @param command 登录命令
     * @return 登录响应
     */
    @PostMapping("/login")
    public Result<UserLoginResponse> login(
            @Valid @RequestBody AdminLoginCommand command) {
        
        log.info("接收用户登录请求，手机号: {}", command.getMobile());
        return Result.success(userAuthCommandService.login(command));
    }
}
