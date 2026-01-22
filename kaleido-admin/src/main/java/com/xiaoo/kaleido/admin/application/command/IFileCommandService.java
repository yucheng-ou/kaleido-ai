package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.file.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IFileCommandService {

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件上传响应
     */
    FileUploadResponse uploadFile(MultipartFile file);
}
