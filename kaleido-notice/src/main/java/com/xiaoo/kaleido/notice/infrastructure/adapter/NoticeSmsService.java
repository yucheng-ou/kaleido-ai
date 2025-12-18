package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ouyucheng
 * @date 2025/12/18
 * @description
 */
@Service
@RequiredArgsConstructor
public class NoticeSmsService extends AbstractNoticeAdapterService{

    private final SmsService smsService;

    @Override
    public boolean sendNoticeByType(String target, String content) {
       return smsService.sendSmsMsg(target, content);
    }
}
