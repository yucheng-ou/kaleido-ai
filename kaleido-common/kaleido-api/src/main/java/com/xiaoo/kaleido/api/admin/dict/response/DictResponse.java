package com.xiaoo.kaleido.api.admin.dict.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;

/**
 * 字典响应对象
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictResponse extends BaseResp {

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

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
