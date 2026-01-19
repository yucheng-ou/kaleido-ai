package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 更新品牌命令
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandCommand {
    
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
