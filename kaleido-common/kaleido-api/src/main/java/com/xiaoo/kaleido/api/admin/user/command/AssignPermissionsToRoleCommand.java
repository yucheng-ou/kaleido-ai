package com.xiaoo.kaleido.api.admin.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * 鍒嗛厤鏉冮檺缁欒鑹插懡浠?
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignPermissionsToRoleCommand extends BaseCommand {
    
    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    
    /**
     * 权限ID列表
     */
    @NotNull(message = "权限ID列表不能为空")
    private List<String> permissionIds;
}
