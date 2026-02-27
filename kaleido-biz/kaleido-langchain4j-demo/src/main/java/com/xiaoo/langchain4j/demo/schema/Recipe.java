package com.xiaoo.langchain4j.demo.schema;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 菜谱数据结构
 * 演示包含列表字段的结构化输出
 * 
 * @author xiaoo
 * @date 2026-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    
    @Description("菜谱的简短标题，不超过5个单词")
    private String title;
    
    @Description("菜谱的详细描述，2-3句话")
    private String description;
    
    @Description("所需食材列表，每个食材单独列出")
    private List<String> ingredients;
    
    @Description("制作步骤列表，每个步骤用简洁的语言描述")
    private List<String> steps;
    
    @Description("预估准备时间（分钟）")
    private Integer prepTimeMinutes;
    
    @Description("预估烹饪时间（分钟）")
    private Integer cookTimeMinutes;
    
    @Description("菜品难度等级：简单、中等、困难")
    private String difficulty;
    
    @Description("可服务的份数")
    private Integer servings;
}
