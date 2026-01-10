package com.xiaoo.kaleido.admin.domain.user.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminStatus;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;

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
     * @param admin 管理员
     * @return 保存后的管理员
     */
    AdminAggregate save(AdminAggregate admin);


    /**
     * 更新管理员
     *
     * @param admin 管理员
     * @return 修改后的管理员
     */
    AdminAggregate update(AdminAggregate admin);

    /**
     * 批量保存管理员
     *
     * @param admins 管理员列表
     * @return 保存后的管理员列表
     */
    List<AdminAggregate> saveAll(List<AdminAggregate> admins);

    /**
     * 根据ID查找管理员
     *
     * @param id 管理员ID
     * @return 管理员
     */
    Optional<AdminAggregate> findById(String id);

    /**
     * 根据账号查找管理员
     *
     * @param username 管理员账号
     * @return 管理员
     */
    Optional<AdminAggregate> findByUsername(String username);

    /**
     * 根据手机号查找管理员
     *
     * @param mobile 手机号
     * @return 管理员
     */
    Optional<AdminAggregate> findByMobile(String mobile);

    /**
     * 根据状态查找管理员列表
     *
     * @param status 管理员状态
     * @return 管理员列表
     */
    List<AdminAggregate> findByStatus(AdminStatus status);

    /**
     * 查找所有管理员
     *
     * @return 管理员列表
     */
    List<AdminAggregate> findAll();

    /**
     * 根据ID列表查找管理员
     *
     * @param ids ID列表
     * @return 管理员列表
     */
    List<AdminAggregate> findAllById(List<String> ids);

    /**
     * 根据角色ID查找拥有该角色的管理员
     *
     * @param roleId 角色ID
     * @return 管理员列表
     */
    List<AdminAggregate> findByRoleId(String roleId);


    /**
     * 检查管理员是否存在
     *
     * @param id 管理员ID
     * @return 是否存在
     */
    boolean existsById(String id);


    /**
     * 检查手机号是否存在
     *
     * @param mobile 手机号
     * @return 是否存在
     */
    boolean existsByMobile(String mobile);

    /**
     * 分页查询管理员
     *
     * @param pageQueryReq 分页查询请求
     * @return 管理员列表
     */
    List<AdminAggregate> pageQuery(AdminPageQueryReq pageQueryReq);

    /**
     * 为管理员分配角色
     *
     * @param admin 管理员
     */
    void assignRoles(AdminAggregate admin);
}
