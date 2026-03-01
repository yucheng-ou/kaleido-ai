package com.xiaoo.kaleido.interview.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.interview.domain.interview.adapter.repository.IInterviewRepository;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.infrastructure.adapter.repository.convertor.InterviewInfraConvertor;
import com.xiaoo.kaleido.interview.infrastructure.dao.InterviewDao;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.InterviewPO;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 面试仓储实现（基础设施层）
 * <p>
 * 面试仓储接口的具体实现，负责面试聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class InterviewRepositoryImpl implements IInterviewRepository {

    private final InterviewDao interviewDao;

    @Override
    public void save(InterviewAggregate interviewAggregate) {
        // 1.转换InterviewAggregate为InterviewPO
        InterviewPO interviewPO = InterviewInfraConvertor.INSTANCE.toPO(interviewAggregate);

        // 2.保存面试基本信息
        interviewDao.insert(interviewPO);

        log.info("面试保存成功，面试ID: {}, 候选人ID: {}, 面试官: {}",
                interviewAggregate.getId(), interviewAggregate.getCandidateId(), interviewAggregate.getInterviewerName());
    }

    @Override
    public void update(InterviewAggregate interviewAggregate) {
        // 1.转换InterviewAggregate为InterviewPO
        InterviewPO interviewPO = InterviewInfraConvertor.INSTANCE.toPO(interviewAggregate);

        // 2.更新面试基本信息
        interviewDao.updateById(interviewPO);

        log.info("面试更新成功，面试ID: {}, 候选人ID: {}, 面试官: {}",
                interviewAggregate.getId(), interviewAggregate.getCandidateId(), interviewAggregate.getInterviewerName());
    }

    @Override
    public InterviewAggregate findById(String interviewId) {
        try {
            // 1.查询面试基本信息
            InterviewPO interviewPO = interviewDao.findById(interviewId);
            if (interviewPO == null) {
                return null;
            }

            // 2.转换为InterviewAggregate
            return InterviewInfraConvertor.INSTANCE.toAggregate(interviewPO);
        } catch (Exception e) {
            log.error("查询面试失败，面试ID: {}, 原因: {}", interviewId, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public InterviewAggregate findByIdOrThrow(String interviewId) {
        InterviewAggregate interview = findById(interviewId);
        if (interview == null) {
            throw InterviewException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return interview;
    }

    @Override
    public List<InterviewAggregate> findByCandidateId(String candidateId) {
        try {
            // 1.查询指定候选人的面试列表
            List<InterviewPO> interviewPOs = interviewDao.findByCandidateId(candidateId);

            // 2.转换为InterviewAggregate列表
            return InterviewInfraConvertor.INSTANCE.toAggregateList(interviewPOs);
        } catch (Exception e) {
            log.error("根据候选人ID查询面试失败，候选人ID: {}, 原因: {}", candidateId, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<InterviewAggregate> findByInterviewerName(String interviewerName) {
        try {
            // 1.查询指定面试官的面试列表
            List<InterviewPO> interviewPOs = interviewDao.findByInterviewerName(interviewerName);

            // 2.转换为InterviewAggregate列表
            return InterviewInfraConvertor.INSTANCE.toAggregateList(interviewPOs);
        } catch (Exception e) {
            log.error("根据面试官姓名查询面试失败，面试官: {}, 原因: {}", interviewerName, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<InterviewAggregate> findByTimeRange(Date startTime, Date endTime) {
        try {
            // 1.查询指定时间范围内的面试列表
            List<InterviewPO> interviewPOs = interviewDao.findByTimeRange(startTime, endTime);

            // 2.转换为InterviewAggregate列表
            return InterviewInfraConvertor.INSTANCE.toAggregateList(interviewPOs);
        } catch (Exception e) {
            log.error("根据时间范围查询面试失败，开始时间: {}, 结束时间: {}, 原因: {}", startTime, endTime, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<InterviewAggregate> findAll() {
        try {
            // 1.查询所有未被删除的面试
            List<InterviewPO> interviewPOs = interviewDao.findAllNotDeleted();

            // 2.转换为InterviewAggregate列表
            return InterviewInfraConvertor.INSTANCE.toAggregateList(interviewPOs);
        } catch (Exception e) {
            log.error("查询所有面试失败，原因: {}", e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteById(String interviewId) {
        try {
            // 逻辑删除面试
            int deletedCount = interviewDao.deleteById(interviewId);

            if (deletedCount == 0) {
                throw InterviewException.of(BizErrorCode.DATA_NOT_EXIST);
            }

            log.info("面试删除成功，面试ID: {}", interviewId);
        } catch (InterviewException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除面试失败，面试ID: {}, 原因: {}", interviewId, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }
}
