package com.xiaoo.kaleido.user.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;

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
     * @param userId 用户ID，不能为空
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findById(String userId);

    /**
     * 根据手机号查询用户
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findByTelephone(String telephone);

    /**
     * 根据邀请码查询用户
     *
     * @param inviteCode 用户邀请码，不能为空
     * @return 用户信息响应，如果用户不存在则返回null
     */
    UserInfoResponse findByInviteCode(String inviteCode);

    /**
     * 分页查询用户
     *
     * @param req 查询条件，包含分页参数和过滤条件
     * @return 分页结果，包含用户列表和分页信息
     */
    PageInfo<UserInfoResponse> pageQuery(UserPageQueryReq req);

}
