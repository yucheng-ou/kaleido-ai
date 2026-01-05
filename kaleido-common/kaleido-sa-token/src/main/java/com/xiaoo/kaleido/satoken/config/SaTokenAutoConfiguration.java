package com.xiaoo.kaleido.satoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenAutoConfiguration {

    @Bean(initMethod = "init")
    public StpUtilConfig setSaTokenConfig() {
        return new StpUtilConfig();
    }
}
