package com.xiaoo.kaleido.user.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.command.ChangeNickNameCommand;
import com.xiaoo.kaleido.user.application.command.UserCommandService;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
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
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping("/{userId}")
    public Result<UserInfoResponse> getUserById(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        UserInfoResponse userInfoResponse = userQueryService.findById(userId);
        return Result.success(userInfoResponse);
    }

    @GetMapping("/by-telephone/{telephone}")
    public Result<UserInfoResponse> getUserByTelephone(
            @NotBlank(message = "手机号不能为空")
            @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
            @PathVariable String telephone) {
        UserInfoResponse userInfoResponse = userQueryService.findByTelephone(telephone);
        return Result.success(userInfoResponse);
    }

    @GetMapping("/by-invite-code/{inviteCode}")
    public Result<UserInfoResponse> getUserByInviteCode(
            @NotBlank(message = "邀请码不能为空")
            @PathVariable String inviteCode) {
        UserInfoResponse userInfoResponse = userQueryService.findByInviteCode(inviteCode);
        return Result.success(userInfoResponse);
    }

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody AddUserCommand request) {
        String userId = userCommandService.createUser(request);
        return Result.success(userId);
    }

    @PutMapping("/change-nickname")
    public Result<Void> updateNickname(@Valid @RequestBody ChangeNickNameCommand request) {
        userCommandService.changeNickName(request);
        return Result.success();
    }

    @PutMapping("/{userId}/freeze")
    public Result<Void> freezeUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.freezeUser(userId);
        return Result.success();
    }

    @PutMapping("/{userId}/unfreeze")
    public Result<Void> unfreezeUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.unfreezeUser(userId);
        return Result.success();
    }

    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId) {
        userCommandService.deleteUser(userId);
        return Result.success();
    }

    @PutMapping("/{userId}/avatar")
    public Result<Void> updateAvatar(
            @NotBlank(message = "用户ID不能为空")
            @PathVariable String userId,
            @NotBlank(message = "头像URL不能为空")
            @RequestParam String avatarUrl) {
        userCommandService.updateAvatar(userId, avatarUrl);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageInfo<UserInfoResponse>> pageQuery(@Valid UserPageQueryReq req) {
        PageInfo<UserInfoResponse> pageResult = userQueryService.pageQuery(req);
        return Result.success(pageResult);
    }
}
