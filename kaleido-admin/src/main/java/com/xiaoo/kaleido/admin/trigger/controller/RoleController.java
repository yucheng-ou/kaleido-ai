package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaoo.kaleido.api.admin.user.command.*;
import com.xiaoo.kaleido.api.admin.user.response.RoleInfoResponse;
import com.xiaoo.kaleido.admin.application.command.RoleCommandService;
import com.xiaoo.kaleido.admin.application.query.RoleQueryService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author ouyucheng
 * @date 2026/1/9
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleCommandService roleCommandService;
    private final RoleQueryService roleQueryService;

    /**
     * 创建角色
     *
     * @param command 创建角色命令
     * @return 角色ID
     */
    @SaCheckPermission(value = "admin:role:create", type = StpAdminUtil.TYPE)
    @PostMapping
    public Result<String> createRole(
            @Valid @RequestBody AddRoleCommand command) {
        String roleId = roleCommandService.createRole(command);
        return Result.success(roleId);
    }

    /**
     * 更新角色信息
     *
     * @param roleId 角色ID（从路径参数获取）
     * @param command 更新角色信息命令（不包含角色ID）
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:role:update", type = StpAdminUtil.TYPE)
    @PutMapping("/{roleId}")
    public Result<Void> updateRole(
            @PathVariable String roleId,
            @Valid @RequestBody UpdateRoleCommand command) {
        roleCommandService.updateRole(roleId, command);
        return Result.success();
    }

    /**
     * 启用角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:role:enable", type = StpAdminUtil.TYPE)
    @PutMapping("/{roleId}/enable")
    public Result<Void> enableRole(
            @PathVariable String roleId) {
        roleCommandService.enableRole(roleId);
        return Result.success();
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:role:disable", type = StpAdminUtil.TYPE)
    @PutMapping("/{roleId}/disable")
    public Result<Void> disableRole(
            @PathVariable String roleId) {
        roleCommandService.disableRole(roleId);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:role:delete", type = StpAdminUtil.TYPE)
    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(
            @PathVariable String roleId) {
        roleCommandService.deleteRole(roleId);
        return Result.success();
    }

    /**
     * 分配权限给角色
     *
     * @param roleId 角色ID（从路径参数获取）
     * @param command 分配权限给角色命令（不包含角色ID）
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:role:assign-permissions", type = StpAdminUtil.TYPE)
    @PostMapping("/{roleId}/permissions")
    public Result<Void> assignPermissions(
            @PathVariable String roleId,
            @Valid @RequestBody AssignPermissionsToRoleCommand command) {
        roleCommandService.assignPermissions(roleId, command);
        return Result.success();
    }


    /**
     * 根据ID查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @SaCheckPermission(value = "admin:role:read", type = StpAdminUtil.TYPE)
    @GetMapping("/{roleId}")
    public Result<RoleInfoResponse> getRoleById(
            @PathVariable String roleId) {
        RoleInfoResponse role = roleQueryService.findById(roleId);
        return Result.success(role);
    }

    /**
     * 根据编码查询角色信息
     *
     * @param code 角色编码
     * @return 角色信息
     */
    @SaCheckPermission(value = "admin:role:read", type = StpAdminUtil.TYPE)
    @GetMapping("/code/{code}")
    public Result<RoleInfoResponse> getRoleByCode(
            @PathVariable String code) {
        RoleInfoResponse role = roleQueryService.findByCode(code);
        return Result.success(role);
    }


    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @SaCheckPermission(value = "admin:role:read", type = StpAdminUtil.TYPE)
    @GetMapping("/list")
    public Result<List<RoleInfoResponse>> getRoleList() {
        List<RoleInfoResponse> roleList = roleQueryService.getRoleList();
        return Result.success(roleList);
    }
}
