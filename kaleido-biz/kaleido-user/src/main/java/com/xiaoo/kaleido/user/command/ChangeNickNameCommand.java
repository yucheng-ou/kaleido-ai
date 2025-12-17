package com.xiaoo.kaleido.user.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改昵称命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNickNameCommand {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作者ID（用于权限验证）
     */
    private String operatorId;

    /**
     * 新昵称
     */
    private String nickName;
}
