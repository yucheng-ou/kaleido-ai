package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.api.user.enums.UserStatus;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 用户实体
 * <p>
 * 用户领域模型的核心实体，包含用户的基本信息、状态和业务行为
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 手机号（唯一）
     * 用户注册和登录的主要标识，必须符合手机号格式规范
     */
    private String mobile;

    /**
     * 昵称
     * 用户显示名称，长度限制为2-20个字符
     */
    private String nickName;

    /**
     * 用户邀请码（唯一）
     * 用户用于邀请新用户的唯一代码，长度为6位字母数字组合
     */
    private String inviteCode;

    /**
     * 邀请人ID（可为空）
     * 邀请该用户注册的用户ID，用于建立邀请关系
     */
    private String inviterId;

    /**
     * 最后登录时间
     * 记录用户最后一次登录的时间
     */
    private Date lastLoginTime;

    /**
     * 头像URL
     * 用户头像的URL地址
     */
    private String avatar;

    /**
     * 性别
     * 用户性别，使用枚举类型表示
     */
    private UserGenderEnum gender;

    /**
     * 用户状态
     * 用户当前状态，包括活跃、冻结、删除等
     */
    private UserStatus status;

    /**
     * 创建用户实体
     * <p>
     * 用于新用户注册时创建用户实体，会自动生成用户ID并设置初始状态
     *
     * @param telephone  手机号，必须符合手机号格式规范
     * @param nickName   昵称，长度限制为2-20个字符
     * @param inviteCode 邀请码，不能为空
     * @param inviterId  邀请人ID，可为空表示无邀请人
     * @return 用户实体
     * @throws IllegalArgumentException 当参数不符合要求时抛出
     */
    public static User create(String telephone, String nickName, String inviteCode, String inviterId) {
        // 1.生成用户ID
        String userId = SnowflakeUtil.newSnowflakeId();

        // 2.构建用户实体
        return User.builder()
                .id(userId)
                .mobile(telephone)
                .nickName(nickName)
                .inviteCode(inviteCode)
                .inviterId(inviterId)
                .status(UserStatus.ACTIVE)
                .build();
    }

    /**
     * 修改昵称
     *
     * @param newNickName 新昵称
     * @throws IllegalStateException 如果用户状态不允许修改
     */
    public void changeNickName(String newNickName) {
        if (!status.canModify()) {
            throw UserException.of(UserErrorCode.USER_IS_FROZEN);
        }
        this.nickName = newNickName;
    }


    /**
     * 更新头像
     *
     * @param avatarUrl 头像URL
     * @throws IllegalStateException 如果用户状态不允许修改
     */
    public void updateAvatar(String avatarUrl) {
        if (checkUserStatusIsActive()) {
            this.avatar = avatarUrl;
        }
    }


    /**
     * 冻结用户
     *
     * @throws IllegalStateException 如果用户状态不允许冻结
     */
    public void freeze() {
        if (checkUserStatusIsActive()) {
            this.status = UserStatus.FROZEN;
        }
    }


    /**
     * 解冻用户
     */
    public void unfreeze() {
        if (checkUserStatusIsActive()) {
            this.status = UserStatus.ACTIVE;
        }
    }

    /**
     * 删除用户
     *
     * @throws IllegalStateException 如果用户状态不允许删除
     */
    public void delete() {
        if (status.isDeleted()) {
            throw UserException.of(UserErrorCode.USER_IS_DELETED);
        }
        this.status = UserStatus.DELETED;
    }

    /**
     * 更新最后登录时间
     */
    public void updateLastLoginTime() {
        this.lastLoginTime = new Date();
    }

    /**
     * 判断用户是否可操作
     *
     * @return 是否可操作
     */
    public boolean isOperable() {
        return status.isOperable();
    }


    /**
     * 判断用户是否活跃
     *
     * @return 是否活跃
     */
    public boolean isActive() {
        return status.isActive();
    }

    /**
     * 判断用户是否冻结
     *
     * @return 是否冻结
     */
    public boolean isFrozen() {
        return status.isFrozen();
    }

    /**
     * 判断用户是否删除
     *
     * @return 是否删除
     */
    public boolean isDeleted() {
        return status.isDeleted();
    }

    public boolean checkUserStatusIsActive() {
        if (isFrozen()) {
            throw UserException.of(UserErrorCode.USER_IS_FROZEN);
        }
        if (isDeleted()) {
            throw UserException.of(UserErrorCode.USER_IS_DELETED);
        }

        return isActive();
    }

    public boolean checkUserStatusIsFrozen() {
        if (isActive()) {
            throw UserException.of(UserErrorCode.USER_IS_ACTIVE);
        }
        if (isDeleted()) {
            throw UserException.of(UserErrorCode.USER_IS_DELETED);
        }

        return isFrozen();
    }
}
