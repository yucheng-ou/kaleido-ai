package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import com.xiaoo.kaleido.api.admin.auth.request.PermissionPageQueryReq;

import java.util.List;
import java.util.Optional;

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
     * @return 权限
     */
    Optional<PermissionAggregate> findById(String id);

    /**
     * 根据编码查找权限
     *
     * @param code 权限编码
     * @return 权限
     */
    Optional<PermissionAggregate> findByCode(String code);

    /**
     * 根据父权限ID查找子权限列表
     *
     * @param parentId 父权限ID
     * @return 子权限列表
     */
    List<PermissionAggregate> findByParentId(String parentId);

    /**
     * 查找根权限列表
     *
     * @return 根权限列表
     */
    List<PermissionAggregate> findRootPermissions();

    /**
     * 根据类型查找权限列表
     *
     * @param type 权限类型
     * @return 权限列表
     */
    List<PermissionAggregate> findByType(PermissionType type);

    /**
     * 查找所有权限
     *
     * @return 权限列表
     */
    List<PermissionAggregate> findAll();

    /**
     * 根据ID列表查找权限
     *
     * @param ids ID列表
     * @return 权限列表
     */
    List<PermissionAggregate> findAllById(List<String> ids);

    /**
     * 根据编码列表查找权限
     *
     * @param codes 编码列表
     * @return 权限列表
     */
    List<PermissionAggregate> findAllByCode(List<String> codes);

    /**
     * 根据条件查询权限列表
     *
     * @param req 查询条件
     * @return 权限列表
     */
    List<PermissionAggregate> findByCondition(PermissionPageQueryReq req);

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
     * 批量删除权限
     *
     * @param ids 权限ID列表
     */
    void deleteAllById(List<String> ids);

    /**
     * 检查权限是否存在
     *
     * @param id 权限ID
     * @return 是否存在
     */
    boolean existsById(String id);

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 统计权限数量
     *
     * @return 权限数量
     */
    long count();

    /**
     * 根据父权限ID统计子权限数量
     *
     * @param parentId 父权限ID
     * @return 子权限数量
     */
    long countByParentId(String parentId);

    /**
     * 根据类型统计权限数量
     *
     * @param type 权限类型
     * @return 权限数量
     */
    long countByType(PermissionType type);

    /**
     * 根据ID列表查询权限编码
     *
     * @param ids 权限ID列表
     * @return 权限编码列表
     */
    List<String> findCodesByIds(List<String> ids);

    /**
     * 根据角色ID列表查询权限编码
     *
     * @param roleIds 角色ID列表
     * @return 权限编码列表
     */
    List<String> findCodesByRoleIds(List<String> roleIds);
}
