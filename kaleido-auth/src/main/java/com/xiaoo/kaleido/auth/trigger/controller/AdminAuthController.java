package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.user.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.user.command.RegisterCommand;
import com.xiaoo.kaleido.api.admin.user.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.user.response.RegisterResponse;
import com.xiaoo.kaleido.auth.application.command.AdminAuthCommandService;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员控制器
 *
 * @author ouyucheng
 * @date 2026/1/4
 */
@Slf4j
@RestController
@RequestMapping("/public/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthCommandService adminAuthCommandService;

    /**
     * 注册管理员
     *
     * @param command 注册管理员命令
     * @return 注册响应
     */
    @PostMapping("/register")
    public Result<RegisterResponse> registerAdminUser(
            @Valid @RequestBody RegisterCommand command) {
        RegisterResponse response = adminAuthCommandService.register(command);
        return Result.success(response);
    }

    /**
     * 管理员登录
     *
     * @param command 登录命令
     * @return 登录响应
     */
    @PostMapping("/login")
    public Result<AdminLoginResponse> login(
            @Valid @RequestBody AdminLoginCommand command) {
        AdminLoginResponse adminLoginResponse = adminAuthCommandService.login(command);
        return Result.success(adminLoginResponse);
    }
}
