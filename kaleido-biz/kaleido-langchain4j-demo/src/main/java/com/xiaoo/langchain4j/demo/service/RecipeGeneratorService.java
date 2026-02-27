package com.xiaoo.langchain4j.demo.service;

import com.xiaoo.langchain4j.demo.schema.Recipe;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

/**
 * 菜谱生成服务接口
 * 演示如何使用AI服务接口生成复杂结构化数据
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
public interface RecipeGeneratorService {
    
    /**
     * 根据食材生成菜谱
     * @param ingredients 食材列表
     * @return 生成的菜谱
     */
    @SystemMessage("""
            你是一位专业厨师，擅长根据可用食材创作美味、实用的菜谱。
            你创作的菜谱总是包含清晰的步骤、准确的准备时间，并且适合家庭烹饪。
            """)
    @UserMessage("请使用以下食材创作一个菜谱：{{ingredients}}")
    Recipe generateRecipe(@V("ingredients") List<String> ingredients);
    
    /**
     * 根据特定要求生成菜谱
     * @param dishType 菜品类型（如：主菜、甜点、汤等）
     * @param cuisine 菜系（如：中餐、意大利餐、墨西哥餐等）
     * @param dietaryRestrictions 饮食限制（如：素食、无麸质、低碳水等）
     * @param cookingTime 最大烹饪时间（分钟）
     * @return 生成的菜谱
     */
    @SystemMessage("你是一位专业厨师，擅长根据特定要求创作定制菜谱。")
    @UserMessage("""
            请创作一个{{dishType}}菜谱，要求如下：
            - 菜系：{{cuisine}}
            - 饮食限制：{{dietaryRestrictions}}
            - 最大烹饪时间：{{cookingTime}}分钟
            
            请确保菜谱实用、美味，并适合家庭烹饪。
            """)
    Recipe generateCustomRecipe(
            @V("dishType") String dishType,
            @V("cuisine") String cuisine,
            @V("dietaryRestrictions") String dietaryRestrictions,
            @V("cookingTime") Integer cookingTime
    );
    
    /**
     * 从描述中提取菜谱
     * @param description 菜谱描述
     * @return 提取的菜谱
     */
    @SystemMessage("你是一位专业的美食作家，擅长从文本描述中提取完整的菜谱信息。")
    @UserMessage("请从以下描述中提取完整的菜谱信息：{{description}}")
    Recipe extractRecipeFromDescription(@V("description") String description);
    
    /**
     * 改进现有菜谱
     * @param originalRecipe 原始菜谱描述
     * @param improvements 改进要求
     * @return 改进后的菜谱
     */
    @SystemMessage("你是一位专业的菜谱顾问，擅长改进和优化现有菜谱。")
    @UserMessage("""
            请改进以下菜谱：
            
            原始菜谱：{{originalRecipe}}
            
            改进要求：{{improvements}}
            
            请提供改进后的完整菜谱。
            """)
    Recipe improveRecipe(@V("originalRecipe") String originalRecipe, @V("improvements") String improvements);
}
