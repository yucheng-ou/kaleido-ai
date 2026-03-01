package com.xiaoo.kaleido.interview.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.InterviewPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

/**
 * 面试数据访问接口
 * <p>
 * 负责面试表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Mapper
public interface InterviewDao extends BaseMapper<InterviewPO> {

    /**
     * 根据ID查询面试
     *
     * @param id 面试ID
     * @return 面试持久化对象
     */
    InterviewPO findById(@Param("id") String id);

    /**
     * 根据候选人ID查询面试列表
     *
     * @param candidateId 候选人ID
     * @return 面试持久化对象列表
     */
    List<InterviewPO> findByCandidateId(@Param("candidateId") String candidateId);

    /**
     * 根据面试官姓名查询面试列表
     *
     * @param interviewerName 面试官姓名
     * @return 面试持久化对象列表
     */
    List<InterviewPO> findByInterviewerName(@Param("interviewerName") String interviewerName);

    /**
     * 根据时间范围查询面试列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 面试持久化对象列表
     */
    List<InterviewPO> findByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询所有未被删除的面试
     *
     * @return 面试持久化对象列表
     */
    List<InterviewPO> findAllNotDeleted();
}
