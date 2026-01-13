package com.xiaoo.kaleido.api.notice.query;

import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.*;

import java.io.Serial;

/**
 * 通知列表分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticePageQueryReq extends BasePageReq {

    private String target;
    private String noticeType;
    private String status;
    private String businessType;
}
