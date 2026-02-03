package com.xiaoo.kaleido.ai.domain.chat.model.aggregate;

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
     * @param userId         用户ID，不能为空
     * @return 会话聚合根
     */
    public static ConversationAggregate create(String userId) {
        
        if (StrUtil.isBlank(userId)) {
            throw AiException.of(AiErrorCode.USER_ID_NOT_NULL, "用户ID不能为空");
        }

        return ConversationAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .conversationId(SnowflakeUtil.newSnowflakeId())
                .userId(userId.trim())
                .title("新会话")
                .lastMessageTime(null)
                .build();
    }

    /**
     * 更新会话标题
     * <p>
     * 更新会话标题
     *
     * @param title 新会话标题，不能为空
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
}
