package com.xiaoo.kaleido.wardrobe.domain.image.strategy;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;

/**
 * 图片转换策略接口
 * <p>
 * 策略模式：定义不同领域的图片转换算法
 *
 * @param <T> 目标DTO类型
 * @author ouyucheng
 * @date 2026/1/23
 */
public interface ImageConversionStrategy<T> {
    
    /**
     * 将基础图片信息和MinIO信息转换为目标DTO
     *
     * @param basicInfo 基础图片信息
     * @param minioInfo MinIO图片信息
     * @return 转换后的DTO
     */
    T convert(BasicImageInfo basicInfo, ImageInfo minioInfo);
    
    /**
     * 获取策略支持的领域类型
     *
     * @return 领域类型
     */
    DomainType getSupportedDomainType();
    
    /**
     * 检查是否支持指定的领域类型
     *
     * @param domainType 领域类型
     * @return 是否支持
     */
    default boolean supports(DomainType domainType) {
        return getSupportedDomainType() == domainType;
    }
}
