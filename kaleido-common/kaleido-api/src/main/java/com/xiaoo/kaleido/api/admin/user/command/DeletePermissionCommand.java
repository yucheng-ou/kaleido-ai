package com.xiaoo.kaleido.api.admin.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 鍒犻櫎鏉冮檺鍛戒护
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePermissionCommand extends BaseCommand {
    
    /**
     * 权限ID
     */
    @NotBlank(message = "权限ID不能为空")
    private String permissionId;
}
