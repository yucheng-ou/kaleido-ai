package com.xiaoo.kaleido.admin.application.command;

/**
 * 用户命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IUserCommandService {

    /**
     * 冻结用户
     *
     * @param userId 用户ID
     */
    void freezeUser(String userId);

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     */
    void unfreezeUser(String userId);

    /**
     * 删除用户（软删除）
     *
     * @param userId 用户ID
     */
    void deleteUser(String userId);
}
