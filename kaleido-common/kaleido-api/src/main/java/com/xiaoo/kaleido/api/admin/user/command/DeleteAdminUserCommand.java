package com.xiaoo.kaleido.api.admin.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 鍒犻櫎绠＄悊鍛樺懡浠?
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAdminUserCommand extends BaseCommand {
    
    /**
     * 管理员ID
     */
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
}
