package com.xiaoo.kaleido.admin.trigger.controller;

import com.xiaoo.kaleido.admin.application.command.FileCommandService;
import com.xiaoo.kaleido.api.file.command.GetUploadUrlCommand;
import com.xiaoo.kaleido.api.file.response.FileUploadResponse;
import com.xiaoo.kaleido.api.file.response.GetUploadUrlResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 * <p>
 * 负责文件上传相关的HTTP接口
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/public/file")
@RequiredArgsConstructor
public class FileController {

    private final FileCommandService fileCommandService;

    /**
     * 获取文件上传URL
     * <p>
     * 为前端提供获取文件上传预签名URL的接口
     *
     * @param command 获取上传URL命令
     * @return 上传URL响应
     */
    @GetMapping("/upload-url")
    public Result<GetUploadUrlResponse> getUploadUrl(
            @Valid GetUploadUrlCommand command) {
        
        log.info("接收到获取文件上传URL请求，文件名: {}", command.getFileName());
        GetUploadUrlResponse response = fileCommandService.getUploadUrl(command);
        return Result.success(response);
    }

    /**
     * 上传文件
     * <p>
     * 接收前端上传的文件，保存到MinIO
     *
     * @param file 上传的文件
     * @return 文件上传响应
     */
    @PostMapping("/upload")
    public Result<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        
        log.info("接收到文件上传请求，原始文件名: {}, 文件大小: {} bytes", 
                file.getOriginalFilename(), file.getSize());
        FileUploadResponse response = fileCommandService.uploadFile(file);
        return Result.success(response);
    }
}
