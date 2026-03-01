package com.xiaoo.kaleido.interview.application.query;

import com.xiaoo.kaleido.api.interview.response.InterviewInfoResponse;
import com.xiaoo.kaleido.interview.application.convertor.InterviewConvertor;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.domain.interview.service.IInterviewDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 面试查询服务
 * <p>
 * 负责面试相关的查询操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewQueryService {

    private final IInterviewDomainService interviewDomainService;

    /**
     * 根据ID查询面试
     *
     * @param interviewId 面试ID
     * @return 面试信息
     */
    public InterviewInfoResponse findById(String interviewId) {
        log.info("查询面试，ID: {}", interviewId);
        InterviewAggregate interview = interviewDomainService.findByIdOrThrow(interviewId);
        return InterviewConvertor.toResponse(interview);
    }

    /**
     * 查询所有面试
     *
     * @return 面试列表
     */
    public List<InterviewInfoResponse> findAll() {
        log.info("查询所有面试");
        List<InterviewAggregate> interviews = interviewDomainService.findAll();
        return interviews.stream()
                .map(InterviewConvertor::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 根据候选人ID查询面试列表
     *
     * @param candidateId 候选人ID
     * @return 面试列表
     */
    public List<InterviewInfoResponse> findByCandidateId(String candidateId) {
        log.info("查询候选人面试，候选人ID: {}", candidateId);
        List<InterviewAggregate> interviews = interviewDomainService.findByCandidateId(candidateId);
        return interviews.stream()
                .map(InterviewConvertor::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 根据面试官查询面试列表
     *
     * @param interviewerName 面试官姓名
     * @return 面试列表
     */
    public List<InterviewInfoResponse> findByInterviewerName(String interviewerName) {
        log.info("查询面试官的面试，面试官: {}", interviewerName);
        List<InterviewAggregate> interviews = interviewDomainService.findByInterviewerName(interviewerName);
        return interviews.stream()
                .map(InterviewConvertor::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 根据时间范围查询面试列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 面试列表
     */
    public List<InterviewInfoResponse> findByTimeRange(Date startTime, Date endTime) {
        log.info("查询时间范围内的面试，开始: {}, 结束: {}", startTime, endTime);
        List<InterviewAggregate> interviews = interviewDomainService.findByTimeRange(startTime, endTime);
        return interviews.stream()
                .map(InterviewConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
