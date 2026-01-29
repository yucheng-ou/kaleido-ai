package com.xiaoo.kaleido.mcp.config;

import com.xiaoo.kaleido.mcp.tool.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

//    @Bean
//    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
//        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
//    }
}
