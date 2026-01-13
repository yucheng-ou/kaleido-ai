package com.xiaoo.kaleido.api.admin.user.request;

import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.*;

/**
 * 角色分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePageQueryReq extends BasePageReq {
    
    /**
     * 角色编码
     */
    private String code;
    
    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
}
