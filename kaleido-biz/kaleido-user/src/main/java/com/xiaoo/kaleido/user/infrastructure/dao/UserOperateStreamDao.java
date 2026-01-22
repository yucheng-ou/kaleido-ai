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

     * 查询指定用户的所有操作流水记录，按操作时间倒序排列
     *
     * @param id 用户ID，不能为空
     * @return 用户操作流水列表，如果用户不存在则返回空列表
     */
    List<UserOperateStreamPO> findById(@Param("id") String id);

    /**
     * 根据用户ID和操作类型查找用户操作流水列表

     * 查询指定用户特定操作类型的流水记录，按操作时间倒序排列
     *
     * @param id          用户ID，不能为空
     * @param operateType 操作类型，不能为空
     * @return 用户操作流水列表，如果用户不存在或没有匹配记录则返回空列表
     */
    List<UserOperateStreamPO> findByIdAndOperateType(@Param("id") String id, @Param("operateType") String operateType);

    /**
     * 根据操作者ID查找用户操作流水列表

     * 查询指定操作者执行的所有操作流水记录，按操作时间倒序排列
     *
     * @param operatorId 操作者ID，不能为空
     * @return 用户操作流水列表，如果操作者不存在则返回空列表
     */
    List<UserOperateStreamPO> findByOperatorId(@Param("operatorId") String operatorId);

    /**
     * 根据用户ID分页查询操作流水

     * 查询指定用户的操作流水记录，支持分页查询，按操作时间倒序排列
     *
     * @param id       用户ID，不能为空
     * @param offset   偏移量，从0开始
     * @param pageSize 每页大小，必须大于0
     * @return 用户操作流水列表，如果用户不存在则返回空列表
     */
    List<UserOperateStreamPO> findByIdWithPage(@Param("id") String id, @Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 根据用户ID查询操作流水（限制条数）

     * 查询指定用户的最新操作流水记录，限制返回条数，按操作时间倒序排列
     *
     * @param id    用户ID，不能为空
     * @param limit 限制条数，必须大于0
     * @return 用户操作流水列表，如果用户不存在则返回空列表
     */
    List<UserOperateStreamPO> findByIdWithLimit(@Param("id") String id, @Param("limit") int limit);

    /**
     * 统计用户操作流水数量

     * 统计指定用户的操作流水记录总数
     *
     * @param id 用户ID，不能为空
     * @return 操作流水数量，如果用户不存在则返回0
     */
    long countById(@Param("id") String id);
}
