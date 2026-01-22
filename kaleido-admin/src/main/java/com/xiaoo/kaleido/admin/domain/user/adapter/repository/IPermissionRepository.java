package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import com.xiaoo.kaleido.api.admin.user.request.PermissionPageQueryReq;

import java.util.List;

/**
 * 权限仓储接口
 * 定义权限数据的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IPermissionRepository {

    /**
     * 保存权限
     *
     * @param permission 权限
     * @return 保存后的权限
     */
    PermissionAggregate save(PermissionAggregate permission);

    /**
     * 更新权限
     *
     * @param permission 权限
     * @return 更新后的权限
     */
    PermissionAggregate update(PermissionAggregate permission);

    /**
     * 批量保存权限
     *
     * @param permissions 权限列表
     * @return 保存后的权限列表
     */
    List<PermissionAggregate> saveAll(List<PermissionAggregate> permissions);

    /**
     * 根据ID查找权限
     *
     * @param id 权限ID
     * @return 权限，如果不存在则返回null
     */
    PermissionAggregate findById(String id);

    /**
     * 根据编码查找权限
     *
     * @param code 权限编码
     * @return 权限，如果不存在则返回null
     */
    PermissionAggregate findByCode(String code);

    /**
     * 根据父权限ID查找子权限列表
     *
     * @param parentId 父权限ID
     * @return 子权限列表
     */
    List<PermissionAggregate> findByParentId(String parentId);

    /**
     * 获取权限树
     *
     * @return 权限树根节点列表
     */
    List<PermissionAggregate> getPermissionTree();

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deleteById(String id);

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据父权限ID统计子权限数量
     *
     * @param parentId 父权限ID
     * @return 子权限数量
     */
    long countByParentId(String parentId);

    /**
     * 根据角色ID列表查询权限编码
     *
     * @param roleIds 角色ID列表
     * @return 权限编码列表
     */
    List<String> findCodesByRoleIds(List<String> roleIds);

    /**
     * 根据管理员ID查询权限编码
     *
     * @param adminId 管理员ID
     * @return 权限编码列表
     */
    List<String> findCodesByAdminId(String adminId);
}
