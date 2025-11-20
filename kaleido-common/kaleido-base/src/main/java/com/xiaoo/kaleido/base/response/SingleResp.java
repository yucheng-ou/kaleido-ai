package com.xiaoo.kaleido.base.response;

import lombok.*;

import java.io.Serial;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 16:50
 * @Description 单数据响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleResp<T> extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 创建成功响应
     *
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> SingleResp<T> success(T data) {
        SingleResp<T> resp = new SingleResp<>();
        resp.setData(data);
        resp.setSuccess(true);
        return resp;
    }

}
