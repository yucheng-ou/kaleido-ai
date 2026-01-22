package com.xiaoo.kaleido.api.file.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取文件上传URL响应
 * <p>
 * 返回文件上传的预签名URL和文件路径信息
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUploadUrlResponse {

    /**
     * 预签名URL

     * 用于前端直接上传文件到MinIO的URL
     */
    private String presignedUrl;

    /**
     * 对象名称

     * 文件在MinIO中的存储路径，格式：yyyyMMdd/fileHash.extension
     */
    private String objectName;

    /**
     * 文件路径

     * 完整的文件访问路径，包含MinIO的基础URL
     */
    private String filePath;
}
