package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePermissionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联数据访问对象
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermissionPO> {

    /**
     * 根据角色ID查找关联记录
     */
    List<RolePermissionPO> findByRoleId(@Param("roleId") String roleId);

    /**
     * 根据权限ID查找关联记录
     */
    List<RolePermissionPO> findByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 根据角色ID列表查找关联记录
     */
    List<RolePermissionPO> findByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 根据权限ID列表查找关联记录
     */
    List<RolePermissionPO> findByPermissionIds(@Param("permissionIds") List<String> permissionIds);

    /**
     * 删除角色的所有权限关联
     */
    int deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 删除权限的所有角色关联
     */
    int deleteByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 批量插入关联记录
     */
    int batchInsert(@Param("list") List<RolePermissionPO> list);
}
