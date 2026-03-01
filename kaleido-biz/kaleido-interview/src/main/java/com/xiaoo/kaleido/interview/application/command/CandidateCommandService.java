package com.xiaoo.kaleido.interview.application.command;

import com.xiaoo.kaleido.api.interview.command.CreateCandidateCommand;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.service.ICandidateDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 候选人命令服务
 * <p>
 * 负责编排候选人相关的命令操作，包括创建、更新、状态变更等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateCommandService {

    private final ICandidateDomainService candidateDomainService;

    /**
     * 创建候选人
     *
     * @param command 创建候选人命令
     * @return 候选人ID
     */
    public String createCandidate(CreateCandidateCommand command) {
        log.info("创建候选人，姓名: {}", command.getName());

        CandidateAggregate candidate = candidateDomainService.createCandidate(
                command.getName(),
                command.getSkills(),
                command.getExperienceYears(),
                command.getRawResumeText()
        );

        log.info("候选人创建成功，ID: {}", candidate.getId());
        return String.valueOf(candidate.getId());
    }

    /**
     * 开始面试
     *
     * @param candidateId 候选人ID
     * @return 更新后的候选人ID
     */
    public String startInterview(String candidateId) {
        log.info("开始面试，候选人ID: {}", candidateId);

        CandidateAggregate candidate = candidateDomainService.startInterview(candidateId);

        log.info("面试状态更新成功，候选人: {}", candidate.getName());
        return String.valueOf(candidate.getId());
    }

    /**
     * 录用候选人
     *
     * @param candidateId 候选人ID
     * @return 更新后的候选人ID
     */
    public String hireCandidate(String candidateId) {
        log.info("录用候选人，候选人ID: {}", candidateId);

        CandidateAggregate candidate = candidateDomainService.hireCandidate(candidateId);

        log.info("候选人录用成功，候选人: {}", candidate.getName());
        return String.valueOf(candidate.getId());
    }

    /**
     * 更新候选人技能
     *
     * @param candidateId 候选人ID
     * @param skills      技能列表
     * @return 更新后的候选人ID
     */
    public String updateSkills(String candidateId, String skills) {
        log.info("更新候选人技能，候选人ID: {}, 技能: {}", candidateId, skills);

        CandidateAggregate candidate = candidateDomainService.updateSkills(candidateId, skills);

        log.info("候选人技能更新成功");
        return String.valueOf(candidate.getId());
    }

    /**
     * 删除候选人
     *
     * @param candidateId 候选人ID
     */
    public void deleteCandidate(String candidateId) {
        log.info("删除候选人，候选人ID: {}", candidateId);

        candidateDomainService.deleteCandidate(candidateId);

        log.info("候选人删除成功");
    }
}
