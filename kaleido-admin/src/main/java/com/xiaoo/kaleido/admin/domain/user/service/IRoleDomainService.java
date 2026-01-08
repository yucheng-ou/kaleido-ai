package com.xiaoo.kaleido.admin.domain.user.service;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;

import java.util.List;

/**
 * 角色领域服务
 * 处理跨聚合的角色业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IRoleDomainService {

    /**
     * 创建角色
     *
     * @param code        角色编码
     * @param name        角色名称
     * @param description 角色描述
     * @param isSystem    是否系统角色
     * @return 创建的角色
     */
    RoleAggregate createRole(String code, String name, String description, Boolean isSystem);

    /**
     * 更新角色信息
     *
     * @param roleId      角色ID
     * @param name        角色名称
     * @param description 角色描述
     * @return 更新后的角色
     */
    RoleAggregate updateRole(String roleId, String name, String description);

    /**
     * 启用角色
     *
     * @param roleId 角色ID
     * @return 启用后的角色
     */
    RoleAggregate enableRole(String roleId);

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 禁用后的角色
     */
    RoleAggregate disableRole(String roleId);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(String roleId);

    /**
     * 分配权限给角色
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return 更新后的角色
     */
    RoleAggregate assignPermissions(String roleId, List<String> permissionIds);

    /**
     * 从角色移除权限
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return 更新后的角色
     */
    RoleAggregate removePermissions(String roleId, List<String> permissionIds);

    /**
     * 清空角色权限
     *
     * @param roleId 角色ID
     * @return 更新后的角色
     */
    RoleAggregate clearPermissions(String roleId);

    /**
     * 根据ID查找角色，不存在则抛出异常
     *
     * @param roleId 角色ID
     * @return 角色
     */
    RoleAggregate findByIdOrThrow(String roleId);

    /**
     * 根据编码查找角色，不存在则抛出异常
     *
     * @param code 角色编码
     * @return 角色
     */
    RoleAggregate findByCodeOrThrow(String code);

    /**
     * 查找所有启用的角色
     *
     * @return 启用的角色列表
     */
    List<RoleAggregate> findEnabledRoles();

    /**
     * 查找系统角色
     *
     * @return 系统角色列表
     */
    List<RoleAggregate> findSystemRoles();

    /**
     * 根据权限ID查找拥有该权限的角色
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    List<RoleAggregate> findByPermissionId(String permissionId);

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
     * 验证角色编码是否可用
     *
     * @param code 角色编码
     * @return 是否可用
     */
    boolean isCodeAvailable(String code);

    /**
     * 验证角色是否拥有某个权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 是否拥有
     */
    boolean hasPermission(String roleId, String permissionId);

    /**
     * 获取角色树（按角色类型分组）
     *
     * @return 角色树
     */
    List<RoleAggregate> getRoleTree();
}
