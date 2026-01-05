package com.xiaoo.kaleido.user.trigger.rpc;

import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.user.application.command.UserCommandService;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 用户RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(
        version = RpcConstants.DUBBO_VERSION,
        group = RpcConstants.DUBBO_GROUP,
        timeout = RpcConstants.DEFAULT_TIMEOUT
)
@Tag(name = "用户RPC服务", description = "用户相关的RPC接口")
public class IRpcUserServiceImpl implements IRpcUserService {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Override
    @Operation(summary = "根据ID查询用户", description = "根据用户ID查询用户详细信息")
    public Result<UserInfoResponse> getById(
            @NotBlank(message = "用户ID不能为空")
            @Parameter(description = "用户ID", example = "1234567890123456789")
            String userId) {
        UserInfoResponse data = userQueryService.findById(userId);
        return Result.success(data);
    }

    @Override
    @Operation(summary = "根据手机号查询用户", description = "根据用户手机号查询用户详细信息")
    public Result<UserInfoResponse> getByTelephone(
            @NotBlank(message = "用户手机号不能为空")
            @Parameter(description = "用户手机号", example = "13066668888")
            String telephone) {
        UserInfoResponse data = userQueryService.findByTelephone(telephone);
        return Result.success(data);
    }

    @Override
    @Operation(summary = "用户注册", description = "注册新用户")
    public Result<String> register(
            @Valid
            @Parameter(description = "添加用户命令")
            AddUserCommand command) {
        String data = userCommandService.createUser(command);
        return Result.success(data);
    }

    @Override
    @Operation(summary = "记录用户登录", description = "记录用户登录时间并更新最后登录时间")
    public Result<Void> login(
            @NotBlank(message = "用户ID不能为空")
            @Parameter(description = "用户ID", example = "1234567890123456789")
            String userId) {
        userCommandService.login(userId);
        return Result.success();
    }

    @Override
    @Operation(summary = "记录用户登出", description = "记录用户登出日志")
    public Result<Void> logout(
            @NotBlank(message = "用户ID不能为空")
            @Parameter(description = "用户ID", example = "1234567890123456789")
            String userId) {
        userCommandService.logout(userId);
        return Result.success();
    }
}
