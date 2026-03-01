package com.xiaoo.kaleido.api.interview.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 面试信息响应
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewInfoResponse {

    /**
     * 面试ID
     */
    private String id;

    /**
     * 候选人ID
     */
    private String candidateId;

    /**
     * 候选人姓名
     */
    private String candidateName;

    /**
     * 面试时间
     */
    private String interviewTime;

    /**
     * 面试官姓名
     */
    private String interviewerName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
