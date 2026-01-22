package com.xiaoo.kaleido.api.wardrobe.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 将衣服添加到位置命令
 * <p>
 * 用于将现有服装添加到指定位置，同时创建位置记录
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddClothingToLocationCommand extends BaseCommand {

    /**
     * 服装ID
     * 关联服装表t_wardrobe_clothing
     */
    @NotBlank(message = "服装ID不能为空")
    private String clothingId;

    /**
     * 位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    @NotBlank(message = "位置ID不能为空")
    private String locationId;

    /**
     * 备注
     * 位置变更的备注信息，可为空
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String notes;
}
