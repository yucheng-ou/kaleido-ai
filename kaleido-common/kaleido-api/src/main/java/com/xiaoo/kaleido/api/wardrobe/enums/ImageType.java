package com.xiaoo.kaleido.api.wardrobe.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 图片类型枚举
 * <p>
 * 定义系统中支持的图片类型，包含对应的MIME类型
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Getter
public enum ImageType {
    /**
     * JPEG图片格式
     */
    JPEG("image/jpeg", "jpg", "jpeg"),

    /**
     * PNG图片格式
     */
    PNG("image/png", "png"),

    /**
     * GIF图片格式
     */
    GIF("image/gif", "gif"),

    /**
     * WebP图片格式
     */
    WEBP("image/webp", "webp"),

    /**
     * BMP图片格式
     */
    BMP("image/bmp", "bmp"),

    /**
     * SVG图片格式
     */
    SVG("image/svg+xml", "svg"),

    /**
     * 未知类型
     */
    UNKNOWN("application/octet-stream");

    /**
     * MIME类型
     */
    private final String mimeType;

    /**
     * 支持的扩展名
     */
    private final String[] extensions;

    /**
     * MIME类型到枚举的映射
     */
    private static final Map<String, ImageType> MIME_TYPE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(ImageType::getMimeType, Function.identity()));

    /**
     * 扩展名到枚举的映射
     */
    private static final Map<String, ImageType> EXTENSION_MAP = Arrays.stream(values())
            .flatMap(type -> Arrays.stream(type.getExtensions())
                    .map(ext -> Map.entry(ext.toLowerCase(), type)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    ImageType(String mimeType, String... extensions) {
        this.mimeType = mimeType;
        this.extensions = extensions;
    }

    /**
     * 根据MIME类型获取图片类型
     *
     * @param mimeType MIME类型字符串
     * @return 对应的图片类型，如果未找到则返回UNKNOWN
     */
    public static ImageType fromMimeType(String mimeType) {
        if (mimeType == null || mimeType.trim().isEmpty()) {
            return UNKNOWN;
        }
        return MIME_TYPE_MAP.getOrDefault(mimeType.trim().toLowerCase(), UNKNOWN);
    }

    /**
     * 根据文件扩展名获取图片类型
     *
     * @param extension 文件扩展名（不带点）
     * @return 对应的图片类型，如果未找到则返回UNKNOWN
     */
    public static ImageType fromExtension(String extension) {
        if (extension == null || extension.trim().isEmpty()) {
            return UNKNOWN;
        }
        // 移除可能的前导点
        String ext = extension.trim().toLowerCase();
        if (ext.startsWith(".")) {
            ext = ext.substring(1);
        }
        return EXTENSION_MAP.getOrDefault(ext, UNKNOWN);
    }

    /**
     * 根据文件名获取图片类型
     *
     * @param fileName 文件名
     * @return 对应的图片类型，如果未找到则返回UNKNOWN
     */
    public static ImageType fromFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return UNKNOWN;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return UNKNOWN;
        }
        String extension = fileName.substring(dotIndex + 1);
        return fromExtension(extension);
    }

    /**
     * 判断是否为支持的图片类型
     *
     * @param mimeType MIME类型
     * @return 如果支持返回true，否则返回false
     */
    public static boolean isSupported(String mimeType) {
        return fromMimeType(mimeType) != UNKNOWN;
    }

    /**
     * 获取默认扩展名
     *
     * @return 第一个扩展名，如果没有扩展名则返回空字符串
     */
    public String getDefaultExtension() {
        return extensions.length > 0 ? extensions[0] : "";
    }

    /**
     * 判断是否为图片类型
     *
     * @return 如果是图片类型返回true，否则返回false
     */
    public boolean isImage() {
        return this != UNKNOWN;
    }
}
