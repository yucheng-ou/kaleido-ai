package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 穿着记录实体
 * <p>
 * 记录用户穿着某个搭配的单次行为
 * 作为OutfitAggregate的一部分进行管理
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WearRecord extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 穿搭ID
     */
    private String outfitId;

    /**
     * 穿着日期
     * 用户选择穿搭时记录当前系统时间
     */
    private Date wearDate;

    /**
     * 备注
     * 用户可选的备注信息
     */
    private String notes;

    /**
     * 创建穿着记录

     * 根据用户ID、穿搭ID和备注信息创建新的穿着记录
     * 注意：wearDate自动设置为当前系统时间
     *
     * @param userId   用户ID，不能为空（已在Service层校验）
     * @param outfitId 穿搭ID，不能为空（已在Service层校验）
     * @param notes    备注，可为空
     * @return 穿着记录实体
     */
    public static WearRecord create(String userId, String outfitId, String notes) {
        return WearRecord.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .outfitId(outfitId)
                .wearDate(new Date())  // 使用当前系统时间
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
     * 判断是否属于指定穿搭
     *
     * @param outfitId 穿搭ID
     * @return 如果属于指定穿搭则返回true，否则返回false
     */
    public boolean belongsToOutfit(String outfitId) {
        return this.outfitId.equals(outfitId);
    }
}
