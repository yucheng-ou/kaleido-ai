package com.xiaoo.kaleido.admin.application.query;

import com.xiaoo.kaleido.api.admin.user.response.RoleInfoResponse;
import com.xiaoo.kaleido.api.admin.user.response.RoleTreeResponse;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据管理员id查询所有拥有的角色编码
     * @param adminId 管理员id
     * @return 角色编码列表
     */
    Set<String> getRoleCodesId(String adminId);
}
