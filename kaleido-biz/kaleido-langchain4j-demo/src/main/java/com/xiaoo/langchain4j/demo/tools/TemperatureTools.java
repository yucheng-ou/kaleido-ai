package com.xiaoo.langchain4j.demo.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 天气查询助手工具类
 * 提供天气查询功能
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
public class TemperatureTools {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureTools.class);

    /**
     * 根据城市查询当天天气
     *
     * @param city 城市名称，例如："北京"、"上海"
     * @return 当天天气信息
     */
    @Tool(value = "根据城市查询当天天气", name = "queryWeatherByCity")
    public String queryWeatherByCity(@P("城市名称") String city) {
        LOG.info("查询天气方法被调用，城市：{}", city);
        
        // 模拟数据 - 根据城市返回不同的天气
        String weatherDescription;
        if ("北京".equals(city)) {
            weatherDescription = "晴，温度 15-23°C，湿度 45%，风速 2级，空气质量 良";
        } else if ("上海".equals(city)) {
            weatherDescription = "多云，温度 18-25°C，湿度 65%，风速 3级，空气质量 优";
        } else if ("广州".equals(city)) {
            weatherDescription = "阵雨，温度 22-28°C，湿度 85%，风速 4级，空气质量 良";
        } else if ("深圳".equals(city)) {
            weatherDescription = "晴转多云，温度 23-30°C，湿度 75%，风速 2级，空气质量 优";
        } else {
            weatherDescription = "晴，温度 20-26°C，湿度 60%，风速 3级，空气质量 良";
        }
        
        return city + " 当天天气：" + weatherDescription;
    }
}
