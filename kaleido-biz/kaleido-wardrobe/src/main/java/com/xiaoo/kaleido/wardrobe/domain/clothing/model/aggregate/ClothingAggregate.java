package com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * 服装状态
     */
    private DataStatusEnum status;

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
     * <p>
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
                .status(DataStatusEnum.ENABLE)
                .build();
    }

    /**
     * 更新服装信息
     * <p>
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
     * 添加图片
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param image 服装图片实体，不能为空（已在Service层校验）
     */
    public void addImage(ClothingImage image) {
        this.images.add(image);
    }

    /**
     * 移除图片
     * 注意：参数校验在Service层完成，这里只负责移除图片
     *
     * @param imageId 图片ID，不能为空（已在Service层校验）
     * @return 如果成功移除返回true，如果图片不存在返回false
     */
    public boolean removeImage(String imageId) {
        return this.images.removeIf(image -> image.getId().equals(imageId));
    }

    /**
     * 设置主图
     * 注意：参数、状态和图片存在性校验在Service层完成，这里只负责设置主图
     *
     * @param imageId 主图ID，不能为空（已在Service层校验）
     */
    public void setPrimaryImage(String imageId) {
        this.primaryImageId = imageId;
    }

    /**
     * 启用服装
     * <p>
     * 将服装状态设置为启用
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用服装
     * <p>
     * 将服装状态设置为禁用
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 验证服装是否可以操作
     * <p>
     * 检查服装是否处于启用状态
     *
     * @return 如果服装处于启用状态返回true，否则返回false
     */
    public boolean isEnabled() {
        return this.status == DataStatusEnum.ENABLE;
    }

    /**
     * 获取主图
     *
     * @return 主图（如果存在），否则返回Optional.empty()
     */
    public Optional<ClothingImage> getPrimaryImage() {
        if (this.primaryImageId == null) {
            return Optional.empty();
        }
        return this.images.stream()
                .filter(image -> image.getId().equals(this.primaryImageId))
                .findFirst();
    }

    /**
     * 获取图片数量
     *
     * @return 图片数量
     */
    public int getImageCount() {
        return this.images.size();
    }

    /**
     * 获取所有图片ID
     *
     * @return 所有图片ID列表
     */
    public List<String> getAllImageIds() {
        return this.images.stream()
                .map(ClothingImage::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取并清空图片列表（用于持久化后清理）
     *
     * @return 图片列表
     */
    public List<ClothingImage> getAndClearImages() {
        List<ClothingImage> imagesCopy = new ArrayList<>(this.images);
        this.images.clear();
        return imagesCopy;
    }
}
