package com.xiaoo.kaleido.user.domain.model.aggregate;

import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import com.xiaoo.kaleido.user.types.exception.UserException;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户聚合根
 * 用户领域模型的核心聚合根，封装用户实体及其操作流水，确保业务规则的一致性
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
public class UserAggregate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户实体（聚合根内的核心实体）
     * 包含用户的基本信息和状态
     */
    private User user;

    /**
     * 操作流水列表（聚合根内的实体）
     * 记录用户的所有操作历史，用于审计和追溯
     */
    @Builder.Default
    private List<UserOperateStream> operateStreams = new ArrayList<>();

    /**
     * 创建用户聚合根（基于已有用户实体）
     * 用于从数据库加载已有用户时构建聚合根
     *
     * @param user 用户实体，不能为空
     * @return 用户聚合根
     */
    public static UserAggregate create(User user) {
        if (user == null) {
            throw UserException.of(UserErrorCode.REQUEST_PARAM_NULL);
        }
        return UserAggregate.builder()
                .user(user)
                .build();
    }

    /**
     * 创建用户聚合根（基于业务参数）
     * 用于新用户注册时创建聚合根，会自动创建用户实体并记录创建操作流水
     *
     * @param telephone  手机号，必须符合手机号格式规范
     * @param nickName   昵称，长度限制为2-20个字符
     * @param inviteCode 邀请码，不能为空
     * @param inviterId  邀请人ID，可为空表示无邀请人
     * @return 用户聚合根
     */
    public static UserAggregate create(String telephone, String nickName, String inviteCode, String inviterId) {
        // 1.创建用户实体
        User user = User.create(
                telephone,
                nickName,
                inviteCode,
                inviterId
        );

        // 2.构建聚合根
        UserAggregate userAggregate = create(user);

        // 3.记录创建操作流水
        userAggregate.addOperateStream(UserOperateType.CREATE,
                String.format("用户创建，手机号：%s，邀请人ID：%s",
                        user.getMobile(),
                        inviterId != null ? inviterId : "无"));

        return userAggregate;
    }

    /**
     * 获取用户ID
     */
    public String getId() {
        return user.getId();
    }

    /**
     * 修改昵称
     * @param newNickName 新昵称
     */
    public void changeNickName(String newNickName) {
        String oldNickName = user.getNickName();
        user.changeNickName(newNickName);
        addOperateStream(UserOperateType.CHANGE_NICKNAME,
                String.format("昵称修改：%s -> %s", oldNickName, newNickName));
    }

    /**
     * 冻结用户
     */
    public void freeze() {
        user.freeze();
        addOperateStream(UserOperateType.FREEZE, "用户冻结");
    }

    /**
     * 解冻用户
     */
    public void unfreeze() {
        user.unfreeze();
        addOperateStream(UserOperateType.UNFREEZE, "用户解冻");
    }

    /**
     * 删除用户
     */
    public void delete() {
        user.delete();
        addOperateStream(UserOperateType.DELETE, "用户删除");
    }

    /**
     * 更新头像
     *
     * @param avatarUrl 头像URL
     */
    public void updateAvatar(String avatarUrl) {
        user.updateAvatar(avatarUrl);
        addOperateStream(UserOperateType.UPDATE_AVATAR, "更新头像");
    }

    public void login() {
        user.updateLastLoginTime();
        addOperateStream(UserOperateType.LOGIN, "登录");
    }

    public void logout() {
        addOperateStream(UserOperateType.LOGIN, "登出");
    }

    /**
     * 获取邀请码
     */
    public String getInviteCode() {
        return user.getInviteCode();
    }

    /**
     * 获取邀请人ID
     */
    public String getInviterId() {
        return user.getInviterId();
    }

    /**
     * 获取手机号
     */
    public String getTelephone() {
        return user.getMobile();
    }

    /**
     * 获取昵称
     */
    public String getNickName() {
        return user.getNickName();
    }


    /**
     * 添加操作流水
     *
     * @param operateType 操作类型
     * @param detail      操作详情
     */
    private void addOperateStream(UserOperateType operateType, String detail) {
        UserOperateStream stream = UserOperateStream.create(
                user.getId(), operateType, detail);
        operateStreams.add(stream);
    }

    /**
     * 获取操作流水列表
     *
     * @return 操作流水列表
     */
    public List<UserOperateStream> getOperateStreams() {
        return new ArrayList<>(operateStreams);
    }

    /**
     * 获取并清空操作流水列表（用于持久化后清理）
     *
     * @return 操作流水列表
     */
    public List<UserOperateStream> getAndClearOperateStreams() {
        List<UserOperateStream> streams = new ArrayList<>(operateStreams);
        operateStreams.clear();
        return streams;
    }
}
