package com.xiaoo.kaleido.interview.domain.interview.adapter.repository;

import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;

import java.util.Date;
import java.util.List;

/**
 * 面试仓储接口
 * <p>
 * 定义面试聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface IInterviewRepository {

    /**
     * 保存面试聚合根
     * <p>
     * 保存面试聚合根到数据库，如果是新面试则插入，如果是已存在面试则更新
     *
     * @param interviewAggregate 面试聚合根，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当保存失败时抛出
     */
    void save(InterviewAggregate interviewAggregate);

    /**
     * 更新面试聚合根
     * <p>
     * 更新面试聚合根信息到数据库
     *
     * @param interviewAggregate 面试聚合根，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当更新失败或面试不存在时抛出
     */
    void update(InterviewAggregate interviewAggregate);

    /**
     * 根据ID查找面试聚合根
     * <p>
     * 根据面试ID查询面试聚合根
     *
     * @param interviewId 面试ID，不能为空
     * @return 面试聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    InterviewAggregate findById(String interviewId);

    /**
     * 根据ID查找面试聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保面试存在的场景，如果面试不存在则抛出异常
     *
     * @param interviewId 面试ID，不能为空
     * @return 面试聚合根
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当面试不存在时抛出
     */
    InterviewAggregate findByIdOrThrow(String interviewId);

    /**
     * 根据候选人ID查找面试列表
     * <p>
     * 查询指定候选人的所有面试记录
     *
     * @param candidateId 候选人ID，不能为空
     * @return 面试聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<InterviewAggregate> findByCandidateId(String candidateId);

    /**
     * 根据面试官姓名查找面试列表
     * <p>
     * 查询指定面试官的所有面试记录
     *
     * @param interviewerName 面试官姓名，不能为空
     * @return 面试聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<InterviewAggregate> findByInterviewerName(String interviewerName);

    /**
     * 根据时间范围查找面试列表
     * <p>
     * 查询指定时间范围内的面试记录
     *
     * @param startTime 开始时间，不能为空
     * @param endTime   结束时间，不能为空
     * @return 面试聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<InterviewAggregate> findByTimeRange(Date startTime, Date endTime);

    /**
     * 查询所有面试
     * <p>
     * 查询所有未被删除的面试列表
     *
     * @return 面试聚合根列表
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当查询失败时抛出
     */
    List<InterviewAggregate> findAll();

    /**
     * 删除面试
     * <p>
     * 逻辑删除面试（设置deleted标志）
     *
     * @param interviewId 面试ID，不能为空
     * @throws com.xiaoo.kaleido.interview.types.exception.InterviewException 当删除失败或面试不存在时抛出
     */
    void deleteById(String interviewId);
}
