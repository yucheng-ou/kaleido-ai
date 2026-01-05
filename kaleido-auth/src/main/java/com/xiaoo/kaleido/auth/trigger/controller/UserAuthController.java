package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.auth.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.auth.command.RegisterCommand;
import com.xiaoo.kaleido.api.admin.auth.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.auth.response.RegisterResponse;
import com.xiaoo.kaleido.api.admin.auth.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.user.response.UserLoginResponse;
import com.xiaoo.kaleido.auth.application.command.UserAuthCommandService;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权控制器
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
@Tag(name = "授权管理", description = "用户认证授权相关接口")
public class UserAuthController {
    
    private final UserAuthCommandService userAuthCommandService;
    
    /**
     * 发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 短信验证码响应
     */
    @Operation(summary = "发送短信验证码", description = "向指定手机号发送短信验证码")
    @PostMapping("/sms-code")
    public Result<SmsCodeResponse> sendSmsCode(
            @Parameter(description = "发送短信验证码命令", required = true)
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
    @Operation(summary = "用户注册", description = "用户注册接口，需要短信验证码")
    @PostMapping("/register")
    public Result<RegisterResponse> register(
            @Parameter(description = "用户注册命令", required = true)
            @Valid @RequestBody RegisterCommand command) {
        
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
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<UserLoginResponse> login(
            @Parameter(description = "用户登录命令", required = true)
            @Valid @RequestBody AdminLoginCommand command) {
        
        log.info("接收用户登录请求，手机号: {}", command.getTelephone());
        return Result.success(userAuthCommandService.login(command));
    }
}
