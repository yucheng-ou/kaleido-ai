package com.xiaoo.kaleido.notice.domain.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


/**
 * 通知模板聚合根
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NoticeTemplateAggregate extends BaseEntity {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板code
     */
    private String code;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 创建通知模板聚合根
     * 注意：此方法不包含参数校验，参数校验应在领域服务层完成
     *
     * @param name    模板名称（已校验）
     * @param code    模板code（已校验）
     * @param content 模板内容（已校验）
     * @return 通知模板聚合根
     */
    public static NoticeTemplateAggregate create(String name, String code, String content) {
        return NoticeTemplateAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name.trim())
                .code(code.trim())
                .content(content.trim())
                .build();
    }

    /**
     * 修改模板名称
     *
     * @param newName 模板新名称
     */
    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND);
        }
        this.name = newName.trim();
    }

    /**
     * 修改模板内容
     *
     * @param newContent 模板新内容
     */
    public void updateContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_CONTENT_EMPTY);
        }
        this.content = newContent.trim();
    }

    /**
     * 渲染模板
     *
     * @param params 渲染参数
     * @return 渲染后的内容
     */
    public  String render(String ...params) {
        return StrUtil.format(content, (Object) params);
    }

}
