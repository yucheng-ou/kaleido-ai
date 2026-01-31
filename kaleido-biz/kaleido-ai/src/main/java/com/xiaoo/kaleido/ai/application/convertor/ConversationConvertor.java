package com.xiaoo.kaleido.ai.application.convertor;

import com.xiaoo.kaleido.api.ai.response.ConversationInfoResponse;
import com.xiaoo.kaleido.ai.domain.model.aggregate.ConversationAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 会话转换器
 * <p>
 * 会话应用层转换器，负责会话领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper(componentModel = "spring")
public interface ConversationConvertor {

    ConversationConvertor INSTANCE = Mappers.getMapper(ConversationConvertor.class);

    /**
     * 将会话聚合根转换为会话信息响应
     * <p>
     * 将领域层的会话聚合根转换为应用层的会话信息响应DTO
     *
     * @param conversationAggregate 会话聚合根，不能为空
     * @return 会话信息响应，包含会话的基本信息和状态
     */
    @Mapping(source = "id", target = "conversationId")
    ConversationInfoResponse toResponse(ConversationAggregate conversationAggregate);

    /**
     * 将会话聚合根列表转换为会话信息响应列表
     *
     * @param conversationAggregates 会话聚合根列表，不能为空
     * @return 会话信息响应列表
     */
    List<ConversationInfoResponse> toResponses(List<ConversationAggregate> conversationAggregates);
}
