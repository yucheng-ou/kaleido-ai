package com.xiaoo.kaleido.wardrobe.domain.image.chain.handler;

import com.xiaoo.kaleido.wardrobe.domain.image.chain.AbstractImageProcessingHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import com.xiaoo.kaleido.wardrobe.domain.image.model.ProcessedImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 图片优化处理器
 * <p>
 * 对图片进行优化处理（压缩、格式转换等）
 * 演示装饰器模式在责任链中的应用
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Component
public class ImageOptimizationHandler extends AbstractImageProcessingHandler {
    
    /**
     * 最大图片大小（5MB）
     */
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    
    /**
     * 需要压缩的图片大小阈值（1MB）
     */
    private static final long COMPRESSION_THRESHOLD = 1024 * 1024;
    
    @Override
    protected void doHandle(ImageProcessingContext context) {
        // 检查是否有MinIO信息
        if (context.getMinioInfo() == null) {
            log.debug("没有MinIO信息，跳过图片优化");
            return;
        }
        
        Long fileSize = context.getMinioInfo().getFileSize();
        String imagePath = context.getBasicImageInfo().getPath();
        
        log.debug("开始图片优化处理: {}, 大小: {} bytes", imagePath, fileSize);
        
        // 检查图片大小是否超过限制
        if (fileSize > MAX_IMAGE_SIZE) {
            log.warn("图片大小超过限制，路径: {}, 大小: {} bytes", imagePath, fileSize);
            context.setError("图片大小超过5MB限制");
            return;
        }
        
        // 如果图片大小超过压缩阈值，标记需要压缩
        if (fileSize > COMPRESSION_THRESHOLD) {
            log.debug("图片需要压缩，路径: {}, 大小: {} bytes", imagePath, fileSize);
            context.setAttribute("needsCompression", true);
            context.setAttribute("originalSize", fileSize);
            
            // 模拟压缩处理
            long compressedSize = simulateCompression(fileSize);
            context.setAttribute("compressedSize", compressedSize);
            context.setAttribute("compressionRatio", (double) compressedSize / fileSize);
        }
        
        // 检查图片格式，如果需要则标记转换
        String mimeType = context.getMinioInfo().getMimeType();
        if (mimeType != null && !isOptimizedFormat(mimeType)) {
            log.debug("图片格式需要优化: {}, 格式: {}", imagePath, mimeType);
            context.setAttribute("needsFormatConversion", true);
            context.setAttribute("targetFormat", "image/webp");
        }
        
        log.debug("图片优化处理完成: {}", imagePath);
    }
    
    /**
     * 模拟图片压缩
     */
    private long simulateCompression(long originalSize) {
        // 简单压缩算法：如果大于2MB，压缩到70%，否则压缩到85%
        double ratio = originalSize > 2 * 1024 * 1024 ? 0.7 : 0.85;
        return (long) (originalSize * ratio);
    }
    
    /**
     * 检查是否为优化格式
     */
    private boolean isOptimizedFormat(String mimeType) {
        // 优化的格式：webp, avif, jpeg 2000等
        return mimeType.contains("webp") || 
               mimeType.contains("avif") || 
               mimeType.contains("jp2") ||
               mimeType.contains("jpeg") || 
               mimeType.contains("jpg");
    }
    
    /**
     * 应用优化到处理结果
     */
    public ProcessedImageInfo applyOptimization(ProcessedImageInfo processedImageInfo, ImageProcessingContext context) {
        if (processedImageInfo == null) {
            return null;
        }
        
        ProcessedImageInfo.ProcessedImageInfoBuilder builder = ProcessedImageInfo.builder()
                .basicInfo(processedImageInfo.getBasicInfo())
                .minioInfo(processedImageInfo.getMinioInfo())
                .validated(processedImageInfo.isValidated())
                .errorMessage(processedImageInfo.getErrorMessage());
        
        // 应用压缩标记
        if (context.getAttribute("needsCompression", false)) {
            builder.compressed(true);
        }
        
        // 应用水印标记（如果有）
        if (context.getAttribute("needsWatermark", false)) {
            builder.watermarked(true);
        }
        
        return builder.build();
    }
    
    @Override
    protected String getHandlerName() {
        return "ImageOptimizationHandler";
    }
}
