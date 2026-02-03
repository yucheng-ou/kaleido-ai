package com.xiaoo.kaleido.ai.trigger.convertor;

import com.xiaoo.kaleido.ai.domain.clothing.model.ClothingVector;
import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 服装事件转换器
 * 负责将 ClothingEventMessage 转换为 ClothingVector
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Component
public class ClothingEventConvertor {

    /**
     * 将 ClothingEventMessage 转换为 ClothingVector
     *
     * @param clothingEventMessage 服装事件消息
     * @return 服装向量领域模型
     */
    public ClothingVector toDomain(ClothingEventMessage clothingEventMessage) {
        if (clothingEventMessage == null) {
            return null;
        }
        
        return ClothingVector.builder()
                .clothingId(clothingEventMessage.getClothingId())
                .userId(clothingEventMessage.getUserId())
                .name(clothingEventMessage.getName())
                .typeName(clothingEventMessage.getTypeName())
                .colorName(clothingEventMessage.getColorName())
                .seasonName(clothingEventMessage.getSeasonName())
                .brandName(clothingEventMessage.getBrandName())
                .size(clothingEventMessage.getSize())
                .purchaseDate(dateToLocalDateTime(clothingEventMessage.getPurchaseDate()))
                .price(clothingEventMessage.getPrice())
                .description(clothingEventMessage.getDescription())
                .currentLocationName(clothingEventMessage.getCurrentLocationName())
                .wearCount(0) // 新创建的衣服穿着次数为0
                .images(null) // 图片列表需要从其他服务获取
                .build();
    }

    /**
     * 将 Date 转换为 LocalDateTime
     */
    private LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
    }
}
