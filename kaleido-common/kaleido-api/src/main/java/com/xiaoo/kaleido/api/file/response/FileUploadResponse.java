package com.xiaoo.kaleido.api.file.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应
 * <p>
 * 返回文件上传成功后的信息
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    /**
     * 对象名称
     * 文件在MinIO中的存储路径，格式：yyyyMMdd/fileHash.extension
     */
    private String objectName;

    /**
     * 文件URL

     * 文件的完整访问URL
     */
    private String fileUrl;

    /**
     * 原始文件名
     * 用户上传时的原始文件名
     */
    private String originalName;

    /**
     * 文件大小
     * 文件大小，单位：字节
     */
    private Long fileSize;
}
