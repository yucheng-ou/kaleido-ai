package com.xiaoo.kaleido.auth.application.command;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.xiaoo.kaleido.api.admin.user.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.RegisterResponse;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.api.user.response.UserLoginResponse;
import com.xiaoo.kaleido.auth.types.exception.AuthErrorCode;
import com.xiaoo.kaleido.auth.types.exception.AuthException;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户授权命令服务
 * <p>
 * 负责普通用户的短信验证码发送、注册、登录等授权相关命令操作
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcNoticeService rpcNoticeService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcUserService rpcUserService;

    public RegisterResponse register(RegisterUserCommand command) {
        log.info("用户注册，手机号: {}", command.getTelephone());

        // 1. 验证短信验证码
        verifySmsCode(command.getTelephone(), command.getVerificationCode());

        // 2. 调用用户服务注册
        Result<String> registerResult = rpcUserService.register(command);

        if (!Boolean.TRUE.equals(registerResult.getSuccess())) {
            log.error("用户注册失败，手机号: {}, 错误: {}", command.getTelephone(), registerResult.getMsg());
            throw AuthException.of(registerResult.getCode(), registerResult.getMsg());
        }

        String userId = registerResult.getData();

        // 3. 构建响应
        RegisterResponse response = RegisterResponse.builder().userId(userId).build();

        log.info("用户注册成功，用户ID: {}, 手机号: {}", userId, command.getTelephone());
        return response;
    }

    public UserLoginResponse login(AdminLoginCommand command) {
        log.info("用户登录，手机号: {}", command.getMobile());

        // 1. 验证短信验证码
        verifySmsCode(command.getMobile(), command.getVerifyCode());

        // 2. 根据手机号查询用户信息
        UserInfoResponse user = rpcUserService.getByTelephone(command.getMobile()).getData();

        // 3. 调用用户服务记录登录
        Result<Void> loginResult = rpcUserService.login(user.getUserId());
        if (!Boolean.TRUE.equals(loginResult.getSuccess())) {
            log.error("用户登录记录失败，用户ID: {}, 错误: {}", user.getUserId(), loginResult.getMsg());
            throw AuthException.of(loginResult.getCode(), loginResult.getMsg());
        }

        // 4. 使用Sa-Token登录
        StpUserUtil.login(user.getUserId());
        StpUserUtil.getStpLogic().getSession().set(user.getUserId(), user);
        SaTokenInfo tokenInfo = StpUserUtil.getTokenInfo();

        // 5. 构建响应
        UserLoginResponse response = new UserLoginResponse(user.getUserId(), tokenInfo.getTokenValue(), user);

        log.info("用户登录成功，用户ID: {}, 手机号: {}", user.getUserId(), command.getMobile());
        return response;
    }

    private void verifySmsCode(String mobile, String code) {
        CheckSmsVerifyCodeCommand checkCommand = CheckSmsVerifyCodeCommand.builder()
                .targetType(TargetTypeEnum.USER)
                .mobile(mobile)
                .verifyCode(code)
                .build();

        Result<Boolean> result = rpcNoticeService.checkSmsVerifyCode(checkCommand);

        if (!Boolean.TRUE.equals(result.getSuccess()) || !Boolean.TRUE.equals(result.getData())) {
            log.error("短信验证码验证失败，手机号: {}, 验证码: {}", mobile, code);
            throw new AuthException(AuthErrorCode.AUTH_CAPTCHA_ERROR);
        }
    }
}
