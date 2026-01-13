package com.xiaoo.kaleido.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author ouyucheng
 * @date 2025/11/3 16:50
 * @description 基础分页请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小
     */
    public static final int MAX_PAGE_SIZE = 1000;

    /**
     * 页码
     */
    private Integer pageNum = DEFAULT_PAGE_NUM;

    /**
     * 分页大小
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 验证分页参数
     */
    @Override
    public void validate() {
        super.validate();
        
        // 确保页码合法
        if (pageNum == null || pageNum < 1) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        
        // 确保分页大小合法
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        
        // 限制最大分页大小
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
    }

    /**
     * 获取偏移量
     *
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 创建分页请求
     *
     * @param pageNum 页码
     * @param pageSize 分页大小
     * @return 分页请求实例
     */
    public static BasePageReq of(Integer pageNum, Integer pageSize) {
        BasePageReq req = new BasePageReq();
        req.setPageNum(pageNum);
        req.setPageSize(pageSize);
        req.validate();
        return req;
    }
}
