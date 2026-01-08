package com.xiaoo.kaleido.api.notice.response;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * 閫氱煡妯℃澘鍝嶅簲瀵硅薄
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeTemplateResponse {
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
     * 模板状态
     */
    private DataStatusEnum status;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
}
