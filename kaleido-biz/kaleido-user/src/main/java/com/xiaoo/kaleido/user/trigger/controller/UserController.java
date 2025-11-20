package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.api.user.response.UserOperateVo;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 * 提供用户信息的查询和更新等操作接口
 * 
 * @author ouyucheng
 * @date 2025/11/20
 */
@Tag(name = "用户管理", description = "用户信息管理相关API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserOperateFacadeService userOperateFacadeService;

    @Operation(summary = "获取用户信息", description = "根据用户ID查询用户详情")
    @GetMapping("/{userId}")
    public Result<UserOperateVo> getById(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Long userId) {
        return userOperateFacadeService.getById(userId);
    }

    @Operation(summary = "更新用户信息", description = "更新用户基本信息，支持部分更新（null值表示不更新该字段）")
    @PutMapping("/update")
    public Result<UserOperateVo> updateUserInfo(
            @Parameter(description = "更新用户信息请求参数", required = true) 
            @RequestBody UpdateUserInfoRequest request) {
        return userOperateFacadeService.updateUserInfo(request);
    }

    @Operation(summary = "查询用户列表（不分页）", description = "根据查询条件返回匹配的用户列表，支持多条件组合查询")
    @ApiResponse(responseCode = "200", description = "成功获取用户列表")
    @ApiResponse(responseCode = "400", description = "请求参数错误")
    @ApiResponse(responseCode = "500", description = "服务器内部错误")
    @PostMapping("/query-list")
    public Result<java.util.List<UserOperateVo>> listUsers(
            @Parameter(description = "用户查询请求参数", required = true) 
            @RequestBody UserQueryRequest request) {
        return userOperateFacadeService.listUsers(request);
    }

    @Operation(summary = "分页查询用户列表", description = "根据查询条件和分页参数返回分页结果，支持多条件组合查询")
    @ApiResponse(responseCode = "200", description = "成功获取用户列表")
    @ApiResponse(responseCode = "400", description = "请求参数错误")
    @ApiResponse(responseCode = "500", description = "服务器内部错误")
    @PostMapping("/query-list-page")
    public Result<java.util.List<UserOperateVo>> listUsersPage(
            @Parameter(description = "用户查询请求参数", required = true) 
            @RequestBody UserQueryRequest request,
            @Parameter(description = "页码（从1开始）", example = "1") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页大小（最大100）", example = "20") 
            @RequestParam(defaultValue = "20") int size) {
        return userOperateFacadeService.listUsers(request, page, size);
    }

}
