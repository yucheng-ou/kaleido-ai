package com.xiaoo.kaleido.api.admin.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 管理员信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "管理员信息响应")
public class AdminUserInfoResponse {
    
    /**
     * 管理员ID
     */
    @Schema(description = "管理员ID", example = "1234567890123456789")
    private String adminUserId;
    
    /**
     * 管理员账号
     */
    @Schema(description = "管理员账号", example = "admin")
    private String username;
    
    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    private String realName;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;
    
    /**
     * 管理员状态
     */
    @Schema(description = "管理员状态", example = "NORMAL")
    private String status;
    
    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间", example = "2025-12-31T15:20:00.000+08:00")
    private Date lastLoginTime;

    
    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表")
    private List<String> roleIds;
}
