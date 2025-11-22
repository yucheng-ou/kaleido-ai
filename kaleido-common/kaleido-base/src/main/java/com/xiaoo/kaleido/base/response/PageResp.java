package com.xiaoo.kaleido.base.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 16:24
 * @Description 分页响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResp<T> extends MultipleResp<T> {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 当前页
     */
    private long pageNum;

    /**
     * 分页大小
     */
    private long pageSize;

    /**
     * 数据总数
     */
    private long total;

    /**
     * 创建分页成功响应
     *
     * @param list     数据列表
     * @param total    数据总数
     * @param pageNum  当前页码
     * @param pageSize 分页大小
     * @param <T>      数据类型
     * @return 分页响应对象
     */
    public static <T> PageResp<T> success(List<T> list, long total, long pageNum, long pageSize) {
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setSuccess(true);
        pageResp.setTotal(total);
        pageResp.setPageNum(pageNum);
        pageResp.setPageSize(pageSize);
        pageResp.setList(list);
        //分页计算
        pageResp.setTotalPage(pageSize == 0 ? 0 : (long) Math.ceil((double) total / pageSize));
        return pageResp;
    }
}
