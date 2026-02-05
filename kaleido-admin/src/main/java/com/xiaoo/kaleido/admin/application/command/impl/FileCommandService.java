package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IFileCommandService;
import com.xiaoo.kaleido.api.file.response.FileUploadResponse;
import com.xiaoo.kaleido.file.service.IMinIOService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

/**
 * 文件命令服务（应用层）
 * 负责文件上传相关的业务逻辑编排
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileCommandService implements IFileCommandService {

    private final IMinIOService minIOService;

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件上传响应
     */
    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {
        try {
            log.info("开始上传文件，原始文件名: {}, 文件大小: {} bytes",
                    file.getOriginalFilename(), file.getSize());

            // 1. 生成UUID作为文件标识
            String fileIdentifier = UUID.randomUUID().toString();

            // 2. 生成对象名称
            String originalFilename = Optional.ofNullable(file.getOriginalFilename())
                    .orElse("unknown");
            String objectName = generateObjectName(originalFilename, fileIdentifier);

            // 3. 获取文件内容类型
            String contentType = file.getContentType();

            // 4. 上传文件到MinIO
            minIOService.uploadFile(file, objectName, contentType);

            // 5. 获取文件访问URL
            String basisUrl = minIOService.getBasisUrl();
            String fileUrl = basisUrl + objectName;

            // 6. 构建响应
            FileUploadResponse response = FileUploadResponse.builder()
                    .objectName(objectName)
                    .fileUrl(fileUrl)
                    .originalName(originalFilename)
                    .fileSize(file.getSize())
                    .build();

            log.info("文件上传成功，对象名称: {}, 文件URL: {}", objectName, fileUrl);
            return response;

        } catch (Exception e) {
            log.error("文件上传失败，原始文件名: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    /**
     * 生成对象名称（基于文件标识）
     *
     * @param fileName       原始文件名
     * @param fileIdentifier 文件标识（UUID）
     * @return 对象名称
     */
    private String generateObjectName(String fileName, String fileIdentifier) {
        // 1. 获取文件扩展名
        String extension = getFileExtension(fileName);

        // 2. 生成日期目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String dateDir = dateFormat.format(new Date());

        // 3. 构建对象名称：日期目录/文件标识.扩展名
        return String.format("%s/%s%s", dateDir, fileIdentifier, extension);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名（包含点，如 .jpg、.png）
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
