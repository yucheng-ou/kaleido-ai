package com.xiaoo.kaleido.admin.domain.user.model.aggregate;

import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 权限聚合根
 * 封装权限相关的业务规则和一致性边界
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PermissionAggregate extends BaseEntity {

    /**
     * 权限编码（唯一）
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
     * 创建权限
     *
     * @param code      权限编码
     * @param name      权限名称
     * @param type      权限类型
     * @param parentId  父权限ID
     * @param sort      排序
     * @param icon      图标
     * @param path      前端路由路径
     * @param component 前端组件路径
     * @param isHidden  是否隐藏
     * @return 权限对象
     */
    public static PermissionAggregate create(String code, String name, PermissionType type,
                                             String parentId, Integer sort, String icon,
                                             String path, String component, Boolean isHidden) {
        return PermissionAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .code(code)
                .name(name)
                .type(type)
                .parentId(parentId)
                .sort(sort != null ? sort : 0)
                .icon(icon)
                .path(path)
                .component(component)
                .isHidden(isHidden != null ? isHidden : false)
                .build();
    }

    /**
     * 更新权限信息
     *
     * @param name      权限名称
     * @param type      权限类型
     * @param parentId  父权限ID
     * @param sort      排序
     * @param icon      图标
     * @param path      前端路由路径
     * @param component 前端组件路径
     * @param isHidden  是否隐藏
     */
    public void updateInfo(String name, PermissionType type, String parentId,
                           Integer sort, String icon, String path,
                           String component, Boolean isHidden) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (type != null) {
            this.type = type;
        }
        if (parentId != null) {
            this.parentId = parentId;
        }
        if (sort != null) {
            this.sort = sort;
        }
        if (icon != null) {
            this.icon = icon;
        }
        if (path != null) {
            this.path = path;
        }
        if (component != null) {
            this.component = component;
        }
        if (isHidden != null) {
            this.isHidden = isHidden;
        }
    }

    /**
     * 更新权限编码
     *
     * @param code 权限编码
     */
    public void updateCode(String code) {
        if (code != null && !code.trim().isEmpty()) {
            this.code = code;
        }
    }

    /**
     * 判断是否为接口权限
     * 注意：权限类型已修改，API类型已不存在，此方法保留但始终返回false
     *
     * @return 是否为接口权限
     */
    public boolean isApi() {
        return false;
    }


}
