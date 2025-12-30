package com.xiaoo.kaleido.api.notice.response;

import com.xiaoo.kaleido.api.notice.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 通知响应对象
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通知响应对象")
public class NoticeResponse {
    
    /**
     * 通知ID
     */
    @Schema(description = "通知ID")
    private String id;
    
    /**
     * 通知类型
     */
    @Schema(description = "通知类型")
    private NoticeTypeEnum noticeType;
    
    /**
     * 目标地址
     */
    @Schema(description = "目标地址")
    private String targetAddress;
    
    /**
     * 通知状态
     */
    @Schema(description = "通知状态")
    private NoticeStatusEnum status;
    
    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private BusinessTypeEnum businessType;
    
    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    private String content;
    
    /**
     * 发送结果信息
     */
    @Schema(description = "发送结果信息")
    private String resultMessage;
    
    /**
     * 发送时间
     */
    @Schema(description = "发送时间")
    private Date sentAt;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updatedAt;
}
