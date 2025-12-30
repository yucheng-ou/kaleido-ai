package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.notice.types.annotation.NoticeAdapter;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 邮件通知适配器
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Service
@NoticeAdapter(NoticeTypeEnum.EMAIL)
@RequiredArgsConstructor
public class NoticeEmailService extends AbstractNoticeAdapterService {

    @Override
    public boolean sendNoticeByType(String target, String content) {
        //TODO 实现邮件通知
        return true;
    }
}
