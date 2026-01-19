package com.xiaoo.kaleido.api.file.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取文件上传URL命令
 * <p>
 * 用于获取文件上传的预签名URL
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUploadUrlCommand {

    /**
     * 原始文件名
     * <p>
     * 用于获取文件扩展名，生成完整的对象名称
     */
    @NotBlank(message = "文件名不能为空")
    @Size(max = 255, message = "文件名长度不能超过255个字符")
    private String fileName;
}
