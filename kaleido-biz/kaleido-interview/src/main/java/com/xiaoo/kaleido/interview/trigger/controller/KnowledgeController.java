package com.xiaoo.kaleido.interview.trigger.controller;

import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.interview.application.command.KnowledgeCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业知识文档管理控制器
 *
 * @author ouyucheng
 * @date 2026/3/1
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeCommandService knowledgeCommandService;

    /**
     * 企业知识文档上传接口
     *
     * @param file 文档文件（PDF/Word/Txt）
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result<String> uploadKnowledge(@RequestParam("file") MultipartFile file) {
        log.info("收到企业知识文档上传请求，文件名: {}", file.getOriginalFilename());
        String message = knowledgeCommandService.uploadKnowledge(file);
        return Result.success(message);
    }
}
