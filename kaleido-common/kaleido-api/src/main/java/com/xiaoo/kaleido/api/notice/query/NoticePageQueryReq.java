package com.xiaoo.kaleido.api.notice.query;

import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 通知分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通知分页查询请求")
public class NoticePageQueryReq extends BasePageReq {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "目标地址（模糊查询）")
    private String target;

    @Schema(description = "通知类型（SMS-短信，EMAIL-邮件，WECHAT-微信）")
    private String noticeType;

    @Schema(description = "通知状态（PENDING-待发送，SUCCESS-发送成功，FAILED-发送失败）")
    private String status;

    @Schema(description = "业务类型")
    private String businessType;
}
