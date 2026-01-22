package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.user.command.RegisterAdminCommand;
import com.xiaoo.kaleido.api.admin.user.command.UpdateAdminCommand;
import com.xiaoo.kaleido.api.admin.user.command.AssignRolesToAdminCommand;

/**
 * 管理员命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IAdminCommandService {

    /**
     * 创建管理员
     *
     * @param command 注册管理员命令
     * @return 管理员ID
     */
    String createAdmin(RegisterAdminCommand command);

    /**
     * 更新管理员信息
     *
     * @param adminId 管理员ID
     * @param command 更新管理员命令
     */
    void updateAdmin(String adminId, UpdateAdminCommand command);

    /**
     * 启用管理员
     *
     * @param adminId 管理员ID
     */
    void enableAdmin(String adminId);

    /**
     * 冻结管理员
     *
     * @param adminId 管理员ID
     */
    void freezeAdmin(String adminId);

    /**
     * 分配角色给管理员
     *
     * @param adminId 管理员ID
     * @param command 分配角色命令
     */
    void assignRoles(String adminId, AssignRolesToAdminCommand command);

    /**
     * 管理员登录
     *
     * @param adminId 管理员ID
     */
    void login(String adminId);
}
