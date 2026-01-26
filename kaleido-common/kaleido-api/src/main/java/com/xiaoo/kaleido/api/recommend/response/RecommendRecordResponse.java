package com.xiaoo.kaleido.api.recommend.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

/**
 * 推荐记录DTO
 * <p>
 * 用于查询层返回推荐记录信息
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRecordResponse extends BaseResp {

    /**
     * 推荐记录ID
     */
    private String id;

    /**
     * 用户ID
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

}
