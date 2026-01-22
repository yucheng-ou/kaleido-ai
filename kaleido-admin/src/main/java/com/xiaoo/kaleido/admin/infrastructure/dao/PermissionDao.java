package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.PermissionPO;
import com.xiaoo.kaleido.api.admin.user.request.PermissionPageQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限数据访问对象
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface PermissionDao extends BaseMapper<PermissionPO> {

    /**
     * 根据ID查找权限
     */
    PermissionPO findById(@Param("id") String id);

    /**
     * 根据编码查找权限
     */
    PermissionPO findByCode(@Param("code") String code);

    /**
     * 根据父权限ID查找子权限列表
     */
    List<PermissionPO> findByParentId(@Param("parentId") String parentId);

    /**
     * 查找所有权限
     */
    List<PermissionPO> findAll();

    /**
     * 根据ID列表查找权限
     */
    List<PermissionPO> findAllById(@Param("ids") List<String> ids);

    /**
     * 根据条件查询权限列表
     */
    List<PermissionPO> findByCondition(@Param("req") PermissionPageQueryReq req);

    /**
     * 获取权限树
     */
    List<PermissionPO> getPermissionTree();


    /**
     * 检查权限编码是否存在
     */
    boolean existsByCode(@Param("code") String code);

    /**
     * 根据类型查找权限列表
     */
    List<PermissionPO> findByType(@Param("type") String type);

    /**
     * 根据父权限ID统计子权限数量
     */
    long countByParentId(@Param("parentId") String parentId);


    /**
     * 根据角色ID列表查询权限编码
     */
    List<String> findCodesByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 根据管理员ID查询权限编码
     */
    List<String> findCodesByAdminId(@Param("adminId") String adminId);
}
