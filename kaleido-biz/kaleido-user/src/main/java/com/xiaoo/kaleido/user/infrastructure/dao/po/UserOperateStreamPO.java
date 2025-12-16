package com.xiaoo.kaleido.user.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户操作流水持久化对象
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_operate_stream")
public class UserOperateStreamPO extends BasePO {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 操作类型
     */
    @TableField("operate_type")
    private String operateType;

    /**
     * 操作详情（JSON格式）
     */
    @TableField("operate_detail")
    private String operateDetail;

    /**
     * 操作者ID
     */
    @TableField("operator_id")
    private String operatorId;

    /**
     * 操作时间
     */
    @TableField("operate_time")
    private LocalDateTime operateTime;
}
