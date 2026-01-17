package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.file.service.IMinIOService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 通用图片处理服务
 * <p>
 * 负责统一处理图片信息的获取和转换，消除重复代码
 * 使用泛型支持不同类型的图片DTO
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingService {

    private final IMinIOService minIOService;

    /**
     * 处理图片信息列表，转换为目标DTO列表
     *
     * @param imageInfos 基础图片信息列表
     * @param converter  转换函数，将基础图片信息和MinIO图片信息转换为目标DTO
     * @param <T>        目标DTO类型
     * @param <I>        基础图片信息类型
     * @return 转换后的DTO列表
     */
    public <T, I extends BasicImageInfo> List<T> processImages(
            List<I> imageInfos,
            BiFunction<I, ImageInfo, T> converter) {
        
        if (imageInfos == null || imageInfos.isEmpty()) {
            return List.of();
        }

        return imageInfos.stream()
                .map(info -> {
                    try {
                        // 从MinIO获取图片详细信息
                        ImageInfo minioImageInfo = minIOService.getImageInfo(info.getPath());
                        
                        // 使用转换函数创建目标DTO
                        return converter.apply(info, minioImageInfo);
                        
                    } catch (Exception e) {
                        // 处理异常：记录日志并返回基本图片信息
                        log.warn("获取图片信息失败，路径: {}, 错误: ", info.getPath(), e);
                        
                        // 使用null作为MinIO信息，让转换函数处理异常情况
                        return converter.apply(info, null);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 处理图片信息列表（简化版，不需要MinIO信息）
     *
     * @param imageInfos 基础图片信息列表
     * @param converter  转换函数，将基础图片信息转换为目标DTO
     * @param <T>        目标DTO类型
     * @param <I>        基础图片信息类型
     * @return 转换后的DTO列表
     */
    public <T, I extends BasicImageInfo> List<T> processImages(
            List<I> imageInfos,
            java.util.function.Function<I, T> converter) {
        
        if (imageInfos == null || imageInfos.isEmpty()) {
            return List.of();
        }

        return imageInfos.stream()
                .map(converter)
                .collect(Collectors.toList());
    }
}
