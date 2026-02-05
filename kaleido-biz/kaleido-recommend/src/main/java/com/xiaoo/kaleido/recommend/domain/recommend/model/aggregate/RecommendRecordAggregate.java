package com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.recommend.types.enums.RecommendRecordStatusEnum;
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
     * 工作流执行记录ID（关联AI工作流执行记录）
     */
    private String executionId;

    /**
     * 推荐记录状态
     */
    private RecommendRecordStatusEnum status;

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
                .status(RecommendRecordStatusEnum.PROCESSING) // 默认状态为处理中
                .build();
    }

    /**
     * 创建新推荐记录聚合根（带执行记录ID）
     * <p>
     * 用于创建新推荐记录时构建聚合根，包含工作流执行记录ID
     *
     * @param userId      用户ID，不能为空
     * @param prompt      用户输入的推荐需求提示词，不能为空
     * @param executionId 工作流执行记录ID
     * @return 推荐记录聚合根
     */
    public static RecommendRecordAggregate createWithExecution(String userId, String prompt, String executionId) {
        return RecommendRecordAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .prompt(prompt)
                .executionId(executionId)
                .status(RecommendRecordStatusEnum.PROCESSING) // 默认状态为处理中
                .build();
    }

    /**
     * 更新推荐记录状态
     *
     * @param status 新的状态
     */
    public void updateStatus(RecommendRecordStatusEnum status) {
        this.status = status;
    }

    /**
     * 更新穿搭ID
     *
     * @param outfitId 穿搭ID
     */
    public void updateOutfitId(String outfitId) {
        this.outfitId = outfitId;
    }

    /**
     * 判断是否为终态（已完成或失败）
     *
     * @return 是否为终态
     */
    public boolean isFinalStatus() {
        return status != null && status.isFinalStatus();
    }

    /**
     * 判断是否为处理中状态
     *
     * @return 是否为处理中状态
     */
    public boolean isProcessing() {
        return status != null && status.isProcessing();
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
