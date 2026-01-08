package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新角色命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleCommand extends BaseCommand {
    
    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    
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
}
