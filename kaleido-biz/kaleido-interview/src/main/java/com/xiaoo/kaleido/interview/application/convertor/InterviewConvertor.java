package com.xiaoo.kaleido.interview.application.convertor;

import com.xiaoo.kaleido.api.interview.response.InterviewInfoResponse;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;

import java.time.format.DateTimeFormatter;

/**
 * 面试转换器
 * <p>
 * 负责面试领域模型与响应模型之间的转换
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public class InterviewConvertor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * 将面试聚合根转换为响应对象
     *
     * @param aggregate 面试聚合根
     * @return 面试信息响应
     */
    public static InterviewInfoResponse toResponse(InterviewAggregate aggregate) {
        if (aggregate == null) {
            return null;
        }

        return InterviewInfoResponse.builder()
                .id(String.valueOf(aggregate.getId()))
                .candidateId(String.valueOf(aggregate.getCandidateId()))
                .interviewTime(aggregate.getInterviewTime() != null ?
                        DATE_FORMATTER.format(aggregate.getInterviewTime().toInstant()
                                .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()) : null)
                .interviewerName(aggregate.getInterviewerName())
                .build();
    }
}
