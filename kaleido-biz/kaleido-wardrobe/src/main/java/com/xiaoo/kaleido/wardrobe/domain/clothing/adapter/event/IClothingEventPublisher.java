package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event;

import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import com.xiaoo.kaleido.wardrobe.types.constant.ClothingDictTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服装事件发布器接口
 * <p>
 * 负责发布服装相关事件到消息队列，供其他服务订阅处理
 * 例如：AI服务需要监听服装创建事件来生成向量
 *
 * @author ouyucheng
 * @date 2026/2/3
 */
public interface IClothingEventPublisher {

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
    void publishClothingEvent(
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
            String currentLocationId);

    /**
     * 发布简单的服装事件（用于删除等简单操作）
     *
     * @param eventType  事件类型
     * @param userId     用户ID
     * @param clothingId 服装ID
     */
    void publishSimpleClothingEvent(
            ClothingEventTypeEnums eventType,
            String userId,
            String clothingId);

    /**
     * 获取字典名称
     *
     * @param dictType 字典类型枚举
     * @param dictCode 字典编码
     * @return 字典名称，如果获取失败则返回null
     */
    String getDictName(ClothingDictTypeEnum dictType, String dictCode);
}
