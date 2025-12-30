package com.xiaoo.kaleido.api.notice.response;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 通知模板响应对象
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通知模板响应对象")
public class NoticeTemplateResponse {
    
    /**
     * 模板ID
     */
    @Schema(description = "模板ID")
    private String id;
    
    /**
     * 模板编码
     */
    @Schema(description = "模板编码")
    private String code;
    
    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String name;
    
    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    private String content;
    
    /**
     * 模板状态
     */
    @Schema(description = "模板状态")
    private DataStatusEnum status;
    
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
