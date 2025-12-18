package com.xiaoo.kaleido.sms.domain.model.entity;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 通知模板实体
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NoticeTemplate extends BaseEntity {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 是否启用
     */
    private DataStatusEnum status;

    /**
     * 创建通知模板实体
     *
     * @param name                模板名称
     * @param content             模板内容
     */
    public static NoticeTemplate create(String name, String content) {
        return NoticeTemplate
                .builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name)
                .content(content)
                .build();
    }

    /**
     * 修改模板名称
     * @param newName 模板新名称
     */
    public void updateName(String newName) {
        this.name = newName;
    }

    /**
     * 渲染模板
     *
     * @param variables 模板变量
     * @return 渲染后的内容
     */
    public String render(Map<String, Object> variables) {

        return StrUtil.format(this.content, variables);
    }
}
