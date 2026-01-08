package com.xiaoo.kaleido.user.trigger.rpc;

import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.user.application.command.UserCommandService;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
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
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcUserServiceImpl implements IRpcUserService {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Override
    public Result<UserInfoResponse> getById(
            String userId) {
        UserInfoResponse data = userQueryService.findById(userId);
        return Result.success(data);
    }

    @Override
    public Result<UserInfoResponse> getByTelephone(
            String telephone) {
        UserInfoResponse data = userQueryService.findByTelephone(telephone);
        return Result.success(data);
    }

    @Override
    public Result<String> register(
            AddUserCommand command) {
        String data = userCommandService.createUser(command);
        return Result.success(data);
    }

    @Override
    public Result<Void> login(String userId) {
        userCommandService.login(userId);
        return Result.success();
    }

    @Override
    public Result<Void> logout(
            String userId) {
        userCommandService.logout(userId);
        return Result.success();
    }
}
