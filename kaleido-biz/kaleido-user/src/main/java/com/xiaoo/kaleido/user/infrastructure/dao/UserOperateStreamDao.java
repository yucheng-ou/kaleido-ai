package com.xiaoo.kaleido.user.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户操作流水Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserOperateStreamDao extends BaseMapper<UserOperateStreamPO> {

    /**
     * 根据用户ID查找用户操作流水列表
     *
     * @param id 用户ID
     * @return 用户操作流水列表
     */
    List<UserOperateStreamPO> findById(@Param("id") String id);

    /**
     * 根据用户ID和操作类型查找用户操作流水列表
     *
     * @param id          用户ID
     * @param operateType 操作类型
     * @return 用户操作流水列表
     */
    List<UserOperateStreamPO> findByIdAndOperateType(@Param("id") String id, @Param("operateType") String operateType);

    /**
     * 根据操作者ID查找用户操作流水列表
     *
     * @param operatorId 操作者ID
     * @return 用户操作流水列表
     */
    List<UserOperateStreamPO> findByOperatorId(@Param("operatorId") String operatorId);

    /**
     * 根据用户ID分页查询操作流水
     *
     * @param id       用户ID
     * @param offset   偏移量
     * @param pageSize 每页大小
     * @return 用户操作流水列表
     */
    List<UserOperateStreamPO> findByIdWithPage(@Param("id") String id, @Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 根据用户ID查询操作流水（限制条数）
     *
     * @param id    用户ID
     * @param limit 限制条数
     * @return 用户操作流水列表
     */
    List<UserOperateStreamPO> findByIdWithLimit(@Param("id") String id, @Param("limit") int limit);

    /**
     * 统计用户操作流水数量
     *
     * @param id 用户ID
     * @return 操作流水数量
     */
    long countById(@Param("id") String id);
}
