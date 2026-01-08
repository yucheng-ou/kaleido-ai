package com.xiaoo.kaleido.api.notice;

import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 通知RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@DubboService
public interface IRpcNoticeService {

    /**
     * 生成并发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 验证码
     */
    Result<String> generateAndSendSmsVerifyCode(@Valid SendSmsVerifyCodeCommand command);

    /**
     * 校验短信验证码
     *
     * @param command 校验短信验证码命令
     * @return 校验结果
     */
    Result<Boolean> checkSmsVerifyCode(@Valid CheckSmsVerifyCodeCommand command);
}
