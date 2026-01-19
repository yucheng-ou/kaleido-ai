package com.xiaoo.kaleido.coin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 金币账户数据访问接口
 * <p>
 * 负责金币账户表的CRUD操作
 * 继承MyBatis Plus的BaseMapper，提供基础CRUD功能
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface CoinAccountDao extends BaseMapper<CoinAccountPO> {

    /**
     * 根据用户ID查询账户
     * <p>
     * 用于按用户ID查找账户信息
     *
     * @param userId 用户ID，不能为空
     * @return 金币账户持久化对象，如果不存在则返回null
     */
    CoinAccountPO findByUserId(@Param("userId") String userId);

    /**
     * 检查用户账户是否存在
     * <p>
     * 用于检查指定用户ID的账户是否存在
     *
     * @param userId 用户ID，不能为空
     * @return true-存在，false-不存在
     */
    boolean existsByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID删除账户（逻辑删除）
     * <p>
     * 用于删除指定用户的账户
     *
     * @param userId 用户ID，不能为空
     * @return 删除的行数
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 根据账户ID查询账户
     * <p>
     * 用于按账户ID查找账户信息
     *
     * @param accountId 账户ID，不能为空
     * @return 金币账户持久化对象，如果不存在则返回null
     */
    CoinAccountPO findById(@Param("accountId") String accountId);
}
