package com.xiaoo.kaleido.user.command;

import lombok.Data;

/**
 * 更新用户信息命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class UpdateUserInfoCommand {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作者ID（必须与用户ID相同，表示用户只能修改自己的信息）
     */
    private String operatorId;

    /**
     * 昵称（可选）
     */
    private String nickName;

    /**
     * 头像URL（可选）
     */
    private String avatarUrl;

    /**
     * 其他扩展信息（JSON格式，可选）
     */
    private String extraInfo;
}
