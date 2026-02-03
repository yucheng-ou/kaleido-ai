package com.xiaoo.kaleido.wardrobe.domain.location.model.entity;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 位置图片实体
 * <p>
 * 表示存储位置与图片之间的关联关系，包含图片的基本信息和业务规则
 * 完全参考服装领域的图片处理模式
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocationImage extends BaseEntity {

    /**
     * 位置ID
     */
    private String locationId;

    /**
     * 图片路径（在minio中的文件路径）
     */
    private String path;

    /**
     * 排序序号
     */
    private Integer imageOrder;

    /**
     * 是否为主图
     */
    private Boolean isPrimary;

    /**
     * 图片大小（字节）
     */
    private Long imageSize;

    /**
     * 图片类型
     */
    private ImageTypeEnums imageType;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 创建位置图片

     * 根据位置ID、图片路径和其他信息创建新的位置图片
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param locationId 位置ID，不能为空（已在Service层校验）
     * @param path       图片路径（在minio中的文件路径），不能为空（已在Service层校验）
     * @param imageOrder 排序序号，不能为空（已在Service层校验）
     * @param isPrimary  是否为主图，不能为空（已在Service层校验）
     * @param imageSize  文件大小，可为空
     * @param imageType  图片类型，可为空
     * @param width      图片宽度，可为空
     * @param height     图片高度，可为空
     * @return 位置图片实体
     */
    public static LocationImage create(
            String locationId,
            String path,
            Integer imageOrder,
            Boolean isPrimary,
            Long imageSize,
            ImageTypeEnums imageType,
            Integer width,
            Integer height) {
        return LocationImage.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .locationId(locationId)
                .path(path)
                .imageOrder(imageOrder)
                .isPrimary(isPrimary)
                .imageSize(imageSize)
                .imageType(imageType)
                .width(width)
                .height(height)
                .build();
    }

    /**
     * 判断是否为主图
     *
     * @return 如果为主图返回true，否则返回false
     */
    public boolean isPrimaryImage() {
        return Boolean.TRUE.equals(this.isPrimary);
    }

    /**
     * 设置为主图
     */
    public void setAsPrimary() {
        this.isPrimary = true;
    }

    /**
     * 取消主图
     */
    public void unsetAsPrimary() {
        this.isPrimary = false;
    }

    /**
     * 判断是否属于指定位置
     *
     * @param locationId 位置ID
     * @return 如果属于指定位置则返回true，否则返回false
     */
    public boolean belongsToLocation(String locationId) {
        return this.locationId.equals(locationId);
    }

}
