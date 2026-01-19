package com.xiaoo.kaleido.api.wardrobe.response;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 服装信息响应
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingInfoResponse extends BaseResp {

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
     * 服装类型编码
     */
    private String typeCode;

    /**
     * 颜色编码
     */
    private String colorCode;

    /**
     * 季节编码
     */
    private String seasonCode;

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
     * 当前位置ID
     */
    private String currentLocationId;

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
    private Date lastWornDate;


    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 图片信息列表
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
         * 图片ID
         */
        private String imageId;

        /**
         * 图片路径
         */
        private String path;

        /**
         * 排序序号
         */
        private Integer imageOrder;

        /**
         * 是否为主图
         */
        private Boolean isMain;

        /**
         * 图片宽度
         */
        private Integer width;

        /**
         * 图片高度
         */
        private Integer height;

        /**
         * 文件大小
         */
        private Long fileSize;

        /**
         * 图片类型
         */
        private ImageType imageType;

        /**
         * 图片描述
         */
        private String description;
    }
}
