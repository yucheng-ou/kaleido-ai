package com.xiaoo.kaleido.user.domain.model.entity;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.api.user.constant.UserStatusEnum;
import com.xiaoo.kaleido.ds.entity.BaseEntity;
import lombok.*;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("t_user")
public class User extends BaseEntity {
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码（hash）
     */
    private String passwordHash;

    /**
     * 用户状态 活跃、冻结
     */
    private UserStatusEnum status;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人ID
     */
    private Long inviterId;

    /**
     * 用户手机号
     */
    private String telephone;


    /**
     * 用户头像URL地址
     */
    private String avatar;

    /**
     * 对密码进行hash计算 使用md5
     *
     * @param password 密码原文
     * @return 密码hash值
     */
    public static String hashPassword(String password) {
        return DigestUtil.md5Hex(password);
    }

    /**
     * 注册用户
     * @param telephone 手机号
     * @param inviteCode 邀请码
     * @param nickName 用户昵称
     * @param password 用户面膜
     * @param inviterId 邀请人id
     * @return 用户信息实体对象
     */
    public static User register(String telephone, String inviteCode, String nickName, String password, Long inviterId) {
        return User.builder()
                .telephone(telephone)
                .inviteCode(inviteCode)
                .nickName(nickName)
                .passwordHash(hashPassword(password))
                .inviterId(inviterId)
                .status(UserStatusEnum.ACTIVE)
                .build();
    }

    /**
     * 更新用户基本信息
     * @param nickName 新昵称（可选，为null时不更新）
     * @param avatar 新头像URL（可选，为null时不更新）
     * @param telephone 新手机号（可选，为null时不更新）
     * @return 当前用户实体对象（用于链式调用）
     */
    public User updateBasicInfo(String nickName, String avatar, String telephone) {
        if (nickName != null) {
            this.nickName = nickName;
        }
        if (avatar != null) {
            this.avatar = avatar;
        }
        if (telephone != null) {
            this.telephone = telephone;
        }
        return this;
    }

}
