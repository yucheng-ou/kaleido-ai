package com.xiaoo.kaleido.coin.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 金币服务基础设施层配置
 * <p>
 * 负责基础设施层的相关配置，包括MyBatis Plus扫描等
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Configuration
@MapperScan("com.xiaoo.kaleido.coin.infrastructure.dao")
public class CoinInfraConfig {
    // 基础设施层配置
    // MyBatis Plus会自动扫描指定包下的DAO接口
}
