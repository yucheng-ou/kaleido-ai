package com.xiaoo.kaleido.auth.application.command;

import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.auth.types.exception.AuthErrorCode;
import com.xiaoo.kaleido.auth.types.exception.AuthException;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class SmsCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcNoticeService rpcNoticeService;

    public SmsCodeResponse sendSmsCode(SendSmsCodeCommand command) {
        log.info("发送短信验证码，手机号: {}", command.getMobile());

        // 调用RPC服务发送短信验证码
        SendSmsVerifyCodeCommand smsCommand = SendSmsVerifyCodeCommand.builder()
                .mobile(command.getMobile())
                .targetType(command.getTargetType())
                .build();

        Result<String> result = rpcNoticeService.generateAndSendSmsVerifyCode(smsCommand);

        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("发送短信验证码失败，手机号: {}, 错误: {}", command.getMobile(), result.getMsg());
            throw AuthException.of(AuthErrorCode.NOTICE_SERVICE_UNAVAILABLE);
        }

        // 构建响应
        SmsCodeResponse response = SmsCodeResponse
                .builder()
                .mobile(command.getMobile())
                .code(result.getData())
                .sendTime(new Date())
                .build();

        log.info("短信验证码发送成功，手机号: {}", command.getMobile());
        return response;
    }
}
