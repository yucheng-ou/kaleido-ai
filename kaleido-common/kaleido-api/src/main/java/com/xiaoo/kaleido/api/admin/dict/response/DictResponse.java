package com.xiaoo.kaleido.api.admin.dict.response;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 字典响应对象
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@Schema(description = "字典响应对象")
public class DictResponse {

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private String id;

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String typeName;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private DataStatusEnum status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updatedAt;
}
