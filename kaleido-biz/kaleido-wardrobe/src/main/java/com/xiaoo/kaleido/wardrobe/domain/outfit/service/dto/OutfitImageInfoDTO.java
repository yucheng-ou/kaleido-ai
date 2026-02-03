package com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import lombok.*;

/**
 * 穿搭图片信息DTO
 * <p>
 * 用于在领域服务中传递穿搭图片数据的传输对象
 * 参考ImageInfoDTO的设计模式
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitImageInfoDTO {
    
    /**
     * 图片路径（在minio中的文件路径）
     */
    private String path;
    
    /**
     * 排序序号
     */
    private Integer imageOrder;
    
    /**
     * 是否为主图
     */
    private Boolean isPrimary;
    
    /**
     * 文件大小（字节）
     */
    private Long imageSize;
    
    /**
     * 文件类型
     */
    private ImageTypeEnums imageTypeEnums;
    
    /**
     * 图片宽度
     */
    private Integer width;
    
    /**
     * 图片高度
     */
    private Integer height;
    
    /**
     * 图片描述
     */
    private String description;
}
