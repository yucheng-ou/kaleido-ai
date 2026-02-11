package com.xiaoo.kaleido.message.application.query.impl;

import com.xiaoo.kaleido.api.message.response.MqMessageResponse;
import com.xiaoo.kaleido.message.application.convertor.MessageConvertor;
import com.xiaoo.kaleido.message.application.query.IMqMessageQueryService;
import com.xiaoo.kaleido.message.domain.mq.adapter.repository.IMqMessageRepository;
import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MQ消息查询服务实现
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqMessageQueryServiceImpl implements IMqMessageQueryService {

    private final IMqMessageRepository mqMessageRepository;

    @Override
    public MqMessageResponse getMessage(String messageId) {
        log.info("查询消息详情, messageId: {}", messageId);
        MqMessageAggregate aggregate = mqMessageRepository.findByIdOrThrow(messageId);
        return MessageConvertor.INSTANCE.toResponse(aggregate);
    }

    @Override
    public List<MqMessageResponse> listMessagesByUserId(String userId) {
        log.info("查询用户消息列表, userId: {}", userId);
        List<MqMessageAggregate> aggregates = mqMessageRepository.findByUserId(userId);
        return MessageConvertor.INSTANCE.toResponseList(aggregates);
    }
}
