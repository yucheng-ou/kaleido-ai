package com.xiaoo.kaleido.auth.application.command;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaoo.kaleido.api.admin.user.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.user.command.RegisterCommand;
import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.RegisterResponse;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.user.IRpcUserService;
import com.xiaoo.kaleido.api.user.command.AddUserCommand;
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
 * 授权命令服务
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

    /**
     * 发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 短信验证码响应
     */
    public SmsCodeResponse sendSmsCode(SendSmsCodeCommand command) {
        log.info("发送短信验证码，手机号: {}", command.getMobile());

        // 调用RPC服务发送短信验证码
        SendSmsVerifyCodeCommand smsCommand = SendSmsVerifyCodeCommand.builder()
                .mobile(command.getMobile())
                .build();

        Result<String> result = rpcNoticeService.generateAndSendSmsVerifyCode(smsCommand);

        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("发送短信验证码失败，手机号: {}, 错误: {}", command.getMobile(), result.getMsg());
            throw new AuthException(AuthErrorCode.AUTH_SERVICE_UNAVAILABLE);
        }

        // 构建响应
        SmsCodeResponse response = new SmsCodeResponse();
        response.setMobile(command.getMobile());
        response.setCode(result.getData());
        response.setSendTime(new Date());

        log.info("短信验证码发送成功，手机号: {}", command.getMobile());
        return response;
    }
    
    /**
     * 用户注册
     *
     * @param command 注册命令
     * @return 注册响应
     */
    public RegisterResponse register(RegisterCommand command) {
        log.info("用户注册，手机号: {}", command.getTelephone());

        // 1. 验证短信验证码
        verifySmsCode(command.getTelephone(), command.getVerificationCode());

        // 2. 调用用户服务注册
        AddUserCommand addUserCommand = AddUserCommand.builder()
                .telephone(command.getTelephone())
                .password(command.getPassword())
                .inviterCode(command.getInviterCode())
                .nickName(command.getNickName())
                .build();

        Result<String> registerResult = rpcUserService.register(addUserCommand);

        if (!Boolean.TRUE.equals(registerResult.getSuccess())) {
            log.error("用户注册失败，手机号: {}, 错误: {}", command.getTelephone(), registerResult.getMsg());
            throw new AuthException(AuthErrorCode.AUTH_LOGIN_FAILED);
        }

        String userId = registerResult.getData();

        // 3. 构建响应
        RegisterResponse response = new RegisterResponse();
        response.setUserId(userId);

        log.info("用户注册成功，用户ID: {}, 手机号: {}", userId, command.getTelephone());
        return response;
    }
    
    /**
     * 用户登录
     *
     * @param command 登录命令
     * @return 登录响应
     */
    public UserLoginResponse login(AdminLoginCommand command) {
        log.info("用户登录，手机号: {}", command.getTelephone());

        //根据手机号查询用户信息
        UserInfoResponse user = rpcUserService.getByTelephone(command.getTelephone()).getData();

        // 调用用户服务记录登录
        Result<Void> loginResult = rpcUserService.login(user.getUserId());
        if (!Boolean.TRUE.equals(loginResult.getSuccess())) {
            log.error("用户登录记录失败，用户ID: {}, 错误: {}", user.getUserId(), loginResult.getMsg());
            throw new AuthException(AuthErrorCode.AUTH_LOGIN_FAILED);
        }

        // 使用Sa-Token登录
         StpUserUtil.login(user.getUserId());
         SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 构建响应
        UserLoginResponse response = new UserLoginResponse();
        response.setUserId(user.getUserId());
        response.setToken(tokenInfo.getTokenValue());
        response.setUserInfo(user);

        log.info("用户登录成功，用户ID: {}, 手机号: {}", user.getUserId(), command.getTelephone());
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
}
