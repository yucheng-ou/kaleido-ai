package com.xiaoo.kaleido.base.response;

import com.xiaoo.kaleido.base.exception.ErrorCode;
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
     * 是否成功
     */
    private Boolean success;

    /**
     * 响应码
     */
    private String responseCode;

    /**
     * 响应信息
     */
    private String responseMsg;

    /**
     * 时间戳 便于进行日志排查
     */
    private Long timestamp;

    /**
     * 创建成功响应
     *
     * @return 成功响应对象
     */
    public static BaseResp success() {
        BaseResp resp = new BaseResp();
        resp.setSuccess(true);
        resp.setResponseCode("SUCCESS");
        resp.setResponseMsg("操作成功");
        resp.setTimestamp(System.currentTimeMillis());
        return resp;
    }

    /**
     * 创建成功响应
     *
     * @param responseMsg 响应信息
     * @return 成功响应对象
     */
    public static BaseResp success(String responseMsg) {
        BaseResp resp = new BaseResp();
        resp.setSuccess(true);
        resp.setResponseCode("SUCCESS");
        resp.setResponseMsg(responseMsg);
        resp.setTimestamp(System.currentTimeMillis());
        return resp;
    }

    /**
     * 创建失败响应
     *
     * @param errorCode 错误码
     * @return 失败响应对象
     */
    public static BaseResp fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 创建失败响应
     *
     * @param responseCode 响应码
     * @param responseMsg 响应信息
     * @return 失败响应对象
     */
    public static BaseResp fail(String responseCode, String responseMsg) {
        BaseResp resp = new BaseResp();
        resp.setSuccess(false);
        resp.setResponseCode(responseCode);
        resp.setResponseMsg(responseMsg);
        resp.setTimestamp(System.currentTimeMillis());
        return resp;
    }
}
