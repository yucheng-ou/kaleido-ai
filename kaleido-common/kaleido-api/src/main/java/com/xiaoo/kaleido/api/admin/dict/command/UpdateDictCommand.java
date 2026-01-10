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
@Value
@EqualsAndHashCode(callSuper = true)
@Builder
public class UpdateDictCommand extends BaseCommand {

    /**
     * 字典类型名称
     */
    String typeName;

    /**
     * 字典名称
     */
    String dictName;

    /**
     * 字典值
     */
    String dictValue;

    /**
     * 排序
     */
    Integer sort;
}
