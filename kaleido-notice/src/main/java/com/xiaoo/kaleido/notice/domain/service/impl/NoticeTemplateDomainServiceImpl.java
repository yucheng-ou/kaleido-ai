package com.xiaoo.kaleido.notice.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.domain.service.INoticeTemplateDomainService;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知模板领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeTemplateDomainServiceImpl implements INoticeTemplateDomainService {

    private final INoticeTemplateRepository noticeTemplateRepository;

    @Override
    public NoticeTemplateAggregate createNoticeTemplate(String name, String code, String content) {
        // 1.验证模板编码唯一性（业务规则校验）
        if (noticeTemplateRepository.existsByCode(code)) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_CODE_EXISTS);
        }

        // 2.创建模板聚合根（聚合根不包含参数校验）

        return NoticeTemplateAggregate.create(name, code, content);
    }

    @Override
    public NoticeTemplateAggregate updateTemplateName(String templateId, String newName) {
        // 1.参数校验
        if (newName == null || newName.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND);
        }

        // 2.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 3.更新模板名称
        template.updateName(newName);

        return template;
    }

    @Override
    public NoticeTemplateAggregate updateTemplateContent(String templateId, String newContent) {
        // 1.参数校验
        if (StrUtil.isBlank(newContent)) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_CONTENT_EMPTY);
        }

        // 2.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 3.更新模板内容
        template.updateContent(newContent);

        return template;
    }

    @Override
    public NoticeTemplateAggregate getTemplateByCode(String code) {
        // 1.根据编码获取模板
        return noticeTemplateRepository.findByCodeOrThrow(code);
    }

    @Override
    public boolean isValidTemplate(String code) {
        try {
            // 1.根据编码获取模板
            noticeTemplateRepository.findByCodeOrThrow(code);
            // 2.模板存在时返回true
            return true;
        } catch (NoticeException e) {
            // 3.模板不存在时返回false
            return false;
        }
    }
}
