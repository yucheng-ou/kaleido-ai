package com.xiaoo.kaleido.web.config;

import com.xiaoo.kaleido.web.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description
 */
@AutoConfiguration
@Import({GlobalExceptionHandler.class, JacksonConfiguration.class})
public class WebAutoConfiguration implements WebMvcConfigurer {


    //TODO 注册过滤器


}
