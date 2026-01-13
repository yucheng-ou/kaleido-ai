package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.types.annotation.NoticeAdapter;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 微信通知适配器
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Service
@NoticeAdapter(NoticeTypeEnum.WECHAT)
@RequiredArgsConstructor
public class NoticeWeChatService extends AbstractNoticeAdapterService {

    @Override
    public Result<Void> sendNoticeByType(String target, String content) {
        //TODO 实现微信通知
        return Result.success();
    }
}
