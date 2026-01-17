package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服装持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_clothing
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_clothing")
public class ClothingPO extends BasePO {

    /**
     * 用户ID
     * 服装所属的用户
     */
    @TableField("user_id")
    private String userId;

    /**
     * 服装名称
     */
    @TableField("name")
    private String name;

    /**
     * 服装类型编码
     * 关联字典表t_dict.dict_code，字典类型为CLOTHING_TYPE
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 颜色编码
     * 关联字典表t_dict.dict_code，字典类型为COLOR
     */
    @TableField("color_code")
    private String colorCode;

    /**
     * 季节编码
     * 关联字典表t_dict.dict_code，字典类型为SEASON
     */
    @TableField("season_code")
    private String seasonCode;

    /**
     * 品牌ID
     * 关联品牌表t_wardrobe_brand
     */
    @TableField("brand_id")
    private String brandId;

    /**
     * 尺码
     */
    @TableField("size")
    private String size;

    /**
     * 购买日期
     */
    @TableField("purchase_date")
    private Date purchaseDate;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 当前位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    @TableField("current_location_id")
    private String currentLocationId;

    /**
     * 主图ID
     * 关联服装图片表t_wardrobe_clothing_image
     */
    @TableField("primary_image_id")
    private String primaryImageId;
}
