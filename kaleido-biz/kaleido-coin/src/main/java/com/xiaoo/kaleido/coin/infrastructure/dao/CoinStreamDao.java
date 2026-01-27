package com.xiaoo.kaleido.coin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinStreamPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 金币流水数据访问接口
 * <p>
 * 负责金币流水表的CRUD操作
 * 继承MyBatis Plus的BaseMapper，提供基础CRUD功能
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface CoinStreamDao extends BaseMapper<CoinStreamPO> {

    /**
     * 根据用户ID查询流水列表

     * 用于查询指定用户的所有流水记录
     *
     * @param userId 用户ID，不能为空
     * @return 流水记录列表，按创建时间倒序排列
     */
    List<CoinStreamPO> findByUserId(@Param("userId") String userId);


    /**
     * 根据业务类型和业务ID查询流水

     * 用于幂等性检查，防止重复处理同一业务
     *
     * @param bizType 业务类型，不能为空
     * @param bizId   业务ID，不能为空
     * @return 流水记录，如果不存在则返回null
     */
    CoinStreamPO findByBizTypeAndBizId(@Param("bizType") String bizType, @Param("bizId") String bizId);

    /**
     * 检查业务是否已处理

     * 用于幂等性检查，检查指定业务是否已处理过
     *
     * @param bizType 业务类型，不能为空
     * @param bizId   业务ID，不能为空
     * @return true-已处理过，false-未处理过
     */
    boolean existsByBizTypeAndBizId(@Param("bizType") String bizType, @Param("bizId") String bizId);

    /**
     * 根据用户ID删除流水记录

     * 用于删除指定用户的所有流水记录
     *
     * @param userId 用户ID，不能为空
     * @return 删除的行数
     */
    int deleteByUserId(@Param("userId") String userId);

}
