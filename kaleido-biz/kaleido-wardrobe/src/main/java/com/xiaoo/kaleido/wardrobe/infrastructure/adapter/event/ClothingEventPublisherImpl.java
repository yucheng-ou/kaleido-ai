package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.event;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.admin.dict.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.mq.event.EventPublisher;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event.ClothingEvent;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event.IClothingEventPublisher;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.types.constant.ClothingDictTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服装事件发布器实现
 * 基础设施层实现，负责发布服装相关事件到消息队列
 *
 * @author ouyucheng
 * @date 2026/2/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClothingEventPublisherImpl implements IClothingEventPublisher {

    private final EventPublisher eventPublisher;
    private final ClothingEvent clothingEvent;
    private final ILocationDomainService locationDomainService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminDictService rpcAdminDictService;

    /**
     * 发布服装事件
     *
     * @param eventType         事件类型
     * @param userId            用户ID
     * @param clothingId        服装ID
     * @param name              服装名称
     * @param typeCode          类型代码
     * @param colorCode         颜色代码
     * @param seasonCode        季节代码
     * @param brandName         品牌名称
     * @param size              尺寸
     * @param purchaseDate      购买日期
     * @param price             价格
     * @param description       描述
     * @param currentLocationId 当前位置ID
     */
    @Override
    public void publishClothingEvent(
            ClothingEventTypeEnums eventType,
            String userId,
            String clothingId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandName,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationId) {

        // 获取字典名称
        String typeName = getDictName(ClothingDictTypeEnum.CLOTHING_TYPE, typeCode);
        String colorName = getDictName(ClothingDictTypeEnum.COLOR, colorCode);
        String seasonName = getDictName(ClothingDictTypeEnum.SEASON, seasonCode);

        // 获取位置名称
        String currentLocationName = locationDomainService.getLocationName(currentLocationId);

        ClothingEventMessage apiMessage = clothingEvent.buildApiMessage(
                eventType,
                userId,
                clothingId,
                name,
                typeName,
                colorName,
                seasonName,
                brandName,
                size,
                purchaseDate,
                price,
                description,
                currentLocationName
        );

        eventPublisher.publish(clothingEvent.topic(), clothingEvent.buildEventMessage(apiMessage));
    }

    /**
     * 发布简单的服装事件（用于删除等简单操作）
     *
     * @param eventType  事件类型
     * @param userId     用户ID
     * @param clothingId 服装ID
     */
    @Override
    public void publishSimpleClothingEvent(
            ClothingEventTypeEnums eventType,
            String userId,
            String clothingId) {

        ClothingEventMessage apiMessage = ClothingEventMessage.builder()
                .eventType(eventType)
                .userId(userId)
                .clothingId(clothingId)
                .build();

        eventPublisher.publish(clothingEvent.topic(), clothingEvent.buildEventMessage(apiMessage));
    }

    /**
     * 获取字典名称
     *
     * @param dictType 字典类型枚举
     * @param dictCode 字典编码
     * @return 字典名称，如果获取失败则返回null
     */
    @Override
    public String getDictName(ClothingDictTypeEnum dictType, String dictCode) {
        if (dictType == null || StrUtil.isBlank(dictCode)) {
            return null;
        }

        try {
            Result<DictResponse> result = rpcAdminDictService.getDictByCode(dictType.getCode(), dictCode);
            if (result != null && Boolean.TRUE.equals(result.getSuccess()) && result.getData() != null) {
                return result.getData().getDictName();
            }
            log.warn("获取字典名称失败，dictType={}, dictCode={}, result={}", dictType, dictCode, result);
        } catch (Exception e) {
            log.error("调用字典服务异常，dictType={}, dictCode={}", dictType, dictCode, e);
        }
        return null;
    }
}
