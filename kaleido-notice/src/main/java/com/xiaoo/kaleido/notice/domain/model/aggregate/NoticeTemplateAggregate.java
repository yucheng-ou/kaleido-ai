package com.xiaoo.kaleido.notice.domain.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.*;


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
     * 是否启用
     */
    private DataStatusEnum status;

    /**
     * 创建通知模板聚合根
     *
     * @param name    模板名称
     * @param code    模板code
     * @param content 模板内容
     * @return 通知模板聚合根
     */
    public static NoticeTemplateAggregate create(String name, String code, String content) {
        if (name == null || name.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND);
        }
        if (content == null || content.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_CONTENT_EMPTY);
        }

        return NoticeTemplateAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name.trim())
                .code(code.trim())
                .content(content.trim())
                .status(DataStatusEnum.ENABLE)
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
     * 启用模板
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用模板
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 检查模板是否启用
     *
     * @return true表示启用
     */
    public boolean isEnabled() {
        return DataStatusEnum.ENABLE.equals(this.status);
    }


    /**
     * 渲染模板
     *
     * @param params 渲染参数
     * @return 渲染后的内容
     */
    public  String render(String ...params) {
        if (!isEnabled()) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_DISABLED);
        }

        return StrUtil.format(content, (Object) params);
    }

}
