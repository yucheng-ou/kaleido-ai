package com.xiaoo.kaleido.api.wardrobe.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;

/**
 * 品牌信息响应
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandInfoResponse extends BaseResp {

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * Logo路径（在MinIO中的文件路径）
     */
    private String logoPath;

    /**
     * 品牌描述
     */
    private String description;




}
