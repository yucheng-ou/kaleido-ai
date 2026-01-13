package com.xiaoo.kaleido.base.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ouyucheng
 * @date 2026/1/13 11:16
 * @description 基础响应类
 */
@Data
public class BaseResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应数据验证（子类可重写）
     */
    public void validate() {
        // 基础验证，子类可扩展
    }
}
