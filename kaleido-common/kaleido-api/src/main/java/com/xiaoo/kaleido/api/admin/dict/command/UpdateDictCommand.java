package com.xiaoo.kaleido.api.admin.dict.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 更新字典命令
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class UpdateDictCommand extends BaseCommand {

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer sort;
}
