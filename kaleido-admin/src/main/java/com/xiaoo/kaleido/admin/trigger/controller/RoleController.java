package com.xiaoo.kaleido.admin.trigger.controller;

import com.xiaoo.kaleido.api.admin.user.command.*;
import com.xiaoo.kaleido.api.admin.user.response.RoleInfoResponse;
import com.xiaoo.kaleido.api.admin.user.response.RoleTreeResponse;
import com.xiaoo.kaleido.admin.application.command.RoleCommandService;
import com.xiaoo.kaleido.admin.application.query.RoleQueryService;
import com.xiaoo.kaleido.base.result.Result;
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
    @PutMapping("/{roleId}/enable")
    public Result<Void> enableRole(
            @PathVariable String roleId) {
        roleCommandService.enableRole(new EnableRoleCommand(roleId));
        return Result.success();
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @PutMapping("/{roleId}/disable")
    public Result<Void> disableRole(
            @PathVariable String roleId) {
        roleCommandService.disableRole(new DisableRoleCommand(roleId));
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
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
    @GetMapping("/code/{code}")
    public Result<RoleInfoResponse> getRoleByCode(
            @PathVariable String code) {
        RoleInfoResponse role = roleQueryService.findByCode(code);
        return Result.success(role);
    }


    /**
     * 获取角色树
     *
     * @return 角色树根节点列表
     */
    @GetMapping("/tree")
    public Result<List<RoleTreeResponse>> getRoleTree() {
        List<RoleTreeResponse> roleTree = roleQueryService.getRoleTree();
        return Result.success(roleTree);
    }
}
