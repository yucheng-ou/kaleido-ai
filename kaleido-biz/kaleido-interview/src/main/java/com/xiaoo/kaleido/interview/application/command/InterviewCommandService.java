package com.xiaoo.kaleido.interview.application.command;

import com.xiaoo.kaleido.api.interview.command.ScheduleInterviewCommand;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.domain.interview.service.IInterviewDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 面试命令服务
 * <p>
 * 负责编排面试相关的命令操作，包括创建、更新面试等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewCommandService {

    private final IInterviewDomainService interviewDomainService;

    /**
     * 安排面试
     *
     * @param command 安排面试命令
     * @return 面试ID
     */
    public String scheduleInterview(ScheduleInterviewCommand command) {
        log.info("安排面试，候选人ID: {}, 面试时间: {}, 面试官: {}",
                command.getCandidateId(), command.getInterviewTime(), command.getInterviewerName());

        InterviewAggregate interview = interviewDomainService.createInterview(
                command.getCandidateId(),
                command.getInterviewTime(),
                command.getInterviewerName()
        );

        log.info("面试安排成功，面试ID: {}", interview.getId());
        return String.valueOf(interview.getId());
    }

    /**
     * 更新面试时间
     *
     * @param interviewId   面试ID
     * @param interviewTime 新面试时间
     * @return 面试ID
     */
    public String updateInterviewTime(String interviewId, java.util.Date interviewTime) {
        log.info("更新面试时间，面试ID: {}, 新时间: {}", interviewId, interviewTime);

        InterviewAggregate interview = interviewDomainService.updateInterviewTime(interviewId, interviewTime);

        log.info("面试时间更新成功");
        return String.valueOf(interview.getId());
    }

    /**
     * 取消面试
     *
     * @param interviewId 面试ID
     */
    public void cancelInterview(String interviewId) {
        log.info("取消面试，面试ID: {}", interviewId);

        interviewDomainService.deleteInterview(interviewId);

        log.info("面试取消成功");
    }
}
