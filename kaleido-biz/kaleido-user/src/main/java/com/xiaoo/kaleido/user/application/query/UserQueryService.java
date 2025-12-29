package com.xiaoo.kaleido.user.application.query;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.response.PageResp;

/**
 * 用户查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserQueryService {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户DTO
     */
    UserInfoResponse findById(String userId);

    /**
     * 根据手机号查询用户
     *
     * @param telephone 手机号
     * @return 用户DTO
     */
    UserInfoResponse findByTelephone(String telephone);

    /**
     * 根据邀请码查询用户
     *
     * @param inviteCode 用户邀请码
     * @return 用户DTO
     */
    UserInfoResponse findByInviteCode(String inviteCode);

    /**
     * 分页查询用户
     *
     * @param req 查询条件
     * @return 分页结果
     */
    PageResp<UserInfoResponse> pageQuery(UserPageQueryReq req);

}
