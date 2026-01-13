package com.xiaoo.kaleido.notice.types.constant;

import com.xiaoo.kaleido.redis.constant.RedisConstant;

public class RedisKeyConstant {

    public static class SmsVerifyCode {
        /**
         * 短信验证码缓存键模板
         * 格式：kaleido:verify_code:sms:{targetType}:{mobile}
         * 示例：kaleido:verify_code:sms:user:13800138000
         */
        public static final String SMS_VERIFY_CODE_KEY = RedisConstant.PROJECT_CACHE_KEY_PREFIX + "verify_code:sms:%s:%s";

        /**
         * 验证码过期时间（5分钟）
         */
        public static final long SMS_VERIFY_CODE_EXPIRE_TIME = 5 * 60 * 1000L;
    }
}
