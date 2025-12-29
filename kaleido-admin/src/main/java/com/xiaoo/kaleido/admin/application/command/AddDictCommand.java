package com.xiaoo.kaleido.admin.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加字典命令
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDictCommand {

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 字典编码
     */
    private String dictCode;

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
