package com.xiaoo.kaleido.api.admin.dict.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 添加字典命令
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class AddDictCommand extends BaseCommand {

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典类型名称不能为空")
    private String typeName;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;
}
