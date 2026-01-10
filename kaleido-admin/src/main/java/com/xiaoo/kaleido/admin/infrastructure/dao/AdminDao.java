package com.xiaoo.kaleido.admin.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminPO;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
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
public interface AdminDao extends BaseMapper<AdminPO> {

    /**
     * 根据ID查找管理员
     */
    AdminPO findById(@Param("id") String id);

    /**
     * 根据账号查找管理员
     */
    AdminPO findByUsername(@Param("username") String username);

    /**
     * 根据手机号查找管理员
     */
    AdminPO findByMobile(@Param("mobile") String mobile);

    /**
     * 查找所有管理员
     */
    List<AdminPO> findAll();

    /**
     * 根据ID列表查找管理员
     */
    List<AdminPO> findAllById(@Param("ids") List<String> ids);

    /**
     * 根据账号列表查找管理员
     */
    List<AdminPO> findAllByUsername(@Param("usernames") List<String> usernames);

    /**
     * 根据角色ID查找拥有该角色的管理员
     */
    List<AdminPO> findByRoleId(@Param("roleId") String roleId);

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
    List<AdminPO> findByStatus(@Param("status") String status);


    /**
     * 统计管理员数量
     */
    long count();

    /**
     * 分页查询管理员
     */
    List<AdminPO> pageQuery(@Param("queryReq") AdminPageQueryReq queryReq);
}
