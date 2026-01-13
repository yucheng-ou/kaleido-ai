package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaoo.kaleido.api.admin.user.command.AddPermissionCommand;
import com.xiaoo.kaleido.api.admin.user.command.UpdatePermissionCommand;
import com.xiaoo.kaleido.api.admin.user.response.PermissionInfoResponse;
import com.xiaoo.kaleido.admin.application.command.PermissionCommandService;
import com.xiaoo.kaleido.admin.application.query.PermissionQueryService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器
 *
 * @author ouyucheng
 * @date 2026/1/9
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionCommandService permissionCommandService;
    private final PermissionQueryService permissionQueryService;

    /**
     * 创建权限
     *
     * @param command 创建权限命令
     * @return 权限ID
     */
    @SaCheckPermission(value = "admin:permission:create", type = StpAdminUtil.TYPE)
    @PostMapping
    public Result<String> createPermission(
            @Valid @RequestBody AddPermissionCommand command) {
        String permissionId = permissionCommandService.createPermission(command);
        return Result.success(permissionId);
    }

    /**
     * 更新权限信息
     *
     * @param permissionId 权限ID（从路径参数获取）
     * @param command 更新权限信息命令（不包含权限ID）
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:permission:update", type = StpAdminUtil.TYPE)
    @PutMapping("/{permissionId}")
    public Result<Void> updatePermission(
            @PathVariable String permissionId,
            @Valid @RequestBody UpdatePermissionCommand command) {
        permissionCommandService.updatePermission(permissionId, command);
        return Result.success();
    }

    /**
     * 更新权限编码
     *
     * @param permissionId 权限ID
     * @param code 新的权限编码
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:permission:update", type = StpAdminUtil.TYPE)
    @PutMapping("/{permissionId}/code")
    public Result<Void> updatePermissionCode(
            @PathVariable String permissionId,
            @RequestParam String code) {
        permissionCommandService.updatePermissionCode(permissionId, code);
        return Result.success();
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     * @return 操作结果
     */
    @SaCheckPermission(value = "admin:permission:delete", type = StpAdminUtil.TYPE)
    @DeleteMapping("/{permissionId}")
    public Result<Void> deletePermission(
            @PathVariable String permissionId) {
        permissionCommandService.deletePermission(permissionId);
        return Result.success();
    }

    /**
     * 根据ID查询权限信息
     *
     * @param permissionId 权限ID
     * @return 权限信息
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/{permissionId}")
    public Result<PermissionInfoResponse> getPermissionById(
            @PathVariable String permissionId) {
        PermissionInfoResponse permission = permissionQueryService.findById(permissionId);
        return Result.success(permission);
    }

    /**
     * 根据编码查询权限信息
     *
     * @param code 权限编码
     * @return 权限信息
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/code/{code}")
    public Result<PermissionInfoResponse> getPermissionByCode(
            @PathVariable String code) {
        PermissionInfoResponse permission = permissionQueryService.findByCode(code);
        return Result.success(permission);
    }

    /**
     * 根据父权限ID查询子权限列表
     *
     * @param parentId 父权限ID
     * @return 子权限列表
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/parent/{parentId}")
    public Result<List<PermissionInfoResponse>> getPermissionsByParentId(
            @PathVariable String parentId) {
        List<PermissionInfoResponse> permissions = permissionQueryService.findByParentId(parentId);
        return Result.success(permissions);
    }

    /**
     * 查询根权限列表
     *
     * @return 根权限列表
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/root")
    public Result<List<PermissionInfoResponse>> getRootPermissions() {
        List<PermissionInfoResponse> permissions = permissionQueryService.findRootPermissions();
        return Result.success(permissions);
    }

    /**
     * 获取权限树
     *
     * @return 权限树根节点列表
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/tree")
    public Result<List<PermissionInfoResponse>> getPermissionTree() {
        List<PermissionInfoResponse> permissionTree = permissionQueryService.getPermissionTree();
        return Result.success(permissionTree);
    }

    /**
     * 检查权限编码是否可用
     *
     * @param code 权限编码
     * @return 是否可用
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/check-code")
    public Result<Boolean> checkCodeAvailable(
            @RequestParam String code) {
        boolean isAvailable = permissionCommandService.isCodeAvailable(code);
        return Result.success(isAvailable);
    }

    /**
     * 检查权限是否存在且有效
     *
     * @param permissionId 权限ID
     * @return 是否存在且有效
     */
    @SaCheckPermission(value = "admin:permission:read", type = StpAdminUtil.TYPE)
    @GetMapping("/check-valid")
    public Result<Boolean> checkPermissionValid(
            @RequestParam String permissionId) {
        boolean isValid = permissionCommandService.isValidPermission(permissionId);
        return Result.success(isValid);
    }
}
