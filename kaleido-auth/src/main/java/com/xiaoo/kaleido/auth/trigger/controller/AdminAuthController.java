package com.xiaoo.kaleido.auth.trigger.controller;

import com.xiaoo.kaleido.api.admin.auth.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.auth.command.RegisterCommand;
import com.xiaoo.kaleido.api.admin.auth.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.auth.response.RegisterResponse;
import com.xiaoo.kaleido.auth.application.command.AdminAuthCommandService;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequestMapping("/public/admin")
@RequiredArgsConstructor
@Tag(name = "管理员管理", description = "管理员相关接口")
public class AdminAuthController {

    private final AdminAuthCommandService adminAuthCommandService;

    /**
     * 注册管理员
     *
     * @param command 注册管理员命令
     * @return 注册响应
     */
    @Operation(summary = "注册管理员", description = "注册新的管理员账号")
    @PostMapping("/register")
    public Result<RegisterResponse> registerAdminUser(
            @Parameter(description = "注册管理员命令", required = true)
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
    @Operation(summary = "管理员登录", description = "管理员登录接口")
    @PostMapping("/login")
    public Result<AdminLoginResponse> login(
            @Parameter(description = "登录命令", required = true)
            @Valid @RequestBody AdminLoginCommand command) {
        AdminLoginResponse adminLoginResponse = adminAuthCommandService.login(command);
        return Result.success(adminLoginResponse);
    }
}
