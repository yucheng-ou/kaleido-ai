package com.xiaoo.kaleido.user.trigger.rpc;

import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.api.user.request.CreateUserRpcRequest;
import com.xiaoo.kaleido.api.user.response.UserResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.user.command.AddUserCommand;
import com.xiaoo.kaleido.user.command.FreezeUserCommand;
import com.xiaoo.kaleido.user.command.UnfreezeUserCommand;
import com.xiaoo.kaleido.user.command.UserCommandService;
import com.xiaoo.kaleido.user.query.dto.UserDTO;
import com.xiaoo.kaleido.user.query.service.UserQueryService;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

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
public class IRpcUserServiceImpl implements IRpcUserService {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Override
    public Result<UserResponse> getUserById(String userId) {
        try {
            log.debug("RPC调用：获取用户信息，userId={}", userId);

            UserDTO userDTO = userQueryService.getUserById(userId);
            UserResponse rpcDTO = convertToRpcDTO(userDTO);

            return Result.success(rpcDTO);
        } catch (UserException e) {
            log.warn("RPC调用失败：获取用户信息，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：获取用户信息，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<UserResponse> getUserByTelephone(String telephone) {
        try {
            log.debug("RPC调用：根据手机号获取用户，telephone={}", telephone);

            UserDTO userDTO = userQueryService.findByTelephone(telephone);
            UserResponse rpcDTO = convertToRpcDTO(userDTO);

            return Result.success(rpcDTO);
        } catch (UserException e) {
            log.warn("RPC调用失败：根据手机号获取用户，telephone={}, errorCode={}", telephone, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：根据手机号获取用户，telephone={}", telephone, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<String> createUser(@Valid CreateUserRpcRequest request) {
        try {
            log.debug("RPC调用：创建用户，telephone={}", request.getMobile());

            // 转换为命令对象（使用builder模式）
            AddUserCommand command = AddUserCommand.builder()
                    .telephone(request.getMobile())
                    .password(request.getPassword())
                    .inviterCode(request.getInviterCode())
                    .nickName(request.getNickName())
                    .build();

            // 调用命令服务
            String userId = userCommandService.createUser(command);

            log.info("RPC调用成功：创建用户，userId={}, telephone={}", userId, request.getMobile());
            return Result.success(userId);
        } catch (UserException e) {
            log.warn("RPC调用失败：创建用户，telephone={}, errorCode={}", request.getMobile(), e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：创建用户，telephone={}", request.getMobile(), e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> freezeUser(String userId, String reason) {
        try {
            log.debug("RPC调用：冻结用户，userId={}, reason={}", userId, reason);

            // 使用builder模式创建命令
            FreezeUserCommand command = FreezeUserCommand.builder()
                    .userId(userId)
                    .operatorId("system") // RPC调用通常由系统触发
                    .reason(reason)
                    .build();

            userCommandService.freezeUser(command);

            log.info("RPC调用成功：冻结用户，userId={}, reason={}", userId, reason);
            return Result.success(true);
        } catch (UserException e) {
            log.warn("RPC调用失败：冻结用户，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：冻结用户，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> unfreezeUser(String userId, String reason) {
        try {
            log.debug("RPC调用：解冻用户，userId={}, reason={}", userId, reason);

            // 使用builder模式创建命令
            UnfreezeUserCommand command = UnfreezeUserCommand.builder()
                    .userId(userId)
                    .operatorId("system") // RPC调用通常由系统触发
                    .reason(reason)
                    .build();

            userCommandService.unfreezeUser(command);

            log.info("RPC调用成功：解冻用户，userId={}, reason={}", userId, reason);
            return Result.success(true);
        } catch (UserException e) {
            log.warn("RPC调用失败：解冻用户，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：解冻用户，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> existsUser(String userId) {
        try {
            log.debug("RPC调用：验证用户是否存在，userId={}", userId);

            // 尝试获取用户，如果获取到则存在
            try {
                UserDTO userDTO = userQueryService.getUserById(userId);
                boolean exists = userDTO != null;
                return Result.success(exists);
            } catch (UserException e) {
                log.warn("RPC调用失败：根据id查询用户信息，userId={}, errorCode={}", userId, e.getErrorCode());
                return Result.error(e.getErrorCode(), e.getMessage());
            }
        } catch (Exception e) {
            log.error("RPC调用异常：验证用户是否存在，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> existsTelephone(String telephone) {
        try {
            log.debug("RPC调用：验证手机号是否已注册，telephone={}", telephone);

            boolean exists = userQueryService.existsByTelephone(telephone);
            return Result.success(exists);
        } catch (Exception e) {
            log.error("RPC调用异常：验证手机号是否已注册，telephone={}", telephone, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> login(String userId) {
        try {
            log.debug("RPC调用：记录用户登录，userId={}", userId);

            // 参数验证
            if (userId == null || userId.trim().isEmpty()) {
                throw UserException.of(UserErrorCode.USER_ID_EMPTY);
            }

            // 调用应用层服务
            userCommandService.login(userId);

            log.info("RPC调用成功：记录用户登录，userId={}", userId);
            return Result.success(true);
        } catch (UserException e) {
            log.warn("RPC调用失败：记录用户登录，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：记录用户登录，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    @Override
    public Result<Boolean> logout(String userId) {
        try {
            log.debug("RPC调用：记录用户登出，userId={}", userId);

            // 参数验证
            if (userId == null || userId.trim().isEmpty()) {
                throw UserException.of(UserErrorCode.USER_ID_EMPTY);
            }

            // 调用应用层服务
            userCommandService.logout(userId);

            log.info("RPC调用成功：记录用户登出，userId={}", userId);
            return Result.success(true);
        } catch (UserException e) {
            log.warn("RPC调用失败：记录用户登出，userId={}, errorCode={}", userId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("RPC调用异常：记录用户登出，userId={}", userId, e);
            return Result.error(UserErrorCode.USER_OPERATE_FAILED.getCode(), "系统异常");
        }
    }

    /**
     * 将UserDTO转换为UserRpcDTO
     */
    private UserResponse convertToRpcDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserResponse rpcDTO = new UserResponse();
        rpcDTO.setUserId(userDTO.getUserId());
        rpcDTO.setTelephone(userDTO.getTelephone());
        rpcDTO.setNickName(userDTO.getNickName());
        rpcDTO.setStatus(userDTO.getStatus() != null ? userDTO.getStatus().name() : null);
        rpcDTO.setInviteCode(userDTO.getInviteCode());
        rpcDTO.setInviterId(userDTO.getInviterId());
        rpcDTO.setAvatarUrl(userDTO.getAvatarUrl());


        return rpcDTO;
    }
}
