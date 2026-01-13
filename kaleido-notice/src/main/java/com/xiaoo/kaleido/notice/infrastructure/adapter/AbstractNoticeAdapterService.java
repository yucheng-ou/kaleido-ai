package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.base.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信服务适配器
 * 将外部短信服务适配到领域层接口
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractNoticeAdapterService implements INoticeAdapterService {

    @Override
    public Result<Void> sendNotice(String target, String content) {
        try {
            log.info("接收人={}, 内容长度={}", target, content.length());
            return sendNoticeByType(target, content);
        } catch (Exception e) {
            log.error("接收人={}, 错误={}", target, e.getMessage(), e);
            return Result.error(NoticeErrorCode.NOTICE_SEND_FAILED.getCode(), e.getMessage());
        }
    }

    public abstract Result<Void> sendNoticeByType(String target, String content);
}
