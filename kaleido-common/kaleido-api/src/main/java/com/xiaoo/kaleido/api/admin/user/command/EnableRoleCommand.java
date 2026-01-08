package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 启用角色命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnableRoleCommand extends BaseCommand {
    
    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
}
