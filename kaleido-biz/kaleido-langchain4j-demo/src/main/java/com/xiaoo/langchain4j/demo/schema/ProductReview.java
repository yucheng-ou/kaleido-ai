package com.xiaoo.langchain4j.demo.schema;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 产品评价数据结构
 * 演示包含评分和分类的结构化输出
 * 
 * @author xiaoo
 * @date 2026-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    
    public enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE
    }
    
    @Description("产品名称")
    private String productName;
    
    @Description("评价者的用户名")
    private String reviewerName;
    
    @Description("整体评分，1-5星")
    private Integer rating;
    
    @Description("评价情感：正面(POSITIVE)、中性(NEUTRAL)、负面(NEGATIVE)")
    private Sentiment sentiment;
    
    @Description("评价标题")
    private String title;
    
    @Description("详细评价内容")
    private String content;
    
    @Description("产品的优点列表")
    private List<String> pros;
    
    @Description("产品的缺点列表")
    private List<String> cons;
    
    @Description("是否推荐该产品")
    private Boolean recommended;
    
    @Description("购买日期，格式：YYYY-MM-DD")
    private String purchaseDate;
}
