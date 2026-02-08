package com.xiaoo.kaleido.message.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MQ消息持久化对象
 * <p>
 * 对应数据库表：t_mq_message
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_mq_message")
public class MqMessagePO extends BasePO {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 消息主体
     */
    @TableField("message")
    private String message;

    /**
     * 消息主题
     */
    @TableField("topic")
    private String topic;

    /**
     * 任务状态：create-创建、completed-完成、fail-失败
     */
    @TableField("state")
    private String state;
}
