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
@Mapper
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

}
