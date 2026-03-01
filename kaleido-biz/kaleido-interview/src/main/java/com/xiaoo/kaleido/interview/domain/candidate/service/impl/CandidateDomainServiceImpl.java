package com.xiaoo.kaleido.interview.domain.candidate.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;
import com.xiaoo.kaleido.interview.domain.candidate.service.ICandidateDomainService;
import com.xiaoo.kaleido.interview.domain.candidate.adapter.repository.ICandidateRepository;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 候选人领域服务实现类
 * <p>
 * 实现候选人领域服务的核心业务逻辑，遵循DDD原则：
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
public class CandidateDomainServiceImpl implements ICandidateDomainService {

    private final ICandidateRepository candidateRepository;

    @Override
    public CandidateAggregate createCandidate(
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText) {

        // 参数校验
        if (StrUtil.isBlank(name)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人姓名不能为空");
        }

        // 创建聚合根
        CandidateAggregate candidate = CandidateAggregate.create(
                name, skills, experienceYears, rawResumeText
        );

        log.info("创建候选人成功，候选人 ID: {}, 姓名: {}", candidate.getId(), name);
        return candidate;
    }

    @Override
    public CandidateAggregate findByIdOrThrow(String candidateId) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查询数据库
        return candidateRepository.findByIdOrThrow(candidateId);
    }

    @Override
    public CandidateAggregate updateCandidate(
            String candidateId,
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人姓名不能为空");
        }

        // 查找候选人
        CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);

        // 更新聚合根信息（聚合根本身包含最核心的业务逻辑）
        candidate.updateInfo(name, skills, experienceYears, rawResumeText);

        log.info("更新候选人成功，候选人 ID: {}, 新姓名: {}", candidateId, name);
        return candidate;
    }

    @Override
    public CandidateAggregate updateSkills(String candidateId, String skills) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查找候选人
        CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);

        // 更新技能（聚合根本身包含最核心的业务逻辑）
        candidate.updateSkills(skills);

        log.info("更新候选人技能成功，候选人 ID: {}", candidateId);
        return candidate;
    }

    @Override
    public CandidateAggregate updateRawResumeText(String candidateId, String rawResumeText) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查找候选人
        CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);

        // 更新简历文本（聚合根本身包含最核心的业务逻辑）
        candidate.updateRawResumeText(rawResumeText);

        log.info("更新候选人简历文本成功，候选人 ID: {}", candidateId);
        return candidate;
    }

    @Override
    public CandidateAggregate startInterview(String candidateId) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查找候选人
        CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);

        // 开始面试（聚合根本身包含最核心的业务逻辑）
        candidate.startInterview();

        log.info("开始面试成功，候选人 ID: {}", candidateId);
        return candidate;
    }

    @Override
    public CandidateAggregate hireCandidate(String candidateId) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 查找候选人
        CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);

        // 录用候选人（聚合根本身包含最核心的业务逻辑）
        candidate.hire();

        log.info("录用候选人成功，候选人 ID: {}", candidateId);
        return candidate;
    }

    @Override
    public List<CandidateAggregate> findByStatus(CandidateStatus status) {
        // 参数校验
        if (status == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人状态不能为空");
        }

        // 查询数据库
        return candidateRepository.findByStatus(status);
    }

    @Override
    public List<CandidateAggregate> findAll() {
        // 查询数据库
        return candidateRepository.findAll();
    }

    @Override
    public List<CandidateAggregate> findByName(String name) {
        // 参数校验
        if (StrUtil.isBlank(name)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人姓名不能为空");
        }

        // 查询数据库
        return candidateRepository.findByName(name);
    }

    @Override
    public List<CandidateAggregate> findBySkillKeyword(String skillKeyword) {
        // 参数校验
        if (StrUtil.isBlank(skillKeyword)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "技能关键词不能为空");
        }

        // 查询数据库
        return candidateRepository.findBySkillKeyword(skillKeyword);
    }

    @Override
    public void deleteCandidate(String candidateId) {
        // 参数校验
        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }

        // 删除候选人
        candidateRepository.deleteById(candidateId);

        log.info("删除候选人成功，候选人 ID: {}", candidateId);
    }
}
