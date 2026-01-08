package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.api.admin.user.request.RolePageQueryReq;

import java.util.List;
import java.util.Optional;

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
     * 批量保存角色
     *
     * @param roles 角色列表
     * @return 保存后的角色列表
     */
    List<RoleAggregate> saveAll(List<RoleAggregate> roles);

    /**
     * 根据ID查找角色
     *
     * @param id 角色ID
     * @return 角色
     */
    Optional<RoleAggregate> findById(String id);

    /**
     * 根据编码查找角色
     *
     * @param code 角色编码
     * @return 角色
     */
    Optional<RoleAggregate> findByCode(String code);

    /**
     * 根据状态查找角色列表
     *
     * @param status 角色状态
     * @return 角色列表
     */
    List<RoleAggregate> findByStatus(DataStatusEnum status);

    /**
     * 查找系统角色
     *
     * @param isSystem 是否系统角色
     * @return 角色列表
     */
    List<RoleAggregate> findByIsSystem(Boolean isSystem);

    /**
     * 查找所有角色
     *
     * @return 角色列表
     */
    List<RoleAggregate> findAll();

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
     * 根据权限ID查找拥有该权限的角色
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    List<RoleAggregate> findByPermissionId(String permissionId);

    /**
     * 根据条件查询角色列表
     *
     * @param req 查询条件
     * @return 角色列表
     */
    List<RoleAggregate> findByCondition(RolePageQueryReq req);

    /**
     * 查找启用的角色列表
     *
     * @return 启用的角色列表
     */
    List<RoleAggregate> findEnabledRoles();

    /**
     * 查找系统角色列表
     *
     * @return 系统角色列表
     */
    List<RoleAggregate> findSystemRoles();

    /**
     * 获取角色树
     *
     * @return 角色树根节点列表
     */
    List<RoleAggregate> getRoleTree();

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteById(String id);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID列表
     */
    void deleteAllById(List<String> ids);

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
     * 统计角色数量
     *
     * @return 角色数量
     */
    long count();

    /**
     * 根据状态统计角色数量
     *
     * @param status 角色状态
     * @return 角色数量
     */
    long countByStatus(DataStatusEnum status);

    /**
     * 根据是否系统角色统计数量
     *
     * @param isSystem 是否系统角色
     * @return 角色数量
     */
    long countByIsSystem(Boolean isSystem);

    /**
     * 根据ID列表查询角色编码
     *
     * @param ids 角色ID列表
     * @return 角色编码列表
     */
    List<String> findCodesByIds(List<String> ids);

    /**
     * 根据管理员ID查询角色编码
     *
     * @param adminId 管理员ID
     * @return 角色编码列表
     */
    List<String> findCodesByAdminId(String adminId);
}
