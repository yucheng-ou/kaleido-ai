package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 穿搭持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_outfit
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_outfit")
public class OutfitPO extends BasePO {

    /**
     * 用户ID
     * 关联用户表
     */
    @TableField("user_id")
    private String userId;

    /**
     * 穿搭名称
     */
    @TableField("name")
    private String name;

    /**
     * 穿搭描述
     */
    @TableField("description")
    private String description;

    /**
     * 季节类型
     * 如：SPRING、SUMMER、AUTUMN、WINTER
     */
    @TableField("season_type")
    private String seasonType;
}
