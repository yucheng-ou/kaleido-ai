package com.xiaoo.kaleido.base.util;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;

import java.util.Collection;
import java.util.Map;

/**
 * @Author ouyucheng
 * @Date 2025/11/18
 * @Description 基础工具类
 */
public class BaseUtils {

    private BaseUtils() {
        // 工具类，防止实例化
    }

    /**
     * 检查对象是否为null，如果为null则抛出业务异常
     *
     * @param obj 要检查的对象
     * @param errorCode 错误码
     * @param message 错误信息
     * @param <T> 对象类型
     * @return 非null的对象
     */
    public static <T> T requireNonNull(T obj, ErrorCode errorCode, String message) {
        if (obj == null) {
            throw BizException.of(errorCode, message);
        }
        return obj;
    }

    /**
     * 检查字符串是否为空，如果为空则抛出业务异常
     *
     * @param str 要检查的字符串
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 非空的字符串
     */
    public static String requireNonEmpty(String str, ErrorCode errorCode, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw BizException.of(errorCode, message);
        }
        return str;
    }

    /**
     * 检查集合是否为空，如果为空则抛出业务异常
     *
     * @param collection 要检查的集合
     * @param errorCode 错误码
     * @param message 错误信息
     * @param <T> 集合类型
     * @return 非空的集合
     */
    public static <T extends Collection<?>> T requireNonEmpty(T collection, ErrorCode errorCode, String message) {
        if (collection == null || collection.isEmpty()) {
            throw BizException.of(errorCode, message);
        }
        return collection;
    }

    /**
     * 检查Map是否为空，如果为空则抛出业务异常
     *
     * @param map 要检查的Map
     * @param errorCode 错误码
     * @param message 错误信息
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 非空的Map
     */
    public static <K, V> Map<K, V> requireNonEmpty(Map<K, V> map, ErrorCode errorCode, String message) {
        if (map == null || map.isEmpty()) {
            throw BizException.of(errorCode, message);
        }
        return map;
    }

    /**
     * 检查条件是否为true，如果为false则抛出业务异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static void checkArgument(boolean condition, ErrorCode errorCode, String message) {
        if (!condition) {
            throw BizException.of(errorCode, message);
        }
    }

    /**
     * 检查状态是否为true，如果为false则抛出业务异常
     *
     * @param condition 状态
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static void checkState(boolean condition, ErrorCode errorCode, String message) {
        if (!condition) {
            throw BizException.of(errorCode, message);
        }
    }

    /**
     * 安全的字符串转换，避免null值
     *
     * @param obj 要转换的对象
     * @return 对象的字符串表示，如果为null则返回空字符串
     */
    public static String safeToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 安全的字符串截取
     *
     * @param str 原始字符串
     * @param maxLength 最大长度
     * @return 截取后的字符串
     */
    public static String safeSubstring(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * 生成请求ID（简单实现）
     *
     * @return 请求ID
     */
    public static String generateRequestId() {
        return System.currentTimeMillis() + "-" + System.nanoTime();
    }
}
