package com.xiaoo.kaleido.user.query.request;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import lombok.Data;

/**
 * 分页用户查询请求
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class PageUserQueryRequest extends UserQueryRequest {

    /**
     * 页码，从1开始
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
