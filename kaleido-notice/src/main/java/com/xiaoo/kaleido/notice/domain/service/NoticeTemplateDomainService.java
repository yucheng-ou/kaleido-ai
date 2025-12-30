package com.xiaoo.kaleido.notice.domain.service;

import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;

/**
 * 通知模板领域服务
 * 处理通知模板相关的业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
public interface NoticeTemplateDomainService {

    /**
     * 创建通知模板
     *
     * @param name    模板名称
     * @param code    模板编码
     * @param content 模板内容
     * @return 创建的模板聚合根
     */
    NoticeTemplateAggregate createNoticeTemplate(String name, String code, String content);

    /**
     * 更新模板名称
     *
     * @param templateId 模板ID
     * @param newName    新名称
     * @return 更新后的模板聚合根
     */
    NoticeTemplateAggregate updateTemplateName(String templateId, String newName);

    /**
     * 更新模板内容
     *
     * @param templateId 模板ID
     * @param newContent 新内容
     * @return 更新后的模板聚合根
     */
    NoticeTemplateAggregate updateTemplateContent(String templateId, String newContent);

    /**
     * 启用模板
     *
     * @param templateId 模板ID
     * @return 启用后的模板聚合根
     */
    NoticeTemplateAggregate enableTemplate(String templateId);

    /**
     * 禁用模板
     *
     * @param templateId 模板ID
     * @return 禁用后的模板聚合根
     */
    NoticeTemplateAggregate disableTemplate(String templateId);

    /**
     * 根据编码获取模板
     *
     * @param code 模板编码
     * @return 模板聚合根
     */
    NoticeTemplateAggregate getTemplateByCode(String code);

    /**
     * 验证模板是否存在且启用
     *
     * @param code 模板编码
     * @return 是否有效
     */
    boolean isValidTemplate(String code);
}
