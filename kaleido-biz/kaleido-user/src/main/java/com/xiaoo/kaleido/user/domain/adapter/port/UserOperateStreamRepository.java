package com.xiaoo.kaleido.user.domain.adapter.port;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserOperateStreamAggregate;

import java.util.List;

/**
 * 用户操作流水仓储接口（领域层）
 * 定义用户操作流水聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserOperateStreamRepository {

    /**
     * 保存用户操作流水聚合根
     *
     * @param userOperateStreamAggregate 用户操作流水聚合根
     * @return 保存后的用户操作流水聚合根
     */
    UserOperateStreamAggregate save(UserOperateStreamAggregate userOperateStreamAggregate);

    /**
     * 根据ID查找用户操作流水聚合根
     *
     * @param id 操作流水ID
     * @return 用户操作流水聚合根（如果存在）
     */
    UserOperateStreamAggregate findById(String id);

    /**
     * 根据用户ID查找用户操作流水列表
     *
     * @param userId 用户ID
     * @return 用户操作流水列表
     */
    List<UserOperateStreamAggregate> findByUserId(String userId);

    /**
     * 根据用户ID和操作类型查找用户操作流水列表
     *
     * @param userId      用户ID
     * @param operateType 操作类型
     * @return 用户操作流水列表
     */
    List<UserOperateStreamAggregate> findByUserIdAndOperateType(String userId, String operateType);

    /**
     * 根据操作者ID查找用户操作流水列表
     *
     * @param operatorId 操作者ID
     * @return 用户操作流水列表
     */
    List<UserOperateStreamAggregate> findByOperatorId(String operatorId);

    /**
     * 根据用户ID分页查询操作流水
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 用户操作流水列表
     */
    List<UserOperateStreamAggregate> findByUserIdWithPage(String userId, int pageNum, int pageSize);

    /**
     * 统计用户操作流水数量
     *
     * @param userId 用户ID
     * @return 操作流水数量
     */
    long countByUserId(String userId);
}
