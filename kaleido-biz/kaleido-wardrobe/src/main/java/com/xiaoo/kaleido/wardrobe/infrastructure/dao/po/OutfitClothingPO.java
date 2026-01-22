package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 穿搭-服装关联持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_outfit_clothing
 * 表示穿搭与服装的多对多关联关系
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_outfit_clothing")
public class OutfitClothingPO extends BasePO {

    /**
     * 穿搭ID
     * 关联穿搭表 t_wardrobe_outfit
     */
    @TableField("outfit_id")
    private String outfitId;

    /**
     * 服装ID
     * 关联服装表 t_wardrobe_clothing
     */
    @TableField("clothing_id")
    private String clothingId;
}
