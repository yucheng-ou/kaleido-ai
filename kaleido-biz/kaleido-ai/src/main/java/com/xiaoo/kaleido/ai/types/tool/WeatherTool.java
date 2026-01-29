package com.xiaoo.kaleido.ai.types.tool;

import org.springframework.ai.tool.annotation.Tool;

public class WeatherTool {

    @Tool(description = "获取当前天气")
    String getWeather() {
        return "今天29摄氏度，晴转多云";
    }
}
