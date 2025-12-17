package com.xiaoo.kaleido.user.domain.constant;

/**
 * 用户操作类型枚举（领域层）
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public enum UserOperateType {

    /**
     * 登录
     */
    LOGIN,

    /**
     * 注册
     */
    REGISTER,

    /**
     * 修改信息
     */
    MODIFY,

    /**
     * 冻结
     */
    FREEZE,

    /**
     * 解冻
     */
    UNFREEZE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 更新头像
     */
    UPDATE_AVATAR,

    /**
     * 修改昵称
     */
    CHANGE_NICKNAME,

    /**
     * 创建用户
     */
    CREATE,

    /**
     * 更新状态
     */
    UPDATE_STATUS;

    /**
     * 获取操作类型描述
     */
    public String getDescription() {
        return switch (this) {
            case LOGIN -> "用户登录";
            case REGISTER -> "用户注册";
            case MODIFY -> "修改信息";
            case FREEZE -> "冻结用户";
            case UNFREEZE -> "解冻用户";
            case DELETE -> "删除用户";
            case UPDATE_AVATAR -> "更新头像";
            case CHANGE_NICKNAME -> "修改昵称";
            case CREATE -> "创建用户";
            case UPDATE_STATUS -> "更新状态";
        };
    }
}
