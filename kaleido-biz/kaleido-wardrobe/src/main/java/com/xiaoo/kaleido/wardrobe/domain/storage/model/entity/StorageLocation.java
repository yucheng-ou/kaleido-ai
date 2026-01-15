package com.xiaoo.kaleido.wardrobe.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 存储位置实体
 * <p>
 * 位置领域模型的核心实体，表示一个具体的存储位置
 * 注意：存储位置实体被包含在StorageLocationAggregate聚合根中
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StorageLocation extends BaseEntity {

    /**
     * 位置ID
     */
    private String id;

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
     * 主图ID
     * 关联位置图片表t_wardrobe_location_image
     */
    private String primaryImageId;

    /**
     * 创建存储位置实体
     * <p>
     * 从聚合根创建存储位置实体（不包含图片列表）
     *
     * @param aggregate 存储位置聚合根
     * @return 存储位置实体
     */
    public static StorageLocation fromAggregate(com.xiaoo.kaleido.wardrobe.domain.model.aggregate.StorageLocationAggregate aggregate) {
        return StorageLocation.builder()
                .id(aggregate.getId())
                .userId(aggregate.getUserId())
                .name(aggregate.getName())
                .description(aggregate.getDescription())
                .address(aggregate.getAddress())
                .primaryImageId(aggregate.getPrimaryImageId())
                .createdAt(aggregate.getCreatedAt())
                .updatedAt(aggregate.getUpdatedAt())
                .build();
    }

    /**
     * 转换为聚合根
     * <p>
     * 将存储位置实体转换为存储位置聚合根（不包含图片列表）
     *
     * @return 存储位置聚合根
     */
    public com.xiaoo.kaleido.wardrobe.domain.model.aggregate.StorageLocationAggregate toAggregate() {
        return com.xiaoo.kaleido.wardrobe.domain.model.aggregate.StorageLocationAggregate.builder()
                .id(this.id)
                .userId(this.userId)
                .name(this.name)
                .description(this.description)
                .address(this.address)
                .primaryImageId(this.primaryImageId)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}
