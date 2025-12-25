package com.xiaoo.kaleido.notice.infrastructure.factory;

import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.domain.factory.INoticeServiceFactory;
import com.xiaoo.kaleido.notice.types.annotation.NoticeAdapter;
import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知服务工厂实现
 * <p>
 * 基础设施层实现，负责根据通知类型获取对应的适配器服务
 * 使用策略模式，通过 {@link NoticeAdapter} 注解自动发现和注册适配器
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Slf4j
@Component
public class NoticeServiceFactoryImpl implements INoticeServiceFactory {

    private final Map<NoticeTypeEnum, INoticeAdapterService> adapterServiceMap;

    /**
     * 构造函数，自动发现并注册所有带有 {@link NoticeAdapter} 注解的适配器
     *
     * @param adapterServices 所有实现了 INoticeAdapterService 接口的 Bean
     */
    public NoticeServiceFactoryImpl(List<INoticeAdapterService> adapterServices) {
        this.adapterServiceMap = new HashMap<>();
        
        for (INoticeAdapterService adapterService : adapterServices) {
            NoticeAdapter annotation = adapterService.getClass().getAnnotation(NoticeAdapter.class);
            if (annotation != null) {
                NoticeTypeEnum noticeType = annotation.value();
                if (adapterServiceMap.containsKey(noticeType)) {
                    log.warn("发现重复的通知适配器类型: {}，类: {} 将被忽略",
                            noticeType, adapterService.getClass().getName());
                } else {
                    adapterServiceMap.put(noticeType, adapterService);
                    log.debug("注册通知适配器: {} -> {}", noticeType, adapterService.getClass().getSimpleName());
                }
            }
        }
        
        log.info("通知服务工厂初始化完成，共注册 {} 个适配器: {}", 
                adapterServiceMap.size(), adapterServiceMap.keySet());
    }

    @Override
    public INoticeAdapterService getNoticeAdapterService(NoticeTypeEnum noticeType) {
        if (noticeType == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TYPE_EMPTY);
        }
        
        INoticeAdapterService adapterService = adapterServiceMap.get(noticeType);
        if (adapterService == null) {
            log.error("不支持的通知类型: {}", noticeType);
            throw NoticeException.of(NoticeErrorCode.UNSUPPORTED_NOTICE_TYPE);
        }
        
        return adapterService;
    }
}
