package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 穿搭图片持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_outfit_image
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_outfit_image")
public class OutfitImagePO extends BasePO {

    /**
     * 穿搭ID
     * 关联穿搭表t_wardrobe_outfit
     */
    @TableField("outfit_id")
    private String outfitId;

    /**
     * 图片路径（在minio中的文件路径）
     */
    @TableField("path")
    private String path;

    /**
     * 图片排序
     * 用于控制图片显示顺序，值越小越靠前
     */
    @TableField("image_order")
    private Integer imageOrder;

    /**
     * 是否为主图
     * true：主图，false：非主图
     */
    @TableField("is_primary")
    private Boolean isPrimary;

    /**
     * 图片大小（字节）
     */
    @TableField("image_size")
    private Long imageSize;

    /**
     * 图片类型
     * 存储枚举的name()值，如：JPEG、PNG等
     */
    @TableField("image_type")
    private String imageType;

    /**
     * 图片宽度（像素）
     */
    @TableField("width")
    private Integer width;

    /**
     * 图片高度（像素）
     */
    @TableField("height")
    private Integer height;

    /**
     * 图片描述
     */
    @TableField("description")
    private String description;
}
