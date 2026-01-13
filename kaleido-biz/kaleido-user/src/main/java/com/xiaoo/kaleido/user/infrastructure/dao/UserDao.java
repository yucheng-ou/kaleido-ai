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
     * 根据ID查找用户
     * <p>
     * 根据用户ID查询用户详细信息，只返回未删除的用户
     *
     * @param id 用户ID，不能为空
     * @return 用户持久化对象，如果用户不存在则返回null
     * @throws IllegalArgumentException 当用户ID为空时抛出
     */
    UserPO findById(@Param("id") String id);

    /**
     * 根据手机号查找用户
     * <p>
     * 根据手机号查询用户详细信息，只返回未删除的用户
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 用户持久化对象，如果用户不存在则返回null
     * @throws IllegalArgumentException 当手机号格式不正确时抛出
     */
    UserPO findByTelephone(@Param("telephone") String telephone);

    /**
     * 根据邀请码查找用户
     * <p>
     * 根据邀请码查询用户详细信息，只返回未删除的用户
     *
     * @param inviteCode 邀请码，不能为空
     * @return 用户持久化对象，如果用户不存在则返回null
     * @throws IllegalArgumentException 当邀请码为空时抛出
     */
    UserPO findByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查用户ID是否存在
     * <p>
     * 检查指定用户ID的用户是否存在且未删除
     *
     * @param id 用户ID，不能为空
     * @return 是否存在，true表示存在且未删除，false表示不存在或已删除
     * @throws IllegalArgumentException 当用户ID为空时抛出
     */
    boolean existsById(@Param("id") String id);

    /**
     * 检查手机号是否存在
     * <p>
     * 检查指定手机号的用户是否存在且未删除
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 是否存在，true表示存在且未删除，false表示不存在或已删除
     * @throws IllegalArgumentException 当手机号格式不正确时抛出
     */
    boolean existsByTelephone(@Param("telephone") String telephone);

    /**
     * 检查邀请码是否存在
     * <p>
     * 检查指定邀请码的用户是否存在且未删除
     *
     * @param inviteCode 邀请码，不能为空
     * @return 是否存在，true表示存在且未删除，false表示不存在或已删除
     * @throws IllegalArgumentException 当邀请码为空时抛出
     */
    boolean existsByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 检查昵称是否存在
     * <p>
     * 检查指定昵称的用户是否存在且未删除
     *
     * @param nickName 昵称，不能为空
     * @return 是否存在，true表示存在且未删除，false表示不存在或已删除
     * @throws IllegalArgumentException 当昵称为空时抛出
     */
    boolean existsByNickName(@Param("nickName") String nickName);

    /**
     * 根据条件分页查询用户
     * <p>
     * 根据查询条件分页查询用户列表，支持多种查询条件组合
     *
     * @param req 查询条件，包含分页参数和过滤条件
     * @return 用户列表，如果无结果则返回空列表
     * @throws IllegalArgumentException 当查询条件无效时抛出
     */
    List<UserPO> selectByCondition(@Param("req") UserPageQueryReq req);

    /**
     * 分页查询邀请码（用于布隆过滤器初始化）
     * <p>
     * 分批查询数据库中的邀请码，用于服务启动时加载到布隆过滤器
     *
     * @param offset 偏移量
     * @param limit 每页大小
     * @return 邀请码列表，如果无结果则返回空列表
     */
    List<String> selectInviteCodesWithLimit(@Param("offset") int offset, @Param("limit") int limit);
}
