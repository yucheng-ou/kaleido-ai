package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 位置持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_storage_location
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_storage_location")
public class LocationPO extends BasePO {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 位置名称（同一用户下唯一）
     */
    @TableField("name")
    private String name;

    /**
     * 位置描述
     */
    @TableField("description")
    private String description;

    /**
     * 具体地址
     */
    @TableField("address")
    private String address;

    /**
     * 主图ID（对应t_wardrobe_location_image表的id）
     */
    @TableField("primary_image_id")
    private String primaryImageId;
}
