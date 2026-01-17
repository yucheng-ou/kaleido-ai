package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

/**
 * 记录穿搭穿着命令
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordOutfitWearCommand {
    
    /**
     * 穿搭ID
     */
    @NotBlank(message = "穿搭ID不能为空")
    private String outfitId;
    
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    
    /**
     * 穿着日期（可选，默认为当前日期）
     */
    private Date wearDate;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String notes;
}
