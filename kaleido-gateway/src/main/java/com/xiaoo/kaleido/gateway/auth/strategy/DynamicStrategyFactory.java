package com.xiaoo.kaleido.gateway.auth.strategy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.gateway.auth.config.AuthStrategyConfig;
import com.xiaoo.kaleido.gateway.auth.strategy.factory.AdminPathStrategy;
import com.xiaoo.kaleido.gateway.enums.AuthStrategyEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 动态策略工厂类
 * 
 * 根据配置文件动态加载认证策略，使用策略模式实现灵活的认证检查
 * 使用 AntPathMatcher 进行路径匹配，支持通配符匹配
 * 配置从 application.yml 加载，支持动态配置管理
 * 
 */
@Slf4j
@Component
public class DynamicStrategyFactory {

    /**
     * 路径匹配器，支持 Ant 风格路径匹配
     */
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 策略配置列表，保持配置顺序
     */
    private final List<PathStrategyPair> strategyPairs = new ArrayList<>();

    /**
     * 请求路径到策略的缓存
     * 使用 Caffeine 缓存提高性能，避免重复路径匹配计算
     */
    private final Cache<String, LoginCheckStrategy> pathCache = Caffeine.newBuilder()
            .maximumSize(1000)                     // 最大缓存1000个请求路径
            .expireAfterWrite(10, TimeUnit.MINUTES) // 10分钟过期，适应配置更新
            .recordStats()                         // 记录缓存统计信息
            .build();

    private final AuthStrategyConfig authStrategyConfig;

    /**
     * 构造函数，注入配置
     */
    public DynamicStrategyFactory(AuthStrategyConfig authStrategyConfig) {
        this.authStrategyConfig = authStrategyConfig;
    }

    /**
     * 初始化方法，加载配置并构建策略映射
     * 使用 @Autowired 方法实现初始化，避免 @PostConstruct 注解问题
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化动态策略工厂...");

        // 验证配置
        authStrategyConfig.validate();

        // 清空现有配置和缓存
        strategyPairs.clear();
        pathCache.invalidateAll();

        // 加载配置的策略
        List<AuthStrategyConfig.StrategyItem> configStrategies = authStrategyConfig.getStrategies();
        if (configStrategies != null && !configStrategies.isEmpty()) {
            for (AuthStrategyConfig.StrategyItem item : configStrategies) {
                String pattern = item.getPattern();
                AuthStrategyEnum strategyType = item.getStrategy();

                LoginCheckStrategy strategy = createStrategy(strategyType);
                strategyPairs.add(new PathStrategyPair(pattern, strategy));

                log.info("加载策略配置: pattern={}, strategy={}", pattern, strategyType);
            }
        }
        log.info("动态策略工厂初始化完成，共加载 {} 个策略配置", strategyPairs.size());
    }

    /**
     * 根据策略类型创建策略实例
     * 
     * @param strategyType 策略类型枚举
     * @return 对应的认证策略实例
     */
    private LoginCheckStrategy createStrategy(AuthStrategyEnum strategyType) {
        return switch (strategyType) {
            case PUBLIC -> new PublicPathStrategy();
            case USER -> new UserPathStrategy();
            case ADMIN -> new AdminPathStrategy();
        };
    }

    /**
     * 根据请求路径获取对应的认证策略
     * 
     * 使用 Caffeine 缓存提高性能，避免重复路径匹配计算
     * 按照配置列表的顺序进行匹配，第一个匹配的路径模式对应的策略将被返回
     * 如果没有匹配的路径模式，则返回默认策略
     * 
     *
     * @param requestPath 请求路径
     * @return 对应的认证策略实例
     */
    public LoginCheckStrategy getStrategy(String requestPath) {
        // 使用缓存，如果缓存未命中则执行路径匹配
        return pathCache.get(requestPath, this::findStrategyByPath);
    }

    /**
     * 查找请求路径对应的认证策略（缓存未命中时调用）
     * 
     * 按照配置列表的顺序进行匹配，第一个匹配的路径模式对应的策略将被返回
     * 如果没有匹配的路径模式，则返回默认策略
     * 
     *
     * @param requestPath 请求路径
     * @return 对应的认证策略实例
     */
    private LoginCheckStrategy findStrategyByPath(String requestPath) {
        // 按照配置顺序遍历（保持优先级）
        for (PathStrategyPair pair : strategyPairs) {
            if (PATH_MATCHER.match(pair.pattern, requestPath)) {
                log.debug("路径匹配成功: requestPath={}, pattern={}, strategy={}",
                        requestPath, pair.pattern, pair.strategy.getClass().getSimpleName());
                return pair.strategy;
            }
        }
        log.warn("未知的请求路径:{}", requestPath);
        throw BizException.of(BizErrorCode.REQUEST_URL_NOT_FOUND);
    }

    /**
     * 获取缓存统计信息
     * 
     * 用于监控和调试缓存性能，包括命中率、加载次数、缓存大小等信息
     * 
     *
     * @return 缓存统计信息字符串
     */
    public String getCacheStats() {
        return pathCache.stats().toString();
    }

    /**
     * 清空缓存
     * 
     * 用于配置更新、系统维护等需要清空缓存的场景
     * 调用此方法将清空所有缓存的路径-策略映射
     * 
     */
    public void clearCache() {
        pathCache.invalidateAll();
        log.info("路径策略缓存已清空");
    }


    /**
     * 路径-策略对（内部类）
     */
    private record PathStrategyPair(String pattern, LoginCheckStrategy strategy) {
    }
}
