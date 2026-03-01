package com.xiaoo.kaleido.interview.application.query;

import com.xiaoo.kaleido.api.interview.response.CandidateInfoResponse;
import com.xiaoo.kaleido.interview.application.convertor.CandidateConvertor;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;
import com.xiaoo.kaleido.interview.domain.candidate.service.ICandidateDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 候选人查询服务
 * <p>
 * 负责候选人相关的查询操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateQueryService {

    private final ICandidateDomainService candidateDomainService;

    /**
     * 根据ID查询候选人
     *
     * @param candidateId 候选人ID
     * @return 候选人信息
     */
    public CandidateInfoResponse findById(String candidateId) {
        log.info("查询候选人，ID: {}", candidateId);
        CandidateAggregate candidate = candidateDomainService.findByIdOrThrow(candidateId);
        return CandidateConvertor.toResponse(candidate);
    }

    /**
     * 查询所有候选人
     *
     * @return 候选人列表
     */
    public List<CandidateInfoResponse> findAll() {
        log.info("查询所有候选人");
        List<CandidateAggregate> candidates = candidateDomainService.findAll();
        return candidates.stream()
                .map(CandidateConvertor::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 根据状态查询候选人
     *
     * @param status 候选人状态
     * @return 候选人列表
     */
    public List<CandidateInfoResponse> findByStatus(String status) {
        log.info("查询候选人，状态: {}", status);
        CandidateStatus candidateStatus = CandidateStatus.valueOf(status);
        List<CandidateAggregate> candidates = candidateDomainService.findByStatus(candidateStatus);
        return candidates.stream()
                .map(CandidateConvertor::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 根据技能关键词搜索候选人
     *
     * @param skillKeyword 技能关键词
     * @return 候选人列表
     */
    public List<CandidateInfoResponse> findBySkillKeyword(String skillKeyword) {
        log.info("搜索候选人，技能关键词: {}", skillKeyword);
        List<CandidateAggregate> candidates = candidateDomainService.findBySkillKeyword(skillKeyword);
        return candidates.stream()
                .map(CandidateConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
