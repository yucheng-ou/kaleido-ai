package com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 位置记录聚合根
 * <p>
 * 记录服装位置变更历史，确保一件衣服在同一时间只能有一个当前位置
 * 遵循聚合根设计原则：包含最核心的业务逻辑，不包含参数校验
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocationRecordAggregate extends BaseEntity {

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
     * 记录服装放入位置的时间
     */
    private Date recordTime;

    /**
     * 备注
     * 位置变更的备注信息
     */
    private String notes;

    /**
     * 是否为当前位置记录
     * true-是，false-否
     * 一件衣服在同一时间只能有一个当前位置记录
     */
    private Boolean isCurrent;

    /**
     * 创建新位置记录聚合根

     * 用于创建新位置记录时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param clothingId 服装ID，不能为空
     * @param locationId 位置ID，不能为空
     * @param userId     用户ID，不能为空
     * @param notes      备注，可为空
     * @return 位置记录聚合根
     */
    public static LocationRecordAggregate create(
            String clothingId,
            String locationId,
            String userId,
            String notes) {
        return LocationRecordAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .clothingId(clothingId)
                .locationId(locationId)
                .userId(userId)
                .recordTime(new Date())
                .notes(notes)
                .isCurrent(true)
                .build();
    }

    /**
     * 标记为非当前记录

     * 用于服装位置变更时，将旧位置记录标记为非当前
     * 注意：调用前需要确保当前记录是当前位置记录
     */
    public void markAsNotCurrent() {
        this.isCurrent = false;
    }

    /**
     * 更新备注信息

     * 更新位置记录的备注信息
     *
     * @param notes 新备注，可为空
     */
    public void updateNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 检查是否为当前位置记录
     *
     * @return 如果是当前位置记录返回true，否则返回false
     */
    public boolean isCurrentRecord() {
        return Boolean.TRUE.equals(isCurrent);
    }
}
