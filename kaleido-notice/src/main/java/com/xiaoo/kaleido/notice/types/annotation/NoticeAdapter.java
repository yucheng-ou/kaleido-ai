package com.xiaoo.kaleido.notice.types.annotation;

import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 通知适配器注解
 * <p>
 * 用于标记具体的通知适配器实现，并指定其支持的通知类型
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NoticeAdapter {

    /**
     * 适配器支持的通知类型
     */
    NoticeTypeEnum value();
}
