package com.xiaoo.kaleido.notice.domain.factory;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;

/**
 * 通知服务工厂接口
 * 
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface INoticeServiceFactory {

    /**
     * 根据通知类型获取对应的适配器服务
     *
     * @param noticeType 通知类型
     * @return 对应的适配器服务
     */
    INoticeAdapterService getNoticeAdapterService(NoticeTypeEnum noticeType);

}
