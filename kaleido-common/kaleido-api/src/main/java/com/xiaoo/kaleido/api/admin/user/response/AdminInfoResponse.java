package com.xiaoo.kaleido.api.admin.user.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理员信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class AdminInfoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 管理员ID
     */
    private String adminId;

    
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
     * 最后登录时间
     */
    private Date lastLoginTime;

    
    /**
     * 角色ID列表
     */
    private List<String> roleIds;
}
