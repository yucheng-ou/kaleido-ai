package com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
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
    @Mapping(source = "target", target = "targetType", qualifiedByName = "targetToTargetType")
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
    @Mapping(source = "po", target = "target", qualifiedByName = "addressAndTypeToTarget")
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

    @Named("targetToTargetType")
    default TargetTypeEnum targetToTargetType(TargetAddress target) {
        return target != null ? target.getTargetType() : null;
    }

    @Named("addressAndTypeToTarget")
    default TargetAddress addressAndTypeToTarget(NoticePO po) {
        if (po == null || po.getTargetAddress() == null) {
            return null;
        }
        // 从PO中获取通知类型和目标类型
        NoticeTypeEnum noticeType = po.getNoticeType();
        TargetTypeEnum targetType = po.getTargetType();
        
        if (noticeType == null) {
            // 如果没有通知类型，假设是手机号（SMS类型）
            noticeType = NoticeTypeEnum.SMS;
        }
        
        if (targetType == null) {
            // 如果没有目标类型，默认为普通用户
            targetType = TargetTypeEnum.USER;
        }
        
        return TargetAddress.create(po.getTargetAddress(), noticeType, targetType);
    }

    @Named("addressToTarget")
    default TargetAddress addressToTarget(String address) {
        if (address == null) {
            return null;
        }
        // 这里需要根据实际情况解析地址类型
        // 暂时简单处理，假设是手机号（SMS类型）
        return TargetAddress.create(address, NoticeTypeEnum.SMS);
    }
}
