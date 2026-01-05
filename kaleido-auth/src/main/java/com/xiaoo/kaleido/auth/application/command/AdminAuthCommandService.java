package com.xiaoo.kaleido.auth.application.command;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaoo.kaleido.api.admin.auth.IRpcAdminAuthService;
import com.xiaoo.kaleido.api.admin.auth.command.AddAdminUserCommand;
import com.xiaoo.kaleido.api.admin.auth.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.auth.command.RegisterCommand;
import com.xiaoo.kaleido.api.admin.auth.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.api.admin.auth.response.RegisterResponse;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
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

/**
 * 管理员授权命令服务
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

    
    /**
     * 管理员注册（需要验证码）
     *
     * @param command 注册命令
     * @return 注册响应
     */
    public RegisterResponse register(RegisterCommand command) {
        log.info("管理员注册，手机号: {}", command.getTelephone());
        
        // 1. 验证短信验证码
        verifySmsCode(command.getTelephone(), command.getVerificationCode());
        
        // 2. 将RegisterCommand转换为AddAdminUserCommand
        AddAdminUserCommand addAdminUserCommand = AddAdminUserCommand.builder()
                .username(generateUsernameFromMobile(command.getTelephone()))
                .password(command.getPassword())
                .realName(command.getNickName() != null ? command.getNickName() : "管理员")
                .mobile(command.getTelephone())
                .build();
        
        // 3. 调用管理员服务注册
        Result<String> register = rpcAdminAuthService.register(addAdminUserCommand);

        if (!Boolean.TRUE.equals(register.getSuccess())) {
            log.error("管理员注册失败，手机号: {}, 错误: {}", command.getTelephone(), register.getMsg());
            throw new AuthException(AuthErrorCode.AUTH_LOGIN_FAILED);
        }

        // 3. 构建响应
        String userId = register.getData();
        RegisterResponse response = new RegisterResponse();
        response.setUserId(userId);
        log.info("用户注册成功，用户ID: {}, 手机号: {}", userId, command.getTelephone());

        return response;
    }

    /**
     * 管理员登录
     *
     * @param command 登录命令
     * @return 登录响应
     */
    public AdminLoginResponse login(AdminLoginCommand command) {
        log.info("用户登录，手机号: {}", command.getTelephone());

        //根据手机号查询用户信息
        AdminUserInfoResponse user = rpcAdminAuthService.findByMobile(command.getTelephone()).getData();

        // 调用用户服务记录登录
        Result<Void> loginResult = rpcAdminAuthService.login(user.getAdminUserId());
        if (!Boolean.TRUE.equals(loginResult.getSuccess())) {
            log.error("管理员登录记录失败，管理员ID: {}, 错误: {}", user.getAdminUserId(), loginResult.getMsg());
            throw new AuthException(AuthErrorCode.AUTH_LOGIN_FAILED);
        }

        // 使用Sa-Token登录
        StpUserUtil.login(user.getAdminUserId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 构建响应
        AdminLoginResponse response = new AdminLoginResponse();
        response.setUserId(user.getAdminUserId());
        response.setToken(tokenInfo.getTokenValue());
        response.setUserInfo(user);

        log.info("用户登录成功，用户ID: {}, 手机号: {}", user.getAdminUserId(), command.getTelephone());
        return response;
    }
    
    /**
     * 验证短信验证码
     *
     * @param mobile 手机号
     * @param code 验证码
     */
    private void verifySmsCode(String mobile, String code) {
        CheckSmsVerifyCodeCommand checkCommand = CheckSmsVerifyCodeCommand.builder()
                .mobile(mobile)
                .verifyCode(code)
                .build();
        
        Result<Boolean> result = rpcNoticeService.checkSmsVerifyCode(checkCommand);
        
        if (!Boolean.TRUE.equals(result.getSuccess()) || !Boolean.TRUE.equals(result.getData())) {
            log.error("短信验证码验证失败，手机号: {}, 验证码: {}", mobile, code);
            throw new AuthException(AuthErrorCode.AUTH_CAPTCHA_ERROR);
        }
    }
    
    /**
     * 根据手机号生成用户名
     *
     * @param mobile 手机号
     * @return 用户名
     */
    private String generateUsernameFromMobile(String mobile) {
        // 简单实现：使用"admin_" + 手机号后6位作为用户名
        if (mobile.length() >= 6) {
            return "admin_" + mobile.substring(mobile.length() - 6);
        }
        return "admin_" + mobile;
    }
}
