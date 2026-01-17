package com.xiaoo.kaleido.api.admin.dict.query;
import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.*;


/**
 * 字典分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class DictPageQueryReq extends BasePageReq {

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

}
