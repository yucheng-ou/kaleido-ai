package com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 搭配聚合根
 * <p>
 * 搭配领域模型的核心聚合根，封装搭配实体及其关联实体（服装关联、图片、穿着记录），确保业务规则的一致性
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OutfitAggregate extends BaseEntity {

    /**
     * 用户ID
     * 搭配所属的用户
     */
    private String userId;

    /**
     * 搭配名称
     */
    private String name;

    /**
     * 搭配描述
     */
    private String description;

    /**
     * 穿着次数
     */
    private Integer wearCount;

    /**
     * 最后穿着日期
     */
    private LocalDate lastWornDate;

    /**
     * 搭配状态
     */
    private DataStatusEnum status;

    /**
     * 主图ID
     * 关联搭配图片表t_wardrobe_outfit_image
     */
    private String primaryImageId;

    /**
     * 服装关联列表（聚合根内的实体）
     * 记录搭配包含的所有服装
     */
    @Builder.Default
    private List<OutfitClothing> clothingAssociations = new ArrayList<>();

    /**
     * 图片列表（聚合根内的实体）
     * 记录搭配的所有图片
     */
    @Builder.Default
    private List<OutfitImage> images = new ArrayList<>();

    /**
     * 穿着记录列表（聚合根内的实体）
     * 记录搭配的穿着历史
     */
    @Builder.Default
    private List<WearRecord> wearRecords = new ArrayList<>();

    /**
     * 创建新搭配聚合根
     * <p>
     * 用于创建新搭配时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId      用户ID，不能为空
     * @param name        搭配名称，不能为空
     * @param description 搭配描述，可为空
     * @return 搭配聚合根
     */
    public static OutfitAggregate create(
            String userId,
            String name,
            String description) {
        return OutfitAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .name(name)
                .description(description)
                .wearCount(0)
                .status(DataStatusEnum.ENABLE)
                .build();
    }

    /**
     * 更新搭配信息
     * <p>
     * 更新搭配的基本信息，不包括服装关联、图片和穿着记录
     * 注意：参数和状态校验在Service层完成，这里只负责更新信息
     *
     * @param name        新搭配名称，不能为空（已在Service层校验）
     * @param description 新搭配描述，可为空
     */
    public void updateInfo(
            String name,
            String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 添加服装关联
     * 注意：参数和状态校验在Service层完成，这里只负责添加关联
     *
     * @param clothingAssociation 服装关联实体，不能为空（已在Service层校验）
     */
    public void addClothingAssociation(OutfitClothing clothingAssociation) {
        this.clothingAssociations.add(clothingAssociation);
    }

    /**
     * 移除服装关联
     * 注意：参数校验在Service层完成，这里只负责移除关联
     *
     * @param clothingId 服装ID，不能为空（已在Service层校验）
     * @return 如果成功移除返回true，如果关联不存在返回false
     */
    public boolean removeClothingAssociation(String clothingId) {
        return this.clothingAssociations.removeIf(association -> association.getClothingId().equals(clothingId));
    }

    /**
     * 添加图片
     * 注意：参数和状态校验在Service层完成，这里只负责添加图片
     *
     * @param image 搭配图片实体，不能为空（已在Service层校验）
     */
    public void addImage(OutfitImage image) {
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
     * 添加穿着记录
     * 注意：参数和状态校验在Service层完成，这里只负责添加记录
     *
     * @param wearRecord 穿着记录实体，不能为空（已在Service层校验）
     */
    public void addWearRecord(WearRecord wearRecord) {
        this.wearRecords.add(wearRecord);
        this.wearCount++;
        this.lastWornDate = wearRecord.getWearDate();
    }

    /**
     * 启用搭配
     * <p>
     * 将搭配状态设置为启用
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用搭配
     * <p>
     * 将搭配状态设置为禁用
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 验证搭配是否可以操作
     * <p>
     * 检查搭配是否处于启用状态
     *
     * @return 如果搭配处于启用状态返回true，否则返回false
     */
    public boolean isEnabled() {
        return this.status == DataStatusEnum.ENABLE;
    }

    /**
     * 获取主图
     *
     * @return 主图（如果存在），否则返回Optional.empty()
     */
    public Optional<OutfitImage> getPrimaryImage() {
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
                .map(OutfitImage::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取服装关联数量
     *
     * @return 服装关联数量
     */
    public int getClothingAssociationCount() {
        return this.clothingAssociations.size();
    }

    /**
     * 获取所有关联的服装ID
     *
     * @return 所有关联的服装ID列表
     */
    public List<String> getAllClothingIds() {
        return this.clothingAssociations.stream()
                .map(OutfitClothing::getClothingId)
                .collect(Collectors.toList());
    }

    /**
     * 获取穿着记录数量
     *
     * @return 穿着记录数量
     */
    public int getWearRecordCount() {
        return this.wearRecords.size();
    }

    /**
     * 获取并清空服装关联列表（用于持久化后清理）
     *
     * @return 服装关联列表
     */
    public List<OutfitClothing> getAndClearClothingAssociations() {
        List<OutfitClothing> associationsCopy = new ArrayList<>(this.clothingAssociations);
        this.clothingAssociations.clear();
        return associationsCopy;
    }

    /**
     * 获取并清空图片列表（用于持久化后清理）
     *
     * @return 图片列表
     */
    public List<OutfitImage> getAndClearImages() {
        List<OutfitImage> imagesCopy = new ArrayList<>(this.images);
        this.images.clear();
        return imagesCopy;
    }

    /**
     * 获取并清空穿着记录列表（用于持久化后清理）
     *
     * @return 穿着记录列表
     */
    public List<WearRecord> getAndClearWearRecords() {
        List<WearRecord> recordsCopy = new ArrayList<>(this.wearRecords);
        this.wearRecords.clear();
        return recordsCopy;
    }
}
