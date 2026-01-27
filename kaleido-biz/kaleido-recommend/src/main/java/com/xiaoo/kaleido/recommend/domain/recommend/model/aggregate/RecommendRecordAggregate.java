package com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 推荐记录聚合根
 * <p>
 * 推荐记录领域模型的核心聚合根，封装推荐记录实体及其业务规则，确保业务完整性
 * 遵循聚合根设计原则：包含最核心的业务逻辑，不包含参数校验
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RecommendRecordAggregate extends BaseEntity {

    /**
     * 用户ID
     * 推荐记录所属的用户
     */
    private String userId;

    /**
     * 用户输入的推荐需求提示词
     */
    private String prompt;

    /**
     * 生成的穿搭ID（关联t_wardrobe_outfit）
     */
    private String outfitId;

    /**
     * 创建新推荐记录聚合根
     * <p>
     * 用于创建新推荐记录时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId   用户ID，不能为空
     * @param prompt   用户输入的推荐需求提示词，不能为空
     * @param outfitId 穿搭id
     * @return 推荐记录聚合根
     */
    public static RecommendRecordAggregate create(String userId, String prompt, String outfitId) {
        return RecommendRecordAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .prompt(prompt)
                .outfitId(outfitId)
                .build();
    }

    /**
     * 获取提示词长度
     *
     * @return 提示词长度
     */
    public int getPromptLength() {
        return prompt != null ? prompt.length() : 0;
    }
}
