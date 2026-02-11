package com.xiaoo.kaleido.message.application.query;

import com.xiaoo.kaleido.api.message.response.MqMessageResponse;

import java.util.List;

/**
 * MQ消息查询服务接口
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
public interface IMqMessageQueryService {

    /**
     * 根据ID查询消息
     *
     * @param messageId 消息ID
     * @return 消息详情
     */
    MqMessageResponse getMessage(String messageId);

    /**
     * 查询用户消息列表
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    List<MqMessageResponse> listMessagesByUserId(String userId);
}
