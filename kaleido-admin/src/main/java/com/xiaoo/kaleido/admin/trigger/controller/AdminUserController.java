package com.xiaoo.kaleido.admin.trigger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoo.kaleido.api.admin.auth.command.*;
import com.xiaoo.kaleido.api.admin.auth.request.AdminUserPageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.admin.application.command.AdminUserCommandService;
import com.xiaoo.kaleido.admin.application.query.AdminUserQueryService;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "管理员管理", description = "管理员相关接口")
public class AdminUserController {

    private final AdminUserCommandService adminUserCommandService;
    private final AdminUserQueryService adminUserQueryService;

    /**
     * 更新管理员信息
     *
     * @param adminUserId 管理员ID
     * @param command 更新管理员信息命令
     * @return 操作结果
     */
    @Operation(summary = "更新管理员信息", description = "更新管理员信息")
    @PutMapping("/{adminUserId}")
    public Result<Void> updateAdminUser(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId,
            @Parameter(description = "更新管理员信息命令", required = true)
            @Valid @RequestBody UpdateAdminUserCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminUserCommandService.updateAdminUser(command);
        return Result.success();
    }

    /**
     * 启用管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @Operation(summary = "启用管理员", description = "启用指定的管理员")
    @PutMapping("/{adminUserId}/enable")
    public Result<Void> enableAdminUser(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId) {
        adminUserCommandService.enableAdminUser(new EnableAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 冻结管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @Operation(summary = "冻结管理员", description = "冻结指定的管理员")
    @PutMapping("/{adminUserId}/freeze")
    public Result<Void> freezeAdminUser(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId) {
        adminUserCommandService.freezeAdminUser(new FreezeAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 删除管理员
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @Operation(summary = "删除管理员", description = "删除指定的管理员")
    @DeleteMapping("/{adminUserId}")
    public Result<Void> deleteAdminUser(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId) {
        adminUserCommandService.deleteAdminUser(new DeleteAdminUserCommand(adminUserId));
        return Result.success();
    }

    /**
     * 修改管理员密码
     *
     * @param adminUserId 管理员ID
     * @param command 修改管理员密码命令
     * @return 操作结果
     */
    @Operation(summary = "修改管理员密码", description = "修改管理员密码")
    @PutMapping("/{adminUserId}/password")
    public Result<Void> changePassword(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId,
            @Parameter(description = "修改管理员密码命令", required = true)
            @Valid @RequestBody ChangeAdminUserPasswordCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminUserCommandService.changePassword(command);
        return Result.success();
    }

    /**
     * 分配角色给管理员
     *
     * @param adminUserId 管理员ID
     * @param command 分配角色命令
     * @return 操作结果
     */
    @Operation(summary = "分配角色给管理员", description = "分配角色给管理员")
    @PostMapping("/{adminUserId}/roles")
    public Result<Void> assignRoles(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId,
            @Parameter(description = "分配角色命令", required = true)
            @Valid @RequestBody AssignRolesToAdminUserCommand command) {

        // 设置管理员ID到命令对象
        command.setAdminUserId(adminUserId);
        adminUserCommandService.assignRoles(command);
        return Result.success();
    }

    /**
     * 从管理员移除角色
     *
     * @param adminUserId 管理员ID
     * @param roleIds 角色ID列表
     * @return 操作结果
     */
    @Operation(summary = "从管理员移除角色", description = "从管理员移除指定的角色")
    @DeleteMapping("/{adminUserId}/roles")
    public Result<Void> removeRoles(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId,
            @Parameter(description = "角色ID列表", required = true)
            @RequestBody List<String> roleIds) {
        adminUserCommandService.removeRoles(adminUserId, roleIds);
        return Result.success();
    }

    /**
     * 清空管理员所有角色
     *
     * @param adminUserId 管理员ID
     * @return 操作结果
     */
    @Operation(summary = "清空管理员所有角色", description = "清空管理员的所有角色")
    @DeleteMapping("/{adminUserId}/roles/all")
    public Result<Void> clearRoles(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId) {
        adminUserCommandService.clearRoles(adminUserId);
        return Result.success();
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param adminUserId 管理员ID
     * @return 管理员信息
     */
    @Operation(summary = "根据ID查询管理员信息", description = "根据ID查询管理员信息")
    @GetMapping("/{adminUserId}")
    public Result<AdminUserInfoResponse> getAdminUserById(
            @Parameter(description = "管理员ID", required = true)
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
    @Operation(summary = "根据账号查询管理员信息", description = "根据账号查询管理员信息")
    @GetMapping("/username/{username}")
    public Result<AdminUserInfoResponse> getAdminUserByUsername(
            @Parameter(description = "管理员账号", required = true)
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
    @Operation(summary = "根据手机号查询管理员信息", description = "根据手机号查询管理员信息")
    @GetMapping("/mobile/{mobile}")
    public Result<AdminUserInfoResponse> getAdminUserByMobile(
            @Parameter(description = "手机号", required = true)
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
    @Operation(summary = "分页查询管理员", description = "分页查询管理员列表")
    @GetMapping("/page")
    public Result<IPage<AdminUserInfoResponse>> pageAdminUsers(
            @Parameter(description = "分页查询条件")
            AdminUserPageQueryReq pageQueryReq) {
        return Result.success(adminUserQueryService.pageQuery(pageQueryReq));
    }


    /**
     * 获取管理员的所有权限
     *
     * @param adminUserId 管理员ID
     * @return 权限ID列表
     */
    @Operation(summary = "获取管理员的所有权限", description = "获取管理员的所有权限（通过角色）")
    @GetMapping("/{adminUserId}/permissions")
    public Result<List<String>> getAdminUserPermissions(
            @Parameter(description = "管理员ID", required = true)
            @PathVariable String adminUserId) {
        List<String> permissions = adminUserQueryService.getPermissionsByAdminUserId(adminUserId);
        return Result.success(permissions);
    }
}
