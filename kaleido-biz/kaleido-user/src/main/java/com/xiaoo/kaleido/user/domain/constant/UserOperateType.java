package com.xiaoo.kaleido.user.domain.constant;

/**
 * 用户操作类型枚举（领域层）
 * 
 * 定义用户所有可能的操作类型，用于记录用户操作流水
 * 操作类型分类：
 * <ul>
 *   <li>认证类：登录、注册</li>
 *   <li>信息类：修改信息、更新头像、修改昵称</li>
 *   <li>状态类：冻结、解冻、删除、更新状态</li>
 *   <li>创建类：创建用户</li>
 * </ul>
 * 
 * @author ouyucheng
 * @date 2025/12/16
 */
public enum UserOperateType {

    /**
     * 登录
     * 用户登录系统
     */
    LOGIN,

    /**
     * 注册
     * 用户注册新账号
     */
    REGISTER,

    /**
     * 修改信息
     * 用户修改个人信息
     */
    MODIFY,

    /**
     * 冻结
     * 管理员冻结用户账号
     */
    FREEZE,

    /**
     * 解冻
     * 管理员解冻用户账号
     */
    UNFREEZE,

    /**
     * 删除
     * 管理员删除用户账号（软删除）
     */
    DELETE,

    /**
     * 更新头像
     * 用户更新个人头像
     */
    UPDATE_AVATAR,

    /**
     * 修改昵称
     * 用户修改个人昵称
     */
    CHANGE_NICKNAME,

    /**
     * 创建用户
     * 系统创建新用户
     */
    CREATE,

    /**
     * 更新状态
     * 用户状态变更
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
