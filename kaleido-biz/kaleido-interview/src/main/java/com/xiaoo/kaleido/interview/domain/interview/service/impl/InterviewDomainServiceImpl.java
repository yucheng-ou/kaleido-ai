package com.xiaoo.kaleido.interview.domain.interview.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.domain.interview.service.IInterviewDomainService;
import com.xiaoo.kaleido.interview.domain.interview.adapter.repository.IInterviewRepository;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 面试领域服务实现类
 * <p>
 * 实现面试领域服务的核心业务逻辑，遵循DDD原则：
 * 1. service层包含参数校验与聚合根的修改
 * 2. 可以查询数据库进行参数校验
 * 3. 不能直接调用仓储层写入或更新数据库（通过聚合根的方法修改状态）
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewDomainServiceImpl implements IInterviewDomainService {

    private final IInterviewRepository interviewRepository;

    @Override
    public InterviewAggregate createInterview(
            String candidateId,
            Date interviewTime,
            String interviewerName) {

        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }
        if (interviewTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试时间不能为空");
        }
        if (StrUtil.isBlank(interviewerName)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试官姓名不能为空");
        }

        // 创建聚合根
        InterviewAggregate interview = InterviewAggregate.create(
                candidateId, interviewTime, interviewerName
        );

        log.info("创建面试成功，面试 ID: {}, 候选人 ID: {}", interview.getId(), candidateId);
        return interview;
    }

    @Override
    public InterviewAggregate findByIdOrThrow(String interviewId) {
        // 参数校验
        if (StrUtil.isBlank(interviewId)) {
            throw InterviewException.of(InterviewErrorCode.INTERVIEW_ID_NOT_NULL, "面试ID不能为空");
        }

        // 查询数据库
        return interviewRepository.findByIdOrThrow(interviewId);
    }

    @Override
    public InterviewAggregate updateInterview(
            String interviewId,
            Date interviewTime,
            String interviewerName) {
        // 参数校验
        if (StrUtil.isBlank(interviewId)) {
            throw InterviewException.of(InterviewErrorCode.INTERVIEW_ID_NOT_NULL, "面试ID不能为空");
        }

        // 查找面试
        InterviewAggregate interview = interviewRepository.findByIdOrThrow(interviewId);

        // 更新聚合根信息（聚合根本身包含最核心的业务逻辑）
        interview.updateInfo(interviewTime, interviewerName);

        log.info("更新面试成功，面试 ID: {}", interviewId);
        return interview;
    }

    @Override
    public InterviewAggregate updateInterviewTime(String interviewId, Date interviewTime) {
        // 参数校验
        if (StrUtil.isBlank(interviewId)) {
            throw InterviewException.of(InterviewErrorCode.INTERVIEW_ID_NOT_NULL, "面试ID不能为空");
        }
        if (interviewTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试时间不能为空");
        }

        // 查找面试
        InterviewAggregate interview = interviewRepository.findByIdOrThrow(interviewId);

        // 更新面试时间（聚合根本身包含最核心的业务逻辑）
        interview.updateInterviewTime(interviewTime);

        log.info("更新面试时间成功，面试 ID: {}", interviewId);
        return interview;
    }

    @Override
    public InterviewAggregate updateInterviewer(String interviewId, String interviewerName) {
        // 参数校验
        if (StrUtil.isBlank(interviewId)) {
            throw InterviewException.of(InterviewErrorCode.INTERVIEW_ID_NOT_NULL, "面试ID不能为空");
        }
        if (StrUtil.isBlank(interviewerName)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试官姓名不能为空");
        }

        // 查找面试
        InterviewAggregate interview = interviewRepository.findByIdOrThrow(interviewId);

        // 更新面试官（聚合根本身包含最核心的业务逻辑）
        interview.updateInterviewer(interviewerName);

        log.info("更新面试官成功，面试 ID: {}", interviewId);
        return interview;
    }

    @Override
    public List<InterviewAggregate> findByCandidateId(String candidateId) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查询数据库
        return interviewRepository.findByCandidateId(candidateId);
    }

    @Override
    public List<InterviewAggregate> findByInterviewerName(String interviewerName) {
        // 参数校验
        if (StrUtil.isBlank(interviewerName)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试官姓名不能为空");
        }

        // 查询数据库
        return interviewRepository.findByInterviewerName(interviewerName);
    }

    @Override
    public List<InterviewAggregate> findByTimeRange(Date startTime, Date endTime) {
        // 参数校验
        if (startTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "开始时间不能为空");
        }
        if (endTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "结束时间不能为空");
        }
        if (startTime.after(endTime)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "开始时间不能晚于结束时间");
        }

        // 查询数据库
        return interviewRepository.findByTimeRange(startTime, endTime);
    }

    @Override
    public List<InterviewAggregate> findAll() {
        // 查询数据库
        return interviewRepository.findAll();
    }

    @Override
    public void deleteInterview(String interviewId) {
        // 参数校验
        if (StrUtil.isBlank(interviewId)) {
            throw InterviewException.of(InterviewErrorCode.INTERVIEW_ID_NOT_NULL, "面试ID不能为空");
        }

        // 删除面试
        interviewRepository.deleteById(interviewId);

        log.info("删除面试成功，面试 ID: {}", interviewId);
    }
}
