package com.xiaoo.kaleido.admin.dict.trigger.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新字典请求
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@Schema(description = "更新字典请求")
public class UpdateDictRequest {

    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", required = true, example = "男")
    private String dictName;

    @Schema(description = "字典值", example = "1")
    private String dictValue;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "字典类型名称", example = "性别")
    private String typeName;
}
