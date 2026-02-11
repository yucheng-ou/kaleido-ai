package com.xiaoo.kaleido.message.application.convertor;

import com.xiaoo.kaleido.api.message.response.MqMessageResponse;
import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 消息应用层转换器
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Mapper
public interface MessageConvertor {

    MessageConvertor INSTANCE = Mappers.getMapper(MessageConvertor.class);

    /**
     * 聚合根转换为响应对象
     *
     * @param aggregate 消息聚合根
     * @return 消息响应对象
     */
    @Mapping(target = "state", expression = "java(aggregate.getState() != null ? aggregate.getState().getCode() : null)")
    MqMessageResponse toResponse(MqMessageAggregate aggregate);

    /**
     * 聚合根列表转换为响应对象列表
     *
     * @param aggregates 消息聚合根列表
     * @return 消息响应对象列表
     */
    List<MqMessageResponse> toResponseList(List<MqMessageAggregate> aggregates);
}
