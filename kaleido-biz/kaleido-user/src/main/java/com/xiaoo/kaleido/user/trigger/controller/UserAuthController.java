package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.user.satoken.model.dto.UserLoginDTO;
import com.xiaoo.kaleido.user.satoken.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户认证控制器
 * 使用新的双账户体系进行认证
 *
 * @author ouyucheng
 * @date 2026/1/5
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
@Tag(name = "用户认证", description = "用户认证相关接口（双账户体系）")
public class UserAuthController {

    private final UserAuthService userAuthService;

    /**
     * 用户登录（新双账户体系）
     *
     * @param loginDTO 用户登录请求
     * @return 登录响应
     */
    @Operation(summary = "用户登录", description = "用户登录接口（使用新的双账户体系）")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @Parameter(description = "用户登录请求", required = true)
            @Valid @RequestBody UserLoginDTO loginDTO) {
        Map<String, Object> loginResult = userAuthService.userLogin(loginDTO);
        return Result.success(loginResult);
    }

    /**
     * 用户登出
     *
     * @return 操作结果
     */
    @Operation(summary = "用户登出", description = "用户登出接口")
    @PostMapping("/logout")
    public Result<Void> logout() {
        userAuthService.userLogout();
        return Result.success();
    }

    /**
     * 用户指定设备登出
     *
     * @param device 设备标识
     * @return 操作结果
     */
    @Operation(summary = "用户指定设备登出", description = "用户指定设备登出接口")
    @PostMapping("/logout/{device}")
    public Result<Void> logoutByDevice(
            @Parameter(description = "设备标识", required = true)
            @PathVariable String device) {
        userAuthService.userLogoutByDevice(device);
        return Result.success();
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录的用户信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo() {
        Map<String, Object> userInfo = userAuthService.getCurrentUserInfo();
        return Result.success(userInfo);
    }

    /**
     * 刷新Token
     *
     * @param token 当前Token
     * @return 新的Token信息
     */
    @Operation(summary = "刷新Token", description = "刷新用户Token")
    @PostMapping("/refresh")
    public Result<Map<String, Object>> refreshToken(
            @Parameter(description = "当前Token", required = true)
            @RequestHeader("Authorization") String token) {
        // 去掉Bearer前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Map<String, Object> result = userAuthService.refreshToken(token);
        return Result.success(result);
    }

    /**
     * 验证Token
     *
     * @param token 待验证的Token
     * @return 验证结果
     */
    @Operation(summary = "验证Token", description = "验证用户Token是否有效")
    @PostMapping("/verify")
    public Result<Boolean> verifyToken(
            @Parameter(description = "待验证的Token", required = true)
            @RequestHeader("Authorization") String token) {
        // 去掉Bearer前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean isValid = userAuthService.verifyToken(token);
        return Result.success(isValid);
    }

    /**
     * 获取用户登录设备列表
     *
     * @param userId 用户ID
     * @return 设备列表
     */
    @Operation(summary = "获取用户登录设备列表", description = "获取用户所有登录的设备列表")
    @GetMapping("/devices/{userId}")
    public Result<Map<String, Object>> getUserLoginDevices(
            @Parameter(description = "用户ID", required = true)
            @PathVariable String userId) {
        Map<String, Object> devices = userAuthService.getUserLoginDevices(userId);
        return Result.success(devices);
    }
}
