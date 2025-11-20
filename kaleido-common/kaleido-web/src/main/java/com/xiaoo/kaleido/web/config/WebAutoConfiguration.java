package com.xiaoo.kaleido.web.config;

import com.xiaoo.kaleido.web.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description
 */
@AutoConfiguration
@Import({GlobalExceptionHandler.class})
public class WebAutoConfiguration {

    //TODO 注册过滤器
}
