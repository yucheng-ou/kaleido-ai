package com.xiaoo.kaleido.api.wardrobe.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * 更新穿搭信息命令
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOutfitCommand extends BaseCommand {
    
    /**
     * 穿搭ID
     */
    @NotBlank(message = "穿搭ID不能为空")
    private String outfitId;
    
    /**
     * 穿搭名称
     */
    @NotBlank(message = "穿搭名称不能为空")
    @Size(max = 100, message = "穿搭名称长度不能超过100个字符")
    private String name;
    
    /**
     * 穿搭描述
     */
    @Size(max = 500, message = "穿搭描述长度不能超过500个字符")
    private String description;
    
    /**
     * 服装ID列表
     * 至少包含1件服装，最多20件
     */
    @NotNull(message = "服装列表不能为空")
    @Size(min = 1, max = 20, message = "服装列表至少包含1件服装，最多20件")
    private List<@NotBlank(message = "服装ID不能为空") String> clothingIds;
    
    /**
     * 图片信息列表（更新后的完整图片列表）
     */
    @NotNull(message = "图片列表不能为空")
    @Size(min = 1, max = 10, message = "图片列表至少包含1张图片，最多10张")
    private List<OutfitImageInfoCommand> images;

}
