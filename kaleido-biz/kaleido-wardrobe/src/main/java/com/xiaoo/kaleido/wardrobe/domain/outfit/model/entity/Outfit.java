package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 搭配实体
 * <p>
 * 搭配领域模型的核心实体，表示一个具体的服装搭配
 * 注意：搭配实体被包含在OutfitAggregate聚合根中
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Outfit extends BaseEntity {

    /**
     * 搭配ID
     */
    private String id;

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
     * 主图ID
     * 关联搭配图片表t_wardrobe_outfit_image
     */
    private String primaryImageId;

    /**
     * 创建搭配实体
     * <p>
     * 从聚合根创建搭配实体（不包含关联列表）
     *
     * @param aggregate 搭配聚合根
     * @return 搭配实体
     */
    public static Outfit fromAggregate(OutfitAggregate aggregate) {
        return Outfit.builder()
                .id(aggregate.getId())
                .userId(aggregate.getUserId())
                .name(aggregate.getName())
                .description(aggregate.getDescription())
                .wearCount(aggregate.getWearCount())
                .lastWornDate(aggregate.getLastWornDate())
                .primaryImageId(aggregate.getPrimaryImageId())
                .createdAt(aggregate.getCreatedAt())
                .updatedAt(aggregate.getUpdatedAt())
                .build();
    }

    /**
     * 转换为聚合根
     * <p>
     * 将搭配实体转换为搭配聚合根（不包含关联列表）
     *
     * @return 搭配聚合根
     */
    public OutfitAggregate toAggregate() {
        return OutfitAggregate.builder()
                .id(this.id)
                .userId(this.userId)
                .name(this.name)
                .description(this.description)
                .wearCount(this.wearCount)
                .lastWornDate(this.lastWornDate)
                .primaryImageId(this.primaryImageId)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}
