package com.xiaoo.kaleido.auth.application.command;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaoo.kaleido.api.admin.user.IRpcAdminAuthService;
import com.xiaoo.kaleido.api.admin.user.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.user.command.RegisterAdminCommand;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.api.admin.user.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.user.response.RegisterResponse;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.auth.types.exception.AuthErrorCode;
import com.xiaoo.kaleido.auth.types.exception.AuthException;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员授权命令服务
 * <p>
 * 负责管理员用户的注册、登录等授权相关命令操作
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminAuthCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcNoticeService rpcNoticeService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminAuthService rpcAdminAuthService;


    public RegisterResponse register(RegisterAdminCommand command) {
        log.info("管理员注册，手机号: {}", command.getMobile());

        // 1. 验证短信验证码
        verifySmsCode(command.getMobile(), command.getVerifyCode());

        // 2. 调用管理员服务注册
        Result<String> register = rpcAdminAuthService.register(command);

        if (!Boolean.TRUE.equals(register.getSuccess())) {
            log.error("管理员注册失败，手机号: {}, 错误: {}", command.getMobile(), register.getMsg());
            throw AuthException.of(register.getCode(), register.getMsg());
        }

        // 3. 构建响应
        String userId = register.getData();
        RegisterResponse response = RegisterResponse.builder().userId(userId).build();
        log.info("用户注册成功，用户ID: {}, 手机号: {}", userId, command.getMobile());

        return response;
    }

    public AdminLoginResponse login(AdminLoginCommand command) {
        log.info("用户登录，手机号: {}", command.getMobile());

        // 1. 验证短信验证码
        verifySmsCode(command.getMobile(), command.getVerifyCode());

        // 2. 根据手机号查询用户信息
        AdminInfoResponse user = rpcAdminAuthService.findByMobile(command.getMobile()).getData();
        if (user == null) {
            throw AuthException.of(AuthErrorCode.AUTH_LOGIN_FAILED.getCode(), "用户不存在");
        }

        // 3. 调用用户服务记录登录
        Result<Void> loginResult = rpcAdminAuthService.login(user.getAdminId());
        if (!Boolean.TRUE.equals(loginResult.getSuccess())) {
            log.error("管理员登录记录失败，管理员ID: {}, 错误: {}", user.getAdminId(), loginResult.getMsg());
            throw AuthException.of(loginResult.getCode(), loginResult.getMsg());
        }

        // 4. 使用Sa-Token登录
        StpAdminUtil.login(user.getAdminId());
        StpAdminUtil.getStpLogic().getSession().set(user.getAdminId(), user);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 5. 构建响应
        AdminLoginResponse response = new AdminLoginResponse(user.getAdminId(), tokenInfo.getTokenValue(), user);

        log.info("用户登录成功，用户ID: {}, 手机号: {}", user.getAdminId(), command.getMobile());
        return response;
    }

    private void verifySmsCode(String mobile, String code) {
        // 1. 构建验证命令
        CheckSmsVerifyCodeCommand checkCommand = CheckSmsVerifyCodeCommand.builder()
                .targetType(TargetTypeEnum.ADMIN)
                .mobile(mobile)
                .verifyCode(code)
                .build();

        // 2. 调用通知服务验证验证码
        Result<Boolean> result = rpcNoticeService.checkSmsVerifyCode(checkCommand);

        // 3. 验证结果
        if (!Boolean.TRUE.equals(result.getSuccess()) || !Boolean.TRUE.equals(result.getData())) {
            log.error("短信验证码验证失败，手机号: {}, 验证码: {}", mobile, code);
            throw new AuthException(AuthErrorCode.AUTH_CAPTCHA_ERROR);
        }
    }
}
