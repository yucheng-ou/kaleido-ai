package com.xiaoo.kaleido.base.model.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public BaseEntity(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    /**
     * 主键id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 是否删除
     */
    private Integer deleted;
}
