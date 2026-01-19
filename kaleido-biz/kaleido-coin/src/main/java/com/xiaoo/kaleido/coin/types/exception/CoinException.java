package com.xiaoo.kaleido.coin.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 金币服务业务异常类
 * <p>
 * 金币服务中所有业务异常的基类，继承自BizException，用于统一处理金币相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CoinException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public CoinException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public CoinException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建金币异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 金币异常实例
     */
    public static CoinException of(String errorCode, String message) {
        return new CoinException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建金币异常
     *
     * @param errorCode 错误码枚举
     * @return 金币异常实例
     */
    public static CoinException of(ErrorCode errorCode) {
        return new CoinException(errorCode);
    }

    /**
     * 静态工厂方法：创建金币异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException of(ErrorCode errorCode, String message) {
        return new CoinException(errorCode.getCode(), message);
    }

    // ========== 便捷方法 ==========

    /**
     * 账户不存在异常
     *
     * @return 金币异常实例
     */
    public static CoinException accountNotFound() {
        return new CoinException(CoinErrorCode.ACCOUNT_NOT_FOUND);
    }

    /**
     * 账户不存在异常（带自定义消息）
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException accountNotFound(String message) {
        return new CoinException(CoinErrorCode.ACCOUNT_NOT_FOUND.getCode(), message);
    }

    /**
     * 账户余额不足异常
     *
     * @return 金币异常实例
     */
    public static CoinException balanceInsufficient() {
        return new CoinException(CoinErrorCode.ACCOUNT_BALANCE_INSUFFICIENT);
    }

    /**
     * 账户余额不足异常（带自定义消息）
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException balanceInsufficient(String message) {
        return new CoinException(CoinErrorCode.ACCOUNT_BALANCE_INSUFFICIENT.getCode(), message);
    }

    /**
     * 参数错误异常
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException paramError(String message) {
        return new CoinException(CoinErrorCode.PARAM_FORMAT_ERROR.getCode(), message);
    }

    /**
     * 参数不能为空异常
     *
     * @param paramName 参数名称
     * @return 金币异常实例
     */
    public static CoinException paramNotNull(String paramName) {
        return new CoinException(CoinErrorCode.PARAM_NOT_NULL.getCode(), paramName + "不能为空");
    }

    /**
     * 数据不存在异常
     *
     * @return 金币异常实例
     */
    public static CoinException dataNotFound() {
        return new CoinException(CoinErrorCode.DATA_NOT_FOUND);
    }

    /**
     * 数据不存在异常（带自定义消息）
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException dataNotFound(String message) {
        return new CoinException(CoinErrorCode.DATA_NOT_FOUND.getCode(), message);
    }

    /**
     * 数据已存在异常
     *
     * @return 金币异常实例
     */
    public static CoinException dataAlreadyExists() {
        return new CoinException(CoinErrorCode.DATA_ALREADY_EXISTS);
    }

    /**
     * 数据已存在异常（带自定义消息）
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException dataAlreadyExists(String message) {
        return new CoinException(CoinErrorCode.DATA_ALREADY_EXISTS.getCode(), message);
    }

    /**
     * 操作失败异常
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException operateFailed(String message) {
        return new CoinException(CoinErrorCode.OPERATE_FAILED.getCode(), message);
    }

    /**
     * 查询失败异常
     *
     * @param message 自定义错误信息
     * @return 金币异常实例
     */
    public static CoinException queryFail(String message) {
        return new CoinException(CoinErrorCode.QUERY_FAIL.getCode(), message);
    }

    /**
     * 邀请奖励处理失败异常
     *
     * @return 金币异常实例
     */
    public static CoinException inviteRewardFailed() {
        return new CoinException(CoinErrorCode.INVITE_REWARD_FAILED);
    }

    /**
     * 位置创建扣费失败异常
     *
     * @return 金币异常实例
     */
    public static CoinException locationCostFailed() {
        return new CoinException(CoinErrorCode.LOCATION_COST_FAILED);
    }

    /**
     * 搭配创建扣费失败异常
     *
     * @return 金币异常实例
     */
    public static CoinException outfitCostFailed() {
        return new CoinException(CoinErrorCode.OUTFIT_COST_FAILED);
    }
}
