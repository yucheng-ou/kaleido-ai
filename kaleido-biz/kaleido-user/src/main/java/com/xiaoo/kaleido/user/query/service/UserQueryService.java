package com.xiaoo.kaleido.user.query.service;

import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import com.xiaoo.kaleido.user.query.dto.UserDTO;
import com.xiaoo.kaleido.user.query.request.PageUserQueryRequest;
import com.xiaoo.kaleido.user.query.request.UserQueryRequest;

import java.util.List;

/**
 * 用户查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserQueryService {

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户DTO
     */
    UserDTO findById(String userId);

    /**
     * 根据手机号查询用户
     *
     * @param telephone 手机号
     * @return 用户DTO
     */
    UserDTO findByTelephone(String telephone);

    /**
     * 根据邀请码查询用户
     *
     * @param inviteCode 邀请码
     * @return 用户DTO
     */
    UserDTO findByInviteCode(String inviteCode);

    /**
     * 根据昵称模糊查询用户
     *
     * @param nickName 昵称（模糊匹配）
     * @return 用户DTO列表
     */
    List<UserDTO> findByNickNameLike(String nickName);

    /**
     * 根据状态查询用户
     *
     * @param status 用户状态
     * @return 用户DTO列表
     */
    List<UserDTO> findByStatus(UserStatus status);

    /**
     * 查询所有用户（分页）
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 用户DTO列表
     */
    List<UserDTO> findAll(int pageNum, int pageSize);

    /**
     * 根据邀请人ID查询用户
     *
     * @param inviterId 邀请人ID
     * @return 用户DTO列表
     */
    List<UserDTO> findByInviterId(String inviterId);

    /**
     * 统计用户数量
     *
     * @return 用户总数
     */
    long count();

    /**
     * 统计指定状态的用户数量
     *
     * @param status 用户状态
     * @return 用户数量
     */
    long countByStatus(UserStatus status);

    /**
     * 检查手机号是否存在
     *
     * @param telephone 手机号
     * @return 是否存在
     */
    boolean existsByTelephone(String telephone);

    /**
     * 检查邀请码是否存在
     *
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    boolean existsByInviteCode(String inviteCode);

    /**
     * 检查昵称是否存在
     *
     * @param nickName 昵称
     * @return 是否存在
     */
    boolean existsByNickName(String nickName);

    /**
     * 根据ID查询用户（别名方法，用于门面层）
     *
     * @param userId 用户ID
     * @return 用户DTO
     */
    default UserDTO getUserById(String userId) {
        return findById(userId);
    }

    /**
     * 查询用户列表（根据查询条件）
     *
     * @param queryRequest 查询条件
     * @return 用户DTO列表
     */
    List<UserDTO> queryUsers(UserQueryRequest queryRequest);

    /**
     * 分页查询用户列表
     *
     * @param queryRequest 分页查询条件
     * @return 分页结果
     */
    PageResp<UserDTO> pageQueryUsers(PageUserQueryRequest queryRequest);
}
