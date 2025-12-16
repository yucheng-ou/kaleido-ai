package com.xiaoo.kaleido.user.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户操作流水Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserOperateStreamMapper extends BaseMapper<UserOperateStreamPO> {

    /**
     * 根据用户ID查找用户操作流水列表
     *
     * @param userId 用户ID
     * @return 用户操作流水列表
     */
    @Select("SELECT * FROM user_operate_stream WHERE user_id = #{userId} AND deleted = 0 ORDER BY operate_time DESC")
    List<UserOperateStreamPO> findByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID和操作类型查找用户操作流水列表
     *
     * @param userId      用户ID
     * @param operateType 操作类型
     * @return 用户操作流水列表
     */
    @Select("SELECT * FROM user_operate_stream WHERE user_id = #{userId} AND operate_type = #{operateType} AND deleted = 0 ORDER BY operate_time DESC")
    List<UserOperateStreamPO> findByUserIdAndOperateType(@Param("userId") String userId, @Param("operateType") String operateType);

    /**
     * 根据操作者ID查找用户操作流水列表
     *
     * @param operatorId 操作者ID
     * @return 用户操作流水列表
     */
    @Select("SELECT * FROM user_operate_stream WHERE operator_id = #{operatorId} AND deleted = 0 ORDER BY operate_time DESC")
    List<UserOperateStreamPO> findByOperatorId(@Param("operatorId") String operatorId);

    /**
     * 根据用户ID分页查询操作流水
     *
     * @param userId   用户ID
     * @param offset   偏移量
     * @param pageSize 每页大小
     * @return 用户操作流水列表
     */
    @Select("SELECT * FROM user_operate_stream WHERE user_id = #{userId} AND deleted = 0 ORDER BY operate_time DESC LIMIT #{offset}, #{pageSize}")
    List<UserOperateStreamPO> findByUserIdWithPage(@Param("userId") String userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 根据用户ID查询操作流水（限制条数）
     *
     * @param userId 用户ID
     * @param limit  限制条数
     * @return 用户操作流水列表
     */
    @Select("SELECT * FROM user_operate_stream WHERE user_id = #{userId} AND deleted = 0 ORDER BY operate_time DESC LIMIT #{limit}")
    List<UserOperateStreamPO> findByUserIdWithLimit(@Param("userId") String userId, @Param("limit") int limit);

    /**
     * 统计用户操作流水数量
     *
     * @param userId 用户ID
     * @return 操作流水数量
     */
    @Select("SELECT COUNT(*) FROM user_operate_stream WHERE user_id = #{userId} AND deleted = 0")
    long countByUserId(@Param("userId") String userId);
}
