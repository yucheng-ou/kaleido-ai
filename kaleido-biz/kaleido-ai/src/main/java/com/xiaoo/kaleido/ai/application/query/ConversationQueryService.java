package com.xiaoo.kaleido.ai.application.query;

import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;

import java.util.List;

/**
 * 会话查询服务接口
 * <p>
 * 会话应用层查询服务，负责会话相关的读操作，包括会话信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface ConversationQueryService {

    /**
     * 根据ID查询会话信息
     * <p>
     * 根据会话ID查询会话详细信息，如果会话不存在则返回null
     *
     * @param conversationId 会话ID，不能为空
     * @return 会话信息响应，如果会话不存在则返回null
     */
    ConversationInfoResponse findById(String conversationId);

    /**
     * 根据用户ID查询会话列表
     * <p>
     * 查询指定用户的所有会话
     *
     * @param userId 用户ID，不能为空
     * @return 会话信息响应列表，如果不存在则返回空列表
     */
    List<ConversationInfoResponse> findByUserId(String userId);
}
