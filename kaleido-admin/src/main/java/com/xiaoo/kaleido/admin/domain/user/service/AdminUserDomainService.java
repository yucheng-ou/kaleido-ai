package com.xiaoo.kaleido.admin.domain.user.service;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;

import java.util.List;

/**
 * 管理员领域服务
 * 处理跨聚合的管理员业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface AdminUserDomainService {

    /**
     * 创建管理员
     *
     * @param username     管理员账号
     * @param passwordHash 密码哈希值
     * @param realName     真实姓名
     * @param mobile       手机号
     * @return 创建的管理员
     */
    AdminUserAggregate createAdminUser(String username, String passwordHash,
                                       String realName, String mobile);

    /**
     * 更新管理员信息
     *
     * @param adminUserId 管理员ID
     * @param realName    真实姓名
     * @param mobile      手机号
     * @return 更新后的管理员
     */
    AdminUserAggregate updateAdminUser(String adminUserId, String realName,
                                       String mobile);

    /**
     * 更新管理员密码
     *
     * @param adminUserId  管理员ID
     * @param passwordHash 密码哈希值
     * @return 更新后的管理员
     */
    AdminUserAggregate updatePassword(String adminUserId, String passwordHash);

    /**
     * 启用管理员
     *
     * @param adminUserId 管理员ID
     * @return 启用后的管理员
     */
    AdminUserAggregate enableAdminUser(String adminUserId);

    /**
     * 冻结管理员
     *
     * @param adminUserId 管理员ID
     * @return 冻结后的管理员
     */
    AdminUserAggregate freezeAdminUser(String adminUserId);

    /**
     * 删除管理员
     *
     * @param adminUserId 管理员ID
     */
    void deleteAdminUser(String adminUserId);

    /**
     * 更新最后登录时间
     *
     * @param adminUserId 管理员ID
     * @return 更新后的管理员
     */
    AdminUserAggregate updateLastLoginTime(String adminUserId);

    /**
     * 分配角色给管理员
     *
     * @param adminUserId 管理员ID
     * @param roleIds     角色ID列表
     * @return 更新后的管理员
     */
    AdminUserAggregate assignRoles(String adminUserId, List<String> roleIds);

    /**
     * 从管理员移除角色
     *
     * @param adminUserId 管理员ID
     * @param roleIds     角色ID列表
     * @return 更新后的管理员
     */
    AdminUserAggregate removeRoles(String adminUserId, List<String> roleIds);

    /**
     * 清空管理员角色
     *
     * @param adminUserId 管理员ID
     * @return 更新后的管理员
     */
    AdminUserAggregate clearRoles(String adminUserId);

    /**
     * 根据ID查找管理员，不存在则抛出异常
     *
     * @param adminUserId 管理员ID
     * @return 管理员
     */
    AdminUserAggregate findByIdOrThrow(String adminUserId);

    /**
     * 根据账号查找管理员，不存在则抛出异常
     *
     * @param username 管理员账号
     * @return 管理员
     */
    AdminUserAggregate findByUsernameOrThrow(String username);

    /**
     * 根据手机号查找管理员
     *
     * @param mobile 手机号
     * @return 管理员
     */
    AdminUserAggregate findByMobile(String mobile);

    /**
     * 查找所有正常的管理员
     *
     * @return 正常的管理员列表
     */
    List<AdminUserAggregate> findNormalAdminUsers();

    /**
     * 根据角色ID查找拥有该角色的管理员
     *
     * @param roleId 角色ID
     * @return 管理员列表
     */
    List<AdminUserAggregate> findByRoleId(String roleId);

    /**
     * 检查管理员账号是否存在
     *
     * @param username 管理员账号
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查手机号是否存在
     *
     * @param mobile 手机号
     * @return 是否存在
     */
    boolean existsByMobile(String mobile);

    /**
     * 检查管理员是否存在且可用
     *
     * @param adminUserId 管理员ID
     * @return 是否存在且可用
     */
    boolean isValidAdminUser(String adminUserId);

    /**
     * 验证管理员账号是否可用
     *
     * @param username 管理员账号
     * @return 是否可用
     */
    boolean isUsernameAvailable(String username);

    /**
     * 验证管理员密码
     *
     * @param adminUserId  管理员ID
     * @param passwordHash 密码哈希值
     */
    void verifyPassword(String adminUserId, String passwordHash);

    /**
     * 验证管理员是否拥有某个角色
     *
     * @param adminUserId 管理员ID
     * @param roleId      角色ID
     * @return 是否拥有
     */
    boolean hasRole(String adminUserId, String roleId);

    /**
     * 获取管理员的所有权限（通过角色）
     *
     * @param adminUserId 管理员ID
     * @return 权限ID列表
     */
    List<String> getPermissionsByAdminUserId(String adminUserId);

    /**
     * 验证管理员是否有某个权限
     *
     * @param adminUserId  管理员ID
     * @param permissionId 权限ID
     * @return 是否有权限
     */
    boolean hasPermission(String adminUserId, String permissionId);
}
