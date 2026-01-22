package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.user.command.AddRoleCommand;
import com.xiaoo.kaleido.api.admin.user.command.UpdateRoleCommand;
import com.xiaoo.kaleido.api.admin.user.command.AssignPermissionsToRoleCommand;

/**
 * 角色命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IRoleCommandService {

    /**
     * 创建角色
     *
     * @param command 添加角色命令
     * @return 角色ID
     */
    String createRole(AddRoleCommand command);

    /**
     * 更新角色信息
     *
     * @param roleId 角色ID
     * @param command 更新角色命令
     */
    void updateRole(String roleId, UpdateRoleCommand command);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(String roleId);

    /**
     * 分配权限给角色
     *
     * @param roleId 角色ID
     * @param command 分配权限命令
     */
    void assignPermissions(String roleId, AssignPermissionsToRoleCommand command);
}
