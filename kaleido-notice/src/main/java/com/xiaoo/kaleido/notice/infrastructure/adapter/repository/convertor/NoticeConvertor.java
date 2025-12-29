package com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.valobj.TargetAddress;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * NoticeAggregate 与 NoticePO 转换器
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Mapper
public interface NoticeConvertor {

    NoticeConvertor INSTANCE = Mappers.getMapper(NoticeConvertor.class);

    /**
     * 将 NoticeAggregate 转换为 NoticePO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "noticeType", target = "noticeType")
    @Mapping(source = "target", target = "targetAddress", qualifiedByName = "targetToAddress")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "businessType", target = "businessType")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "resultMessage", target = "resultMessage")
    @Mapping(source = "sentAt", target = "sentAt")
    @Mapping(source = "retryStatus.retryNum", target = "retryNum")
    @Mapping(source = "retryStatus.nextRetryAt", target = "nextRetryAt")
    @Mapping(target = "maxRetryNum", constant = "3") // 默认最大重试次数为3
    NoticePO toPO(NoticeAggregate aggregate);

    /**
     * 将 NoticePO 转换为 NoticeAggregate
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "noticeType", target = "noticeType")
    @Mapping(source = "targetAddress", target = "target", qualifiedByName = "addressToTarget")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "businessType", target = "businessType")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "resultMessage", target = "resultMessage")
    @Mapping(source = "sentAt", target = "sentAt")
    @Mapping(source = "retryNum", target = "retryStatus.retryNum")
    @Mapping(source = "nextRetryAt", target = "retryStatus.nextRetryAt")
    @Mapping(target = "retryStatus", ignore = true) // maxRetryNum在RetryStatus中没有对应字段
    NoticeAggregate toAggregate(NoticePO po);

    @Named("targetToAddress")
    default String targetToAddress(TargetAddress target) {
        return target != null ? target.getFormattedAddress() : null;
    }

    @Named("addressToTarget")
    default TargetAddress addressToTarget(String address) {
        if (address == null) {
            return null;
        }
        // 这里需要根据实际情况解析地址类型
        // 暂时简单处理，假设是手机号（SMS类型）
        return TargetAddress.create(address, com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum.SMS);
    }
}
