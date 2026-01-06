package com.xiaoo.kaleido.admin.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.auth.request.AdminUserPageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;

import java.util.List;

/**
 * 管理员查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface AdminUserQueryService {
    
    /**
     * 根据ID查询管理员信息
     *
     * @param adminUserId 管理员ID
     * @return 管理员信息
     */
    AdminUserInfoResponse findById(String adminUserId);
    
    /**
     * 根据账号查询管理员信息
     *
     * @param username 管理员账号
     * @return 管理员信息
     */
    AdminUserInfoResponse findByUsername(String username);
    
    /**
     * 根据手机号查询管理员信息
     *
     * @param mobile 手机号
     * @return 管理员信息
     */
    AdminUserInfoResponse findByMobile(String mobile);
    
    /**
     * 查询所有正常的管理员
     *
     * @return 管理员列表
     */
    List<AdminUserInfoResponse> findNormalAdminUsers();
    
    /**
     * 根据角色ID查询拥有该角色的管理员
     *
     * @param roleId 角色ID
     * @return 管理员列表
     */
    List<AdminUserInfoResponse> findByRoleId(String roleId);
    
    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询请求
     * @return 分页结果
     */
    PageInfo<AdminUserInfoResponse> pageQuery(AdminUserPageQueryReq pageQueryReq);
    
    /**
     * 检查管理员账号是否存在
     *
     * @param username 管理员账号
     * @return 是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查手机号是否存在
     *
     * @param mobile 手机号
     * @return 是否存在
     */
    boolean existsByMobile(String mobile);

    /**
     * 获取管理员的所有权限（通过角色）
     *
     * @param adminUserId 管理员ID
     * @return 权限ID列表
     */
    List<String> getPermissionsByAdminUserId(String adminUserId);
    
    /**
     * 验证管理员是否有某个权限
     *
     * @param adminUserId 管理员ID
     * @param permissionId 权限ID
     * @return 是否有权限
     */
    boolean hasPermission(String adminUserId, String permissionId);
}
