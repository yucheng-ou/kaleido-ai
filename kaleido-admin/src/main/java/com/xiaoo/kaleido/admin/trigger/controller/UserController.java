package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.admin.application.command.impl.UserCommandService;
import com.xiaoo.kaleido.admin.application.query.IUserQueryService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理API（管理后台）
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserQueryService userQueryService;
    private final UserCommandService userCommandService;

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @SaCheckPermission(value = "admin:user:read", type = StpAdminUtil.TYPE)
    @GetMapping("/{userId}")
    public Result<UserInfoResponse> getUserById(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        UserInfoResponse userInfoResponse = userQueryService.findById(userId);
        return Result.success(userInfoResponse);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 手机号
     * @return 用户信息
     */
    @SaCheckPermission(value = "admin:user:read", type = StpAdminUtil.TYPE)
    @GetMapping("/by-telephone/{telephone}")
    public Result<UserInfoResponse> getUserByTelephone(
            @NotBlank(message = "手机号不能为空")
            @PathVariable String telephone) {
        UserInfoResponse userInfoResponse = userQueryService.findByTelephone(telephone);
        return Result.success(userInfoResponse);
    }

    /**
     * 冻结用户
     *
     * @param userId 用户ID，不能为空
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:user:freeze", type = StpAdminUtil.TYPE)
    @PutMapping("/{userId}/freeze")
    public Result<Void> freezeUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.freezeUser(userId);
        return Result.success();
    }

    /**
     * 解冻用户
     *
     * @param userId 用户ID，不能为空
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:user:unfreeze", type = StpAdminUtil.TYPE)
    @PutMapping("/{userId}/unfreeze")
    public Result<Void> unfreezeUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.unfreezeUser(userId);
        return Result.success();
    }

    /**
     * 删除用户（软删除）
     *
     * @param userId 用户ID，不能为空
     * @return 空响应
     */
    @SaCheckPermission(value = "admin:user:delete", type = StpAdminUtil.TYPE)
    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.deleteUser(userId);
        return Result.success();
    }

    /**
     * 分页查询用户列表
     *
     * @param req 分页查询请求
     * @return 分页用户列表
     */
    @SaCheckPermission(value = "admin:user:read", type = StpAdminUtil.TYPE)
    @GetMapping("/page")
    public Result<PageInfo<UserInfoResponse>> pageQuery(@Valid UserPageQueryReq req) {
        PageInfo<UserInfoResponse> pageResult = userQueryService.pageQuery(req);
        return Result.success(pageResult);
    }
}
