package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.query.IUserQueryService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 用户查询服务实现
 * 负责用户相关的读操作编排，通过RPC调用用户服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements IUserQueryService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcUserService rpcUserService;

    @Override
    public UserInfoResponse findById(String userId) {
        Result<UserInfoResponse> result = rpcUserService.getById(userId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("查询用户信息失败，用户ID: {}, 错误信息: {}", userId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public UserInfoResponse findByTelephone(String telephone) {
        Result<UserInfoResponse> result = rpcUserService.getByTelephone(telephone);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("根据手机号查询用户失败，手机号: {}, 错误信息: {}", telephone, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public PageInfo<UserInfoResponse> pageQuery(UserPageQueryReq req) {
        Result<PageInfo<UserInfoResponse>> result = rpcUserService.pageQueryUsers(req);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("分页查询用户列表失败，错误信息: {}", result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }
}
