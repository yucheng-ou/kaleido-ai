package com.xiaoo.kaleido.notice.trigger.rpc;

import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.application.NoticeCommandService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 通知RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
@Tag(name = "通知RPC服务", description = "通知相关的RPC接口")
public class RpcNoticeServiceImpl implements IRpcNoticeService {

    private final NoticeCommandService noticeCommandService;

    @Override
    @Operation(summary = "生成并发送短信验证码", description = "生成短信验证码并发送到指定手机号")
    public Result<String> generateAndSendSmsVerifyCode(
            @Parameter(description = "发送短信验证码命令")
            SendSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.sendSmsVerifyCode(command));
    }

    @Override
    @Operation(summary = "校验短信验证码", description = "校验短信验证码是否正确且未过期")
    public Result<Boolean> checkSmsVerifyCode(CheckSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.checkSmsVerifyCode(command));
    }
}
