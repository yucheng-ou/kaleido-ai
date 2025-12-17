package com.xiaoo.kaleido.user.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 解冻用户命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnfreezeUserCommand {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作者ID（用于权限验证）
     */
    private String operatorId;

    /**
     * 解冻原因
     */
    private String reason;
}
