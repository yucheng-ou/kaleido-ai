package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 更新服装信息命令
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClothingCommand {
    
    /**
     * 服装ID
     */
    @NotBlank(message = "服装ID不能为空")
    private String clothingId;
    
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    
    /**
     * 服装名称
     */
    @NotBlank(message = "服装名称不能为空")
    @Size(max = 100, message = "服装名称长度不能超过100个字符")
    private String name;
    
    /**
     * 服装类型编码
     * 关联字典表t_dict.dict_code，字典类型为CLOTHING_TYPE
     */
    @NotBlank(message = "服装类型编码不能为空")
    private String typeCode;
    
    /**
     * 颜色编码
     * 关联字典表t_dict.dict_code，字典类型为COLOR
     */
    private String colorCode;
    
    /**
     * 季节编码
     * 关联字典表t_dict.dict_code，字典类型为SEASON
     */
    private String seasonCode;
    
    /**
     * 品牌ID
     * 关联品牌表t_wardrobe_brand
     */
    private String brandId;
    
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
    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;
    
    /**
     * 当前位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    private String currentLocationId;
    
    /**
     * 图片信息列表（更新后的完整图片列表）
     */
    private List<ImageInfo> images;
    
    /**
     * 图片信息
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfo {
        /**
         * 图片ID（更新时可能为空，表示新增图片）
         */
        private String imageId;
        
        /**
         * 图片路径（在MinIO中的文件路径）
         */
        @NotBlank(message = "图片路径不能为空")
        private String path;
        
        /**
         * 排序序号
         */
        @NotNull(message = "排序序号不能为空")
        private Integer imageOrder;
        
        /**
         * 是否为主图
         */
        @NotNull(message = "是否为主图不能为空")
        private Boolean isMain;
    }
}
