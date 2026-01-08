package com.xiaoo.kaleido.api.notice.query;
import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
/**
 * 閫氱煡鍒嗛〉鏌ヨ璇锋眰
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticePageQueryReq extends BasePageReq {
    @Serial
    private static final long serialVersionUID = 1L;
    private String target;
    private String noticeType;
    private String status;
    private String businessType;
}
