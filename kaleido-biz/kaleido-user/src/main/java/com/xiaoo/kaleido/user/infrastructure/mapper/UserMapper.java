package com.xiaoo.kaleido.user.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID（业务主键）
     * @return 用户持久化对象
     */
    @Select("SELECT * FROM user WHERE user_id = #{userId} AND deleted = 0")
    UserPO findByUserId(@Param("userId") String userId);

    /**
     * 根据手机号查找用户
     *
     * @param telephone 手机号
     * @return 用户持久化对象
     */
    @Select("SELECT * FROM user WHERE telephone = #{telephone} AND deleted = 0")
    UserPO findByTelephone(@Param("telephone") String telephone);

    /**
     * 根据邀请码查找用户
     *
     * @param inviteCode 邀请码
     * @return 用户持久化对象
     */
    @Select("SELECT * FROM user WHERE invite_code = #{inviteCode} AND deleted = 0")
    UserPO findByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查用户ID是否存在
     *
     * @param userId 用户ID（业务主键）
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE user_id = #{userId} AND deleted = 0")
    boolean existsByUserId(@Param("userId") String userId);

    /**
     * 检查手机号是否存在
     *
     * @param telephone 手机号
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE telephone = #{telephone} AND deleted = 0")
    boolean existsByTelephone(@Param("telephone") String telephone);

    /**
     * 检查邀请码是否存在
     *
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE invite_code = #{inviteCode} AND deleted = 0")
    boolean existsByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查昵称是否存在
     *
     * @param nickName 昵称
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE nick_name = #{nickName} AND deleted = 0")
    boolean existsByNickName(@Param("nickName") String nickName);
}
