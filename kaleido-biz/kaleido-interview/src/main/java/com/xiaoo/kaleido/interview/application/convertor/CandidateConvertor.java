package com.xiaoo.kaleido.interview.application.convertor;

import com.xiaoo.kaleido.api.interview.response.CandidateInfoResponse;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;

import java.time.format.DateTimeFormatter;

/**
 * 候选人转换器
 * <p>
 * 负责候选人领域模型与响应模型之间的转换
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public class CandidateConvertor {

    /**
     * 将候选人聚合根转换为响应对象
     *
     * @param aggregate 候选人聚合根
     * @return 候选人信息响应
     */
    public static CandidateInfoResponse toResponse(CandidateAggregate aggregate) {
        if (aggregate == null) {
            return null;
        }

        return CandidateInfoResponse.builder()
                .id(String.valueOf(aggregate.getId()))
                .name(aggregate.getName())
                .skills(aggregate.getSkills())
                .experienceYears(aggregate.getExperienceYears())
                .rawResumeText(aggregate.getRawResumeText())
                .status(aggregate.getStatus().name())
                .build();
    }
}
