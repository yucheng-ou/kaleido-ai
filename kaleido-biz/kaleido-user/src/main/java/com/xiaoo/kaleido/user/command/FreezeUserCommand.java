package com.xiaoo.kaleido.user.command;

import lombok.Data;

/**
 * 冻结用户命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class FreezeUserCommand {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作者ID（用于权限验证）
     */
    private String operatorId;

    /**
     * 冻结原因
     */
    private String reason;
}
