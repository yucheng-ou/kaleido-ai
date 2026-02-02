package com.xiaoo.kaleido.ai.trigger.convertor;

import com.xiaoo.kaleido.ai.trigger.dto.ClothingDocumentDto;
import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 服装事件转换器
 * 负责将 ClothingEventMessage 转换为 ClothingDocumentDto
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Component
public class ClothingEventConvertor {

    /**
     * 将 IClothingEventMessage 转换为 ClothingDocumentDto
     *
     * @param clothingEventMessage 服装事件消息接口
     * @return 服装文档DTO
     */
    public ClothingDocumentDto toDto(ClothingEventMessage clothingEventMessage) {
        if (clothingEventMessage == null) {
            return null;
        }
        
        ClothingDocumentDto dto = new ClothingDocumentDto();
        dto.setClothingId(clothingEventMessage.getClothingId());
        dto.setUserId(clothingEventMessage.getUserId());
        dto.setName(clothingEventMessage.getName());
        dto.setTypeName(clothingEventMessage.getTypeName());
        dto.setColorName(clothingEventMessage.getColorName());
        dto.setSeasonName(clothingEventMessage.getSeasonName());
        dto.setBrandName(clothingEventMessage.getBrandName());
        dto.setSize(clothingEventMessage.getSize());
        dto.setPurchaseDate(dateToLocalDateTime(clothingEventMessage.getPurchaseDate()));
        dto.setPrice(clothingEventMessage.getPrice());
        dto.setDescription(clothingEventMessage.getDescription());
        
        // 设置默认值
        dto.setBrandId(null); // 品牌ID不再需要
        dto.setCurrentLocationName(null); // 位置名称需要从其他服务获取
        dto.setWearCount(0); // 新创建的衣服穿着次数为0
        dto.setLastWornDate(null); // 新创建的衣服没有最后穿着日期
        dto.setImages(null); // 图片列表需要从其他服务获取
        
        return dto;
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
