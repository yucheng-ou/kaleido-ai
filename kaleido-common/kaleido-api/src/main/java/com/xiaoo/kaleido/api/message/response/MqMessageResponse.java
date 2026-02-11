package com.xiaoo.kaleido.api.message.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * MQ消息响应
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MqMessageResponse extends BaseResp {

    /**
     * 消息ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息状态
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
