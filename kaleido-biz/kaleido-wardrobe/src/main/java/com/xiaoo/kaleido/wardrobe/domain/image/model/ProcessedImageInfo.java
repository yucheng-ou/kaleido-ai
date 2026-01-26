package com.xiaoo.kaleido.wardrobe.domain.image.model;

import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import lombok.Builder;
import lombok.Data;

/**
 * 处理后的图片信息
 * <p>
 * 包含基础图片信息和从MinIO获取的详细信息
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Data
@Builder
public class ProcessedImageInfo {
    
    /**
     * 基础图片信息
     */
    private final BasicImageInfo basicInfo;
    
    /**
     * MinIO图片信息
     */
    private final ImageInfo minioInfo;
    
    /**
     * 是否已压缩
     */
    @Builder.Default
    private boolean compressed = false;
    
    /**
     * 是否已添加水印
     */
    @Builder.Default
    private boolean watermarked = false;
    
    /**
     * 是否已验证
     */
    @Builder.Default
    private boolean validated = false;
    
    /**
     * 处理过程中的错误信息
     */
    private String errorMessage;
    
    /**
     * 从基础图片信息和MinIO信息创建ProcessedImageInfo
     */
    public static ProcessedImageInfo from(BasicImageInfo basicInfo, ImageInfo minioInfo) {
        return ProcessedImageInfo.builder()
                .basicInfo(basicInfo)
                .minioInfo(minioInfo)
                .validated(true) // 默认认为已验证
                .build();
    }
    
    /**
     * 创建只有基础信息的ProcessedImageInfo（MinIO信息获取失败时使用）
     */
    public static ProcessedImageInfo fromBasicOnly(BasicImageInfo basicInfo) {
        return ProcessedImageInfo.builder()
                .basicInfo(basicInfo)
                .minioInfo(null)
                .validated(false)
                .errorMessage("MinIO信息获取失败")
                .build();
    }
    
    /**
     * 检查是否包含完整的MinIO信息
     */
    public boolean hasCompleteMinioInfo() {
        return minioInfo != null;
    }
    
    /**
     * 检查处理是否成功
     */
    public boolean isSuccess() {
        return errorMessage == null || errorMessage.isEmpty();
    }
}
