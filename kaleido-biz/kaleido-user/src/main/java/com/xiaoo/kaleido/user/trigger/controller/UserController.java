package com.xiaoo.kaleido.user.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.command.ChangeNickNameCommand;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
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
 * 用户API
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

    /**
     * 查询用户自身信息
     *
     * @return 用户信息响应
     */
    @GetMapping
    public Result<UserInfoResponse> getUserInfo() {
        UserInfoResponse userInfoResponse = userQueryService.findById(StpUserUtil.getLoginId());
        return Result.success(userInfoResponse);
    }

    /**
     * 修改用户昵称
     *
     * @param request 修改昵称请求
     * @return 空响应
     */
    @PutMapping("/change-nickname")
    public Result<Void> updateNickname(@Valid @RequestBody ChangeNickNameCommand request) {
        String userId = StpUserUtil.getLoginId();
        userCommandService.changeNickName(userId, request.getNickName());
        return Result.success();
    }

    /**
     * 更新用户头像
     *
     * @param avatarUrl 头像URL，不能为空
     * @return 空响应
     */
    @PutMapping("/avatar")
    public Result<Void> updateAvatar(
            @NotBlank(message = "头像URL不能为空")
            @RequestParam String avatarUrl) {
        String userId = StpUserUtil.getLoginId();
        userCommandService.updateAvatar(userId, avatarUrl);
        return Result.success();
    }

    /**
     * 分页查询用户列表
     *
     * @param req 分页查询请求
     * @return 分页用户列表
     */
    @GetMapping("/page")
    public Result<PageInfo<UserInfoResponse>> pageQuery(@Valid UserPageQueryReq req) {
        PageInfo<UserInfoResponse> pageResult = userQueryService.pageQuery(req);
        return Result.success(pageResult);
    }
}
