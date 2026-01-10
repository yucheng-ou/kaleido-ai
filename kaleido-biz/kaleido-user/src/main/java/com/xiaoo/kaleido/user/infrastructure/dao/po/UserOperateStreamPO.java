package com.xiaoo.kaleido.user.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户操作流水持久化对象
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_operate_stream")
public class UserOperateStreamPO extends BasePO {

    /**
     * 操作类型
     */
    @TableField("operate_type")
    private UserOperateType operateType;

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
    private Date operateTime;
}
