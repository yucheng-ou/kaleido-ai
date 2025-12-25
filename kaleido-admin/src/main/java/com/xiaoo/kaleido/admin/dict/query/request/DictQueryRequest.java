package com.xiaoo.kaleido.admin.dict.query.request;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import lombok.Data;

/**
 * 字典查询请求
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
public class DictQueryRequest {

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典类型名称（模糊查询）
     */
    private String typeName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称（模糊查询）
     */
    private String dictName;

    /**
     * 字典值（模糊查询）
     */
    private String dictValue;

    /**
     * 状态
     */
    private DataStatusEnum status;
}
