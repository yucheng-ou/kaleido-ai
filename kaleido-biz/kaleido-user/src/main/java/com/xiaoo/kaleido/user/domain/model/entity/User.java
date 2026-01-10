package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 用户实体
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
     */
    private String mobile;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户邀请码（唯一）
     */
    private String inviteCode;

    /**
     * 邀请人ID（可为空）
     */
    private String inviterId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别
     */
    private UserGenderEnum gender;

    /**
     * 用户状态
     */
    private UserStatus status;

    /**
     * 创建用户实体
     *
     * @param telephone  手机号
     * @param nickName   昵称
     * @param inviteCode 邀请码
     * @param inviterId  邀请人ID（可选）
     * @return 用户实体
     */
    public static User create(String telephone, String nickName, String inviteCode, String inviterId) {

        return User.builder()
                .id(SnowflakeUtil.newSnowflakeId())
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
     * 验证密码
     *
     * @param passwordHash 待验证的密码哈希
     * @return 是否匹配
     */
    public boolean verifyPassword(String passwordHash) {
        return this.passwordHash.equals(passwordHash);
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
