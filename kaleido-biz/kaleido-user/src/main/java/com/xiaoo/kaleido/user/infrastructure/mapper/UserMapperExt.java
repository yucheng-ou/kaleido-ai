package com.xiaoo.kaleido.user.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper扩展接口（查询侧专用）
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserMapperExt extends BaseMapper<UserPO> {

    /**
     * 根据昵称模糊查询用户
     *
     * @param nickName 昵称（模糊匹配）
     * @return 用户持久化对象列表
     */
    @Select("SELECT * FROM user WHERE nick_name LIKE CONCAT('%', #{nickName}, '%') AND deleted = 0")
    List<UserPO> findByNickNameLike(@Param("nickName") String nickName);

    /**
     * 根据状态查询用户
     *
     * @param status 状态码
     * @return 用户持久化对象列表
     */
    @Select("SELECT * FROM user WHERE status = #{status} AND deleted = 0")
    List<UserPO> findByStatus(@Param("status") Integer status);

    /**
     * 根据邀请人ID查询用户
     *
     * @param inviterId 邀请人ID
     * @return 用户持久化对象列表
     */
    @Select("SELECT * FROM user WHERE inviter_id = #{inviterId} AND deleted = 0")
    List<UserPO> findByInviterId(@Param("inviterId") String inviterId);

    /**
     * 查询所有用户（分页）
     *
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户持久化对象列表
     */
    @Select("SELECT * FROM user WHERE deleted = 0 ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<UserPO> findAllWithPage(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计用户总数
     *
     * @return 用户总数
     */
    @Select("SELECT COUNT(*) FROM user WHERE deleted = 0")
    long countAll();

    /**
     * 统计指定状态的用户数量
     *
     * @param status 状态码
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE status = #{status} AND deleted = 0")
    long countByStatus(@Param("status") Integer status);
}
