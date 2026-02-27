package com.xiaoo.langchain4j.demo.service;

import com.xiaoo.langchain4j.demo.schema.ProductReview;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

/**
 * 产品评价分析服务接口
 * 演示如何使用AI服务接口分析情感和提取结构化数据
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
public interface ReviewAnalyzerService {
    
    /**
     * 分析产品评价
     * @param reviewText 评价文本
     * @param productName 产品名称
     * @return 分析后的评价结构
     */
    @SystemMessage("""
            你是一位专业的产品评价分析师，擅长从客户评价中提取关键信息、
            分析情感倾向，并提供结构化的分析结果。
            
            请确保你的分析客观、准确，并覆盖评价的所有重要方面。
            """)
    @UserMessage("""
            请分析以下产品评价：
            
            产品名称：{{productName}}
            评价内容：{{reviewText}}
            
            请提供完整的分析结果。
            """)
    ProductReview analyzeReview(@V("reviewText") String reviewText, @V("productName") String productName);
    
    /**
     * 批量分析产品评价
     * @param reviews 评价列表
     * @param productName 产品名称
     * @return 分析后的评价结构列表
     */
    @SystemMessage("你是一位专业的产品评价分析师，擅长批量分析客户评价。")
    @UserMessage("""
            请分析以下产品评价列表：
            
            产品名称：{{productName}}
            评价列表：{{reviews}}
            
            请为每个评价提供完整的分析结果。
            """)
    List<ProductReview> analyzeReviewsBatch(@V("reviews") List<String> reviews, @V("productName") String productName);
    
    /**
     * 生成虚构的产品评价（用于测试和演示）
     * @param productName 产品名称
     * @param sentiment 情感倾向（正面、中性、负面）
     * @param rating 评分（1-5星）
     * @return 生成的虚构评价
     */
    @SystemMessage("""
            你是一位专业的市场调研专家，擅长创建真实可信的虚构产品评价。
            你创建的评价总是包含具体的优点、缺点，并且符合指定的情感倾向。
            """)
    @UserMessage("""
            请为产品"{{productName}}"创建一个虚构的评价，要求如下：
            - 情感倾向：{{sentiment}}
            - 评分：{{rating}}星
            
            请确保评价内容真实可信，包含具体的产品使用体验。
            """)
    ProductReview generateFictionalReview(
            @V("productName") String productName,
            @V("sentiment") ProductReview.Sentiment sentiment,
            @V("rating") Integer rating
    );
    
    /**
     * 总结多个评价的核心观点
     * @param reviews 评价列表
     * @return 总结的核心观点列表
     */
    @SystemMessage("你是一位专业的内容总结专家，擅长从多个评价中提取核心观点。")
    @UserMessage("""
            请从以下产品评价中提取核心观点：
            
            {{reviews}}
            
            请列出最重要的5个核心观点。
            """)
    List<String> summarizeReviewPoints(@V("reviews") List<String> reviews);
    
    /**
     * 比较两个产品的评价
     * @param product1Name 第一个产品名称
     * @param product1Reviews 第一个产品的评价
     * @param product2Name 第二个产品名称
     * @param product2Reviews 第二个产品的评价
     * @return 比较分析结果
     */
    @SystemMessage("你是一位专业的竞品分析专家，擅长比较不同产品的客户评价。")
    @UserMessage("""
            请比较以下两个产品的客户评价：
            
            产品1：{{product1Name}}
            评价1：{{product1Reviews}}
            
            产品2：{{product2Name}}
            评价2：{{product2Reviews}}
            
            请提供详细的比较分析，包括各自的优点、缺点和改进建议。
            """)
    String compareProducts(
            @V("product1Name") String product1Name,
            @V("product1Reviews") List<String> product1Reviews,
            @V("product2Name") String product2Name,
            @V("product2Reviews") List<String> product2Reviews
    );
}
