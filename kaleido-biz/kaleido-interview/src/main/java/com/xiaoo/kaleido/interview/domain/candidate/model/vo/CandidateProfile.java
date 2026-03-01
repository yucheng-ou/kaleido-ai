package com.xiaoo.kaleido.interview.domain.candidate.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人简介值对象（Domain层定义）
 * <p>
 * 从简历中提取的结构化候选人信息，用于领域逻辑处理
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile {

    /**
     * 候选人姓名
     */
    private String name;

    /**
     * 联系方式（电话或邮箱）
     */
    private String contact;

    /**
     * 学历
     */
    private String education;

    /**
     * 核心技能列表（逗号分隔）
     */
    private String skills;

    /**
     * 工作年限
     */
    private Integer experienceYears;
}
