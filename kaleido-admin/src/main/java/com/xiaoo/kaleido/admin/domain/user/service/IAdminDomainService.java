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
     * 根据手机号创建新的管理员账户，系统会自动生成管理员ID并设置初始状态
     *
     * @param mobile 手机号，必须符合手机号格式
     * @return 创建的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当手机号已存在时抛出
     */
    AdminAggregate createAdmin(String mobile);

    /**
     * 更新管理员信息
     * 
     * 更新管理员的基本信息，包括真实姓名和手机号
     *
     * @param adminId 管理员ID，不能为空
     * @param realName 真实姓名，可以为空
     * @param mobile 手机号，必须符合手机号格式
     * @return 更新后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在或手机号已存在时抛出
     */
    AdminAggregate updateAdmin(String adminId, String realName,
                               String mobile);


    /**
     * 启用管理员
     * 
     * 将管理员状态从冻结或禁用状态改为启用状态
     *
     * @param adminId 管理员ID，不能为空
     * @return 启用后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在时抛出
     */
    AdminAggregate enableAdmin(String adminId);

    /**
     * 冻结管理员
     * 
     * 将管理员状态从启用状态改为冻结状态
     *
     * @param adminId 管理员ID，不能为空
     * @return 冻结后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在时抛出
     */
    AdminAggregate freezeAdmin(String adminId);


    /**
     * 更新最后登录时间
     * 
     * 更新管理员的最后登录时间为当前时间
     *
     * @param adminId 管理员ID，不能为空
     * @return 更新后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在时抛出
     */
    AdminAggregate updateLastLoginTime(String adminId);

    /**
     * 分配角色给管理员
     * 
     * 为管理员分配一个或多个角色，会替换原有的角色分配
     *
     * @param adminId 管理员ID，不能为空
     * @param roleIds 角色ID列表，不能为空
     * @return 分配角色后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在、角色不存在或角色已禁用时抛出
     */
    AdminAggregate assignRoles(String adminId, List<String> roleIds);


    /**
     * 根据ID查找管理员，不存在则抛出异常
     * 
     * 根据管理员ID查询管理员信息，如果不存在则抛出异常
     *
     * @param adminId 管理员ID，不能为空
     * @return 管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在时抛出
     */
    AdminAggregate findByIdOrThrow(String adminId);

    /**
     * 根据手机号查找管理员
     * 
     * 根据手机号查询管理员信息，如果不存在则返回null
     *
     * @param mobile 手机号，必须符合手机号格式
     * @return 管理员聚合根，如果不存在则返回null
     */
    AdminAggregate findByMobile(String mobile);

    /**
     * 获取管理员的所有权限（通过角色）
     * 
     * 通过管理员分配的角色获取所有权限ID，包括直接分配和通过角色继承的权限
     *
     * @param adminId 管理员ID，不能为空
     * @return 权限ID列表，如果没有权限则返回空列表
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在时抛出
     */
    List<String> getPermissionsByAdminId(String adminId);


    /**
     * 管理员登录
     * 
     * 管理员登录操作，验证管理员状态并更新最后登录时间
     *
     * @param adminId 管理员ID，不能为空
     * @return 登录后的管理员聚合根
     * @throws com.xiaoo.kaleido.admin.types.exception.AdminException 当管理员不存在或状态不可用时抛出
     */
    AdminAggregate login(String adminId);
}
