package com.xiaoo.kaleido.ai.trigger.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服装文档DTO，用于接收保存到Milvus的衣服信息
 */
@Data
public class ClothingDocumentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 服装ID
     */
    private String clothingId;
    
    /**
     * 用户ID
     */
    private String userId;
    
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
     * 品牌ID
     */
    private String brandId;
    
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
    private LocalDateTime purchaseDate;
    
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
    
    /**
     * 穿着次数
     */
    private Integer wearCount;
    
    /**
     * 最后穿着日期
     */
    private LocalDateTime lastWornDate;
    
    /**
     * 图片列表
     */
    private List<String> images;
}
