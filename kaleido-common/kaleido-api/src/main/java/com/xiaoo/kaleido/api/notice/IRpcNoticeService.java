package com.xiaoo.kaleido.api.notice;

import com.xiaoo.kaleido.api.notice.request.AddNoticeTemplateRequest;
import com.xiaoo.kaleido.api.notice.request.CheckSmsVerifyCodeRequest;
import com.xiaoo.kaleido.api.notice.request.SendSmsVerifyCodeRequest;
import com.xiaoo.kaleido.base.result.Result;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */
public interface IRpcNoticeService {

    Result<String> generateAndSendSmsVerifyCode(SendSmsVerifyCodeRequest request);

    Result<Boolean> checkSmsVerifyCode(CheckSmsVerifyCodeRequest request);
}
