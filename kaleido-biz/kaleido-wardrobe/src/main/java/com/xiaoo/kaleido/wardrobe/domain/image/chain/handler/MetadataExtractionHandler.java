package com.xiaoo.kaleido.wardrobe.domain.image.chain.handler;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.file.service.IMinIOService;
import com.xiaoo.kaleido.wardrobe.domain.image.chain.AbstractImageProcessingHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 元数据提取处理器
 * <p>
 * 从MinIO获取图片的元数据信息（尺寸、类型、大小等）
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MetadataExtractionHandler extends AbstractImageProcessingHandler {
    
    private final IMinIOService minIOService;
    
    @Override
    protected void doHandle(ImageProcessingContext context) {
        String imagePath = context.getBasicImageInfo().getPath();
        log.debug("开始提取图片元数据: {}", imagePath);
        
        try {
            // 从MinIO获取图片详细信息
            ImageInfo minioInfo = minIOService.getImageInfo(imagePath);
            
            if (minioInfo == null) {
                log.warn("MinIO图片信息获取失败，路径: {}", imagePath);
                context.setError("无法获取图片元数据信息");
                return;
            }
            
            // 验证必要的元数据
            Long fileSize = minioInfo.getFileSize();
            if (fileSize == null || fileSize <= 0) {
                log.warn("图片文件大小无效，路径: {}", imagePath);
                context.setError("图片文件大小无效");
                return;
            }
            
            // 设置元数据到上下文
            context.setMinioInfo(minioInfo);
            log.debug("图片元数据提取成功: {}, 大小: {} bytes", imagePath, minioInfo.getFileSize());
            
        } catch (Exception e) {
            log.error("提取图片元数据失败，路径: {}, 错误: ", imagePath, e);
            context.setError("提取图片元数据失败: " + e.getMessage());
        }
    }
    
    @Override
    protected String getHandlerName() {
        return "MetadataExtractionHandler";
    }
}
