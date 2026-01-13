package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.notice.types.annotation.NoticeAdapter;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.sms.service.SmsService;
import com.xiaoo.kaleido.base.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 短信通知适配器
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Service
@NoticeAdapter(NoticeTypeEnum.SMS)
@RequiredArgsConstructor
public class NoticeSmsService extends AbstractNoticeAdapterService {

    private final SmsService smsService;

    @Override
    public Result<Void> sendNoticeByType(String target, String content) {
        return smsService.sendSmsMsg(target, content);
    }
}
