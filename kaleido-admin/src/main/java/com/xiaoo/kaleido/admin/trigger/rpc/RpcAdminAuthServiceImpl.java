package com.xiaoo.kaleido.admin.trigger.rpc;

import com.xiaoo.kaleido.admin.application.command.AdminCommandService;
import com.xiaoo.kaleido.admin.application.query.IAdminQueryService;
import com.xiaoo.kaleido.api.admin.user.IRpcAdminAuthService;
import com.xiaoo.kaleido.api.admin.user.command.RegisterAdminCommand;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
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
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
@Validated
public class RpcAdminAuthServiceImpl implements IRpcAdminAuthService {

    private final AdminCommandService adminCommandService;
    private final IAdminQueryService adminUserQueryService;

    @Override
    public Result<String> register(
            RegisterAdminCommand command) {

        return Result.success(adminCommandService.createAdmin(command));
    }

    @Override
    public Result<Void> login(
            String adminUserId) {

        adminCommandService.login(adminUserId);
        return Result.success();
    }

    @Override
    public Result<AdminInfoResponse> findByMobile(String telephone) {

        return Result.success(adminUserQueryService.findByMobile(telephone));
    }
}
