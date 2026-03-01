package com.xiaoo.kaleido.interview.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.CandidatePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 候选人数据访问接口
 * <p>
 * 负责候选人表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Mapper
public interface CandidateDao extends BaseMapper<CandidatePO> {

    /**
     * 根据ID查询候选人
     *
     * @param id 候选人ID
     * @return 候选人持久化对象
     */
    CandidatePO findById(@Param("id") String id);

    /**
     * 根据状态查询候选人列表
     *
     * @param status 候选人状态
     * @return 候选人持久化对象列表
     */
    List<CandidatePO> findByStatus(@Param("status") String status);

    /**
     * 根据技能关键词搜索候选人
     *
     * @param skillKeyword 技能关键词
     * @return 候选人持久化对象列表
     */
    List<CandidatePO> findBySkillKeyword(@Param("skillKeyword") String skillKeyword);

    /**
     * 根据姓名搜索候选人
     *
     * @param name 姓名关键词
     * @return 候选人持久化对象列表
     */
    List<CandidatePO> findByName(@Param("name") String name);

    /**
     * 查询所有未被删除的候选人
     *
     * @return 候选人持久化对象列表
     */
    List<CandidatePO> findAllNotDeleted();
}
