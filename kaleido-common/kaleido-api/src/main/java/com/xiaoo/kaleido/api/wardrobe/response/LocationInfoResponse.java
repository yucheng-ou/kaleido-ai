package com.xiaoo.kaleido.api.wardrobe.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 位置信息响应
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationInfoResponse extends BaseResp {

    /**
     * 位置ID
     */
    private String locationId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 位置名称
     */
    private String name;

    /**
     * 位置描述
     */
    private String description;

    /**
     * 具体地址
     */
    private String address;

    /**
     * 主图ID
     */
    private String primaryImageId;


    /**
     * 图片列表
     */
    private List<LocationImageResponse> images;
}
