package com.xiaoo.kaleido.api.admin.response;

import lombok.Data;

/**
 * 字典响应对象
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
public class DictResponse {

    /**
     * 字典ID
     */
    private String id;

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
}
