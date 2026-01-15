package com.xiaoo.kaleido.api.tag.query;

import com.xiaoo.kaleido.base.request.BasePageReq;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 标签列表查询请求
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagListQueryReq extends BasePageReq {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 标签类型编码
     * 关联字典表t_dict.dict_code，字典类型为TAG_TYPE
     */
    @NotBlank(message = "标签类型编码不能为空")
    private String typeCode;
}
