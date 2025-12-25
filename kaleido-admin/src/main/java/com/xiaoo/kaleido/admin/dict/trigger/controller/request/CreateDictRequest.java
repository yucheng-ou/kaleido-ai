package com.xiaoo.kaleido.admin.dict.trigger.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建字典请求
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@Schema(description = "创建字典请求")
public class CreateDictRequest {

    @NotBlank(message = "字典类型编码不能为空")
    @Schema(description = "字典类型编码", required = true, example = "GENDER")
    private String typeCode;

    @NotBlank(message = "字典类型名称不能为空")
    @Schema(description = "字典类型名称", required = true, example = "性别")
    private String typeName;

    @NotBlank(message = "字典编码不能为空")
    @Schema(description = "字典编码", required = true, example = "MALE")
    private String dictCode;

    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", required = true, example = "男")
    private String dictName;

    @Schema(description = "字典值", example = "1")
    private String dictValue;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序", required = true, example = "1")
    private Integer sort;
}
