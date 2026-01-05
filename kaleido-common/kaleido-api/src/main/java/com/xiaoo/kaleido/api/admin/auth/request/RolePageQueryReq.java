package com.xiaoo.kaleido.api.admin.auth.request;

import com.xiaoo.kaleido.base.request.BasePageReq;
import com.xiaoo.kaleido.base.request.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色分页查询请求")
public class RolePageQueryReq extends BasePageReq {
    
    /**
     * 角色编码
     */
    @Schema(description = "角色编码", example = "admin")
    private String code;
    
    /**
     * 角色名称
     */
    @Schema(description = "角色名称", example = "管理员")
    private String name;
    
    /**
     * 是否系统角色
     */
    @Schema(description = "是否系统角色", example = "false")
    private Boolean isSystem;
    
    /**
     * 是否启用
     */
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}
