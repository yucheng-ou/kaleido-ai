package com.xiaoo.kaleido.base.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.List;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 17:09
 * @Description 多数据响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MultipleResp<T> extends BaseResp {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 成功工厂方法
     * @param list 返回的数据列表
     * @return 数据对象
     * @param <T> 数据类型
     */
    public static <T> MultipleResp<T> success(List<T> list) {
        MultipleResp<T> multipleResp = new MultipleResp<>();
        multipleResp.setList(list);
        multipleResp.setSuccess(true);
        return multipleResp;
    }

}
