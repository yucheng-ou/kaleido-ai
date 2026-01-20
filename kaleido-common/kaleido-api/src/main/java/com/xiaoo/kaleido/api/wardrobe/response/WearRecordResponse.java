package com.xiaoo.kaleido.api.wardrobe.response;

import lombok.*;

import java.util.Date;

/**
 * 穿着记录响应
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WearRecordResponse {

    /**
     * 记录ID
     */
    private String recordId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 穿搭ID
     */
    private String outfitId;

    /**
     * 穿着日期
     */
    private Date wearDate;

    /**
     * 备注
     */
    private String notes;


}
