package com.xiaoo.kaleido.admin.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.user.command.*;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.admin.application.command.AdminCommandService;
import com.xiaoo.kaleido.admin.application.query.IAdminQueryService;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员API
 *
 * @author ouyucheng
 * @date 2026/1/4
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminCommandService adminCommandService;
    private final IAdminQueryService adminQueryService;

    /**
     * 更新管理员信息
     *
     * @param adminId 管理员ID（从路径参数获取）
     * @param command     更新管理员信息命令（不包含管理员ID）
     * @return 操作结果
     */
    @PutMapping("/{adminId}")
    public Result<Void> updateAdmin(
            @PathVariable String adminId,
            @Valid @RequestBody UpdateAdminCommand command) {
        adminCommandService.updateAdmin(adminId, command);
        return Result.success();
    }

    /**
     * 解冻管理员
     *
     * @param adminId 管理员ID
     * @return 操作结果
     */
    @PutMapping("/{adminId}/enable")
    public Result<Void> enableAdmin(
            @PathVariable String adminId) {
        adminCommandService.enableAdmin(adminId);
        return Result.success();
    }

    /**
     * 冻结管理员
     *
     * @param adminId 管理员ID
     * @return 操作结果
     */
    @PutMapping("/{adminId}/freeze")
    public Result<Void> freezeAdmin(
            @PathVariable String adminId) {
        adminCommandService.freezeAdmin(adminId);
        return Result.success();
    }

    /**
     * 分配角色给管理员
     *
     * @param adminId 管理员ID（从路径参数获取）
     * @param command     分配角色命令（不包含管理员ID）
     * @return 操作结果
     */
    @PostMapping("/{adminId}/roles")
    public Result<Void> assignRoles(
            @PathVariable String adminId,
            @Valid @RequestBody AssignRolesToAdminCommand command) {
        adminCommandService.assignRoles(adminId, command);
        return Result.success();
    }

    /**
     * 根据ID查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    @GetMapping("/{adminId}")
    public Result<AdminInfoResponse> getAdminById(
            @PathVariable String adminId) {
        AdminInfoResponse admin = adminQueryService.findById(adminId);
        return Result.success(admin);
    }


    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageInfo<AdminInfoResponse>> pageAdminList(
            AdminPageQueryReq pageQueryReq) {
        return Result.success(adminQueryService.pageQuery(pageQueryReq));
    }


    /**
     * 获取管理员的所有权限
     *
     * @param adminId 管理员ID
     * @return 权限ID列表
     */
    @GetMapping("/{adminId}/permissions")
    public Result<List<String>> getAdminPermissions(
            @PathVariable String adminId) {
        List<String> permissions = adminQueryService.getPermissionsByAdminId(adminId);
        return Result.success(permissions);
    }
}
