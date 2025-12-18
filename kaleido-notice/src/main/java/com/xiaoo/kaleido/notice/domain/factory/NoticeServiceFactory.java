package com.xiaoo.kaleido.notice.domain.factory;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ouyucheng
 * @date 2025/12/18
 * @description
 */
@Component
public class NoticeServiceFactory {

    @Resource
    private Map<String, INoticeAdapterService> noticeAdapterServiceMap;

    public INoticeAdapterService getNoticeAdapterService(NoticeTypeEnum noticeTypeEnum) {
        return noticeAdapterServiceMap.get(noticeTypeEnum.name());
    }
}
