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
        switch (this) {
            case LOGIN:
                return "用户登录";
            case REGISTER:
                return "用户注册";
            case MODIFY:
                return "修改信息";
            case FREEZE:
                return "冻结用户";
            case UNFREEZE:
                return "解冻用户";
            case DELETE:
                return "删除用户";
            case UPDATE_AVATAR:
                return "更新头像";
            case CHANGE_NICKNAME:
                return "修改昵称";
            case CREATE:
                return "创建用户";
            case UPDATE_STATUS:
                return "更新状态";
            default:
                return "未知操作";
        }
    }

    /**
     * 根据API层枚举转换
     */
    public static UserOperateType fromApiEnum(com.xiaoo.kaleido.api.user.constant.UserOperateTypeEnum apiEnum) {
        if (apiEnum == null) {
            return null;
        }
        switch (apiEnum) {
            case LOGIN:
                return LOGIN;
            case REGISTER:
                return REGISTER;
            case MODIFY:
                return MODIFY;
            case FREEZE:
                return FREEZE;
            case UNFREEZE:
                return UNFREEZE;
            default:
                throw new IllegalArgumentException("未知的操作类型: " + apiEnum);
        }
    }
}
