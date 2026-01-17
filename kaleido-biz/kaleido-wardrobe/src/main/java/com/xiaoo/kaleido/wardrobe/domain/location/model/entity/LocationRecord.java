package com.xiaoo.kaleido.wardrobe.domain.location.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 位置记录实体
 * <p>
 * 记录服装位置变更历史，作为读模型使用
 * 遵循CQRS原则：位置记录主要用于查询和历史追溯
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocationRecord extends BaseEntity {

    /**
     * 服装ID
     * 关联服装表t_wardrobe_clothing
     */
    private String clothingId;

    /**
     * 位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    private String locationId;

    /**
     * 用户ID（冗余，便于查询）
     * 关联用户表
     */
    private String userId;

    /**
     * 记录时间
     * 服装被放置到该位置的时间
     */
    private LocalDateTime recordTime;

    /**
     * 备注
     * 位置变更的备注信息
     */
    private String notes;

    /**
     * 是否为当前位置记录
     * 0-否，1-是
     * 一件服装只能有一个当前位置记录
     */
    private Boolean isCurrent;

    /**
     * 创建位置记录
     * <p>
     * 用于记录服装位置变更
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param clothingId 服装ID，不能为空（已在Service层校验）
     * @param locationId 位置ID，不能为空（已在Service层校验）
     * @param userId     用户ID，不能为空（已在Service层校验）
     * @param recordTime 记录时间，不能为空（已在Service层校验）
     * @param notes      备注，可为空
     * @param isCurrent  是否为当前位置记录，不能为空（已在Service层校验）
     * @return 位置记录实体
     */
    public static LocationRecord create(
            String clothingId,
            String locationId,
            String userId,
            LocalDateTime recordTime,
            String notes,
            Boolean isCurrent) {
        return LocationRecord.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .clothingId(clothingId)
                .locationId(locationId)
                .userId(userId)
                .recordTime(recordTime)
                .notes(notes)
                .isCurrent(isCurrent)
                .build();
    }

    /**
     * 判断是否为当前位置记录
     *
     * @return 如果是当前位置记录返回true，否则返回false
     */
    public boolean isCurrentRecord() {
        return Boolean.TRUE.equals(this.isCurrent);
    }

    /**
     * 设置为当前位置记录
     */
    public void setAsCurrent() {
        this.isCurrent = true;
    }

    /**
     * 取消当前位置记录
     */
    public void unsetAsCurrent() {
        this.isCurrent = false;
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
     * 判断是否属于指定位置
     *
     * @param locationId 位置ID
     * @return 如果属于指定位置则返回true，否则返回false
     */
    public boolean belongsToLocation(String locationId) {
        return this.locationId.equals(locationId);
    }

    /**
     * 判断是否属于指定用户
     *
     * @param userId 用户ID
     * @return 如果属于指定用户则返回true，否则返回false
     */
    public boolean belongsToUser(String userId) {
        return this.userId.equals(userId);
    }
}
