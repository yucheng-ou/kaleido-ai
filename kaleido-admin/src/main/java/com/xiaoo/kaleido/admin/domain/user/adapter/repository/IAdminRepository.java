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
     * 保存新的管理员到数据库，如果管理员已存在则执行插入操作
     *
     * @param admin 管理员聚合根，不能为空
     * @return 保存后的管理员聚合根
     */
    AdminAggregate save(AdminAggregate admin);


    /**
     * 更新管理员
     *
     * 更新已存在的管理员信息到数据库
     *
     * @param admin 管理员聚合根，不能为空
     * @return 更新后的管理员聚合根
     */
    AdminAggregate update(AdminAggregate admin);

    /**
     * 根据ID查找管理员
     *
     * 根据管理员ID从数据库查询管理员信息
     *
     * @param id 管理员ID，不能为空
     * @return 管理员聚合根的Optional对象，如果不存在则返回Optional.empty()
     */
    Optional<AdminAggregate> findById(String id);


    /**
     * 根据手机号查找管理员
     *
     * 根据手机号从数据库查询管理员信息
     *
     * @param mobile 手机号，必须符合手机号格式
     * @return 管理员聚合根的Optional对象，如果不存在则返回Optional.empty()
     */
    Optional<AdminAggregate> findByMobile(String mobile);

    /**
     * 检查手机号是否存在
     *
     * 检查数据库中是否存在指定手机号的管理员
     *
     * @param mobile 手机号，必须符合手机号格式
     * @return 如果存在返回true，否则返回false
     */
    boolean existsByMobile(String mobile);

    /**
     * 分页查询管理员
     *
     * 根据查询条件分页查询管理员列表
     *
     * @param pageQueryReq 分页查询请求，不能为空
     * @return 管理员聚合根列表
     */
    List<AdminAggregate> pageQuery(AdminPageQueryReq pageQueryReq);

    /**
     * 为管理员分配角色
     *
     * 更新管理员与角色的关联关系
     *
     * @param admin 管理员聚合根，不能为空
     */
    void assignRoles(AdminAggregate admin);
}
