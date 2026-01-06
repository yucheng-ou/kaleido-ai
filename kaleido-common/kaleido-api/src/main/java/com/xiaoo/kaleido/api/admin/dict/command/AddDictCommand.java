package com.xiaoo.kaleido.api.admin.dict.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加字典命令
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "添加字典命令")
public class AddDictCommand extends BaseCommand {

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空")
    @Schema(description = "字典类型编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "GENDER")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典类型名称不能为空")
    @Schema(description = "字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "性别")
    private String typeName;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Schema(description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "MALE")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "男")
    private String dictName;

    /**
     * 字典值
     */
    @Schema(description = "字典值", example = "1")
    private String dictValue;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;
}
