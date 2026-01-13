package com.xiaoo.kaleido.notice.domain.service.impl;

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
        // 1.验证模板编码唯一性
        if (noticeTemplateRepository.existsByCode(code)) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_CODE_EXISTS);
        }

        // 2.创建模板聚合根
        NoticeTemplateAggregate template = NoticeTemplateAggregate.create(name, code, content);

        log.info("通知模板领域服务创建模板，模板ID: {}, 模板编码: {}", template.getId(), code);
        return template;
    }

    @Override
    public NoticeTemplateAggregate updateTemplateName(String templateId, String newName) {
        // 1.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 2.更新模板名称
        template.updateName(newName);

        log.info("通知模板领域服务更新模板名称，模板ID: {}, 新名称: {}", templateId, newName);
        return template;
    }

    @Override
    public NoticeTemplateAggregate updateTemplateContent(String templateId, String newContent) {
        // 1.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 2.更新模板内容
        template.updateContent(newContent);

        log.info("通知模板领域服务更新模板内容，模板ID: {}, 内容长度: {}", templateId, newContent.length());
        return template;
    }

    @Override
    public NoticeTemplateAggregate enableTemplate(String templateId) {
        // 1.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 2.启用模板
        template.enable();

        log.info("通知模板领域服务启用模板，模板ID: {}", templateId);
        return template;
    }

    @Override
    public NoticeTemplateAggregate disableTemplate(String templateId) {
        // 1.获取模板
        NoticeTemplateAggregate template = noticeTemplateRepository.findByIdOrThrow(templateId);

        // 2.禁用模板
        template.disable();

        log.info("通知模板领域服务禁用模板，模板ID: {}", templateId);
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
            NoticeTemplateAggregate template = noticeTemplateRepository.findByCodeOrThrow(code);
            // 2.检查模板是否启用
            return template.isEnabled();
        } catch (NoticeException e) {
            // 3.模板不存在时返回false
            return false;
        }
    }
}
