package com.xiaoo.kaleido.api.interview.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简历上传响应
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeUploadResponse {

    /**
     * 候选人ID
     */
    private String candidateId;

    /**
     * 候选人姓名
     */
    private String name;

    /**
     * 提取的技能列表
     */
    private String skills;

    /**
     * 工作年限
     */
    private Integer experienceYears;

    /**
     * 处理消息
     */
    private String message;
}
