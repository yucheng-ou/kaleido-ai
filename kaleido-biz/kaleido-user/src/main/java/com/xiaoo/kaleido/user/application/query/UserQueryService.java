package com.xiaoo.kaleido.user.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;

/**
 * 用户查询服务接口
 * <p>
 * 用户应用层查询服务，负责用户相关的读操作，包括用户信息查询、分页查询等
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserQueryService {

    /**
     * 根据用户ID查询用户信息
     * <p>
     * 根据用户ID查询用户详细信息，如果用户不存在则返回null
     *
     * @param userId 用户ID，不能为空
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findById(String userId);

    /**
     * 根据手机号查询用户
     * <p>
     * 根据手机号查询用户详细信息，如果用户不存在则返回null
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findByTelephone(String telephone);

    /**
     * 根据邀请码查询用户
     * <p>
     * 根据邀请码查询用户详细信息，如果用户不存在则返回null
     *
     * @param inviteCode 用户邀请码，不能为空
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findByInviteCode(String inviteCode);

    /**
     * 分页查询用户
     * <p>
     * 根据查询条件分页查询用户列表，支持多种查询条件组合
     *
     * @param req 查询条件，包含分页参数和过滤条件
     * @return 分页结果，包含用户列表和分页信息
     */
    PageInfo<UserInfoResponse> pageQuery(UserPageQueryReq req);

}
