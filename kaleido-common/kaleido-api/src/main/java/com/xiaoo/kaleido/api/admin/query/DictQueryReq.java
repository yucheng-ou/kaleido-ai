package com.xiaoo.kaleido.api.admin.query;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典查询请求
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Schema(description = "字典查询请求")
public class DictQueryReq {

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    private String typeCode;

    /**
     * 字典类型名称（模糊查询）
     */
    @Schema(description = "字典类型名称（模糊查询）")
    private String typeName;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称（模糊查询）
     */
    @Schema(description = "字典名称（模糊查询）")
    private String dictName;

    /**
     * 字典值（模糊查询）
     */
    @Schema(description = "字典值（模糊查询）")
    private String dictValue;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private DataStatusEnum status;
}
