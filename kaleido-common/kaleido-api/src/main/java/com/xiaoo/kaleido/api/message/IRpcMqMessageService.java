package com.xiaoo.kaleido.api.message;

import com.xiaoo.kaleido.api.message.command.CreateMqMessageCommand;
import com.xiaoo.kaleido.api.message.response.MqMessageResponse;
import com.xiaoo.kaleido.base.result.Result;

import java.util.List;

/**
 * MQ消息RPC服务接口
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
public interface IRpcMqMessageService {

    /**
     * 创建消息
     *
     * @param userId  用户ID
     * @param command 创建命令
     * @return 消息ID
     */
    Result<String> createMessage(String userId, CreateMqMessageCommand command);

    /**
     * 根据ID查询消息
     *
     * @param messageId 消息ID
     * @return 消息详情
     */
    Result<MqMessageResponse> getMessage(String messageId);

    /**
     * 查询用户消息列表
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    Result<List<MqMessageResponse>> listMessagesByUserId(String userId);

    /**
     * 更新消息状态为完成
     *
     * @param messageId 消息ID
     * @return 是否成功
     */
    Result<Void> markAsCompleted(String messageId);

    /**
     * 更新消息状态为失败
     *
     * @param messageId 消息ID
     * @return 是否成功
     */
    Result<Void> markAsFailed(String messageId);
}
