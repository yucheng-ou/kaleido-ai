package com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 服装图片实体
 * <p>
 * 表示服装与图片之间的关联关系，包含图片的基本信息和业务规则
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClothingImage extends BaseEntity {

    /**
     * 图片ID
     */
    private String id;

    /**
     * 服装ID
     */
    private String clothingId;

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
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String mimeType;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 缩略图路径（在minio中的文件路径）
     */
    private String thumbnailPath;

    /**
     * 图片描述
     */
    private String description;

    /**
     * 图片状态
     */
    private DataStatusEnum status;

    /**
     * 创建服装图片
     * <p>
     * 根据服装ID、图片路径和其他信息创建新的服装图片
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param clothingId     服装ID，不能为空（已在Service层校验）
     * @param path           图片路径（在minio中的文件路径），不能为空（已在Service层校验）
     * @param imageOrder     排序序号，不能为空（已在Service层校验）
     * @param isPrimary      是否为主图，不能为空（已在Service层校验）
     * @param fileName       原始文件名，可为空
     * @param fileSize       文件大小，可为空
     * @param mimeType       文件类型，可为空
     * @param width          图片宽度，可为空
     * @param height         图片高度，可为空
     * @param thumbnailPath  缩略图路径（在minio中的文件路径），可为空
     * @param description    图片描述，可为空
     * @return 服装图片实体
     */
    public static ClothingImage create(
            String clothingId,
            String path,
            Integer imageOrder,
            Boolean isPrimary,
            String fileName,
            Long fileSize,
            String mimeType,
            Integer width,
            Integer height,
            String thumbnailPath,
            String description) {
        return ClothingImage.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .clothingId(clothingId)
                .path(path)
                .imageOrder(imageOrder)
                .isPrimary(isPrimary)
                .fileName(fileName)
                .fileSize(fileSize)
                .mimeType(mimeType)
                .width(width)
                .height(height)
                .thumbnailPath(thumbnailPath)
                .description(description)
                .status(DataStatusEnum.ENABLE)
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
     * 判断是否属于指定服装
     *
     * @param clothingId 服装ID
     * @return 如果属于指定服装则返回true，否则返回false
     */
    public boolean belongsToClothing(String clothingId) {
        return this.clothingId.equals(clothingId);
    }

    /**
     * 启用图片
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用图片
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 验证图片是否可以操作
     *
     * @return 如果图片处于启用状态返回true，否则返回false
     */
    public boolean isEnabled() {
        return this.status == DataStatusEnum.ENABLE;
    }
}
