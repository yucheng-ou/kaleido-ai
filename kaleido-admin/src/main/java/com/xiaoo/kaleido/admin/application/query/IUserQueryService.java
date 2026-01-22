package com.xiaoo.kaleido.admin.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;

/**
 * 用户查询服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IUserQueryService {
    
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoResponse findById(String userId);
    
    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 手机号
     * @return 用户信息
     */
    UserInfoResponse findByTelephone(String telephone);

    
    /**
     * 分页查询用户列表
     *
     * @param req 分页查询请求
     * @return 分页结果
     */
    PageInfo<UserInfoResponse> pageQuery(UserPageQueryReq req);

}
