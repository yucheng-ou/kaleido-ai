package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event;

import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.mq.event.BaseEvent;
import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服装事件
 * 用于在服装创建、更新、删除后通知其他服务进行相关操作
 * 
 * @author ouyucheng
 * @date 2026/1/31
 */
@Component
public class ClothingEvent extends BaseEvent<ClothingEventMessage> {

    @Value("${spring.rabbitmq.topic.clothing-event}")
    private String topic;

    @Override
    public EventMessage<ClothingEventMessage> buildEventMessage(ClothingEventMessage data) {
        return EventMessage.<ClothingEventMessage>builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    /**
     * 构建服装事件消息
     * 直接构建 ClothingEventMessage，避免中间转换
     */
    public ClothingEventMessage buildApiMessage(
            ClothingEventTypeEnums eventType,
            String userId,
            String clothingId,
            String name,
            String typeName,
            String colorName,
            String seasonName,
            String brandName,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationName) {
        
        ClothingEventMessage.ClothingEventMessageBuilder builder = ClothingEventMessage.builder()
                .eventType(eventType)
                .userId(userId)
                .clothingId(clothingId);
        
        // 只有创建和更新事件才有完整的服装信息
        if (eventType != ClothingEventTypeEnums.DELETE) {
            builder.name(name)
                   .typeName(typeName)
                   .colorName(colorName)
                   .seasonName(seasonName)
                   .brandName(brandName)
                   .size(size)
                   .purchaseDate(purchaseDate)
                   .price(price)
                   .description(description)
                   .currentLocationName(currentLocationName);
        }
        
        return builder.build();
    }
    
}
