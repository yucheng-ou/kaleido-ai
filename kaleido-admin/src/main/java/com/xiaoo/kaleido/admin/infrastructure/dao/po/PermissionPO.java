package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限持久化对象
 * 对应数据库表：permission
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("permission")
public class PermissionPO extends BasePO {

    /**
     * 权限编码，如：user:view
     */
    @TableField("code")
    private String code;

    /**
     * 权限名称
     */
    @TableField("name")
    private String name;

    /**
     * 权限类型：1-菜单 2-按钮 3-接口
     */
    @TableField("type")
    private PermissionType type;

    /**
     * 父权限ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 前端路由路径
     */
    @TableField("path")
    private String path;

    /**
     * 前端组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 是否隐藏：0-否 1-是
     */
    @TableField("is_hidden")
    private Boolean isHidden;
}
