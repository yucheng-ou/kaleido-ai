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
     * @param code      权限编码
     * @param name      权限名称
     * @param type      权限类型
     * @param parentId  父权限ID
     * @param sort      排序
     * @param icon      图标
     * @param path      前端路由路径
     * @param component 前端组件路径
     * @param isHidden  是否隐藏
     * @return 创建的权限
     */
    PermissionAggregate createPermission(String code, String name, PermissionType type,
                                         String parentId, Integer sort, String icon,
                                         String path, String component, Boolean isHidden);

    /**
     * 更新权限信息
     *
     * @param permissionId 权限ID
     * @param name         权限名称
     * @param type         权限类型
     * @param parentId     父权限ID
     * @param sort         排序
     * @param icon         图标
     * @param path         前端路由路径
     * @param component    前端组件路径
     * @param isHidden     是否隐藏
     * @return 更新后的权限
     */
    PermissionAggregate updatePermission(String permissionId, String name, PermissionType type,
                                         String parentId, Integer sort, String icon,
                                         String path, String component, Boolean isHidden);

    /**
     * 更新权限编码
     *
     * @param permissionId 权限ID
     * @param code         权限编码
     * @return 更新后的权限
     */
    PermissionAggregate updatePermissionCode(String permissionId, String code);

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     */
    void deletePermission(String permissionId);

    /**
     * 根据ID查找权限，不存在则抛出异常
     *
     * @param permissionId 权限ID
     * @return 权限
     */
    PermissionAggregate findByIdOrThrow(String permissionId);

    /**
     * 根据编码查找权限，不存在则抛出异常
     *
     * @param code 权限编码
     * @return 权限
     */
    PermissionAggregate findByCodeOrThrow(String code);

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
     * 获取完整的权限树
     *
     * @return 权限树根节点列表
     */
    List<PermissionAggregate> getPermissionTree();

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 检查权限是否存在且有效
     *
     * @param permissionId 权限ID
     * @return 是否存在且有效
     */
    boolean isValidPermission(String permissionId);

    /**
     * 验证权限编码是否可用
     *
     * @param code 权限编码
     * @return 是否可用
     */
    boolean isCodeAvailable(String code);
}
