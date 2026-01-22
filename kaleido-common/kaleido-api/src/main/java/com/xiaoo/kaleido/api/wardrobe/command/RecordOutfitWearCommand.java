package com.xiaoo.kaleido.api.wardrobe.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RecordOutfitWearCommand extends BaseCommand {

    private String outfitId;
    
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
