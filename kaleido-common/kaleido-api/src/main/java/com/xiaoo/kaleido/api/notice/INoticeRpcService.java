package com.xiaoo.kaleido.api.notice;

import com.xiaoo.kaleido.base.result.Result;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */
public interface INoticeRpcService {

    /**
     * 生成并发送短信验证码
     * @param mobile 手机号
     * @return 是否发送成功
     */
    Result<Boolean> generateAndSendSmsVerifyCode(String mobile);
}
