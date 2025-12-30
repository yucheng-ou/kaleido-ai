package com.xiaoo.kaleido.base.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 16:47
 * @Description 基础响应类
 */
@Data
@Accessors(chain = true)
public class BaseResp implements Serializable {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 创建成功响应
     *
     * @return 成功响应对象
     */
    public static BaseResp of() {
        return new BaseResp();
    }


}
