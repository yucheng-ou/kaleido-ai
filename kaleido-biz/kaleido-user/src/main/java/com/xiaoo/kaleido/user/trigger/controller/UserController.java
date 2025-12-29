package com.xiaoo.kaleido.user.trigger.controller;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.command.ChangeNickNameCommand;
import com.xiaoo.kaleido.user.application.command.UserCommandService;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Operation(summary = "查询用户信息", description = "根据用户ID查询用户信息")
    @GetMapping("/{userId}")
    public Result<UserInfoResponse> getUserById(
            @Parameter(description = "用户ID", required = true)
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        UserInfoResponse userInfoResponse = userQueryService.findById(userId);
        return Result.success(userInfoResponse);
    }

    @Operation(summary = "根据手机号查询用户", description = "根据手机号查询用户信息")
    @GetMapping("/by-telephone/{telephone}")
    public Result<UserInfoResponse> getUserByTelephone(
            @Parameter(description = "手机号", required = true, example = "13800138000")
            @NotBlank(message = "手机号不能为空")
            @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
            @PathVariable String telephone) {
        UserInfoResponse userInfoResponse = userQueryService.findByTelephone(telephone);
        return Result.success(userInfoResponse);
    }

    @Operation(summary = "根据邀请码查询用户", description = "根据邀请码查询用户信息")
    @GetMapping("/by-invite-code/{inviteCode}")
    public Result<UserInfoResponse> getUserByInviteCode(
            @Parameter(description = "邀请码", required = true, example = "INVITE123")
            @NotBlank(message = "邀请码不能为空")
            @PathVariable String inviteCode) {
        UserInfoResponse userInfoResponse = userQueryService.findByInviteCode(inviteCode);
        return Result.success(userInfoResponse);
    }

    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody AddUserCommand request) {
        String userId = userCommandService.createUser(request);
        return Result.success(userId);
    }

    @Operation(summary = "修改用户昵称", description = "修改当前用户的昵称")
    @PutMapping("/change-nickname")
    public Result<Void> updateNickname(@Valid @RequestBody ChangeNickNameCommand request) {
        userCommandService.changeNickName(request);
        return Result.success();
    }

    @Operation(summary = "冻结用户", description = "冻结指定用户")
    @PutMapping("/{userId}/freeze")
    public Result<Void> freezeUser(
            @Parameter(description = "用户ID", required = true)
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.freezeUser(userId);
        return Result.success();
    }

    @Operation(summary = "解冻用户", description = "解冻指定用户")
    @PutMapping("/{userId}/unfreeze")
    public Result<Void> unfreezeUser(
            @Parameter(description = "用户ID", required = true)
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.unfreezeUser(userId);
        return Result.success();
    }

    @Operation(summary = "删除用户", description = "删除指定用户（逻辑删除）")
    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true)
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.deleteUser(userId);
        return Result.success();
    }

    @Operation(summary = "更新用户头像", description = "更新指定用户的头像")
    @PutMapping("/{userId}/avatar")
    public Result<Void> updateAvatar(
            @Parameter(description = "用户ID", required = true)
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId,
            @Parameter(description = "头像URL", required = true, example = "https://example.com/avatar.jpg")
            @NotBlank(message = "头像URL不能为空")
            @RequestParam String avatarUrl) {
        userCommandService.updateAvatar(userId, avatarUrl);
        return Result.success();
    }

    @Operation(summary = "分页查询用户", description = "根据昵称、手机号等条件分页查询用户")
    @GetMapping("/page")
    public Result<PageResp<UserInfoResponse>> pageQuery(@Valid UserPageQueryReq req) {
        PageResp<UserInfoResponse> pageResult = userQueryService.pageQuery(req);
        return Result.success(pageResult);
    }
}
