package com.xiaoo.kaleido.notice.domain.factory;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;

/**
 * 通知服务工厂接口
 * <p>
 * 定义根据通知类型获取适配器服务的契约
 * 属于领域层，不依赖任何外部框架
 * </p>
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
     * @throws com.xiaoo.kaleido.notice.types.exception.NoticeException 如果通知类型不支持或适配器未找到
     */
    INoticeAdapterService getNoticeAdapterService(NoticeTypeEnum noticeType);

}
