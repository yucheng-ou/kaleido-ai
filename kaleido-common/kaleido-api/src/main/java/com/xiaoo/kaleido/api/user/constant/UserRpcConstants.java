package com.xiaoo.kaleido.api.user.constant;

/**
 * 用户RPC常量
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public class UserRpcConstants {

    /**
     * Dubbo服务分组
     */
    public static final String DUBBO_GROUP = "kaleido-user";

    /**
     * Dubbo服务版本
     */
    public static final String DUBBO_VERSION = "1.0.0";

    /**
     * 默认超时时间（毫秒）
     */
    public static final int DEFAULT_TIMEOUT = 1000 * 20;

    /**
     * 创建用户超时时间（毫秒）
     */
    public static final int CREATE_USER_TIMEOUT = 5000;

    /**
     * 更新用户超时时间（毫秒）
     */
    public static final int UPDATE_USER_TIMEOUT = 3000;

    /**
     * 查询用户超时时间（毫秒）
     */
    public static final int QUERY_USER_TIMEOUT = 2000;

    private UserRpcConstants() {
        // 防止实例化
    }
}
