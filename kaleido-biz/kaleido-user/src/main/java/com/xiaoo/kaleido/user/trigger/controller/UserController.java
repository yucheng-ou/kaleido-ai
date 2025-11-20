package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
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

}
