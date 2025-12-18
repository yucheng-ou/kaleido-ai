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
public class NoticeWeChatService extends AbstractNoticeAdapterService{

    @Override
    public boolean sendNoticeByType(String target, String content) {
        //TODO 实现微信通知

       return true;
    }
}
