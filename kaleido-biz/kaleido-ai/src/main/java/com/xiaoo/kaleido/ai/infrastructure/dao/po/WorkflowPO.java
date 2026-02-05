package com.xiaoo.kaleido.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作流持久化对象
 * <p>
 * 对应数据库表：t_ai_workflow
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ai_workflow")
public class WorkflowPO extends BasePO {

    /**
     * 工作流编码（唯一）
     */
    @TableField("code")
    private String code;

    /**
     * 工作流名称
     */
    @TableField("name")
    private String name;

    /**
     * 工作流描述
     */
    @TableField("description")
    private String description;

    /**
     * 工作流DSL定义（JSON格式）
     */
    @TableField("definition")
    private String definition;

    /**
     * 状态：NORMAL-正常，DISABLED-禁用
     */
    @TableField("status")
    private String status;

}
