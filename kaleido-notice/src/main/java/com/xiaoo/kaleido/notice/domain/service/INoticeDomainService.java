package com.xiaoo.kaleido.notice.domain.service;

import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;

/**
 * 通知领域服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface INoticeDomainService {

    NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content);

    NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content, TargetTypeEnum targetType);

}
