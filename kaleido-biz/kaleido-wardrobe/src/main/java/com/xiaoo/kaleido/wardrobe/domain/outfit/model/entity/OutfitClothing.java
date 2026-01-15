package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 搭配-服装关联实体
 * <p>
 * 表示搭配与服装之间的关联关系
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OutfitClothing extends BaseEntity {

    /**
     * 关联ID
     */
    private String id;

    /**
     * 搭配ID
     */
    private String outfitId;

    /**
     * 服装ID
     */
    private String clothingId;

    /**
     * 创建搭配-服装关联
     * <p>
     * 根据搭配ID和服装ID创建新的关联
     * 注意：参数校验在Service层完成，这里只负责构建实体
     *
     * @param outfitId   搭配ID，不能为空（已在Service层校验）
     * @param clothingId 服装ID，不能为空（已在Service层校验）
     * @return 搭配-服装关联实体
     */
    public static OutfitClothing create(
            String outfitId,
            String clothingId) {
        return OutfitClothing.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .outfitId(outfitId)
                .clothingId(clothingId)
                .build();
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
     * 判断是否关联指定服装
     *
     * @param clothingId 服装ID
     * @return 如果关联指定服装则返回true，否则返回false
     */
    public boolean isAssociatedWithClothing(String clothingId) {
        return this.clothingId.equals(clothingId);
    }
}
