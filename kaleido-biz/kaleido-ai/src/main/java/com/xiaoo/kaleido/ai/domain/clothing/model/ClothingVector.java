package com.xiaoo.kaleido.ai.domain.clothing.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服装向量领域模型
 * <p>
 * 表示服装的向量表示，用于向量存储和相似性搜索
 * 包含服装的核心业务属性和领域逻辑
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Data
@Builder
public class ClothingVector {

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
     * 图片列表
     */
    private List<String> images;

    /**
     * 生成向量存储的描述性文本内容
     * <p>
     * 根据服装属性生成用于向量存储的描述性文本
     *
     * @return 描述性文本内容
     */
    public String generateVectorContent() {
        StringBuilder content = new StringBuilder();
        content.append("这是一件").append(name);
        
        if (typeName != null && !typeName.isEmpty()) {
            content.append("，类型为").append(typeName);
        }
        
        if (colorName != null && !colorName.isEmpty()) {
            content.append("，颜色为").append(colorName);
        }
        
        if (seasonName != null && !seasonName.isEmpty()) {
            content.append("，适合").append(seasonName).append("季节");
        }
        
        if (size != null && !size.isEmpty()) {
            content.append("，尺码为").append(size);
        }
        
        if (brandName != null && !brandName.isEmpty()) {
            content.append("，品牌为").append(brandName);
        }
        
        if (currentLocationName != null && !currentLocationName.isEmpty()) {
            content.append("，当前位置在").append(currentLocationName);
        }

        if (description != null && !description.isEmpty()) {
            content.append("。描述：").append(description);
        }
        content.append(" id:").append(clothingId).append("。");
        return content.toString();
    }
}
