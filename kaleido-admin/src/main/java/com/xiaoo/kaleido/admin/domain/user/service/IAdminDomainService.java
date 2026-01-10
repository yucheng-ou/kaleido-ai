package com.xiaoo.kaleido.admin.domain.user.service;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;

import java.util.List;

/**
 * 管理员领域服务
 * 处理跨聚合的管理员业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IAdminDomainService {

    /**
     * 创建管理员
     *
     * @param mobile 手机号
     * @return 创建的管理员
     */
    AdminAggregate createAdmin(String mobile);

    /**
     * 更新管理员信息
     *
     * @param adminId 管理员ID
     * @param realName    真实姓名
     * @param mobile      手机号
     * @return 更新后的管理员
     */
    AdminAggregate updateAdmin(String adminId, String realName,
                               String mobile);


    /**
     * 启用管理员
     *
     * @param adminId 管理员ID
     * @return 启用后的管理员
     */
    AdminAggregate enableAdmin(String adminId);

    /**
     * 冻结管理员
     *
     * @param adminId 管理员ID
     * @return 冻结后的管理员
     */
    AdminAggregate freezeAdmin(String adminId);


    /**
     * 更新最后登录时间
     *
     * @param adminId 管理员ID
     * @return 更新后的管理员
     */
    AdminAggregate updateLastLoginTime(String adminId);

    /**
     * 分配角色给管理员
     *
     * @param adminId 管理员ID
     * @param roleIds 角色ID列表
     * @return 更新后的管理员
     */
    AdminAggregate assignRoles(String adminId, List<String> roleIds);


    /**
     * 根据ID查找管理员，不存在则抛出异常
     *
     * @param adminId 管理员ID
     * @return 管理员
     */
    AdminAggregate findByIdOrThrow(String adminId);

    /**
     * 根据手机号查找管理员
     *
     * @param mobile 手机号
     * @return 管理员
     */
    AdminAggregate findByMobile(String mobile);

    /**
     * 查找所有正常的管理员
     *
     * @return 正常的管理员列表
     */
    List<AdminAggregate> findNormalAdminUsers();


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
     * @param adminId 管理员ID
     * @return 是否存在且可用
     */
    boolean isValidAdminUser(String adminId);


    /**
     * 验证管理员是否拥有某个角色
     *
     * @param adminId 管理员ID
     * @param roleId      角色ID
     * @return 是否拥有
     */
    boolean hasRole(String adminId, String roleId);

    /**
     * 获取管理员的所有权限（通过角色）
     *
     * @param adminId 管理员ID
     * @return 权限ID列表
     */
    List<String> getPermissionsByAdminId(String adminId);

    /**
     * 验证管理员是否有某个权限
     *
     * @param adminId  管理员ID
     * @param permissionId 权限ID
     * @return 是否有权限
     */
    boolean hasPermission(String adminId, String permissionId);

    /**
     * 管理员登陆
     * 登录使用短信验证码 不使用密码
     *
     * @param adminId 管理员id
     * @return 管理员信息
     */
    AdminAggregate login(String adminId);
}
