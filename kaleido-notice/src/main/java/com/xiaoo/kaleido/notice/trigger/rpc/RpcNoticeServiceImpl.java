package com.xiaoo.kaleido.notice.trigger.rpc;

import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.request.CheckSmsVerifyCodeRequest;
import com.xiaoo.kaleido.api.notice.request.SendSmsVerifyCodeRequest;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.command.NoticeCommandService;
import com.xiaoo.kaleido.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
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
@DubboService(
        version = RpcConstants.DUBBO_VERSION,
        group = RpcConstants.DUBBO_GROUP,
        timeout = RpcConstants.DEFAULT_TIMEOUT
)
public class RpcNoticeServiceImpl implements IRpcNoticeService {

    private final NoticeCommandService noticeCommandService;

    @Override
    public Result<String> generateAndSendSmsVerifyCode(SendSmsVerifyCodeRequest request) {
        SendSmsVerifyCodeCommand command = SendSmsVerifyCodeCommand.builder().mobile(request.getMobile()).build();
        return Result.success(noticeCommandService.sendSmsVerifyCode(command));
    }

    @Override
    public Result<Boolean> checkSmsVerifyCode(CheckSmsVerifyCodeRequest request) {
        return null;
    }
}
