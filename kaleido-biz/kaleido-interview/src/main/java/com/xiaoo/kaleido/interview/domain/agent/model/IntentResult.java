package com.xiaoo.kaleido.interview.domain.agent.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 意图识别结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentResult {
    /**
     * 识别出的意图类型
     */
    private IntentType intentType;

    /**
     * 意图置信度 (0.0 - 1.0)
     */
    private Double confidence;

    /**
     * 改写后的用户查询（优化后的语义表达）
     */
    private String rewrittenQuery;

    /**
     * 提取的关键参数（可选）
     */
    private String parameters;
}
