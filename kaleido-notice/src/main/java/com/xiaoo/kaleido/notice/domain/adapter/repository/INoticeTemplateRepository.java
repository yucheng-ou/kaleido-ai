package com.xiaoo.kaleido.notice.domain.adapter.repository;

import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import java.util.Optional;

/**
 * 通知模板仓储接口（领域层）
 * 定义通知模板聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
public interface INoticeTemplateRepository {

    /**
     * 根据ID查找模板聚合根
     *
     * @param id 模板ID
     * @return 模板聚合根（如果存在）
     */
    Optional<NoticeTemplateAggregate> findById(String id);

    /**
     * 根据ID查找模板聚合根
     *
     * @param code 模板code
     * @return 模板聚合根（如果存在）
     */
    Optional<NoticeTemplateAggregate> findByCode(String code);

    /**
     * 保存模板聚合根
     *
     * @param template 模板聚合根
     * @return 保存后的模板聚合根
     */
    NoticeTemplateAggregate save(NoticeTemplateAggregate template);

    /**
     * 根据ID删除模板聚合根
     *
     * @param id 模板ID
     */
    void deleteById(String id);

}
