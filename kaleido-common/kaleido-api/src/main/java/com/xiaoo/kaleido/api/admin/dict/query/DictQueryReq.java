package com.xiaoo.kaleido.api.admin.dict.query;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.request.BaseReq;
import lombok.*;

/**
 * 字典查询请求
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictQueryReq extends BaseReq {

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
