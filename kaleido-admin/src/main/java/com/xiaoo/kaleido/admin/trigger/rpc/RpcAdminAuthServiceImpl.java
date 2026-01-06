package com.xiaoo.kaleido.admin.trigger.rpc;

import com.xiaoo.kaleido.admin.application.command.AdminUserCommandService;
import com.xiaoo.kaleido.admin.application.query.AdminUserQueryService;
import com.xiaoo.kaleido.api.admin.auth.IRpcAdminAuthService;
import com.xiaoo.kaleido.api.admin.auth.command.AddAdminUserCommand;
import com.xiaoo.kaleido.api.admin.auth.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.auth.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
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
 * 管理后台RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
@Tag(name = "管理后台RPC服务", description = "管理后台认证相关的RPC接口")
public class RpcAdminAuthServiceImpl implements IRpcAdminAuthService {

    private final AdminUserCommandService adminUserCommandService;
    private final AdminUserQueryService adminUserQueryService;

    @Override
    @Operation(summary = "管理员注册", description = "注册新的管理员账号")
    public Result<String> register(
            @Parameter(description = "添加管理员命令")
            AddAdminUserCommand command) {

        return Result.success(adminUserCommandService.createAdminUser(command));
    }

    @Override
    @Operation(summary = "管理员登录", description = "管理员账号登录")
    public Result<Void> login(
            @Parameter(description = "用户ID", example = "1234567890123456789")
            String adminUserId) {

        adminUserCommandService.login(adminUserId);
        return Result.success();
    }

    @Override
    public Result<AdminUserInfoResponse> findByMobile(
            @Parameter(description = "用户手机号", example = "13066668888")
            String telephone) {

        return Result.success(adminUserQueryService.findByMobile(telephone));
    }
}
