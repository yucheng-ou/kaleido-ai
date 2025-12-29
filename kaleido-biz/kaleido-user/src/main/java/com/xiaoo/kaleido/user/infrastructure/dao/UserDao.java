package com.xiaoo.kaleido.user.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserDao extends BaseMapper<UserPO> {

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID（业务主键）
     * @return 用户持久化对象
     */
    UserPO findByUserId(@Param("userId") String userId);

    /**
     * 根据手机号查找用户
     *
     * @param telephone 手机号
     * @return 用户持久化对象
     */
    UserPO findByTelephone(@Param("telephone") String telephone);

    /**
     * 根据邀请码查找用户
     *
     * @param inviteCode 邀请码
     * @return 用户持久化对象
     */
    UserPO findByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查用户ID是否存在
     *
     * @param userId 用户ID（业务主键）
     * @return 是否存在
     */
    boolean existsByUserId(@Param("userId") String userId);

    /**
     * 检查手机号是否存在
     *
     * @param telephone 手机号
     * @return 是否存在
     */
    boolean existsByTelephone(@Param("telephone") String telephone);

    /**
     * 检查邀请码是否存在
     *
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    boolean existsByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查昵称是否存在
     *
     * @param nickName 昵称
     * @return 是否存在
     */
    boolean existsByNickName(@Param("nickName") String nickName);

    /**
     * 根据条件分页查询用户
     *
     * @param req 查询条件
     * @return 用户列表
     */
    List<UserPO> selectByCondition(@Param("req") UserPageQueryReq req);
}
