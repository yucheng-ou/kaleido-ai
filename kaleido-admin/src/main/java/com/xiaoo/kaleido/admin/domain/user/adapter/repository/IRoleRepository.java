package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;

import java.util.List;

/**
 * 角色仓储接口
 * 定义角色数据的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IRoleRepository {

    /**
     * 保存角色
     *
     * @param role 角色
     * @return 保存后的角色
     */
    RoleAggregate save(RoleAggregate role);

    /**
     * 更新角色
     *
     * @param role 角色
     * @return 更新后的角色
     */
    RoleAggregate update(RoleAggregate role);


    /**
     * 根据ID查找角色
     *
     * @param id 角色ID
     * @return 角色，如果不存在则返回null
     */
    RoleAggregate findById(String id);

    /**
     * 根据编码查找角色
     *
     * @param code 角色编码
     * @return 角色，如果不存在则返回null
     */
    RoleAggregate findByCode(String code);

    /**
     * 根据ID列表查找角色
     *
     * @param ids ID列表
     * @return 角色列表
     */
    List<RoleAggregate> findAllById(List<String> ids);

    /**
     * 根据编码列表查找角色
     *
     * @param codes 编码列表
     * @return 角色列表
     */
    List<RoleAggregate> findAllByCode(List<String> codes);

    /**
     * 获取所有角色列表
     *
     * @return 角色列表
     */
    List<RoleAggregate> findAll();

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteById(String id);

    /**
     * 检查角色是否存在
     *
     * @param id 角色ID
     * @return 是否存在
     */
    boolean existsById(String id);

    /**
     * 检查角色编码是否存在
     *
     * @param code 角色编码
     * @return 是否存在
     */
    boolean existsByCode(String code);


    /**
     * 根据管理员ID查询角色编码
     *
     * @param adminId 管理员ID
     * @return 角色编码列表
     */
    List<String> findCodesByAdminId(String adminId);

    /**
     * 为角色分配权限
     *
     * @param roleId        角色id
     * @param permissionIds 权限列表
     */
    void saveRolePermissions(String roleId, List<String> permissionIds);
}
