package com.xiaoo.kaleido.notice.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticePO;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Mapper
public interface NoticeDao extends BaseMapper<NoticePO> {

    /**
     * 根据目标地址查找通知列表
     *
     * @param targetAddress 目标地址
     * @return 通知列表
     */
    List<NoticePO> findByTargetAddress(@Param("targetAddress") String targetAddress);

    /**
     * 根据状态查找通知列表
     *
     * @param status 通知状态
     * @return 通知列表
     */
    List<NoticePO> findByStatus(@Param("status") NoticeStatusEnum status);

    /**
     * 根据业务类型查找通知列表
     *
     * @param businessType 业务类型
     * @return 通知列表
     */
    List<NoticePO> findByBusinessType(@Param("businessType") String businessType);

    /**
     * 根据条件分页查询通知
     *
     * @param req 查询条件
     * @return 通知列表
     */
    List<NoticePO> selectByCondition(@Param("req") NoticePageQueryReq req);

    /**
     * 查找需要重试的通知列表
     *
     * @param limit 查询限制条数
     * @return 需要重试的通知列表
     */
    List<NoticePO> findRetryNotices(@Param("limit") Integer limit);
}
