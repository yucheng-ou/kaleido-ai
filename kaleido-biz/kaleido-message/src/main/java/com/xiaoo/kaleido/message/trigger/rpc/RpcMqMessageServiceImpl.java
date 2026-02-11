package com.xiaoo.kaleido.message.trigger.rpc;

import com.xiaoo.kaleido.api.message.IRpcMqMessageService;
import com.xiaoo.kaleido.api.message.command.CreateMqMessageCommand;
import com.xiaoo.kaleido.api.message.response.MqMessageResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.message.application.command.MqMessageCommandService;
import com.xiaoo.kaleido.message.application.query.IMqMessageQueryService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * MQ消息RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Slf4j
@DubboService(version = RpcConstants.DUBBO_VERSION)
@RequiredArgsConstructor
public class RpcMqMessageServiceImpl implements IRpcMqMessageService {

    private final MqMessageCommandService mqMessageCommandService;
    private final IMqMessageQueryService mqMessageQueryService;

    @Override
    public Result<String> createMessage(String userId, CreateMqMessageCommand command) {
            String messageId = mqMessageCommandService.createMessage(userId, command);
            return Result.success(messageId);
    }

    @Override
    public Result<MqMessageResponse> getMessage(String messageId) {
            MqMessageResponse response = mqMessageQueryService.getMessage(messageId);
            return Result.success(response);
    }

    @Override
    public Result<List<MqMessageResponse>> listMessagesByUserId(String userId) {
            List<MqMessageResponse> responses = mqMessageQueryService.listMessagesByUserId(userId);
            return Result.success(responses);
    }

    @Override
    public Result<Void> markAsCompleted(String messageId) {
            mqMessageCommandService.markAsCompleted(messageId);
            return Result.success();
    }

    @Override
    public Result<Void> markAsFailed(String messageId) {
            mqMessageCommandService.markAsFailed(messageId);
            return Result.success();
    }
}
