package com.xiaoo.kaleido.wardrobe.application.command;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.admin.dict.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.api.tag.IRpcTagService;
import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.mq.event.EventPublisher;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event.ClothingEvent;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IBrandRepository;
import com.xiaoo.kaleido.wardrobe.types.constant.ClothingEventTypeEnums;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationRecordDomainService;
import com.xiaoo.kaleido.wardrobe.types.constant.ClothingDictTypeEnum;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 命令服务辅助类
 * <p>
 * 提取 ClothingCommandService 中的公共代码，避免重复
 * </p>
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommandServiceHelper {

    private final IBrandRepository brandRepository;
    private final ILocationRecordDomainService locationRecordDomainService;
    private final ILocationRecordRepository locationRecordRepository;
    private final EventPublisher eventPublisher;
    private final ClothingEvent clothingEvent;
    
    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcTagService rpcTagService;
    
    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminDictService rpcAdminDictService;

    /**
     * 查询品牌名称
     *
     * @param brandId 品牌ID
     * @return 品牌名称，如果品牌不存在或brandId为空则返回null
     */
    public String getBrandName(String brandId) {
        if (StrUtil.isNotBlank(brandId)) {
            BrandAggregate brand = brandRepository.findById(brandId);
            if (brand != null) {
                return brand.getName();
            }
        }
        return null;
    }

    /**
     * 获取字典名称
     *
     * @param dictType 字典类型枚举
     * @param dictCode 字典编码
     * @return 字典名称，如果获取失败则返回null
     */
    private String getDictName(ClothingDictTypeEnum dictType, String dictCode) {
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
    
    /**
     * 获取字典名称（兼容旧版本）
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典名称，如果获取失败则返回null
     */
    private String getDictName(String typeCode, String dictCode) {
        if (StrUtil.isBlank(typeCode)) {
            return null;
        }
        ClothingDictTypeEnum dictType = ClothingDictTypeEnum.fromCode(typeCode);
        return getDictName(dictType, dictCode);
    }

    /**
     * 发布服装事件
     *
     * @param eventType     事件类型
     * @param userId        用户ID
     * @param clothingId    服装ID
     * @param name          服装名称
     * @param typeCode      类型代码
     * @param colorCode     颜色代码
     * @param seasonCode    季节代码
     * @param brandName     品牌名称
     * @param size          尺寸
     * @param purchaseDate  购买日期
     * @param price         价格
     * @param description   描述
     */
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
            String description) {

        // 获取字典名称
        String typeName = getDictName(ClothingDictTypeEnum.CLOTHING_TYPE, typeCode);
        String colorName = getDictName(ClothingDictTypeEnum.COLOR, colorCode);
        String seasonName = getDictName(ClothingDictTypeEnum.SEASON, seasonCode);

        ClothingEvent.ClothingMessage internalMessage = ClothingEvent.ClothingMessage.builder()
                .eventType(eventType)
                .userId(userId)
                .clothingId(clothingId)
                .name(name)
                .typeName(typeName)
                .colorName(colorName)
                .seasonName(seasonName)
                .brandName(brandName)
                .size(size)
                .purchaseDate(purchaseDate)
                .price(price)
                .description(description)
                .build();

        com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage apiMessage =
                clothingEvent.buildApiMessage(internalMessage);
        eventPublisher.publish(clothingEvent.topic(), clothingEvent.buildEventMessage(apiMessage));
    }

    /**
     * 发布简单的服装事件（用于删除等简单操作）
     *
     * @param eventType  事件类型
     * @param userId     用户ID
     * @param clothingId 服装ID
     */
    public void publishSimpleClothingEvent(
            ClothingEventTypeEnums eventType,
            String userId,
            String clothingId) {

        ClothingEvent.ClothingMessage internalMessage = ClothingEvent.ClothingMessage.builder()
                .eventType(eventType)
                .userId(userId)
                .clothingId(clothingId)
                .build();

        com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage apiMessage =
                clothingEvent.buildApiMessage(internalMessage);
        eventPublisher.publish(clothingEvent.topic(), clothingEvent.buildEventMessage(apiMessage));
    }

    /**
     * 创建位置记录
     *
     * @param clothingId     服装ID
     * @param locationId     位置ID
     * @param userId         用户ID
     * @return 创建的位置记录
     */
    public LocationRecordAggregate createLocationRecord(String clothingId, String locationId, String userId) {
        if (StrUtil.isNotBlank(locationId)) {
            LocationRecordAggregate locationRecord = locationRecordDomainService.createLocationRecord(
                    clothingId,
                    locationId,
                    userId
            );
            locationRecordRepository.save(locationRecord);

            log.info("服装位置记录创建成功，服装ID: {}, 位置ID: {}, 位置记录ID: {}",
                    clothingId, locationId, locationRecord.getId());
            return locationRecord;
        }
        return null;
    }

    /**
     * 处理位置变更
     *
     * @param clothingId     服装ID
     * @param oldLocationId  旧位置ID
     * @param newLocationId  新位置ID
     * @param userId         用户ID
     * @return 是否创建了新的位置记录
     */
    public boolean handleLocationChange(String clothingId, String oldLocationId, String newLocationId, String userId) {
        boolean locationChanged = checkLocationChanged(oldLocationId, newLocationId);
        if (locationChanged && StrUtil.isNotBlank(newLocationId)) {
            // 将旧位置记录标记为非当前
            locationRecordRepository.markAllAsNotCurrentByClothingId(clothingId);

            // 创建新的位置记录
            createLocationRecord(clothingId, newLocationId, userId);

            log.info("服装位置变更记录创建成功，服装ID: {}, 旧位置ID: {}, 新位置ID: {}",
                    clothingId, oldLocationId, newLocationId);
            return true;
        }
        return false;
    }

    /**
     * 检查位置是否发生变化
     *
     * @param oldLocationId 旧位置ID
     * @param newLocationId 新位置ID
     * @return 如果位置发生变化返回true，否则返回false
     */
    public boolean checkLocationChanged(String oldLocationId, String newLocationId) {
        // 如果旧位置ID和新位置ID都为null，表示没有变化
        if (oldLocationId == null && newLocationId == null) {
            return false;
        }

        // 如果旧位置ID为null，新位置ID不为null，表示从无位置变为有位置
        if (oldLocationId == null) {
            return true;
        }

        // 如果旧位置ID不为null，新位置ID为null，表示从有位置变为无位置
        if (newLocationId == null) {
            return true;
        }

        // 如果都不为null，比较是否相等
        return !oldLocationId.equals(newLocationId);
    }

    /**
     * 验证服装所有权
     *
     * @param clothing 服装聚合根
     * @param userId   用户ID
     */
    public void validateClothingOwnership(ClothingAggregate clothing, String userId) {
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH, "只有服装所有者可以操作");
        }
    }

    /**
     * 关联标签到实体
     *
     * @param userId          用户ID
     * @param entityId        实体ID
     * @param tagId           标签ID
     * @param entityTypeCode  实体类型代码
     */
    public void associateTagToEntity(String userId, String entityId, String tagId, String entityTypeCode) {
        AssociateEntityCommand command = AssociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(entityId)
                .entityTypeCode(entityTypeCode)
                .build();

        Result<Void> result = rpcTagService.associateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_ASSOCIATION_FAILED, "标签关联失败: " + result.getMsg());
        }

        log.info("标签关联成功，用户ID: {}, 实体ID: {}, 标签ID: {}, 实体类型: {}", 
                userId, entityId, tagId, entityTypeCode);
    }

    /**
     * 从实体取消关联标签
     *
     * @param userId   用户ID
     * @param entityId 实体ID
     * @param tagId    标签ID
     */
    public void dissociateTagFromEntity(String userId, String entityId, String tagId) {
        DissociateEntityCommand command = DissociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(entityId)
                .build();

        Result<Void> result = rpcTagService.dissociateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_DISSOCIATION_FAILED, "标签取消关联失败: " + result.getMsg());
        }

        log.info("标签取消关联成功，用户ID: {}, 实体ID: {}, 标签ID: {}", userId, entityId, tagId);
    }
}
