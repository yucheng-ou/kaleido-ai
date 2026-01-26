package com.xiaoo.kaleido.recommend.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 推荐记录持久化对象
 * <p>
 * 对应数据库表：t_recommend_record
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_recommend_record")
public class RecommendRecordPO extends BasePO {

    /**
     * 用户ID
     * 推荐记录所属的用户
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户输入的推荐需求提示词
     */
    @TableField("prompt")
    private String prompt;

    /**
     * 生成的穿搭ID（关联t_wardrobe_outfit）
     */
    @TableField("outfit_id")
    private String outfitId;
}
