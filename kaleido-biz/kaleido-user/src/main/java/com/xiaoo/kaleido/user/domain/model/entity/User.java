package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.user.domain.constant.UserStatus;
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
    private String telephone;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户状态
     */
    private UserStatus status;

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
    private String avatarUrl;

    /**
     * 创建用户实体
     *
     * @param telephone    手机号
     * @param passwordHash 密码哈希
     * @param nickName     昵称
     * @param inviteCode   邀请码
     * @param inviterId    邀请人ID（可选）
     * @return 用户实体
     */
    public static User create(String telephone, String passwordHash,
                              String nickName, String inviteCode, String inviterId) {

        return User.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .telephone(telephone)
                .passwordHash(passwordHash)
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
            throw new IllegalStateException("用户当前状态不允许修改信息");
        }
        this.nickName = newNickName;
    }

    /**
     * 冻结用户
     *
     * @throws IllegalStateException 如果用户状态不允许冻结
     */
    public void freeze() {
        if (status.isDeleted()) {
            throw new IllegalStateException("已删除的用户不能冻结");
        }
        if (status.isFrozen()) {
            throw new IllegalStateException("用户已经是冻结状态");
        }
        this.status = UserStatus.FROZEN;
    }

    /**
     * 解冻用户
     *
     * @throws IllegalStateException 如果用户状态不允许解冻
     */
    public void unfreeze() {
        if (status.isDeleted()) {
            throw new IllegalStateException("已删除的用户不能解冻");
        }
        if (!status.isFrozen()) {
            throw new IllegalStateException("只有冻结状态的用户才能解冻");
        }
        this.status = UserStatus.ACTIVE;
    }

    /**
     * 软删除用户
     *
     * @throws IllegalStateException 如果用户状态不允许删除
     */
    public void delete() {
        if (status.isDeleted()) {
            throw new IllegalStateException("用户已经是删除状态");
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
     * 更新头像
     *
     * @param avatarUrl 头像URL
     * @throws IllegalStateException 如果用户状态不允许修改
     */
    public void updateAvatar(String avatarUrl) {
        if (!status.canModify()) {
            throw new IllegalStateException("用户当前状态不允许修改信息");
        }
        this.avatarUrl = avatarUrl;
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
}
