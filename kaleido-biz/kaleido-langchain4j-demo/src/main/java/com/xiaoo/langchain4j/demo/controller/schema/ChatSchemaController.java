package com.xiaoo.langchain4j.demo.controller.schema;

import com.xiaoo.langchain4j.demo.schema.Cv;
import com.xiaoo.langchain4j.demo.schema.Person;
import com.xiaoo.langchain4j.demo.schema.ProductReview;
import com.xiaoo.langchain4j.demo.schema.Recipe;
import com.xiaoo.langchain4j.demo.service.*;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Langchain4j 结构化数据生成控制器
 * 提供四个独立的接口，分别对应不同的结构化数据生成功能
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain/schema")
@RestController
@RequiredArgsConstructor
public class ChatSchemaController {

    private final OpenAiChatModel chatModel;

    /**
     * 人员信息提取接口
     * 从文本中提取结构化的人员信息
     *
     * @param text 包含人员信息的文本
     * @return 提取的人员信息对象
     */
    @PostMapping("/person/extract")
    public Person extractPerson(
            @RequestParam String text) {
        
        log.info("开始提取人员信息，文本长度: {}", text.length());
        PersonExtractorService personService = AiServices.create(PersonExtractorService.class, chatModel);
        Person person = personService.extractPerson(text);
        log.info("人员信息提取完成: {}", person);
        return person;
    }

    /**
     * 生成虚构人员信息接口
     * 根据职业和年龄范围生成虚构的人员信息
     *
     * @param profession 职业
     * @param ageRange 年龄范围
     * @return 生成的虚构人员信息
     */
    @PostMapping("/person/generate-fictional")
    public Person generateFictionalPerson(
            @RequestParam String profession,
            @RequestParam String ageRange) {
        
        log.info("开始生成虚构人员信息，职业: {}, 年龄范围: {}", profession, ageRange);
        PersonExtractorService personService = AiServices.create(PersonExtractorService.class, chatModel);
        Person person = personService.generateFictionalPerson(profession, ageRange);
        log.info("虚构人员信息生成完成: {}", person);
        return person;
    }

    /**
     * 菜谱生成接口
     * 根据食材列表生成菜谱
     *
     * @param ingredients 食材列表
     * @return 生成的菜谱
     */
    @PostMapping("/recipe/generate")
    public Recipe generateRecipe(
            @RequestParam String ingredients) {
        
        log.info("开始生成菜谱，食材: {}", ingredients);
        List<String> ingredientList = Arrays.asList(ingredients.split(","));
        RecipeGeneratorService recipeService = AiServices.create(RecipeGeneratorService.class, chatModel);
        Recipe recipe = recipeService.generateRecipe(ingredientList);
        log.info("菜谱生成完成: {}", recipe);
        return recipe;
    }

    /**
     * 定制菜谱生成接口
     * 根据特定要求生成定制菜谱
     *
     * @param dishType 菜品类型
     * @param cuisine 菜系
     * @param dietaryRestrictions 饮食限制
     * @param cookingTime 烹饪时间（分钟）
     * @return 生成的定制菜谱
     */
    @PostMapping("/recipe/generate-custom")
    public Recipe generateCustomRecipe(
            @RequestParam String dishType,
            @RequestParam String cuisine,
            @RequestParam String dietaryRestrictions,
            @RequestParam Integer cookingTime) {
        
        log.info("开始生成定制菜谱，菜品类型: {}, 菜系: {}, 饮食限制: {}, 烹饪时间: {}分钟", 
                dishType, cuisine, dietaryRestrictions, cookingTime);
        RecipeGeneratorService recipeService = AiServices.create(RecipeGeneratorService.class, chatModel);
        Recipe recipe = recipeService.generateCustomRecipe(dishType, cuisine, dietaryRestrictions, cookingTime);
        log.info("定制菜谱生成完成: {}", recipe);
        return recipe;
    }

    /**
     * 产品评价分析接口
     * 分析产品评价并返回结构化分析结果
     *
     * @param reviewText 评价文本
     * @param productName 产品名称
     * @return 分析后的评价结构
     */
    @PostMapping("/review/analyze")
    public ProductReview analyzeReview(
            @RequestParam String reviewText,
            @RequestParam String productName) {

        log.info("开始分析产品评价，产品名称: {}, 评价长度: {}", productName, reviewText.length());
        ReviewAnalyzerService reviewService = AiServices.create(ReviewAnalyzerService.class, chatModel);
        ProductReview review = reviewService.analyzeReview(reviewText, productName);
        log.info("产品评价分析完成: {}", review);
        return review;
    }

    /**
     * 生成虚构产品评价接口
     * 生成指定情感倾向和评分的虚构产品评价
     *
     * @param productName 产品名称
     * @param sentiment 情感倾向（POSITIVE, NEUTRAL, NEGATIVE）
     * @param rating 评分（1-5星）
     * @return 生成的虚构评价
     */
    @PostMapping("/review/generate-fictional")
    public ProductReview generateFictionalReview(
            @RequestParam String productName,
            @RequestParam ProductReview.Sentiment sentiment,
            @RequestParam Integer rating) {
        
        log.info("开始生成虚构产品评价，产品名称: {}, 情感倾向: {}, 评分: {}星", 
                productName, sentiment, rating);
        ReviewAnalyzerService reviewService = AiServices.create(ReviewAnalyzerService.class, chatModel);
        ProductReview review = reviewService.generateFictionalReview(productName, sentiment, rating);
        log.info("虚构产品评价生成完成: {}", review);
        return review;
    }

    /**
     * 简历生成接口
     * 根据个人描述生成简历
     *
     * @param personalDescription 个人描述
     * @return 生成的简历
     */
    @PostMapping("/cv/generate")
    public Cv generateCv(
            @RequestParam String personalDescription) {
        
        log.info("开始生成简历，个人描述长度: {}", personalDescription.length());
        CvGeneratorService cvService = AiServices.create(CvGeneratorService.class, chatModel);
        Cv cv = cvService.generateCv(personalDescription);
        log.info("简历生成完成: {}", cv);
        return cv;
    }

    /**
     * 定制简历生成接口
     * 根据个人描述和职位描述生成定制简历
     *
     * @param personalDescription 个人描述
     * @param jobDescription 职位描述
     * @return 定制化的简历
     */
    @PostMapping("/cv/customize")
    public Cv customizeCvForJob(
            @RequestParam String personalDescription,
            @RequestParam String jobDescription) {
        
        log.info("开始生成定制简历，个人描述长度: {}, 职位描述长度: {}", 
                personalDescription.length(), jobDescription.length());
        CvGeneratorService cvService = AiServices.create(CvGeneratorService.class, chatModel);
        Cv cv = cvService.customizeCvForJob(personalDescription, jobDescription);
        log.info("定制简历生成完成: {}", cv);
        return cv;
    }

}
