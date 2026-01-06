package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminUserPO;
import com.xiaoo.kaleido.api.admin.auth.request.AdminUserPageQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员数据访问对象
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface AdminUserDao extends BaseMapper<AdminUserPO> {

    /**
     * 根据ID查找管理员
     */
    AdminUserPO findById(@Param("id") String id);

    /**
     * 根据账号查找管理员
     */
    AdminUserPO findByUsername(@Param("username") String username);

    /**
     * 根据手机号查找管理员
     */
    AdminUserPO findByMobile(@Param("mobile") String mobile);

    /**
     * 查找所有管理员
     */
    List<AdminUserPO> findAll();

    /**
     * 根据ID列表查找管理员
     */
    List<AdminUserPO> findAllById(@Param("ids") List<String> ids);

    /**
     * 根据账号列表查找管理员
     */
    List<AdminUserPO> findAllByUsername(@Param("usernames") List<String> usernames);

    /**
     * 根据角色ID查找拥有该角色的管理员
     */
    List<AdminUserPO> findByRoleId(@Param("roleId") String roleId);

    /**
     * 检查管理员是否存在
     */
    boolean existsById(@Param("id") String id);

    /**
     * 检查管理员账号是否存在
     */
    boolean existsByUsername(@Param("username") String username);

    /**
     * 检查手机号是否存在
     */
    boolean existsByMobile(@Param("mobile") String mobile);

    /**
     * 根据状态查找管理员列表
     */
    List<AdminUserPO> findByStatus(@Param("status") String status);

    /**
     * 根据状态统计管理员数量
     */
    long countByStatus(@Param("status") String status);

    /**
     * 统计管理员数量
     */
    long count();

    /**
     * 分页查询管理员
     */
    List<AdminUserPO> pageQuery(@Param("queryReq") AdminUserPageQueryReq queryReq);
}
