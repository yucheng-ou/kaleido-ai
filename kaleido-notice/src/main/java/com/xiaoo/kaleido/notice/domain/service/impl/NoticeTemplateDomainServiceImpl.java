package com.xiaoo.kaleido.notice.domain.service.impl;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.domain.service.INoticeTemplateDomainService;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 模板领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Service
@RequiredArgsConstructor
public class NoticeTemplateDomainServiceImpl implements INoticeTemplateDomainService {

    private final INoticeTemplateRepository noticeTemplateRepository;


    @Override
    public NoticeTemplateAggregate create(String name, String code, String content) {
        Optional<NoticeTemplateAggregate> byCode = noticeTemplateRepository.findByCode(code);
        if (byCode.isPresent()) {
            return NoticeTemplateAggregate.create(name, code, content);
        }
        throw NoticeException.of(BizErrorCode.UNIQUE_INDEX_CONFLICT, "模板已存在");
    }
}
