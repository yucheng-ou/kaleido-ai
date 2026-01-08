package com.xiaoo.kaleido.api.admin.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * 鍒嗛厤瑙掕壊缁欑鐞嗗憳鍛戒护
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesToAdminUserCommand extends BaseCommand {
    
    /**
     * 管理员ID
     */
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
    
    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    private List<String> roleIds;
}
