package com.xiaoo.langchain4j.demo.service;

import com.xiaoo.langchain4j.demo.schema.Person;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 人员信息提取服务接口
 * 演示如何使用AI服务接口提取结构化数据
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
public interface PersonExtractorService {
    
    /**
     * 从文本中提取人员信息
     * @param text 包含人员信息的文本
     * @return 提取的人员信息对象
     */
    @SystemMessage("你是一个专业的信息提取助手，擅长从文本中提取结构化的人员信息。")
    @UserMessage("请从以下文本中提取人员信息：{{text}}")
    Person extractPerson(@V("text") String text);
    
    /**
     * 从文本中提取人员信息（带更多上下文）
     * @param text 包含人员信息的文本
     * @param context 额外的上下文信息
     * @return 提取的人员信息对象
     */
    @SystemMessage("你是一个专业的信息提取助手，擅长从文本中提取结构化的人员信息。")
    @UserMessage("""
            请从以下文本中提取人员信息：
            
            文本内容：{{text}}
            
            额外上下文：{{context}}
            
            请确保提取完整准确的信息。
            """)
    Person extractPersonWithContext(@V("text") String text, @V("context") String context);
    
    /**
     * 生成虚构的人员信息
     * @param profession 职业
     * @param ageRange 年龄范围
     * @return 生成的虚构人员信息
     */
    @SystemMessage("你是一个创意写作助手，擅长创建虚构但合理的人物角色。")
    @UserMessage("请创建一个{{profession}}职业的虚构人物，年龄在{{ageRange}}之间。")
    Person generateFictionalPerson(@V("profession") String profession, @V("ageRange") String ageRange);
}
