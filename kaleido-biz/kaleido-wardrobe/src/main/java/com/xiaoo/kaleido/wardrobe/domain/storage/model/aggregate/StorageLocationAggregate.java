package com.xiaoo.kaleido.wardrobe.domain.model.aggregate;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.model.entity.LocationImage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 存储位置聚合根
 * <p>
 * 位置领域模型的核心聚合根，封装存储位置实体及其关联实体（图片），确保业务规则的一致性
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StorageLocationAggregate extends BaseEntity {

    /**
     * 用户ID
     * 位置所属的用户
     */
    private String userId;

    /**
     * 位置名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 具体地址
     */
    private String address;

    /**
     * 位置状态
     */
    private DataStatusEnum status;

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
     * <p>
     * 用于创建新位置时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId      用户ID，不能为空
     * @param name        位置名称，不能为空
     * @param description 描述，可为空
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
                .status(DataStatusEnum.ENABLE)
                .build();
    }

    /**
     * 更新位置信息
     * <p>
     * 更新位置的基本信息，不包括图片
     * 注意：参数和状态校验在Service层完成，这里只负责更新信息
     *
     * @param name        新位置名称，不能为空（已在Service层校验）
     * @param description 新描述，可为空
     * @param address     新具体地址，可为空
     */
    public void updateInfo(
            String name,
            String description,
            String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    /**
     * 添加图片
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param image 位置图片实体，不能为空（已在Service层校验）
     */
    public void addImage(LocationImage image) {
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
     * 启用位置
     * <p>
     * 将位置状态设置为启用
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用位置
     * <p>
     * 将位置状态设置为禁用
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 验证位置是否可以操作
     * <p>
     * 检查位置是否处于启用状态
     *
     * @return 如果位置处于启用状态返回true，否则返回false
     */
    public boolean isEnabled() {
        return this.status == DataStatusEnum.ENABLE;
    }

    /**
     * 获取主图
     *
     * @return 主图（如果存在），否则返回Optional.empty()
     */
    public Optional<LocationImage> getPrimaryImage() {
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
                .map(LocationImage::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取并清空图片列表（用于持久化后清理）
     *
     * @return 图片列表
     */
    public List<LocationImage> getAndClearImages() {
        List<LocationImage> imagesCopy = new ArrayList<>(this.images);
        this.images.clear();
        return imagesCopy;
    }
}
