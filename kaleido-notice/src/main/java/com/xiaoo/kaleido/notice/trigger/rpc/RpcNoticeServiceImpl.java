package com.xiaoo.kaleido.notice.trigger.rpc;

import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.application.NoticeCommandService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
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
public class RpcNoticeServiceImpl implements IRpcNoticeService {

    private final NoticeCommandService noticeCommandService;

    @Override
    public Result<String> generateAndSendSmsVerifyCode(
            SendSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.sendSmsVerifyCode(command));
    }

    @Override
    public Result<Boolean> checkSmsVerifyCode(CheckSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.checkSmsVerifyCode(command));
    }
}
