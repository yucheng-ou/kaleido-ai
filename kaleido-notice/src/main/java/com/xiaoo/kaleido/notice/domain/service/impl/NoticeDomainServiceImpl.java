package com.xiaoo.kaleido.notice.domain.service.impl;

import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.valobj.TargetAddress;
import com.xiaoo.kaleido.notice.domain.service.INoticeDomainService;
import com.xiaoo.kaleido.api.notice.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.notice.domain.service.INoticeTemplateDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 通知领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Service
@RequiredArgsConstructor
public class NoticeDomainServiceImpl implements INoticeDomainService {

    private final INoticeRepository noticeRepository;
    private final INoticeTemplateDomainService noticeTemplateDomainService;

    @Override
    public NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content) {
        // 默认目标类型为普通用户
        return createSmsVerifyCodeAggregate(mobile, content, TargetTypeEnum.USER);
    }

    @Override
    public NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content, TargetTypeEnum targetType) {
        //推送目标值对象
        TargetAddress targetAddress = TargetAddress.create(mobile, NoticeTypeEnum.SMS, targetType);

        //创建最终通知聚合根
        return NoticeAggregate.create(NoticeTypeEnum.SMS, targetAddress, BusinessTypeEnum.VERIFY_CODE, content);
    }
}
