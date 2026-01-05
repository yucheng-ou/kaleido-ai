package com.xiaoo.kaleido.api.admin.auth.request;

import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限分页查询请求")
public class PermissionPageQueryReq extends BasePageReq {
    
    /**
     * 权限编码
     */
    @Schema(description = "权限编码", example = "user")
    private String code;
    
    /**
     * 权限名称
     */
    @Schema(description = "权限名称", example = "用户管理")
    private String name;
    
    /**
     * 权限类型
     */
    @Schema(description = "权限类型", example = "MENU")
    private PermissionType type;
    
    /**
     * 父权限ID
     */
    @Schema(description = "父权限ID", example = "1234567890123456789")
    private String parentId;
    
    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏", example = "false")
    private Boolean isHidden;
}
