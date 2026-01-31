package com.xiaoo.kaleido.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 会话持久化对象
 * <p>
 * 对应数据库表：t_ai_conversation
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ai_conversation")
public class ConversationPO extends BasePO {

    /**
     * 会话ID（业务唯一，对应MongoDB中的会话ID）
     */
    @TableField("conversation_id")
    private String conversationId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 会话标题
     */
    @TableField("title")
    private String title;

    /**
     * 最后消息时间
     */
    @TableField("last_message_time")
    private Date lastMessageTime;
}
