package com.xiaoo.kaleido.recommend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 推荐配置类
 * <p>
 * 管理推荐相关的配置参数，如金币费用、业务规则等
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Getter
@Configuration
public class RecommendConfig {

    /**
     * 推荐生成费用（金币）
     * 默认值：10金币
     */
    @Value("${recommend.generation.cost:10}")
    private Long recommendGenerationCost;

    /**
     * 测试模式：是否使用测试数据生成穿搭
     * 默认值：true（在没有AI服务时使用）
     */
    @Value("${recommend.test.mode:true}")
    private Boolean testMode;

    /**
     * 测试穿搭名称前缀
     */
    @Value("${recommend.test.outfit.name.prefix:AI推荐穿搭}")
    private String testOutfitNamePrefix;

    /**
     * 测试穿搭描述前缀
     */
    @Value("${recommend.test.outfit.description.prefix:基于提示词生成的AI推荐穿搭}")
    private String testOutfitDescriptionPrefix;
}
