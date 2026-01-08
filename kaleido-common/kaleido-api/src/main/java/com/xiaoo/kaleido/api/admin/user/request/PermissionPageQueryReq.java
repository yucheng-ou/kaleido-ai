package com.xiaoo.kaleido.api.admin.user.request;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 鏉冮檺鍒嗛〉鏌ヨ璇锋眰
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionPageQueryReq extends BasePageReq {
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
     * 是否隐藏
     */
    private Boolean isHidden;
}
