package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminUserStatus;
import com.xiaoo.kaleido.api.admin.user.request.AdminUserPageQueryReq;

import java.util.List;
import java.util.Optional;

/**
 * 管理员仓储接口
 * 定义管理员数据的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
public interface IAdminRepository {

    /**
     * 保存管理员
     *
     * @param adminUser 管理员
     * @return 保存后的管理员
     */
    AdminUserAggregate save(AdminUserAggregate adminUser);


    /**
     * 更新管理员
     *
     * @param adminUser 管理员
     * @return 修改后的管理员
     */
    AdminUserAggregate update(AdminUserAggregate adminUser);

    /**
     * 批量保存管理员
     *
     * @param adminUsers 管理员列表
     * @return 保存后的管理员列表
     */
    List<AdminUserAggregate> saveAll(List<AdminUserAggregate> adminUsers);

    /**
     * 根据ID查找管理员
     *
     * @param id 管理员ID
     * @return 管理员
     */
    Optional<AdminUserAggregate> findById(String id);

    /**
     * 根据账号查找管理员
     *
     * @param username 管理员账号
     * @return 管理员
     */
    Optional<AdminUserAggregate> findByUsername(String username);

    /**
     * 根据手机号查找管理员
     *
     * @param mobile 手机号
     * @return 管理员
     */
    Optional<AdminUserAggregate> findByMobile(String mobile);

    /**
     * 根据状态查找管理员列表
     *
     * @param status 管理员状态
     * @return 管理员列表
     */
    List<AdminUserAggregate> findByStatus(AdminUserStatus status);

    /**
     * 查找所有管理员
     *
     * @return 管理员列表
     */
    List<AdminUserAggregate> findAll();

    /**
     * 根据ID列表查找管理员
     *
     * @param ids ID列表
     * @return 管理员列表
     */
    List<AdminUserAggregate> findAllById(List<String> ids);

    /**
     * 根据账号列表查找管理员
     *
     * @param usernames 账号列表
     * @return 管理员列表
     */
    List<AdminUserAggregate> findAllByUsername(List<String> usernames);

    /**
     * 根据角色ID查找拥有该角色的管理员
     *
     * @param roleId 角色ID
     * @return 管理员列表
     */
    List<AdminUserAggregate> findByRoleId(String roleId);

    /**
     * 删除管理员
     *
     * @param id 管理员ID
     */
    void deleteById(String id);

    /**
     * 批量删除管理员
     *
     * @param ids 管理员ID列表
     */
    void deleteAllById(List<String> ids);

    /**
     * 检查管理员是否存在
     *
     * @param id 管理员ID
     * @return 是否存在
     */
    boolean existsById(String id);

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
     * 统计管理员数量
     *
     * @return 管理员数量
     */
    long count();

    /**
     * 根据状态统计管理员数量
     *
     * @param status 管理员状态
     * @return 管理员数量
     */
    long countByStatus(AdminUserStatus status);

    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询请求
     * @return 管理员列表
     */
    List<AdminUserAggregate> pageQuery(AdminUserPageQueryReq pageQueryReq);
}
