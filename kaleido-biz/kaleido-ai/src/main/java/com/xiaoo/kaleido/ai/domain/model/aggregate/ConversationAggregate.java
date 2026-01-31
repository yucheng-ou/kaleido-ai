package com.xiaoo.kaleido.ai.domain.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * AI对话会话聚合根
 * <p>
 * AI对话会话领域模型的核心聚合根，封装会话实体及其相关信息
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ConversationAggregate extends BaseEntity {

    /**
     * 会话ID（业务唯一，对应MongoDB中的会话ID）
     */
    private String conversationId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会话标题
     */
    private String title;

    /**
     * 最后消息时间
     */
    private Date lastMessageTime;

    /**
     * 创建新会话聚合根
     * <p>
     * 用于创建新会话时构建聚合根
     *
     * @param conversationId 会话ID（业务唯一），不能为空
     * @param userId         用户ID，不能为空
     * @param title          会话标题，可为空
     * @return 会话聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    public static ConversationAggregate create(
            String conversationId,
            String userId,
            String title) {
        
        if (StrUtil.isBlank(conversationId)) {
            throw AiException.of(AiErrorCode.CONVERSATION_ID_NOT_NULL, "会话ID不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw AiException.of(AiErrorCode.USER_ID_NOT_NULL, "用户ID不能为空");
        }

        return ConversationAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .conversationId(conversationId.trim())
                .userId(userId.trim())
                .title(title != null ? title.trim() : null)
                .lastMessageTime(new Date())
                .build();
    }

    /**
     * 更新会话信息
     * <p>
     * 更新会话的标题和最后消息时间
     *
     * @param title 新会话标题，可为空
     * @throws IllegalStateException 如果会话状态不允许修改
     */
    public void updateInfo(String title) {
        this.title = title != null ? title.trim() : null;
        this.lastMessageTime = new Date();
    }

    /**
     * 更新会话标题
     * <p>
     * 更新会话标题
     *
     * @param title 新会话标题，不能为空
     * @throws IllegalArgumentException 如果标题为空
     */
    public void updateTitle(String title) {
        if (StrUtil.isBlank(title)) {
            throw AiException.of(AiErrorCode.CONVERSATION_TITLE_EMPTY, "会话标题不能为空");
        }
        this.title = title.trim();
    }

    /**
     * 更新最后消息时间
     * <p>
     * 当会话中有新消息时调用，更新最后消息时间为当前时间
     */
    public void updateLastMessageTime() {
        this.lastMessageTime = new Date();
    }

    /**
     * 检查会话是否属于指定用户
     *
     * @param userId 用户ID
     * @return 如果会话属于该用户返回true，否则返回false
     */
    public boolean belongsToUser(String userId) {
        return this.userId.equals(userId);
    }

    /**
     * 检查用户是否是会话的所有者
     *
     * @param userId 用户ID
     * @return 如果用户是会话所有者返回true，否则返回false
     */
    public boolean isOwner(String userId) {
        return this.userId.equals(userId);
    }

    /**
     * 标记为已删除
     * <p>
     * 将会话标记为已删除状态
     */
    public void markAsDeleted() {
        // 这里可以添加删除标记逻辑，如果需要的话
        // 目前只是简单的方法实现
    }

    /**
     * 获取会话活跃状态
     * <p>
     * 根据最后消息时间判断会话是否活跃（24小时内）
     *
     * @return 如果最后消息时间在24小时内返回true，否则返回false
     */
    public boolean isActive() {
        if (this.lastMessageTime == null) {
            return false;
        }
        
        long twentyFourHours = 24 * 60 * 60 * 1000L;
        long timeDiff = System.currentTimeMillis() - this.lastMessageTime.getTime();
        return timeDiff <= twentyFourHours;
    }

    /**
     * 获取会话闲置天数
     *
     * @return 会话闲置天数（从最后消息时间到现在）
     */
    public long getIdleDays() {
        if (this.lastMessageTime == null) {
            return 0;
        }
        
        long oneDay = 24 * 60 * 60 * 1000L;
        long timeDiff = System.currentTimeMillis() - this.lastMessageTime.getTime();
        return timeDiff / oneDay;
    }
}
