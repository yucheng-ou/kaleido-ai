package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IUserCommandService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 用户命令服务（应用层）
 * 负责用户相关的写操作编排，通过RPC调用用户服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService implements IUserCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcUserService rpcUserService;

    /**
     * 冻结用户
     *
     * @param userId 用户ID
     */

    @Override
    public void freezeUser(String userId) {
        Result<Void> result = rpcUserService.freezeUser(userId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("冻结用户失败，用户ID: {}, 错误信息: {}", userId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("冻结用户成功，用户ID: {}", userId);
    }

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     */
    @Override
    public void unfreezeUser(String userId) {
        Result<Void> result = rpcUserService.unfreezeUser(userId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("解冻用户失败，用户ID: {}, 错误信息: {}", userId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("解冻用户成功，用户ID: {}", userId);
    }

    /**
     * 删除用户（软删除）
     *
     * @param userId 用户ID
     */
    @Override
    public void deleteUser(String userId) {
        Result<Void> result = rpcUserService.deleteUser(userId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("删除用户失败，用户ID: {}, 错误信息: {}", userId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("删除用户成功，用户ID: {}", userId);
    }
}
