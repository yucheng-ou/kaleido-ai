package com.xiaoo.kaleido.ai.application.config;

import lombok.Data;

/**
 * 记忆工具配置
 * <p>
 * 用于配置MessageChatMemoryAdvisor的参数
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
public class MemoryConfig {

    /**
     * 最大消息数量
     * 默认值：200
     */
    private Integer maxMessages = 200;

    /**
     * 是否启用记忆
     * 默认值：true
     */
    private Boolean enabled = true;
}
