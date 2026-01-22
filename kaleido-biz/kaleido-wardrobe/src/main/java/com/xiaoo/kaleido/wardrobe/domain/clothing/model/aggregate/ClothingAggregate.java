package com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 服装聚合根
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClothingAggregate extends BaseEntity {

    /**
     * 每个服装最多允许的图片数量
     */
    public static final int MAX_IMAGES_PER_CLOTHING = 10;

    /**
     * 用户ID
     * 服装所属的用户
     */
    private String userId;

    /**
     * 服装名称
     */
    private String name;

    /**
     * 服装类型编码
     * 关联字典表t_dict.dict_code，字典类型为CLOTHING_TYPE
     */
    private String typeCode;

    /**
     * 颜色编码
     * 关联字典表t_dict.dict_code，字典类型为COLOR
     */
    private String colorCode;

    /**
     * 季节编码
     * 关联字典表t_dict.dict_code，字典类型为SEASON
     */
    private String seasonCode;

    /**
     * 品牌ID
     * 关联品牌表t_wardrobe_brand
     */
    private String brandId;

    /**
     * 尺码
     */
    private String size;

    /**
     * 购买日期
     */
    private Date purchaseDate;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    /**
     * 当前位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    private String currentLocationId;

    /**
     * 主图ID
     * 关联服装图片表t_wardrobe_clothing_image
     */
    private String primaryImageId;

    /**
     * 图片列表（聚合根内的实体）
     * 记录服装的所有图片
     */
    @Builder.Default
    private List<ClothingImage> images = new ArrayList<>();

    /**
     * 创建新服装聚合根

     * 用于创建新服装时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId            用户ID，不能为空
     * @param name              服装名称，不能为空
     * @param typeCode          服装类型编码，不能为空
     * @param colorCode         颜色编码，可为空
     * @param seasonCode        季节编码，可为空
     * @param brandId           品牌ID，可为空
     * @param size              尺码，可为空
     * @param purchaseDate      购买日期，可为空
     * @param price             价格，可为空
     * @param description       描述，可为空
     * @param currentLocationId 当前位置ID，可为空
     * @return 服装聚合根
     */
    public static ClothingAggregate create(
            String userId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationId) {
        return ClothingAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .name(name)
                .typeCode(typeCode)
                .colorCode(colorCode)
                .seasonCode(seasonCode)
                .brandId(brandId)
                .size(size)
                .purchaseDate(purchaseDate)
                .price(price)
                .description(description)
                .currentLocationId(currentLocationId)
                .build();
    }

    /**
     * 更新服装信息

     * 更新服装的基本信息，不包括图片和位置
     * 注意：参数和状态校验在Service层完成，这里只负责更新信息
     *
     * @param name         新服装名称，不能为空（已在Service层校验）
     * @param typeCode     新服装类型编码，不能为空（已在Service层校验）
     * @param colorCode    新颜色编码，可为空
     * @param seasonCode   新季节编码，可为空
     * @param brandId      新品牌ID，可为空
     * @param size         新尺码，可为空
     * @param purchaseDate 新购买日期，可为空
     * @param price        新价格，可为空
     * @param description  新描述，可为空
     */
    public void updateInfo(
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description) {
        this.name = name;
        this.typeCode = typeCode;
        this.colorCode = colorCode;
        this.seasonCode = seasonCode;
        this.brandId = brandId;
        this.size = size;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.description = description;
    }

    /**
     * 变更服装位置
     * 注意：参数和状态校验在Service层完成，这里只负责更新位置
     *
     * @param locationId 新位置ID，不能为空（已在Service层校验）
     */
    public void changeLocation(String locationId) {
        this.currentLocationId = locationId;
    }



    /**
     * 批量添加图片

     * 用于一次性添加多张图片的场景
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param images 图片实体列表，不能为空（已在Service层校验）
     */
    public void addImages(List<ClothingImage> images) {
        if (images != null && !images.isEmpty()) {
            this.images.addAll(images);
        }
    }

    /**
     * 根据图片ID查找图片
     *
     * @param imageId 图片ID
     * @return 图片实体（如果存在），否则返回Optional.empty()
     */
    public Optional<ClothingImage> findImageById(String imageId) {
        if (imageId == null || imageId.trim().isEmpty()) {
            return Optional.empty();
        }
        return this.images.stream()
                .filter(image -> imageId.equals(image.getId()))
                .findFirst();
    }

    /**
     * 设置主图（包含业务规则）

     * 设置指定图片为主图，同时更新图片实体的isPrimary状态
     * 注意：调用前需要确保图片存在且属于当前服装
     *
     * @param imageId 主图ID，不能为空
     * @return 如果设置成功返回true，如果图片不存在返回false
     */
    public boolean setAsPrimaryImage(String imageId) {
        Optional<ClothingImage> imageOpt = findImageById(imageId);
        if (imageOpt.isEmpty()) {
            return false;
        }

        // 清除原有主图状态
        this.images.stream()
                .filter(ClothingImage::isPrimaryImage)
                .forEach(ClothingImage::unsetAsPrimary);

        // 设置新主图
        ClothingImage newPrimaryImage = imageOpt.get();
        newPrimaryImage.setAsPrimary();
        this.primaryImageId = imageId;

        return true;
    }

}
