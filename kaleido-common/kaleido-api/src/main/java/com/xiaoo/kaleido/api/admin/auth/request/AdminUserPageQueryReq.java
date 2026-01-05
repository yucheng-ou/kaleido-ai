package com.xiaoo.kaleido.api.admin.auth.request;

import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 管理员分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理员分页查询请求")
public class AdminUserPageQueryReq extends BasePageReq {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
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
     * 角色ID
     */
    @Schema(description = "角色ID", example = "1234567890123456789")
    private String roleId;
    
    /**
     * 开始时间（创建时间）
     */
    @Schema(description = "开始时间（创建时间）", example = "2025-12-01T00:00:00.000+08:00")
    private String startTime;
    
    /**
     * 结束时间（创建时间）
     */
    @Schema(description = "结束时间（创建时间）", example = "2025-12-31T23:59:59.999+08:00")
    private String endTime;
}
