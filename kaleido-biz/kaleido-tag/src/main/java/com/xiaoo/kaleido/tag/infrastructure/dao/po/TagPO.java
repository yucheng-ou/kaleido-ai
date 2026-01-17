package com.xiaoo.kaleido.tag.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签持久化对象
 * <p>
 * 对应数据库表：t_tag
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tag")
public class TagPO extends BasePO {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 标签类型编码
     * <p>
     * 关联字典表t_dict.dict_code，字典类型为TAG_TYPE
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 标签颜色
     */
    @TableField("color")
    private String color;

    /**
     * 标签描述
     */
    @TableField("description")
    private String description;

    /**
     * 使用次数
     */
    @TableField("usage_count")
    private Integer usageCount;
}
