package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminRolePO;
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
public interface AdminRoleDao extends BaseMapper<AdminRolePO> {

    /**
     * 根据管理员ID查找关联记录
     */
    List<AdminRolePO> findByAdminId(@Param("adminId") String adminId);

    /**
     * 根据角色ID查找关联记录
     */
    List<AdminRolePO> findByRoleId(@Param("roleId") String roleId);

    /**
     * 根据管理员ID列表查找关联记录
     */
    List<AdminRolePO> findByAdminIds(@Param("adminIds") List<String> adminIds);

    /**
     * 根据角色ID列表查找关联记录
     */
    List<AdminRolePO> findByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 删除管理员的所有角色关联
     */
    int deleteByAdminId(@Param("adminId") String adminId);

    /**
     * 删除角色的所有管理员关联
     */
    int deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 批量插入关联记录
     */
    int batchInsert(@Param("list") List<AdminRolePO> list);
}
