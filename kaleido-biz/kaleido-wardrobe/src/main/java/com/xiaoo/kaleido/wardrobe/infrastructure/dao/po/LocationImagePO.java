package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 位置图片持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_location_image
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_location_image")
public class LocationImagePO extends BasePO {

    /**
     * 位置ID
     */
    @TableField("location_id")
    private String locationId;

    /**
     * 图片路径（在minio中的文件路径）
     */
    @TableField("path")
    private String path;

    /**
     * 排序序号
     */
    @TableField("image_order")
    private Integer imageOrder;

    /**
     * 是否为主图
     */
    @TableField("is_primary")
    private Boolean isPrimary;

    /**
     * 图片大小（字节）
     */
    @TableField("image_size")
    private Long imageSize;

    /**
     * 文件类型
     */
    @TableField("image_type")
    private String imageType;

    /**
     * 图片宽度
     */
    @TableField("width")
    private Integer width;

    /**
     * 图片高度
     */
    @TableField("height")
    private Integer height;
}
