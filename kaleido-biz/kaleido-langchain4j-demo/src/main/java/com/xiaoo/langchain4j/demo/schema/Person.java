package com.xiaoo.langchain4j.demo.schema;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 人员信息数据结构
 * 演示如何使用 @Description 注解帮助AI模型理解字段含义
 * 
 * @author xiaoo
 * @date 2026-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Description("人员的名字")
    private String firstName;
    
    @Description("人员的姓氏")
    private String lastName;
    
    @Description("人员的年龄，整数")
    private Integer age;
    
    @Description("人员的职业")
    private String occupation;
    
    @Description("人员的电子邮箱地址")
    private String email;
    
    @Description("人员的兴趣爱好，多个兴趣用逗号分隔")
    private String hobbies;
}
