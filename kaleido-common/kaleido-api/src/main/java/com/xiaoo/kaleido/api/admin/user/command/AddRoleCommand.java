package com.xiaoo.kaleido.api.admin.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 娣诲姞瑙掕壊鍛戒护
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleCommand extends BaseCommand {
    /**
     * 瑙掕壊缂栫爜
     */
    @NotBlank(message = "角色编码不能为空")
    @Size(min = 2, max = 50, message = "角色编码长度必须在2-50位之间")
    @Pattern(regexp = "^[a-zA-Z0-9:_-]+$", message = "角色编码只能包含字母、数字、冒号、下划线和短横线")
    private String code;
    
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 2, max = 50, message = "角色名称长度必须在2-50位之间")
    private String name;
    
    /**
     * 角色描述
     */
    @Size(max = 200, message = "角色描述长度不能超过200位")
    private String description;
    
    /**
     * 是否系统角色
     */
    private Boolean isSystem;
}
