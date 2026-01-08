package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 冻结管理员命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezeAdminUserCommand extends BaseCommand {
    
    /**
     * 管理员ID
     */
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
}
