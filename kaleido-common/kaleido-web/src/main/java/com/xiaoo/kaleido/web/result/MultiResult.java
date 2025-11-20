package com.xiaoo.kaleido.web.result;

import com.xiaoo.kaleido.base.response.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description
 */
@Getter
@Setter
public class MultiResult<T> extends Result<List<T>> {
    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页记录数
     */
    private int size;

    public MultiResult() {
        super();
    }

    public MultiResult(String code, String msg, Boolean success, List<T> data, long total, int page, int size) {
        super(code, msg, success, data);
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public static <T> MultiResult<T> successMulti(List<T> data, long total, int page, int size) {
        return new MultiResult<>(ResponseCode.SUCCESS.name(), ResponseCode.SUCCESS.name(), true, data, total, page, size);
    }

    public static <T> MultiResult<T> errorMulti(String errorCode, String errorMsg) {
        return new MultiResult<>(errorCode, errorMsg, true, null, 0L, 0, 0);
    }

}
