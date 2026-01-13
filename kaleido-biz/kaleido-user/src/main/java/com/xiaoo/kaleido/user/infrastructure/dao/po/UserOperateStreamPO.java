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
     * 用户操作的类型，使用UserOperateType枚举定义
     */
    @TableField("operate_type")
    private UserOperateType operateType;

    /**
     * 操作详情（JSON格式）
     * 操作的详细描述，包含操作的具体内容和参数
     */
    @TableField("operate_detail")
    private String operateDetail;

    /**
     * 操作者ID
     * 执行操作的用户ID
     */
    @TableField("operator_id")
    private String operatorId;

    /**
     * 操作时间
     * 操作发生的时间戳
     */
    @TableField("operate_time")
    private Date operateTime;
}
