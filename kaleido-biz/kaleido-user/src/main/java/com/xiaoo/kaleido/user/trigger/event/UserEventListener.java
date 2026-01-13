package com.xiaoo.kaleido.user.trigger.event;

import com.xiaoo.kaleido.api.user.enums.UserStatus;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import com.xiaoo.kaleido.user.domain.event.UserCreatedEvent;
import com.xiaoo.kaleido.user.domain.event.UserNickNameChangedEvent;
import com.xiaoo.kaleido.user.domain.event.UserStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户事件监听器
 * 
 * 监听用户相关的领域事件并异步处理，实现事件驱动架构
 * 职责：
 * <ul>
 *   <li>监听用户领域事件</li>
 *   <li>异步处理事件逻辑</li>
 *   <li>实现跨领域协作</li>
 * </ul>
 * 
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Component
public class UserEventListener {

    /**
     * 处理用户创建事件
     * 
     * 监听用户创建事件，异步处理相关逻辑
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     * 
     * @param event 用户创建事件，不能为空
     */
    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("处理用户创建事件: {}", event.getDescription());
        
        // 1.操作流水已在UserAggregate内同步记录，无需额外处理
        // 2.此处可添加其他逻辑，如发送欢迎通知、更新缓存等
        
        log.info("用户创建事件处理完成，用户ID: {}", event.getUser().getId());
    }

    /**
     * 处理用户昵称修改事件
     * 
     * 监听用户昵称修改事件，异步处理相关逻辑
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     * 
     * @param event 用户昵称修改事件，不能为空
     */
    @EventListener
    @Async
    public void handleUserNickNameChangedEvent(UserNickNameChangedEvent event) {
        log.info("处理用户昵称修改事件: {}", event.getDescription());
        
        // 1.操作流水已在UserAggregate内同步记录，无需额外处理
        // 2.此处可添加其他逻辑，如发送通知、更新缓存等
        
        log.info("用户昵称修改事件处理完成，用户ID: {}", event.getUserId());
    }

    /**
     * 处理用户状态变更事件
     * 
     * 监听用户状态变更事件，异步处理相关逻辑
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     * 
     * @param event 用户状态变更事件，不能为空
     */
    @EventListener
    @Async
    public void handleUserStatusChangedEvent(UserStatusChangedEvent event) {
        log.info("处理用户状态变更事件: {}", event.getDescription());
        
        // 1.操作流水已在UserAggregate内同步记录，无需额外处理
        // 2.此处可添加其他逻辑，如发送通知、更新缓存等
        
        log.info("用户状态变更事件处理完成，用户ID: {}", event.getUserId());
    }

    /**
     * 根据状态变更确定操作类型
     */
    private UserOperateType determineOperateType(UserStatus oldStatus,
                                                 UserStatus newStatus) {
        if (newStatus == UserStatus.FROZEN) {
            return UserOperateType.FREEZE;
        } else if (oldStatus == UserStatus.FROZEN &&
                   newStatus == UserStatus.ACTIVE) {
            return UserOperateType.UNFREEZE;
        } else if (newStatus == UserStatus.DELETED) {
            return UserOperateType.DELETE;
        } else {
            return UserOperateType.UPDATE_STATUS;
        }
    }
}
