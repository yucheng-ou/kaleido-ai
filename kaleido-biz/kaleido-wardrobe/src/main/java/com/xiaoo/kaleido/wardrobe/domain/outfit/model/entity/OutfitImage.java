package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 穿搭图片实体
 * <p>
 * 表示穿搭与图片之间的关联关系，包含图片的基本信息和业务规则
 * 参考ClothingImage和LocationImage的设计模式
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OutfitImage extends BaseEntity {

    /**
     * 穿搭ID
     */
    private String outfitId;

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
     * 文件大小（字节）
     */
    private Long imageSize;

    /**
     * 文件类型
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
     * 图片描述
     */
    private String description;

    /**
     * 创建穿搭图片

     * 根据穿搭ID、图片路径和其他信息创建新的穿搭图片
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param outfitId      穿搭ID，不能为空（已在Service层校验）
     * @param path          图片路径（在minio中的文件路径），不能为空（已在Service层校验）
     * @param imageOrder    排序序号，不能为空（已在Service层校验）
     * @param isPrimary     是否为主图，不能为空（已在Service层校验）
     * @param imageSize     文件大小，可为空
     * @param imageType     文件类型，可为空
     * @param width         图片宽度，可为空
     * @param height        图片高度，可为空
     * @param description   图片描述，可为空
     * @return 穿搭图片实体
     */
    public static OutfitImage create(
            String outfitId,
            String path,
            Integer imageOrder,
            Boolean isPrimary,
            Long imageSize,
            ImageTypeEnums imageType,
            Integer width,
            Integer height,
            String description) {
        return OutfitImage.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .outfitId(outfitId)
                .path(path)
                .imageOrder(imageOrder)
                .isPrimary(isPrimary)
                .imageSize(imageSize)
                .imageType(imageType)
                .width(width)
                .height(height)
                .description(description)
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
     * 判断是否属于指定穿搭
     *
     * @param outfitId 穿搭ID
     * @return 如果属于指定穿搭则返回true，否则返回false
     */
    public boolean belongsToOutfit(String outfitId) {
        return this.outfitId.equals(outfitId);
    }
}
