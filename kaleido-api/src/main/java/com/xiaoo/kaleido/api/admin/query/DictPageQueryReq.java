package com.xiaoo.kaleido.api.admin.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Schema(description = "字典分页查询请求")
public class DictPageQueryReq extends DictQueryReq {

    /**
     * 页码，从1开始
     */
    @Schema(description = "页码，从1开始", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    @Schema(description = "每页大小", example = "10")
    private Integer pageSize = 10;
}
