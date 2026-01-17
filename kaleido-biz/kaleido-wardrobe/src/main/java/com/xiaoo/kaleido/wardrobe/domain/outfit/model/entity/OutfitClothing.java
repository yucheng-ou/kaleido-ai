package com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 穿搭-服装关联实体
 * <p>
 * 表示穿搭与服装的多对多关联关系
 * 这是一个简单的关联实体，不包含复杂业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OutfitClothing extends BaseEntity {

    /**
     * 穿搭ID
     * 关联搭配表t_wardrobe_outfit
     */
    private String outfitId;

    /**
     * 服装ID
     * 关联服装表t_wardrobe_clothing
     */
    private String clothingId;
}
