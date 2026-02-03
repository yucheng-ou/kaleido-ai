package com.xiaoo.kaleido.api.ai.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话信息响应
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationInfoResponse extends BaseResp {

    /**
     * 会话ID
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
     * 消息列表
     */
    private List<Message> messages;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        /**
         * 消息角色：USER, ASSISTANT, SYSTEM
         */
        private String role;

        /**
         * 消息内容
         */
        private String content;
    }
}
