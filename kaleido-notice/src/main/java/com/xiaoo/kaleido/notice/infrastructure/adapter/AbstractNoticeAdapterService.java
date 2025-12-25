package com.xiaoo.kaleido.notice.infrastructure.adapter;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
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
    public boolean sendNotice(String target, String content) {
        try {
            log.info("接收人={}, 内容长度={}",target, content.length());
            return sendNoticeByType(target, content);
        } catch (Exception e) {
            log.error("接收人={}, 错误={}",target, e.getMessage(), e);
            return false;
        }
    }

    public abstract boolean sendNoticeByType(String target, String content);
}
