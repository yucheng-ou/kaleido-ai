package com.xiaoo.langchain4j.demo.service;

import com.xiaoo.langchain4j.demo.schema.Cv;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 简历生成服务接口
 * 演示如何使用AI服务接口生成复杂嵌套的结构化数据
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
public interface CvGeneratorService {
    
    /**
     * 根据个人描述生成简历
     * @param personalDescription 个人描述
     * @return 生成的简历
     */
    @SystemMessage("""
            你是一位专业的职业顾问和简历撰写专家，擅长根据个人描述创建专业、完整的简历。
            你总是能够识别个人的技能、经验，并将其组织成有说服力的简历格式。
            
            请确保简历包含：
            1. 完整的联系方式
            2. 相关技能总结
            3. 详细的工作经验
            4. 教育背景
            5. 项目经验（如适用）
            6. 证书和语言能力
            """)
    @UserMessage("请根据以下个人描述创建一份专业简历：{{personalDescription}}")
    Cv generateCv(@V("personalDescription") String personalDescription);
    
    /**
     * 改进现有简历
     * @param existingCvDescription 现有简历描述
     * @param improvementAreas 需要改进的方面
     * @return 改进后的简历
     */
    @SystemMessage("你是一位专业的简历优化专家，擅长改进和增强现有简历。")
    @UserMessage("""
            请改进以下简历：
            
            现有简历描述：{{existingCvDescription}}
            
            需要改进的方面：{{improvementAreas}}
            
            请提供改进后的完整简历。
            """)
    Cv improveCv(@V("existingCvDescription") String existingCvDescription, 
                 @V("improvementAreas") String improvementAreas);
    
    /**
     * 为特定职位定制简历
     * @param personalDescription 个人描述
     * @param jobDescription 职位描述
     * @return 定制化的简历
     */
    @SystemMessage("""
            你是一位专业的求职顾问，擅长根据特定职位要求定制简历。
            你总是能够突出展示与职位最相关的技能和经验。
            """)
    @UserMessage("""
            请根据以下信息创建一份针对特定职位的定制简历：
            
            个人描述：{{personalDescription}}
            
            职位描述：{{jobDescription}}
            
            请确保简历突出展示与职位要求最相关的方面。
            """)
    Cv customizeCvForJob(@V("personalDescription") String personalDescription, 
                        @V("jobDescription") String jobDescription);
    
    /**
     * 从LinkedIn或其他职业资料中提取简历
     * @param profileDescription 职业资料描述
     * @return 提取的简历
     */
    @SystemMessage("你是一位专业的数据提取专家，擅长从职业资料中提取结构化信息。")
    @UserMessage("""
            请从以下职业资料中提取信息并创建一份完整简历：
            
            职业资料：{{profileDescription}}
            
            请确保提取所有相关信息并组织成专业简历格式。
            """)
    Cv extractCvFromProfile(@V("profileDescription") String profileDescription);
    
    /**
     * 生成摘要版本的简历（适合求职信或简短介绍）
     * @param fullCvDescription 完整简历描述
     * @return 摘要版本简历
     */
    @SystemMessage("你是一位专业的沟通专家，擅长创建简洁有力的职业摘要。")
    @UserMessage("""
            请根据以下完整简历创建一份摘要版本（适合求职信或简短介绍）：
            
            完整简历：{{fullCvDescription}}
            
            请创建一份简洁但信息完整的摘要，突出核心技能和经验。
            """)
    Cv generateCvSummary(@V("fullCvDescription") String fullCvDescription);
    
    /**
     * 比较两份简历的差异
     * @param cv1Description 第一份简历描述
     * @param cv2Description 第二份简历描述
     * @return 差异分析
     */
    @SystemMessage("你是一位专业的简历比较专家，擅长分析不同简历的优势和不足。")
    @UserMessage("""
            请比较以下两份简历：
            
            简历1：{{cv1Description}}
            
            简历2：{{cv2Description}}
            
            请提供详细的比较分析，包括各自的优势、不足和改进建议。
            """)
    String compareCvs(@V("cv1Description") String cv1Description, 
                     @V("cv2Description") String cv2Description);
}
