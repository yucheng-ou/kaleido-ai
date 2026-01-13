package com.xiaoo.kaleido.api.admin.user.response;

import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.List;

/**
 * 权限信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class PermissionInfoResponse extends BaseResp {
    
    /**
     * 权限ID
     */
    private String permissionId;
    
    /**
     * 权限编码
     */
    private String code;
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限类型
     */
    private PermissionType type;
    
    /**
     * 父权限ID
     */
    private String parentId;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 前端路由路径
     */
    private String path;
    
    /**
     * 前端组件路径
     */
    private String component;
    
    /**
     * 是否隐藏
     */
    private Boolean isHidden;
    
    /**
     * 子权限列表
     */
    private List<PermissionInfoResponse> children;
}
