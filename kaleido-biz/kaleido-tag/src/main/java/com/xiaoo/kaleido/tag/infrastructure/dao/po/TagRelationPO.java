package com.xiaoo.kaleido.tag.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 标签关联持久化对象
 * <p>
 * 对应数据库表：t_tag_relation
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tag_relation")
public class TagRelationPO extends BasePO {

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private String tagId;

    /**
     * 实体ID
     */
    @TableField("entity_id")
    private String entityId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
}
