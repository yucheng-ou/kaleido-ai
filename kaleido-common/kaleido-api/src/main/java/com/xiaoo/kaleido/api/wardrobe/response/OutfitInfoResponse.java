package com.xiaoo.kaleido.api.wardrobe.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 穿搭信息响应
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitInfoResponse extends BaseResp {

    /**
     * 穿搭ID
     */
    private String outfitId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 穿搭名称
     */
    private String name;

    /**
     * 穿搭描述
     */
    private String description;

    /**
     * 穿着次数
     */
    private Integer wearCount;

    /**
     * 最后穿着日期
     */
    private Date lastWornDate;





    /**
     * 服装信息列表
     */
    private List<ClothingInfo> clothings;

    /**
     * 图片信息列表
     */
    private List<OutfitImageResponse> images;

    /**
     * 穿着记录列表
     */
    private List<WearRecordResponse> wearRecords;

    /**
     * 服装信息（简化版，用于穿搭详情中的服装列表）
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClothingInfo {
        /**
         * 服装ID
         */
        private String clothingId;

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
         * 品牌名称
         */
        private String brandName;

        /**
         * 主图路径
         */
        private String primaryImagePath;
    }
}
