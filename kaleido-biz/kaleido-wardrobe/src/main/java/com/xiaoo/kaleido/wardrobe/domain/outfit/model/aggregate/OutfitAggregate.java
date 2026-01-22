package com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 穿搭聚合根
 * <p>
 * 穿搭领域模型的核心聚合根，封装穿搭实体及其业务规则，确保业务完整性
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
public class OutfitAggregate extends BaseEntity {

    /**
     * 每个穿搭最多允许的图片数量
     */
    public static final int MAX_IMAGES_PER_OUTFIT = 10;

    /**
     * 每个穿搭最多允许的服装数量
     */
    public static final int MAX_CLOTHINGS_PER_OUTFIT = 20;

    /**
     * 用户ID
     * 穿搭所属的用户
     */
    private String userId;

    /**
     * 穿搭名称
     */
    private String name;

    /**
     * 穿搭描述
     */
    private String description;

    /**
     * 穿着次数
     */
    @Builder.Default
    private Integer wearCount = 0;

    /**
     * 最后穿着日期
     */
    private Date lastWornDate;

    /**
     * 主图ID
     * 关联穿搭图片表t_wardrobe_outfit_image
     */
    private String primaryImageId;

    /**
     * 服装列表（聚合根内的实体）
     * 记录穿搭包含的所有服装
     */
    @Builder.Default
    private List<OutfitClothing> clothings = new ArrayList<>();

    /**
     * 图片列表（聚合根内的实体）
     * 记录穿搭的所有图片
     */
    @Builder.Default
    private List<OutfitImage> images = new ArrayList<>();

    /**
     * 穿着记录列表（聚合根内的实体）
     * 记录穿搭的所有穿着历史
     */
    @Builder.Default
    private List<WearRecord> wearRecords = new ArrayList<>();

    /**
     * 创建新穿搭聚合根

     * 用于创建新穿搭时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId      用户ID，不能为空
     * @param name        穿搭名称，不能为空
     * @param description 穿搭描述，可为空
     * @return 穿搭聚合根
     */
    public static OutfitAggregate create(String userId, String name, String description) {
        return OutfitAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .name(name)
                .description(description)
                .wearCount(0)
                .build();
    }

    /**
     * 更新穿搭信息

     * 更新穿搭的基本信息，不包括服装、图片和穿着记录
     * 注意：参数和状态校验在Service层完成，这里只负责更新信息
     *
     * @param name        新穿搭名称，不能为空（已在Service层校验）
     * @param description 新穿搭描述，可为空
     */
    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 更新服装列表（全量替换）

     * 用于更新穿搭包含的服装列表
     * 注意：参数和状态校验在Service层完成，这里只负责更新服装列表
     *
     * @param newClothings 新的服装实体列表，不能为空（已在Service层校验）
     */
    public void updateClothings(List<OutfitClothing> newClothings) {
        this.clothings.clear();
        if (newClothings != null && !newClothings.isEmpty()) {
            this.clothings.addAll(newClothings);
        }
    }

    /**
     * 批量添加图片

     * 用于一次性添加多张图片的场景
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param images 图片实体列表，不能为空（已在Service层校验）
     */
    public void addImages(List<OutfitImage> images) {
        if (images != null && !images.isEmpty()) {
            this.images.addAll(images);
        }
    }

    /**
     * 记录穿着行为

     * 用户穿着该搭配时调用，更新穿着次数和最后穿着日期，并创建穿着记录
     * 注意：wearDate自动使用当前系统时间
     *
     * @param userId 用户ID，不能为空（已在Service层校验）
     * @param notes  备注，可为空
     */
    public void recordWear(String userId, String notes) {
        // 更新穿着次数
        this.wearCount = (this.wearCount == null ? 0 : this.wearCount) + 1;
        
        // 更新最后穿着日期
        this.lastWornDate = new Date();
        
        // 创建穿着记录
        WearRecord wearRecord = WearRecord.create(userId, this.getId(), notes);
        this.wearRecords.add(wearRecord);
    }

    /**
     * 根据图片ID查找图片
     *
     * @param imageId 图片ID
     * @return 图片实体（如果存在），否则返回Optional.empty()
     */
    public Optional<OutfitImage> findImageById(String imageId) {
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
     * 注意：调用前需要确保图片存在且属于当前穿搭
     *
     * @param imageId 主图ID，不能为空
     * @return 如果设置成功返回true，如果图片不存在返回false
     */
    public boolean setAsPrimaryImage(String imageId) {
        Optional<OutfitImage> imageOpt = findImageById(imageId);
        if (imageOpt.isEmpty()) {
            return false;
        }

        // 清除原有主图状态
        this.images.stream()
                .filter(OutfitImage::isPrimaryImage)
                .forEach(OutfitImage::unsetAsPrimary);

        // 设置新主图
        OutfitImage newPrimaryImage = imageOpt.get();
        newPrimaryImage.setAsPrimary();
        this.primaryImageId = imageId;

        return true;
    }

    /**
     * 检查是否包含服装
     *
     * @return 如果包含服装返回true，否则返回false
     */
    public boolean hasClothings() {
        return clothings != null && !clothings.isEmpty();
    }

    /**
     * 获取服装数量
     *
     * @return 服装数量
     */
    public int getClothingCount() {
        return clothings != null ? clothings.size() : 0;
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

    /**
     * 检查是否有穿着记录
     *
     * @return 如果有穿着记录返回true，否则返回false
     */
    public boolean hasWearRecords() {
        return wearRecords != null && !wearRecords.isEmpty();
    }

    /**
     * 获取穿着记录数量
     *
     * @return 穿着记录数量
     */
    public int getWearRecordCount() {
        return wearRecords != null ? wearRecords.size() : 0;
    }
}
