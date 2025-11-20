package com.xiaoo.kaleido.user.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

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
    User getById(Long id);

    /**
     * 根据邀请码查询用户信息
     *
     * @param inviteCode 用户邀请码
     * @return 用户实体对象，如果不存在则返回null
     */
    User getByInviteCode(String inviteCode);

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 用户手机号
     * @return 用户实体对象，如果不存在则返回null
     */
    User getByTelephone(String telephone);
}
