package com.xiaoo.kaleido.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Agent持久化对象
 * <p>
 * 对应数据库表：t_ai_agent
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ai_agent")
public class AgentPO extends BasePO {

    /**
     * Agent编码（唯一）
     */
    @TableField("code")
    private String code;

    /**
     * Agent名称
     */
    @TableField("name")
    private String name;

    /**
     * Agent描述
     */
    @TableField("description")
    private String description;

    /**
     * 系统提示词
     */
    @TableField("system_prompt")
    private String systemPrompt;

    /**
     * AI模型名称
     */
    @TableField("model_name")
    private String modelName;

    /**
     * 温度参数
     */
    @TableField("temperature")
    private BigDecimal temperature;

    /**
     * 最大token数
     */
    @TableField("max_tokens")
    private Integer maxTokens;

    /**
     * Agent状态：NORMAL-正常，DISABLED-禁用
     */
    @TableField("status")
    private String status;
}
