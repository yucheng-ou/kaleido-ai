package com.xiaoo.kaleido.admin.domain.user.service;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;

import java.util.List;

/**
 * 权限领域服务
 * 处理跨聚合的权限业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IPermissionDomainService {

    /**
     * 创建权限
     *
     * 创建新的权限，包括权限编码、名称、类型等基本信息
     *
     * @param code      权限编码，必须唯一且符合编码规范
     * @param name      权限名称，不能为空
     * @param type      权限类型，不能为空
     * @param parentId  父权限ID，可以为空（表示根权限）
     * @param sort      排序值，可以为空
     * @param icon      图标，可以为空
     * @param path      前端路由路径，可以为空
     * @param component 前端组件路径，可以为空
     * @param isHidden  是否隐藏，可以为空
     * @return 创建的权限聚合根
     */
    PermissionAggregate createPermission(String code, String name, PermissionType type,
                                         String parentId, Integer sort, String icon,
                                         String path, String component, Boolean isHidden);

    /**
     * 更新权限信息
     *
     * 更新权限的基本信息，不包括权限编码
     *
     * @param permissionId 权限ID，不能为空
     * @param name         权限名称，不能为空
     * @param type         权限类型，不能为空
     * @param parentId     父权限ID，可以为空（表示根权限）
     * @param sort         排序值，可以为空
     * @param icon         图标，可以为空
     * @param path         前端路由路径，可以为空
     * @param component    前端组件路径，可以为空
     * @param isHidden     是否隐藏，可以为空
     * @return 更新后的权限聚合根
     */
    PermissionAggregate updatePermission(String permissionId, String name, PermissionType type,
                                         String parentId, Integer sort, String icon,
                                         String path, String component, Boolean isHidden);

    /**
     * 更新权限编码
     *
     * 更新权限的编码，需要验证编码的唯一性
     *
     * @param permissionId 权限ID，不能为空
     * @param code         权限编码，必须唯一且符合编码规范
     * @return 更新后的权限聚合根
     */
    PermissionAggregate updatePermissionCode(String permissionId, String code);

    /**
     * 删除权限
     *
     * 删除指定的权限，如果权限有子权限则不允许删除
     *
     * @param permissionId 权限ID，不能为空
     */
    void deletePermission(String permissionId);

    /**
     * 根据ID查找权限，不存在则抛出异常
     *
     * 根据权限ID查询权限信息，如果不存在则抛出异常
     *
     * @param permissionId 权限ID，不能为空
     * @return 权限聚合根
     */
    PermissionAggregate findByIdOrThrow(String permissionId);

    /**
     * 根据编码查找权限，不存在则抛出异常
     *
     * 根据权限编码查询权限信息，如果不存在则抛出异常
     *
     * @param code 权限编码，不能为空
     * @return 权限聚合根
     */
    PermissionAggregate findByCodeOrThrow(String code);

    /**
     * 根据父权限ID查找子权限列表
     *
     * 根据父权限ID查询所有子权限，按排序值升序排列
     *
     * @param parentId 父权限ID，可以为空（表示查询根权限）
     * @return 子权限列表，如果没有子权限则返回空列表
     */
    List<PermissionAggregate> findByParentId(String parentId);

    /**
     * 查找根权限列表
     *
     * 查询所有没有父权限的根权限，按排序值升序排列
     *
     * @return 根权限列表，如果没有根权限则返回空列表
     */
    List<PermissionAggregate> findRootPermissions();

    /**
     * 获取完整的权限树
     *
     * 获取所有权限的树形结构，包含父子关系
     *
     * @return 权限树根节点列表，如果没有权限则返回空列表
     */
    List<PermissionAggregate> getPermissionTree();

    /**
     * 检查权限编码是否存在
     *
     * 检查数据库中是否存在指定编码的权限
     *
     * @param code 权限编码，不能为空
     * @return 如果存在返回true，否则返回false
     */
    boolean existsByCode(String code);

    /**
     * 检查权限是否存在且有效
     *
     * 检查权限是否存在且未被删除
     *
     * @param permissionId 权限ID，不能为空
     * @return 如果权限存在且有效返回true，否则返回false
     */
    boolean isValidPermission(String permissionId);

    /**
     * 验证权限编码是否可用
     *
     * 验证权限编码是否未被使用，可用于创建新权限时的编码验证
     *
     * @param code 权限编码，不能为空
     * @return 如果编码可用返回true，否则返回false
     */
    boolean isCodeAvailable(String code);
}
