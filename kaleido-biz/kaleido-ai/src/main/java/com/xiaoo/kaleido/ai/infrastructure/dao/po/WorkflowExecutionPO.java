package com.xiaoo.kaleido.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工作流执行持久化对象
 * <p>
 * 对应数据库表：t_ai_workflow_execution
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ai_workflow_execution")
public class WorkflowExecutionPO extends BasePO {

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 工作流ID
     */
    @TableField("workflow_id")
    private String workflowId;

    /**
     * 状态：RUNNING-执行中，SUCCESS-成功，FAILED-失败
     */
    @TableField("status")
    private String status;

    /**
     * 输入数据（JSON格式）
     */
    @TableField("input_data")
    private String inputData;

    /**
     * 输出数据（JSON格式）
     */
    @TableField("output_data")
    private String outputData;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 开始时间
     */
    @TableField("started_at")
    private Date startedAt;

    /**
     * 完成时间
     */
    @TableField("completed_at")
    private Date completedAt;
}
