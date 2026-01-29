package com.xiaoo.kaleido.mcp.tool;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @McpTool(name = "getWeatherByCity", description = "根据城市名称获取当前城市的天气信息")
    public String getWeatherByCity(
            @McpToolParam(description = "城市名称", required = true) String city) {
        return "今天8-16摄氏度，晴转多云";
    }
}
