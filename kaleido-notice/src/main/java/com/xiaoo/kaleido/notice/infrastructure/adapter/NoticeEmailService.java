package com.xiaoo.kaleido.notice.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ouyucheng
 * @date 2025/12/18
 * @description
 */
@Service
@RequiredArgsConstructor
public class NoticeEmailService extends AbstractNoticeAdapterService{
    
    @Override
    public boolean sendNoticeByType(String target, String content) {
        //TODO 实现邮件通知

       return true;
    }
}
