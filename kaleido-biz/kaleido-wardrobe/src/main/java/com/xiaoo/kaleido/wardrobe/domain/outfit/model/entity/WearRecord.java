package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 穿着记录实体
 * <p>
 * 记录用户穿着搭配的历史记录
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WearRecord extends BaseEntity {

    /**
     * 记录ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 搭配ID
     */
    private String outfitId;

    /**
     * 穿着日期
     */
    private LocalDate wearDate;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建穿着记录
     * <p>
     * 根据用户ID、搭配ID、穿着日期和备注创建新的穿着记录
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param userId   用户ID，不能为空（已在Service层校验）
     * @param outfitId 搭配ID，不能为空（已在Service层校验）
     * @param wearDate 穿着日期，不能为空（已在Service层校验）
     * @param notes    备注，可为空
     * @return 穿着记录实体
     */
    public static WearRecord create(
            String userId,
            String outfitId,
            LocalDate wearDate,
            String notes) {
        return WearRecord.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .outfitId(outfitId)
                .wearDate(wearDate)
                .notes(notes)
                .build();
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

    /**
     * 判断是否属于指定搭配
     *
     * @param outfitId 搭配ID
     * @return 如果属于指定搭配则返回true，否则返回false
     */
    public boolean belongsToOutfit(String outfitId) {
        return this.outfitId.equals(outfitId);
    }

    /**
     * 判断是否在指定日期穿着
     *
     * @param date 日期
     * @return 如果在指定日期穿着则返回true，否则返回false
     */
    public boolean isWornOnDate(LocalDate date) {
        return this.wearDate.equals(date);
    }
}
