package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.request.PageUserQueryRequest;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.api.user.response.UserBasicInfoVO;
import com.xiaoo.kaleido.api.user.response.UserInfoVO;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public Result<UserBasicInfoVO> getById(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        return userOperateFacadeService.getById(userId);
    }

    @Operation(summary = "更新用户信息", description = "更新用户基本信息，支持部分更新（null值表示不更新该字段）")
    @PutMapping("/update")
    public Result<UserBasicInfoVO> updateUserInfo(
            @Parameter(description = "更新用户信息请求参数", required = true)
            @RequestBody UpdateUserInfoRequest request) {
        return userOperateFacadeService.updateUserInfo(request);
    }

    @Operation(summary = "查询用户列表（不分页）", description = "根据查询条件返回匹配的用户列表，包含邀请人昵称等扩展信息，支持多条件组合查询")
    @PostMapping("/query-list")
    public Result<java.util.List<UserInfoVO>> query(
            @Parameter(description = "用户查询请求参数", required = true)
            @RequestBody UserQueryRequest request) {
        return userOperateFacadeService.query(request);
    }

    @Operation(summary = "分页查询用户列表", description = "根据查询条件和分页参数返回分页结果，包含邀请人昵称等扩展信息，支持多条件组合查询")
    @PostMapping("/query-list-page")
    public Result<PageResp<UserInfoVO>> pageQuery(
            @Parameter(description = "用户查询请求参数", required = true)
            @RequestBody PageUserQueryRequest request) {
        return userOperateFacadeService.pageQuery(request);
    }

}
