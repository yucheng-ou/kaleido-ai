package com.xiaoo.kaleido.ai.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.ConversationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 会话数据访问接口
 * <p>
 * 负责会话表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface ConversationDao extends BaseMapper<ConversationPO> {

    /**
     * 根据ID查询会话
     *
     * @param id 会话ID
     * @return 会话持久化对象
     */
    ConversationPO findById(@Param("id") String id);

    /**
     * 根据会话ID（业务唯一）查询会话
     *
     * @param conversationId 会话ID（业务唯一）
     * @return 会话持久化对象
     */
    ConversationPO findByConversationId(@Param("conversationId") String conversationId);

    /**
     * 根据用户ID查询会话列表
     *
     * @param userId 用户ID
     * @return 会话持久化对象列表
     */
    List<ConversationPO> findByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询活跃会话列表（24小时内有消息）
     *
     * @param userId 用户ID
     * @param activeTimeThreshold 活跃时间阈值（24小时前的时间点）
     * @return 活跃会话持久化对象列表
     */
    List<ConversationPO> findActiveConversationsByUserId(
            @Param("userId") String userId,
            @Param("activeTimeThreshold") Date activeTimeThreshold);

    /**
     * 根据用户ID查询闲置会话列表（超过指定天数没有消息）
     *
     * @param userId 用户ID
     * @param idleTimeThreshold 闲置时间阈值（指定天数前的时间点）
     * @return 闲置会话持久化对象列表
     */
    List<ConversationPO> findIdleConversationsByUserId(
            @Param("userId") String userId,
            @Param("idleTimeThreshold") Date idleTimeThreshold);

    /**
     * 根据会话ID（业务唯一）删除会话
     *
     * @param conversationId 会话ID（业务唯一）
     * @return 删除的行数
     */
    int deleteByConversationId(@Param("conversationId") String conversationId);
}
