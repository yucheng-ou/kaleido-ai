package com.xiaoo.kaleido.recommend.domain.recommend.adapter.service;

/**
 * AI推荐服务接口
 * <p>
 * 定义AI推荐服务的操作，实现应放在infrastructure层
 * 遵循依赖倒置原则：领域层定义接口，基础设施层实现
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
public interface IAiRecommendService {

    /**
     * 生成穿搭推荐
     * <p>
     * 根据用户提示词生成穿搭推荐
     *
     * @param prompt 用户输入的推荐需求提示词
     * @return 生成的穿搭ID
     */
    String generateOutfitRecommendation(String prompt);

    /**
     * 检查AI服务是否可用
     *
     * @return 如果AI服务可用返回true，否则返回false
     */
    boolean isServiceAvailable();
}
