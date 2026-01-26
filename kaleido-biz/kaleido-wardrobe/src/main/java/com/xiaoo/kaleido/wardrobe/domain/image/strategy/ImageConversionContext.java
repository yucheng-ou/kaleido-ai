package com.xiaoo.kaleido.wardrobe.domain.image.strategy;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.impl.ClothingImageConversionStrategy;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.impl.LocationImageConversionStrategy;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.impl.OutfitImageConversionStrategy;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片转换策略上下文
 * <p>
 * 策略模式：管理不同领域的图片转换策略
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageConversionContext {
    
    private final ClothingImageConversionStrategy clothingStrategy;
    private final LocationImageConversionStrategy locationStrategy;
    private final OutfitImageConversionStrategy outfitStrategy;
    
    /**
     * 策略映射表
     */
    private final Map<DomainType, ImageConversionStrategy<?>> strategyMap = new HashMap<>();
    
    @PostConstruct
    public void init() {
        // 注册所有策略
        strategyMap.put(DomainType.CLOTHING, clothingStrategy);
        strategyMap.put(DomainType.LOCATION, locationStrategy);
        strategyMap.put(DomainType.OUTFIT, outfitStrategy);
        
        log.info("图片转换策略上下文初始化完成，注册策略数量: {}", strategyMap.size());
    }
    
    /**
     * 根据领域类型获取转换策略
     *
     * @param domainType 领域类型
     * @return 转换策略
     * @throws IllegalArgumentException 如果找不到对应的策略
     */
    @SuppressWarnings("unchecked")
    public <T> ImageConversionStrategy<T> getStrategy(DomainType domainType) {
        ImageConversionStrategy<?> strategy = strategyMap.get(domainType);
        if (strategy == null) {
            throw new IllegalArgumentException("未找到领域类型对应的转换策略: " + domainType);
        }
        return (ImageConversionStrategy<T>) strategy;
    }
    
    /**
     * 执行图片转换
     *
     * @param domainType 领域类型
     * @param basicInfo 基础图片信息
     * @param minioInfo MinIO图片信息
     * @return 转换后的DTO
     * @param <T> 目标DTO类型
     */
    public <T> T convert(DomainType domainType, BasicImageInfo basicInfo, ImageInfo minioInfo) {
        ImageConversionStrategy<T> strategy = getStrategy(domainType);
        
        log.debug("使用策略转换图片，领域类型: {}, 策略: {}", 
                domainType, strategy.getClass().getSimpleName());
        
        try {
            T result = strategy.convert(basicInfo, minioInfo);
            log.debug("图片转换成功，领域类型: {}", domainType);
            return result;
        } catch (Exception e) {
            log.error("图片转换失败，领域类型: {}, 错误: ", domainType, e);
            throw new RuntimeException("图片转换失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 注册新的转换策略
     *
     * @param domainType 领域类型
     * @param strategy 转换策略
     */
    public void registerStrategy(DomainType domainType, ImageConversionStrategy<?> strategy) {
        if (strategyMap.containsKey(domainType)) {
            log.warn("覆盖已存在的转换策略，领域类型: {}, 原策略: {}, 新策略: {}", 
                    domainType, 
                    strategyMap.get(domainType).getClass().getSimpleName(),
                    strategy.getClass().getSimpleName());
        }
        
        strategyMap.put(domainType, strategy);
        log.info("注册转换策略成功，领域类型: {}, 策略: {}", 
                domainType, strategy.getClass().getSimpleName());
    }
    
    /**
     * 移除转换策略
     *
     * @param domainType 领域类型
     */
    public void removeStrategy(DomainType domainType) {
        ImageConversionStrategy<?> removed = strategyMap.remove(domainType);
        if (removed != null) {
            log.info("移除转换策略成功，领域类型: {}, 策略: {}", 
                    domainType, removed.getClass().getSimpleName());
        } else {
            log.warn("尝试移除不存在的转换策略，领域类型: {}", domainType);
        }
    }
    
    /**
     * 检查是否支持指定的领域类型
     *
     * @param domainType 领域类型
     * @return 是否支持
     */
    public boolean supports(DomainType domainType) {
        return strategyMap.containsKey(domainType);
    }
    
    /**
     * 获取所有支持的领域类型
     *
     * @return 领域类型列表
     */
    public DomainType[] getSupportedDomainTypes() {
        return strategyMap.keySet().toArray(new DomainType[0]);
    }
}
