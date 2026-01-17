package com.xiaoo.kaleido.api.notice.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;

/**
 * 通知模板响应对象
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeTemplateResponse extends BaseResp {
    /**
     * 模板ID
     */
    private String id;
    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
}
