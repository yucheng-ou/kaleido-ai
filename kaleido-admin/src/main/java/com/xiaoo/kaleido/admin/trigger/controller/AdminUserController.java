package com.xiaoo.kaleido.admin.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.user.command.*;
import com.xiaoo.kaleido.api.admin.user.request.AdminUserPageQueryReq;
import com.xiaoo.kaleido.api.admin.user.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.admin.application.command.AdminCommandService;
import com.xiaoo.kaleido.admin.application.query.IAdminUserQueryService;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 *
 * @author ouyucheng
 * @date 2026/1/4
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminCommandService adminCommandService;
    private final IAdminUserQueryService adminUserQueryService;

    /**
     * 更新管理员信息
     *
     * @param adminUserId 管理员ID
     * @param command     更新管理员信息命令
     * @return 操作结果
     */
    @PutMapping("/{adminUserId}")
    public Result<Void> updateAdminUser(
            @PathVariable String adminUserId,
            @Valid @RequestBody UpdateAdminUserCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminCommandService.updateAdminUser(command);
        return Result.success();
    }

    /**
     * 启用管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @PutMapping("/{adminUserId}/enable")
    public Result<Void> enableAdminUser(
            @PathVariable String adminUserId) {
        adminCommandService.enableAdminUser(new EnableAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 冻结管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @PutMapping("/{adminUserId}/freeze")
    public Result<Void> freezeAdminUser(
            @PathVariable String adminUserId) {
        adminCommandService.freezeAdminUser(new FreezeAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 删除管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @DeleteMapping("/{adminUserId}")
    public Result<Void> deleteAdminUser(
            @PathVariable String adminUserId) {
        adminCommandService.deleteAdminUser(new DeleteAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 修改管理员密码
     *
     * @param adminUserId 管理员ID
     * @param command     修改管理员密码命令
     * @return 操作结果
     */
    @PutMapping("/{adminUserId}/password")
    public Result<Void> changePassword(
            @PathVariable String adminUserId,
            @Valid @RequestBody ChangeAdminUserPasswordCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminCommandService.changePassword(command);
        return Result.success();
    }

    /**
     * 分配角色给管理员
     *
     * @param adminUserId 管理员ID
     * @param command     分配角色命令
     * @return 操作结果
     */
    @PostMapping("/{adminUserId}/roles")
    public Result<Void> assignRoles(
            @PathVariable String adminUserId,
            @Valid @RequestBody AssignRolesToAdminUserCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminCommandService.assignRoles(command);
        return Result.success();
    }

    /**
     * 从管理员移除角色
     *
     * @param adminUserId 管理员ID
     * @param roleIds     角色ID列表
     * @return 操作结果
     */
    @DeleteMapping("/{adminUserId}/roles")
    public Result<Void> removeRoles(
            @PathVariable String adminUserId,
            @RequestBody List<String> roleIds) {
        adminCommandService.removeRoles(adminUserId, roleIds);
        return Result.success();
    }

    /**
     * 清空管理员所有角色
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @DeleteMapping("/{adminUserId}/roles/all")
    public Result<Void> clearRoles(
            @PathVariable String adminUserId) {
        adminCommandService.clearRoles(adminUserId);
        return Result.success();
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param adminUserId 管理员ID
     * @return 管理员信息
     */
    @GetMapping("/{adminUserId}")
    public Result<AdminUserInfoResponse> getAdminUserById(
            @PathVariable String adminUserId) {
        AdminUserInfoResponse adminUser = adminUserQueryService.findById(adminUserId);
        return Result.success(adminUser);
    }

    /**
     * 根据账号查询管理员信息
     *
     * @param username 管理员账号
     * @return 管理员信息
     */
    @GetMapping("/username/{username}")
    public Result<AdminUserInfoResponse> getAdminUserByUsername(
            @PathVariable String username) {
        AdminUserInfoResponse adminUser = adminUserQueryService.findByUsername(username);
        return Result.success(adminUser);
    }

    /**
     * 根据手机号查询管理员信息
     *
     * @param mobile 手机号
     * @return 管理员信息
     */
    @GetMapping("/mobile/{mobile}")
    public Result<AdminUserInfoResponse> getAdminUserByMobile(
            @PathVariable String mobile) {
        AdminUserInfoResponse adminUser = adminUserQueryService.findByMobile(mobile);
        return Result.success(adminUser);
    }

    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageInfo<AdminUserInfoResponse>> pageAdminUsers(
            AdminUserPageQueryReq pageQueryReq) {
        return Result.success(adminUserQueryService.pageQuery(pageQueryReq));
    }


    /**
     * 获取管理员的所有权限
     *
     * @param adminUserId 管理员ID
     * @return 权限ID列表
     */
    @GetMapping("/{adminUserId}/permissions")
    public Result<List<String>> getAdminUserPermissions(
            @PathVariable String adminUserId) {
        List<String> permissions = adminUserQueryService.getPermissionsByAdminUserId(adminUserId);
        return Result.success(permissions);
    }
}
