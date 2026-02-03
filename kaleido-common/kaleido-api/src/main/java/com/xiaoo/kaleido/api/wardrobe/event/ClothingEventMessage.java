package com.xiaoo.kaleido.api.wardrobe.event;

import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服装事件消息
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingEventMessage {
    
    /**
     * 事件类型
     * 取值：CREATE, UPDATE, DELETE
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
     * 通过字典类型CLOTHING_TYPE的dictCode查询得到dictName
     */
    private String typeName;
    
    /**
     * 颜色名称
     * 通过字典类型COLOR的dictCode查询得到dictName
     */
    private String colorName;
    
    /**
     * 季节名称
     * 通过字典类型SEASON的dictCode查询得到dictName
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
    
    /**
     * 当前位置名称
     */
    private String currentLocationName;
}
