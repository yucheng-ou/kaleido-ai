package com.xiaoo.kaleido.user.domain.model.aggregate;

import com.xiaoo.kaleido.user.domain.event.BaseDomainEvent;
import com.xiaoo.kaleido.user.domain.event.UserCreatedEvent;
import com.xiaoo.kaleido.user.domain.event.UserNickNameChangedEvent;
import com.xiaoo.kaleido.user.domain.event.UserStatusChangedEvent;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户聚合根
 * 封装用户相关的业务规则和一致性边界
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
public class UserAggregate {

    /**
     * 用户实体（聚合根内的核心实体）
     */
    private User user;

    /**
     * 领域事件列表
     */
    @Builder.Default
    private List<BaseDomainEvent> domainEvents = new ArrayList<>();

    /**
     * 操作流水列表（聚合根内的实体）
     */
    @Builder.Default
    private List<UserOperateStream> operateStreams = new ArrayList<>();

    /**
     * 创建用户聚合根
     *
     * @param user 用户实体
     * @return 用户聚合根
     */
    public static UserAggregate create(User user) {
        return UserAggregate.builder()
                .user(user)
                .build();
    }

    /**
     * 获取用户ID
     */
    public String getId() {
        return user.getId();
    }

    /**
     * 获取用户状态
     */
    public UserStatus getStatus() {
        return user.getStatus();
    }

    /**
     * 修改昵称
     *
     * @param newNickName 新昵称
     * @throws IllegalStateException 如果用户状态不允许修改
     */
    public void changeNickName(String newNickName) {
        String oldNickName = user.getNickName();
        user.changeNickName(newNickName);
        recordNickNameChangedEvent(oldNickName, newNickName);
        addOperateStream(UserOperateType.CHANGE_NICKNAME,
                String.format("昵称修改：%s -> %s", oldNickName, newNickName));
    }

    /**
     * 冻结用户
     *
     * @throws IllegalStateException 如果用户状态不允许冻结
     */
    public void freeze() {
        UserStatus oldStatus = user.getStatus();
        user.freeze();
        recordStatusChangedEvent(oldStatus, user.getStatus(), "用户主动冻结");
        addOperateStream(UserOperateType.FREEZE, "用户冻结");
    }

    /**
     * 解冻用户
     *
     * @throws IllegalStateException 如果用户状态不允许解冻
     */
    public void unfreeze() {
        UserStatus oldStatus = user.getStatus();
        user.unfreeze();
        recordStatusChangedEvent(oldStatus, user.getStatus(), "用户主动解冻");
        addOperateStream(UserOperateType.UNFREEZE, "用户解冻");
    }

    /**
     * 软删除用户
     *
     * @throws IllegalStateException 如果用户状态不允许删除
     */
    public void delete() {
        UserStatus oldStatus = user.getStatus();
        user.delete();
        recordStatusChangedEvent(oldStatus, user.getStatus(), "用户主动删除");
        addOperateStream(UserOperateType.DELETE, "用户删除");
    }

    /**
     * 更新头像
     *
     * @param avatarUrl 头像URL
     * @throws IllegalStateException 如果用户状态不允许修改
     */
    public void updateAvatar(String avatarUrl) {
        user.updateAvatar(avatarUrl);
        addOperateStream(UserOperateType.UPDATE_AVATAR, "更新头像");
    }

    public void updateLastLoginTime(){
        user.updateLastLoginTime();
        addOperateStream(UserOperateType.LOGIN, "登录");
    }

    /**
     * 验证密码
     *
     * @param passwordHash 待验证的密码哈希
     * @return 是否匹配
     */
    public boolean verifyPassword(String passwordHash) {
        return user.verifyPassword(passwordHash);
    }

    /**
     * 判断用户是否可操作
     *
     * @return 是否可操作
     */
    public boolean isOperable() {
        return user.isOperable();
    }

    /**
     * 判断用户是否活跃
     *
     * @return 是否活跃
     */
    public boolean isActive() {
        return user.isActive();
    }

    /**
     * 判断用户是否冻结
     *
     * @return 是否冻结
     */
    public boolean isFrozen() {
        return user.isFrozen();
    }

    /**
     * 判断用户是否删除
     *
     * @return 是否删除
     */
    public boolean isDeleted() {
        return user.isDeleted();
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
        return user.getTelephone();
    }

    /**
     * 获取昵称
     */
    public String getNickName() {
        return user.getNickName();
    }

    /**
     * 获取创建时间
     */
    public Date getCreatedAt() {
        return user.getCreatedAt();
    }

    /**
     * 获取更新时间
     */
    public Date getUpdatedAt() {
        return user.getUpdatedAt();
    }

    /**
     * 创建用户聚合根并记录创建事件
     *
     * @param user 用户实体
     * @param inviterId 邀请人ID
     * @return 用户聚合根
     */
    public static UserAggregate createWithEvent(User user, String inviterId) {
        UserAggregate aggregate = create(user);
        aggregate.recordUserCreatedEvent(inviterId);
        return aggregate;
    }

    /**
     * 记录用户创建事件
     *
     * @param inviterId 邀请人ID
     */
    public void recordUserCreatedEvent(String inviterId) {
        UserCreatedEvent event = new UserCreatedEvent(user, inviterId);
        domainEvents.add(event);
    }

    /**
     * 记录用户昵称修改事件
     *
     * @param oldNickName 旧昵称
     * @param newNickName 新昵称
     */
    public void recordNickNameChangedEvent(String oldNickName, String newNickName) {
        UserNickNameChangedEvent event = new UserNickNameChangedEvent(
                user.getId(), oldNickName, newNickName);
        domainEvents.add(event);
    }

    /**
     * 记录用户状态变更事件
     *
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     * @param reason 变更原因
     */
    public void recordStatusChangedEvent(UserStatus oldStatus, UserStatus newStatus, String reason) {
        UserStatusChangedEvent event = new UserStatusChangedEvent(
                user.getId(), oldStatus, newStatus, reason);
        domainEvents.add(event);
    }

    /**
     * 获取并清空领域事件
     *
     * @return 领域事件列表
     */
    public List<BaseDomainEvent> getAndClearDomainEvents() {
        List<BaseDomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    /**
     * 获取领域事件（不清空）
     *
     * @return 领域事件列表
     */
    public List<BaseDomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    /**
     * 清空领域事件
     */
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    /**
     * 添加操作流水
     *
     * @param operateType 操作类型
     * @param detail      操作详情
     */
    private void addOperateStream(UserOperateType operateType, String detail) {
        UserOperateStream stream = UserOperateStream.createBySelf(
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

    /**
     * 清空操作流水列表
     */
    public void clearOperateStreams() {
        operateStreams.clear();
    }

    /**
     * 创建用户聚合根并添加创建操作流水
     *
     * @param user      用户实体
     * @param inviterId 邀请人ID
     * @return 用户聚合根
     */
    public static UserAggregate createWithOperateStream(User user, String inviterId) {
        UserAggregate aggregate = create(user);
        aggregate.recordUserCreatedEvent(inviterId);
        aggregate.addOperateStream(UserOperateType.CREATE,
                String.format("用户创建，手机号：%s，邀请人ID：%s",
                        user.getTelephone(),
                        inviterId != null ? inviterId : "无"));
        return aggregate;
    }
}
