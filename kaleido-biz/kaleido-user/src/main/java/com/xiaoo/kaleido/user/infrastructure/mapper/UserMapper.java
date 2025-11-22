package com.xiaoo.kaleido.user.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoo.kaleido.api.user.request.PageUserQueryRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户数据访问映射接口
 *
 * <p>负责用户实体的数据库操作，包括基本的CRUD操作和自定义查询方法</p>
 *
 * @author ouyucheng
 * @date 2025/11/18
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户唯一标识
     * @return 用户实体对象，如果不存在则返回null
     */
    User getById(@Param("id") Long id);

    /**
     * 根据邀请码查询用户信息
     *
     * @param inviteCode 用户邀请码
     * @return 用户实体对象，如果不存在则返回null
     */
    User getByInviteCode(@Param("id") String inviteCode);

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 用户手机号
     * @return 用户实体对象，如果不存在则返回null
     */
    User getByTelephone(@Param("id") String telephone);

    /**
     * 查询用户列表（不分页）
     * 根据查询条件返回匹配的用户列表
     *
     * @param request 用户查询请求参数
     * @return 用户列表
     */
    List<User> query(@Param("request") UserQueryRequest request);

    /**
     * 分页查询用户列表
     * 使用MyBatis Plus分页插件进行分页查询
     *
     * @param page    MyBatis Plus分页对象
     * @param request 用户查询请求参数
     * @return 分页结果
     */
    Page<User> pageQuery(Page<User> page, @Param("request") PageUserQueryRequest request);

    /**
     * 根据用户ID列表批量查询用户信息
     *
     * @param ids 用户ID列表
     * @return 用户列表
     */
    List<User> getByIds(@Param("ids") Set<Long> ids);
}
