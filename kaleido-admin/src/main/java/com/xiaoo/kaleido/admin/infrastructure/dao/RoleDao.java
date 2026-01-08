package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePO;
import com.xiaoo.kaleido.api.admin.user.request.RolePageQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色数据访问对象
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface RoleDao extends BaseMapper<RolePO> {

    /**
     * 根据ID查找角色
     */
    RolePO findById(@Param("id") String id);

    /**
     * 根据编码查找角色
     */
    RolePO findByCode(@Param("code") String code);

    /**
     * 根据状态查找角色列表
     */
    List<RolePO> findByStatus(@Param("status") String status);

    /**
     * 查找系统角色
     */
    List<RolePO> findByIsSystem(@Param("isSystem") Boolean isSystem);

    /**
     * 查找所有角色
     */
    List<RolePO> findAll();

    /**
     * 根据ID列表查找角色
     */
    List<RolePO> findAllById(@Param("ids") List<String> ids);

    /**
     * 根据编码列表查找角色
     */
    List<RolePO> findAllByCode(@Param("codes") List<String> codes);

    /**
     * 根据权限ID查找拥有该权限的角色
     */
    List<RolePO> findByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 根据条件查询角色列表
     */
    List<RolePO> findByCondition(@Param("req") RolePageQueryReq req);

    /**
     * 查找启用的角色列表
     */
    List<RolePO> findEnabledRoles();

    /**
     * 查找系统角色列表
     */
    List<RolePO> findSystemRoles();

    /**
     * 获取角色树
     */
    List<RolePO> getRoleTree();

    /**
     * 检查角色是否存在
     */
    boolean existsById(@Param("id") String id);

    /**
     * 检查角色编码是否存在
     */
    boolean existsByCode(@Param("code") String code);

    /**
     * 统计角色数量
     */
    long count();

    /**
     * 根据状态统计角色数量
     */
    long countByStatus(@Param("status") String status);

    /**
     * 根据是否系统角色统计数量
     */
    long countByIsSystem(@Param("isSystem") Boolean isSystem);

    /**
     * 根据ID列表查询角色编码
     */
    List<String> findCodesByIds(@Param("ids") List<String> ids);

    /**
     * 根据管理员ID查询角色编码
     */
    List<String> findCodesByAdminId(@Param("adminId") String adminId);
}
