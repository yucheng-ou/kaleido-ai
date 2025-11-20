package com.xiaoo.kaleido.base.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 16:17
 * @Description 基础请求类
 */
@Data
public class BaseReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 验证请求参数（子类可重写）
     */
    public void validate() {
        // 基础验证，子类可扩展
    }
}
