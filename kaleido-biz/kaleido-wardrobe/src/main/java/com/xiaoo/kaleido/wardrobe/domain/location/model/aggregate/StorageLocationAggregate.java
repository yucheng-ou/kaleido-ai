package com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 存储位置聚合根
 * <p>
 * 存储位置领域模型的核心聚合根，封装存储位置实体及其业务规则，确保业务完整性
 * 遵循聚合根设计原则：包含最核心的业务逻辑，不包含参数校验
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StorageLocationAggregate extends BaseEntity {

    /**
     * 每个位置最多允许的图片数量
     */
    public static final int MAX_IMAGES_PER_LOCATION = 10;

    /**
     * 用户ID
     * 位置所属的用户
     */
    private String userId;

    /**
     * 位置名称
     * 同一用户下位置名称需要唯一
     */
    private String name;

    /**
     * 位置描述
     */
    private String description;

    /**
     * 具体地址
     * 位置的详细地址信息
     */
    private String address;

    /**
     * 主图ID
     * 关联位置图片表t_wardrobe_location_image
     */
    private String primaryImageId;

    /**
     * 图片列表（聚合根内的实体）
     * 记录位置的所有图片
     */
    @Builder.Default
    private List<LocationImage> images = new ArrayList<>();

    /**
     * 创建新存储位置聚合根

     * 用于创建新位置时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId      用户ID，不能为空
     * @param name        位置名称，不能为空
     * @param description 位置描述，可为空
     * @param address     具体地址，可为空
     * @return 存储位置聚合根
     */
    public static StorageLocationAggregate create(
            String userId,
            String name,
            String description,
            String address) {
        return StorageLocationAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .name(name)
                .description(description)
                .address(address)
                .build();
    }

    /**
     * 更新位置信息

     * 更新位置的基本信息，不包括图片
     * 注意：参数和状态校验在Service层完成，这里只负责更新信息
     *
     * @param name        新位置名称，不能为空（已在Service层校验）
     * @param description 新位置描述，可为空
     * @param address     新具体地址，可为空
     */
    public void updateInfo(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    /**
     * 批量添加图片

     * 用于一次性添加多张图片的场景
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param images 图片实体列表，不能为空（已在Service层校验）
     */
    public void addImages(List<LocationImage> images) {
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
    public Optional<LocationImage> findImageById(String imageId) {
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
     * 注意：调用前需要确保图片存在且属于当前位置
     *
     * @param imageId 主图ID，不能为空
     * @return 如果设置成功返回true，如果图片不存在返回false
     */
    public boolean setAsPrimaryImage(String imageId) {
        Optional<LocationImage> imageOpt = findImageById(imageId);
        if (imageOpt.isEmpty()) {
            return false;
        }

        // 清除原有主图状态
        this.images.stream()
                .filter(LocationImage::isPrimaryImage)
                .forEach(LocationImage::unsetAsPrimary);

        // 设置新主图
        LocationImage newPrimaryImage = imageOpt.get();
        newPrimaryImage.setAsPrimary();
        this.primaryImageId = imageId;

        return true;
    }

    /**
     * 检查是否包含图片
     *
     * @return 如果包含图片返回true，否则返回false
     */
    public boolean hasImages() {
        return images != null && !images.isEmpty();
    }

    /**
     * 获取图片数量
     *
     * @return 图片数量
     */
    public int getImageCount() {
        return images != null ? images.size() : 0;
    }
}
