package com.xiaoo.kaleido.notice.domain.service;

import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;

/**
 * 模板领域服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface INoticeTemplateDomainService {

    NoticeTemplateAggregate create(String name, String code, String content);

}
