package com.xiaoo.kaleido.wardrobe.application.command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片信息转换模板
 * <p>
 * 使用模板方法模式定义图片信息转换的标准流程
 * 消除LocationCommandService中的重复转换代码
 *
 * @param <S> 源图片信息类型
 * @param <T> 目标图片信息类型
 * @author ouyucheng
 * @date 2026/1/17
 */
public abstract class ImageInfoConverterTemplate<S, T> {
    
    /**
     * 模板方法：定义转换流程
     * 
     * @param sourceImages 源图片信息列表
     * @return 转换后的目标图片信息列表
     */
    public final List<T> convert(List<S> sourceImages) {
        // 1. 前置处理（钩子方法）
        beforeConvert(sourceImages);
        
        // 2. 处理空值情况
        if (sourceImages == null || sourceImages.isEmpty()) {
            return handleEmptySource();
        }
        
        // 3. 执行转换
        List<T> result = sourceImages.stream()
                .map(this::convertSingle)
                .collect(Collectors.toList());
        
        // 4. 后置处理（钩子方法）
        afterConvert(result);
        
        return result;
    }
    
    /**
     * 抽象方法：子类必须实现的单个对象转换逻辑
     * 
     * @param sourceImage 单个源图片信息
     * @return 转换后的目标图片信息
     */
    protected abstract T convertSingle(S sourceImage);
    
    /**
     * 处理空源列表的情况
     * 
     * @return 空的目标列表
     */
    protected List<T> handleEmptySource() {
        return List.of();
    }
    
    /**
     * 钩子方法：转换前的处理
     * 
     * @param sourceImages 源图片信息列表
     */
    protected void beforeConvert(List<S> sourceImages) {
        // 默认空实现，子类可覆盖
    }
    
    /**
     * 钩子方法：转换后的处理
     * 
     * @param convertedImages 转换后的图片信息列表
     */
    protected void afterConvert(List<T> convertedImages) {
        // 默认空实现，子类可覆盖
    }
    
    /**
     * 批量转换的便捷方法
     * 
     * @param sourceImages 源图片信息列表
     * @param converter 转换器实例
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的列表
     */
    public static <S, T> List<T> convertList(List<S> sourceImages, ImageInfoConverterTemplate<S, T> converter) {
        return converter.convert(sourceImages);
    }
}
