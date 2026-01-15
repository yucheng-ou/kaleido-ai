package com.xiaoo.kaleido.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @author ouyucheng
 * @date 2025/11/21
 * @description
 */
@Configuration
@RequiredArgsConstructor
public class JacksonConfiguration {


    private final ObjectMapper objectMapper;

    @PostConstruct
    public void myObjectMapper() {
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }
}
