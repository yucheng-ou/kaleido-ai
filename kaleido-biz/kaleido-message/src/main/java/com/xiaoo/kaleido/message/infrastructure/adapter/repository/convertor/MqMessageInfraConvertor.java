package com.xiaoo.kaleido.message.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import com.xiaoo.kaleido.message.domain.mq.model.vo.MessageState;
import com.xiaoo.kaleido.message.infrastructure.dao.po.MqMessagePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * MQ消息基础设施层转换器
 * <p>
 * 负责MqMessageAggregate和MqMessagePO之间的转换
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Mapper
public interface MqMessageInfraConvertor {

    MqMessageInfraConvertor INSTANCE = Mappers.getMapper(MqMessageInfraConvertor.class);

    /**
     * MqMessageAggregate转换为MqMessagePO
     *
     * @param aggregate MQ消息聚合根
     * @return MQ消息持久化对象
     */
    @Mapping(target = "state", source = "state", qualifiedByName = "enumToString")
    MqMessagePO toPO(MqMessageAggregate aggregate);

    /**
     * MqMessagePO转换为MqMessageAggregate
     *
     * @param po MQ消息持久化对象
     * @return MQ消息聚合根
     */
    @Mapping(target = "state", source = "state", qualifiedByName = "stringToEnum")
    MqMessageAggregate toAggregate(MqMessagePO po);

    @Named("enumToString")
    default String enumToString(MessageState state) {
        return state != null ? state.getCode() : null;
    }

    @Named("stringToEnum")
    default MessageState stringToEnum(String state) {
        return state != null ? MessageState.fromCode(state) : null;
    }
}
