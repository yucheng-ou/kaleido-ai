package com.xiaoo.kaleido.interview.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.interview.domain.candidate.adapter.repository.ICandidateRepository;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;
import com.xiaoo.kaleido.interview.infrastructure.adapter.repository.convertor.CandidateInfraConvertor;
import com.xiaoo.kaleido.interview.infrastructure.dao.CandidateDao;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.CandidatePO;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 候选人仓储实现（基础设施层）
 * <p>
 * 候选人仓储接口的具体实现，负责候选人聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements ICandidateRepository {

    private final CandidateDao candidateDao;

    @Override
    public void save(CandidateAggregate candidateAggregate) {
        // 1.转换CandidateAggregate为CandidatePO
        CandidatePO candidatePO = CandidateInfraConvertor.INSTANCE.toPO(candidateAggregate);

        // 2.保存候选人基本信息
        candidateDao.insert(candidatePO);

        log.info("候选人保存成功，候选人ID: {}, 候选人姓名: {}",
                candidateAggregate.getId(), candidateAggregate.getName());
    }

    @Override
    public void update(CandidateAggregate candidateAggregate) {
        // 1.转换CandidateAggregate为CandidatePO
        CandidatePO candidatePO = CandidateInfraConvertor.INSTANCE.toPO(candidateAggregate);

        // 2.更新候选人基本信息
        candidateDao.updateById(candidatePO);

        log.info("候选人更新成功，候选人ID: {}, 候选人姓名: {}",
                candidateAggregate.getId(), candidateAggregate.getName());
    }

    @Override
    public CandidateAggregate findById(String candidateId) {
        try {
            // 1.查询候选人基本信息
            CandidatePO candidatePO = candidateDao.findById(candidateId);
            if (candidatePO == null) {
                return null;
            }

            // 2.转换为CandidateAggregate
            return CandidateInfraConvertor.INSTANCE.toAggregate(candidatePO);
        } catch (Exception e) {
            log.error("查询候选人失败，候选人ID: {}, 原因: {}", candidateId, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public CandidateAggregate findByIdOrThrow(String candidateId) {
        CandidateAggregate candidate = findById(candidateId);
        if (candidate == null) {
            throw InterviewException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return candidate;
    }

    @Override
    public List<CandidateAggregate> findByStatus(CandidateStatus status) {
        try {
            // 1.查询指定状态的候选人列表
            List<CandidatePO> candidatePOs = candidateDao.findByStatus(status.getCode());

            // 2.转换为CandidateAggregate列表
            return CandidateInfraConvertor.INSTANCE.toAggregateList(candidatePOs);
        } catch (Exception e) {
            log.error("根据状态查询候选人失败，状态: {}, 原因: {}", status.getCode(), e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<CandidateAggregate> findAll() {
        try {
            // 1.查询所有未被删除的候选人
            List<CandidatePO> candidatePOs = candidateDao.findAllNotDeleted();

            // 2.转换为CandidateAggregate列表
            return CandidateInfraConvertor.INSTANCE.toAggregateList(candidatePOs);
        } catch (Exception e) {
            log.error("查询所有候选人失败，原因: {}", e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<CandidateAggregate> findByName(String name) {
        try {
            // 1.根据姓名查询PO
            List<CandidatePO> candidatePOs = candidateDao.findByName(name);

            // 2.转换为聚合根
            return CandidateInfraConvertor.INSTANCE.toAggregateList(candidatePOs);
        } catch (Exception e) {
            log.error("根据姓名搜索候选人失败，姓名: {}, 原因: {}", name, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<CandidateAggregate> findBySkillKeyword(String skillKeyword) {
        try {
            // 1.根据技能关键词搜索候选人
            List<CandidatePO> candidatePOs = candidateDao.findBySkillKeyword(skillKeyword);

            // 2.转换为CandidateAggregate列表
            return CandidateInfraConvertor.INSTANCE.toAggregateList(candidatePOs);
        } catch (Exception e) {
            log.error("根据技能关键词搜索候选人失败，关键词: {}, 原因: {}", skillKeyword, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteById(String candidateId) {
        try {
            // 逻辑删除候选人
            int deletedCount = candidateDao.deleteById(candidateId);

            if (deletedCount == 0) {
                throw InterviewException.of(BizErrorCode.DATA_NOT_EXIST);
            }

            log.info("候选人删除成功，候选人ID: {}", candidateId);
        } catch (InterviewException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除候选人失败，候选人ID: {}, 原因: {}", candidateId, e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.DATABASE_ERROR);
        }
    }
}
