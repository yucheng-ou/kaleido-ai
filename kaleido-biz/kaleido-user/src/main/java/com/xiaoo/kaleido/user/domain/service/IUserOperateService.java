package com.xiaoo.kaleido.user.domain.service;

import com.xiaoo.kaleido.api.user.request.PageUserQueryRequest;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.api.user.response.UserInfoVO;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.user.domain.model.entity.User;

import java.util.List;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description 用户操作领域 负责用户注册 信息修改 查询 状态变更等工作
 */
public interface IUserOperateService {


    /**
     * 注册用户
     *
     * @param telephone  用户手机号
     * @param inviteCode 邀请码
     * @return 用户信息
     */
    User register(String telephone, String inviteCode);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户操作结果
     */
    User getById(Long userId);

    /**
     * 更新用户基本信息
     * 包括昵称、头像和手机号的更新
     *
     * @param request 更新用户信息请求参数
     * @return 更新后的用户实体对象
     */
    User updateUserInfo(UpdateUserInfoRequest request);

    /**
     * 查询用户列表（不分页）
     * 根据查询条件返回匹配的用户列表，包含邀请人昵称等扩展信息
     *
     * @param request 用户查询请求参数
     * @return 用户信息VO列表
     */
    List<UserInfoVO> query(UserQueryRequest request);

    /**
     * 分页查询用户列表
     * 根据查询条件和分页参数返回分页结果，包含邀请人昵称等扩展信息
     *
     * @param request 用户查询请求参数
     * @return 用户信息VO分页结果
     */
    PageResp<UserInfoVO> pageQuery(PageUserQueryRequest request);
}
