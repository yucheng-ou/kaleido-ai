package com.xiaoo.kaleido.ai.domain.agent.armory.config;

import lombok.Data;

/**
 * 向量存储工具配置
 * <p>
 * 用于配置QuestionAnswerAdvisor的参数
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
public class VectorStoreConfig {

    /**
     * 返回的最相似文档数量
     * 默认值：100
     */
    private Integer topK = 100;

    /**
     * 过滤表达式
     * 例如："knowledge == '知识库名称'"
     */
    private String filterExpression;

    /**
     * 相似度阈值
     * 默认值：0
     */
    private Double similarityThreshold = 0.0;

    /**
     * 是否启用向量存储
     * 默认值：true
     */
    private Boolean enabled = true;
}
