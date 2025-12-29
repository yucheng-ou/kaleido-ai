package com.xiaoo.kaleido.api.notice.query;

import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 通知模板分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通知模板分页查询请求")
public class NoticeTemplatePageQueryReq extends BasePageReq {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "模板名称（模糊查询）")
    private String name;

    @Schema(description = "模板编码（模糊查询）")
    private String code;

    @Schema(description = "通知类型（SMS-短信，EMAIL-邮件，WECHAT-微信）")
    private String noticeType;
}
