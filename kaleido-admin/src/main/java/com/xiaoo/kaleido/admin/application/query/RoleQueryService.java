package com.xiaoo.kaleido.admin.application.query;

import com.xiaoo.kaleido.api.admin.auth.request.RolePageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.RoleInfoResponse;
import com.xiaoo.kaleido.api.admin.auth.response.RoleTreeResponse;
import com.xiaoo.kaleido.base.response.PageResp;

import java.util.List;

/**
 * 角色查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface RoleQueryService {
    
    /**
     * 根据ID查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    RoleInfoResponse findById(String roleId);
    
    /**
     * 根据编码查询角色信息
     *
     * @param code 角色编码
     * @return 角色信息
     */
    RoleInfoResponse findByCode(String code);
    
    /**
     * 分页查询角色
     *
     * @param req 查询条件
     * @return 分页结果
     */
    PageResp<RoleInfoResponse> pageQuery(RolePageQueryReq req);
    
    /**
     * 查询所有启用的角色
     *
     * @return 启用的角色列表
     */
    List<RoleInfoResponse> findEnabledRoles();
    
    /**
     * 查询系统角色
     *
     * @return 系统角色列表
     */
    List<RoleInfoResponse> findSystemRoles();
    
    /**
     * 根据权限ID查询拥有该权限的角色
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    List<RoleInfoResponse> findByPermissionId(String permissionId);
    
    /**
     * 获取角色树
     *
     * @return 角色树根节点列表
     */
    List<RoleTreeResponse> getRoleTree();
    
    /**
     * 检查角色编码是否存在
     *
     * @param code 角色编码
     * @return 是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 检查角色是否存在且启用
     *
     * @param roleId 角色ID
     * @return 是否存在且启用
     */
    boolean isValidRole(String roleId);
    
    /**
     * 验证角色是否拥有某个权限
     *
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 是否拥有
     */
    boolean hasPermission(String roleId, String permissionId);
}
