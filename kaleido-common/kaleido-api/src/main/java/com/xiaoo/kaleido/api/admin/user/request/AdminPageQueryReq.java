package com.xiaoo.kaleido.api.admin.user.request;

import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.*;

import java.io.Serial;

/**
 * 管理员分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPageQueryReq extends BasePageReq {
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 管理员状态
     */
    private String status;
    
    /**
     * 角色ID
     */
    private String roleId;
    
    /**
     * 开始时间（创建时间）
     */
    private String startTime;
    
    /**
     * 结束时间（创建时间）
     */
    private String endTime;
}
