package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminUserRolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员角色关联数据访问对象
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface AdminUserRoleDao extends BaseMapper<AdminUserRolePO> {

    /**
     * 根据管理员ID查找关联记录
     */
    List<AdminUserRolePO> findByAdminUserId(@Param("adminUserId") String adminUserId);

    /**
     * 根据角色ID查找关联记录
     */
    List<AdminUserRolePO> findByRoleId(@Param("roleId") String roleId);

    /**
     * 根据管理员ID列表查找关联记录
     */
    List<AdminUserRolePO> findByAdminUserIds(@Param("adminUserIds") List<String> adminUserIds);

    /**
     * 根据角色ID列表查找关联记录
     */
    List<AdminUserRolePO> findByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 删除管理员的所有角色关联
     */
    int deleteByAdminUserId(@Param("adminUserId") String adminUserId);

    /**
     * 删除角色的所有管理员关联
     */
    int deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 批量插入关联记录
     */
    int batchInsert(@Param("list") List<AdminUserRolePO> list);
}
