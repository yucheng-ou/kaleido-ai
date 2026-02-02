package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event;

import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.mq.event.BaseEvent;
import com.xiaoo.kaleido.wardrobe.types.constant.ClothingEventTypeEnums;
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
     * 将内部 ClothingMessage 转换为 API 模块的 ClothingEventMessage
     */
    public ClothingEventMessage buildApiMessage(ClothingMessage clothingMessage) {
        ClothingEventMessage.ClothingEventMessageBuilder builder = ClothingEventMessage.builder()
                .eventType(clothingMessage.getEventType().name())
                .userId(clothingMessage.getUserId())
                .clothingId(clothingMessage.getClothingId());
        
        // 只有创建和更新事件才有完整的服装信息
        if (clothingMessage.getEventType() != ClothingEventTypeEnums.DELETE) {
            builder.name(clothingMessage.getName())
                   .typeName(clothingMessage.getTypeName())
                   .colorName(clothingMessage.getColorName())
                   .seasonName(clothingMessage.getSeasonName())
                   .brandName(clothingMessage.getBrandName())
                   .size(clothingMessage.getSize())
                   .purchaseDate(clothingMessage.getPurchaseDate())
                   .price(clothingMessage.getPrice())
                   .description(clothingMessage.getDescription());
        }
        
        return builder.build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClothingMessage {
        /**
         * 事件类型
         */
        private ClothingEventTypeEnums eventType;
        
        /**
         * 用户ID
         */
        private String userId;
        
        /**
         * 服装ID
         */
        private String clothingId;
        
        /**
         * 服装名称
         */
        private String name;
        
        /**
         * 服装类型名称
         */
        private String typeName;
        
        /**
         * 颜色名称
         */
        private String colorName;
        
        /**
         * 季节名称
         */
        private String seasonName;
        
        /**
         * 品牌名称
         */
        private String brandName;
        
        /**
         * 尺码
         */
        private String size;
        
        /**
         * 购买日期
         */
        private Date purchaseDate;
        
        /**
         * 价格
         */
        private BigDecimal price;
        
        /**
         * 描述
         */
        private String description;
    }
}
