package com.xiaoo.kaleido.wardrobe.domain.image.context;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.model.ProcessedImageInfo;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片处理上下文
 * <p>
 * 在责任链中传递处理状态和结果
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Data
public class ImageProcessingContext {
    
    /**
     * 原始基础图片信息
     */
    private final BasicImageInfo basicImageInfo;
    
    /**
     * 从MinIO获取的图片信息
     */
    private ImageInfo minioInfo;
    
    /**
     * 处理后的图片信息
     */
    private ProcessedImageInfo processedImageInfo;
    
    /**
     * 处理过程中是否发生错误
     */
    private boolean hasError;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 扩展属性，用于在不同处理器间传递数据
     */
    private final Map<String, Object> attributes = new HashMap<>();
    
    public ImageProcessingContext(BasicImageInfo basicImageInfo) {
        this.basicImageInfo = basicImageInfo;
        this.hasError = false;
    }
    
    /**
     * 设置错误信息
     */
    public void setError(String errorMessage) {
        this.hasError = true;
        this.errorMessage = errorMessage;
    }
    
    /**
     * 检查是否已发生错误
     */
    public boolean hasError() {
        return hasError;
    }
    
    /**
     * 获取处理结果
     */
    public ProcessedImageInfo getResult() {
        if (processedImageInfo != null) {
            return processedImageInfo;
        }
        
        // 如果没有显式设置processedImageInfo，根据现有信息创建
        if (minioInfo != null) {
            return ProcessedImageInfo.from(basicImageInfo, minioInfo);
        } else {
            return ProcessedImageInfo.fromBasicOnly(basicImageInfo);
        }
    }
    
    /**
     * 设置属性
     */
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    
    /**
     * 获取属性
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
    
    /**
     * 获取属性，如果不存在则返回默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, T defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }
}
