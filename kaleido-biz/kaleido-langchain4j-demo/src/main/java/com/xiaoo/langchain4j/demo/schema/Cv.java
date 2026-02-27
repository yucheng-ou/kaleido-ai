package com.xiaoo.langchain4j.demo.schema;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 简历数据结构
 * 演示复杂嵌套的结构化输出
 * 
 * @author xiaoo
 * @date 2026-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cv {
    
    @Description("候选人的全名")
    private String fullName;
    
    @Description("候选人的联系方式")
    private ContactInfo contactInfo;
    
    @Description("候选人的专业技能，用逗号分隔")
    private String skills;
    
    @Description("候选人的工作经验列表")
    private List<WorkExperience> workExperience;
    
    @Description("候选人的教育背景列表")
    private List<Education> education;
    
    @Description("候选人的项目经验列表")
    private List<Project> projects;
    
    @Description("候选人的证书和认证")
    private List<String> certifications;
    
    @Description("候选人的语言能力")
    private List<String> languages;
    
    // 嵌套类：联系方式
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactInfo {
        @Description("电子邮箱地址")
        private String email;
        
        @Description("电话号码")
        private String phone;
        
        @Description("LinkedIn个人资料URL")
        private String linkedIn;
        
        @Description("GitHub个人资料URL")
        private String github;
        
        @Description("居住地址")
        private String address;
    }
    
    // 嵌套类：工作经验
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkExperience {
        @Description("公司名称")
        private String company;
        
        @Description("职位名称")
        private String position;
        
        @Description("开始日期，格式：YYYY-MM")
        private String startDate;
        
        @Description("结束日期，格式：YYYY-MM（如仍在职则为'至今'）")
        private String endDate;
        
        @Description("工作职责描述")
        private String description;
        
        @Description("主要成就")
        private List<String> achievements;
    }
    
    // 嵌套类：教育背景
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Education {
        @Description("学校名称")
        private String institution;
        
        @Description("学位名称")
        private String degree;
        
        @Description("专业领域")
        private String fieldOfStudy;
        
        @Description("开始年份")
        private Integer startYear;
        
        @Description("结束年份")
        private Integer endYear;
        
        @Description("成绩/GPA")
        private String grade;
    }
    
    // 嵌套类：项目经验
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Project {
        @Description("项目名称")
        private String name;
        
        @Description("项目描述")
        private String description;
        
        @Description("使用的技术栈")
        private List<String> technologies;
        
        @Description("项目角色")
        private String role;
        
        @Description("项目成果")
        private List<String> outcomes;
    }
}
