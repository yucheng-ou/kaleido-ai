package com.xiaoo.kaleido.api.tag.response;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;

/**
 * 标签信息响应
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagInfoResponse extends BaseResp {

    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型编码
     */
    private String typeCode;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 使用次数
     */
    private Integer usageCount;

    /**
     * 标签状态
     */
    private DataStatusEnum status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
