package com.xiaoo.kaleido.admin.application.query;

import com.xiaoo.kaleido.api.admin.auth.response.PermissionInfoResponse;
import java.util.List;
import java.util.Set;

/**
 * 权限查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface PermissionQueryService {
    
    /**
     * 根据ID查询权限信息
     *
     * @param permissionId 权限ID
     * @return 权限信息
     */
    PermissionInfoResponse findById(String permissionId);
    
    /**
     * 根据编码查询权限信息
     *
     * @param code 权限编码
     * @return 权限信息
     */
    PermissionInfoResponse findByCode(String code);
    
    /**
     * 根据父权限ID查询子权限列表
     *
     * @param parentId 父权限ID
     * @return 子权限列表
     */
    List<PermissionInfoResponse> findByParentId(String parentId);
    
    /**
     * 查询根权限列表
     *
     * @return 根权限列表
     */
    List<PermissionInfoResponse> findRootPermissions();
    
    /**
     * 获取权限树
     *
     * @return 权限树根节点列表
     */
    List<PermissionInfoResponse> getPermissionTree();
    
    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据管理员id查询所有拥有的权限编码
     * @param adminId 管理员id
     * @return 权限编码列表
     */
    Set<String> getPermCodesById(String adminId);
}
