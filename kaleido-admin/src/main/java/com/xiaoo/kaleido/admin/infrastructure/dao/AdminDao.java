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
     * 根据手机号查找管理员
     */
    AdminPO findByMobile(@Param("mobile") String mobile);

    /**
     * 检查手机号是否存在
     */
    boolean existsByMobile(@Param("mobile") String mobile);

    /**
     * 分页查询管理员
     */
    List<AdminPO> pageQuery(@Param("queryReq") AdminPageQueryReq queryReq);
}
