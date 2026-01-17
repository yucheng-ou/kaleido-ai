package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 创建品牌命令
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandCommand {
    
    /**
     * 品牌名称
     */
    @NotBlank(message = "品牌名称不能为空")
    @Size(max = 100, message = "品牌名称长度不能超过100个字符")
    private String name;
    
    /**
     * Logo路径（在MinIO中的文件路径）
     */
    @Size(max = 500, message = "Logo路径长度不能超过500个字符")
    private String logoPath;
    
    /**
     * 品牌描述
     */
    @Size(max = 500, message = "品牌描述长度不能超过500个字符")
    private String description;
}
