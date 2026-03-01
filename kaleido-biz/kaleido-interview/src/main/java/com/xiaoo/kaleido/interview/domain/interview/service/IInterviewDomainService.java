package com.xiaoo.kaleido.interview.domain.interview.service;

import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;

import java.util.Date;
import java.util.List;

/**
 * 面试领域服务接口
 * <p>
 * 定义面试领域服务的核心业务逻辑，处理面试的创建、更新、查询等操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface IInterviewDomainService {

    /**
     * 创建面试
     *
     * @param candidateId     候选人ID，不能为空
     * @param interviewTime   面试时间，不能为空
     * @param interviewerName 面试官姓名，不能为空
     * @return 面试聚合根
     */
    InterviewAggregate createInterview(
            String candidateId,
            Date interviewTime,
            String interviewerName);

    /**
     * 根据ID查找面试
     *
     * @param interviewId 面试ID，不能为空
     * @return 面试聚合根
     */
    InterviewAggregate findByIdOrThrow(String interviewId);

    /**
     * 更新面试信息
     *
     * @param interviewId     面试ID，不能为空
     * @param interviewTime   新面试时间，可为空
     * @param interviewerName 新面试官姓名，可为空
     * @return 更新后的面试聚合根
     */
    InterviewAggregate updateInterview(
            String interviewId,
            Date interviewTime,
            String interviewerName);

    /**
     * 更新面试时间
     *
     * @param interviewId   面试ID，不能为空
     * @param interviewTime 新面试时间，不能为空
     * @return 更新后的面试聚合根
     */
    InterviewAggregate updateInterviewTime(String interviewId, Date interviewTime);

    /**
     * 更新面试官
     *
     * @param interviewId     面试ID，不能为空
     * @param interviewerName 新面试官姓名，不能为空
     * @return 更新后的面试聚合根
     */
    InterviewAggregate updateInterviewer(String interviewId, String interviewerName);

    /**
     * 根据候选人ID查找面试列表
     *
     * @param candidateId 候选人ID，不能为空
     * @return 面试聚合根列表
     */
    List<InterviewAggregate> findByCandidateId(String candidateId);

    /**
     * 根据面试官姓名查找面试列表
     *
     * @param interviewerName 面试官姓名，不能为空
     * @return 面试聚合根列表
     */
    List<InterviewAggregate> findByInterviewerName(String interviewerName);

    /**
     * 根据时间范围查找面试列表
     *
     * @param startTime 开始时间，不能为空
     * @param endTime   结束时间，不能为空
     * @return 面试聚合根列表
     */
    List<InterviewAggregate> findByTimeRange(Date startTime, Date endTime);

    /**
     * 查询所有面试
     *
     * @return 面试聚合根列表
     */
    List<InterviewAggregate> findAll();

    /**
     * 删除面试
     *
     * @param interviewId 面试ID，不能为空
     */
    void deleteInterview(String interviewId);
}
