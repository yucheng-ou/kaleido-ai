package com.xiaoo.kaleido.user.command;

import lombok.Data;

/**
 * 添加用户命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class AddUserCommand {

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 密码
     */
    private String password;

    /**
     * 邀请码（可选）
     */
    private String inviterCode;

    /**
     * 昵称（可选，如果不提供则使用默认昵称）
     */
    private String nickName;
}
