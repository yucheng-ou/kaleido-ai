package com.xiaoo.kaleido.interview.domain.candidate.service;

import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;

import java.util.List;

/**
 * 候选人领域服务接口
 * <p>
 * 定义候选人领域服务的核心业务逻辑，处理候选人的创建、更新、状态变更等操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface ICandidateDomainService {

    /**
     * 创建候选人
     *
     * @param name             候选人姓名，不能为空
     * @param skills           技能列表，可为空
     * @param experienceYears  工作年限，可为空
     * @param rawResumeText    原始简历文本，可为空
     * @return 候选人聚合根
     */
    CandidateAggregate createCandidate(
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText);

    /**
     * 根据ID查找候选人
     *
     * @param candidateId 候选人ID，不能为空
     * @return 候选人聚合根
     */
    CandidateAggregate findByIdOrThrow(String candidateId);

    /**
     * 更新候选人信息
     *
     * @param candidateId      候选人ID，不能为空
     * @param name             新候选人姓名，不能为空
     * @param skills           新技能列表，可为空
     * @param experienceYears  新工作年限，可为空
     * @param rawResumeText    新原始简历文本，可为空
     * @return 更新后的候选人聚合根
     */
    CandidateAggregate updateCandidate(
            String candidateId,
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText);

    /**
     * 更新候选人技能
     *
     * @param candidateId 候选人ID，不能为空
     * @param skills      新技能列表，可为空
     * @return 更新后的候选人聚合根
     */
    CandidateAggregate updateSkills(String candidateId, String skills);

    /**
     * 更新候选人简历文本
     *
     * @param candidateId    候选人ID，不能为空
     * @param rawResumeText  新原始简历文本，可为空
     * @return 更新后的候选人聚合根
     */
    CandidateAggregate updateRawResumeText(String candidateId, String rawResumeText);

    /**
     * 开始面试
     * <p>
     * 将候选人状态从 NEW 变更为 INTERVIEWING
     *
     * @param candidateId 候选人ID，不能为空
     * @return 更新后的候选人聚合根
     */
    CandidateAggregate startInterview(String candidateId);

    /**
     * 录用候选人
     * <p>
     * 将候选人状态从 INTERVIEWING 变更为 HIRED
     *
     * @param candidateId 候选人ID，不能为空
     * @return 更新后的候选人聚合根
     */
    CandidateAggregate hireCandidate(String candidateId);

    /**
     * 根据状态查找候选人列表
     *
     * @param status 候选人状态，不能为空
     * @return 候选人聚合根列表
     */
    List<CandidateAggregate> findByStatus(CandidateStatus status);

    /**
     * 查询所有候选人
     *
     * @return 候选人聚合根列表
     */
    List<CandidateAggregate> findAll();

    /**
     * 根据技能关键词搜索候选人
     *
     * @param skillKeyword 技能关键词，不能为空
     * @return 候选人聚合根列表
     */
    List<CandidateAggregate> findBySkillKeyword(String skillKeyword);

    /**
     * 删除候选人
     *
     * @param candidateId 候选人ID，不能为空
     */
    void deleteCandidate(String candidateId);
}
