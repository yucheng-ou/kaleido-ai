package com.xiaoo.kaleido.file.model;

import lombok.Data;

/**
 * 图片信息
 */
@Data
public class ImageInfo {

    /**
     * 图片对象名称
     */
    private String objectName;

    /**
     * 图片类型（如 JPEG、PNG、GIF 等）
     */
    private String imageType;

    /**
     * 文件大小（字节）
     */
    private long fileSize;

    /**
     * 图片宽度（像素）
     */
    private int width;

    /**
     * 图片高度（像素）
     */
    private int height;

    /**
     * MIME 类型
     */
    private String mimeType;

    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后修改时间
     */
    private String lastModified;

}
