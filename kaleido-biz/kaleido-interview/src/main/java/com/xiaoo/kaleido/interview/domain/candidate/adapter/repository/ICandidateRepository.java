package com.xiaoo.kaleido.interview.domain.candidate.adapter.repository;

import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;

import java.util.List;

/**
 * 候选人仓储接口
 * <p>
 * 定义候选人聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface ICandidateRepository {

    /**
     * 保存候选人聚合根
     * <p>
     * 保存候选人聚合根到数据库，如果是新候选人则插入，如果是已存在候选人则更新
     *
     * @param candidateAggregate 候选人聚合根，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当保存失败时抛出
     */
    void save(CandidateAggregate candidateAggregate);

    /**
     * 更新候选人聚合根
     * <p>
     * 更新候选人聚合根信息到数据库
     *
     * @param candidateAggregate 候选人聚合根，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当更新失败或候选人不存在时抛出
     */
    void update(CandidateAggregate candidateAggregate);

    /**
     * 根据ID查找候选人聚合根
     * <p>
     * 根据候选人ID查询候选人聚合根
     *
     * @param candidateId 候选人ID，不能为空
     * @return 候选人聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    CandidateAggregate findById(String candidateId);

    /**
     * 根据ID查找候选人聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保候选人存在的场景，如果候选人不存在则抛出异常
     *
     * @param candidateId 候选人ID，不能为空
     * @return 候选人聚合根
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当候选人不存在时抛出
     */
    CandidateAggregate findByIdOrThrow(String candidateId);

    /**
     * 根据状态查找候选人列表
     * <p>
     * 查询指定状态的候选人列表
     *
     * @param status 候选人状态，不能为空
     * @return 候选人聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<CandidateAggregate> findByStatus(CandidateStatus status);

    /**
     * 查询所有候选人
     * <p>
     * 查询所有未被删除的候选人列表
     *
     * @return 候选人聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<CandidateAggregate> findAll();

    /**
     * 根据技能关键词搜索候选人
     * <p>
     * 根据技能关键词模糊匹配查询候选人列表
     *
     * @param skillKeyword 技能关键词，不能为空
     * @return 候选人聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<CandidateAggregate> findBySkillKeyword(String skillKeyword);

    /**
     * 删除候选人
     * <p>
     * 逻辑删除候选人（设置deleted标志）
     *
     * @param candidateId 候选人ID，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当删除失败或候选人不存在时抛出
     */
    void deleteById(String candidateId);
}
