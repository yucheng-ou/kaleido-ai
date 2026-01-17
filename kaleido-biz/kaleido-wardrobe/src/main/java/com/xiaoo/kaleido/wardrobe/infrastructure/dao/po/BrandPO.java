package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌持久化对象
 * <p>
 * 对应数据库表：t_wardrobe_brand
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_brand")
public class BrandPO extends BasePO {

    /**
     * 品牌名称（唯一）
     */
    @TableField("name")
    private String name;

    /**
     * 品牌Logo路径（在minio中的文件路径）
     */
    @TableField("logo_path")
    private String logoPath;

    /**
     * 品牌描述
     */
    @TableField("description")
    private String description;
}
