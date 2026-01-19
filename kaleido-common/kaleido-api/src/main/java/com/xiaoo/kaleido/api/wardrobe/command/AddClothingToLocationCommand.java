package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class AddClothingToLocationCommand {

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
     * 用户ID
     * 用于验证权限，确保用户只能操作自己的服装和位置
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 备注
     * 位置变更的备注信息，可为空
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String notes;
}
