package com.xiaoo.kaleido.admin.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;

import java.util.List;

/**
 * 管理员查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IAdminQueryService {
    
    /**
     * 根据ID查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    AdminInfoResponse findById(String adminId);
    
    /**
     * 根据手机号查询管理员信息
     *
     * @param mobile 手机号
     * @return 管理员信息
     */
    AdminInfoResponse findByMobile(String mobile);

    
    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询请求
     * @return 分页结果
     */
    PageInfo<AdminInfoResponse> pageQuery(AdminPageQueryReq pageQueryReq);

    /**
     * 获取管理员的所有权限（通过角色）
     *
     * @param adminId 管理员ID
     * @return 权限ID列表
     */
    List<String> getPermissionsByAdminId(String adminId);

}
