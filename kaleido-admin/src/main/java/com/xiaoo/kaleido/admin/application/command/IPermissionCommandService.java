package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.user.command.AddPermissionCommand;
import com.xiaoo.kaleido.api.admin.user.command.UpdatePermissionCommand;

/**
 * 权限命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IPermissionCommandService {

    /**
     * 创建权限
     *
     * @param command 添加权限命令
     * @return 权限ID
     */
    String createPermission(AddPermissionCommand command);

    /**
     * 更新权限信息
     *
     * @param permissionId 权限ID
     * @param command 更新权限命令
     */
    void updatePermission(String permissionId, UpdatePermissionCommand command);

    /**
     * 更新权限编码
     *
     * @param permissionId 权限ID
     * @param code 新的权限编码
     */
    void updatePermissionCode(String permissionId, String code);

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     */
    void deletePermission(String permissionId);

    /**
     * 检查权限编码是否可用
     *
     * @param code 权限编码
     * @return 是否可用
     */
    boolean isCodeAvailable(String code);

    /**
     * 检查权限是否有效
     *
     * @param permissionId 权限ID
     * @return 是否有效
     */
    boolean isValidPermission(String permissionId);
}
