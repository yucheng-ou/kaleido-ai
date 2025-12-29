package com.xiaoo.kaleido.user.trigger.event;

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
 * 监听用户相关的领域事件并处理
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Component
public class UserEventListener {

    /**
     * 处理用户创建事件
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     */
    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("处理用户创建事件: {}", event.getDescription());
        
        // 操作流水已在UserAggregate内同步记录，无需额外处理
        // 此处可添加其他逻辑，如发送欢迎通知、更新缓存等
        
        log.info("用户创建事件处理完成，用户ID: {}", event.getUser().getId());
    }

    /**
     * 处理用户昵称修改事件
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     */
    @EventListener
    @Async
    public void handleUserNickNameChangedEvent(UserNickNameChangedEvent event) {
        log.info("处理用户昵称修改事件: {}", event.getDescription());
        
        // 操作流水已在UserAggregate内同步记录，无需额外处理
        // 此处可添加其他逻辑，如发送通知、更新缓存等
        
        log.info("用户昵称修改事件处理完成，用户ID: {}", event.getUserId());
    }

    /**
     * 处理用户状态变更事件
     * 注意：操作流水已在UserAggregate内同步记录，此处仅处理其他逻辑（如发送通知等）
     */
    @EventListener
    @Async
    public void handleUserStatusChangedEvent(UserStatusChangedEvent event) {
        log.info("处理用户状态变更事件: {}", event.getDescription());
        
        // 操作流水已在UserAggregate内同步记录，无需额外处理
        // 此处可添加其他逻辑，如发送通知、更新缓存等
        
        log.info("用户状态变更事件处理完成，用户ID: {}", event.getUserId());
    }

    /**
     * 根据状态变更确定操作类型
     */
    private UserOperateType determineOperateType(com.xiaoo.kaleido.user.domain.constant.UserStatus oldStatus, 
                                                com.xiaoo.kaleido.user.domain.constant.UserStatus newStatus) {
        if (newStatus == com.xiaoo.kaleido.user.domain.constant.UserStatus.FROZEN) {
            return UserOperateType.FREEZE;
        } else if (oldStatus == com.xiaoo.kaleido.user.domain.constant.UserStatus.FROZEN && 
                   newStatus == com.xiaoo.kaleido.user.domain.constant.UserStatus.ACTIVE) {
            return UserOperateType.UNFREEZE;
        } else if (newStatus == com.xiaoo.kaleido.user.domain.constant.UserStatus.DELETED) {
            return UserOperateType.DELETE;
        } else {
            return UserOperateType.UPDATE_STATUS;
        }
    }
}
