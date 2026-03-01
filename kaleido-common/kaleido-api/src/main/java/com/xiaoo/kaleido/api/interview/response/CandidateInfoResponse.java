package com.xiaoo.kaleido.api.interview.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人信息响应
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateInfoResponse {

    /**
     * 候选人ID
     */
    private String id;

    /**
     * 候选人姓名
     */
    private String name;

    /**
     * 技能列表（逗号分隔）
     */
    private String skills;

    /**
     * 工作年限
     */
    private Integer experienceYears;

    /**
     * 原始简历文本
     */
    private String rawResumeText;

    /**
     * 候选人状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
