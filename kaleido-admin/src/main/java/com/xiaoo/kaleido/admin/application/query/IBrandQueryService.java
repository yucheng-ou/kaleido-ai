package com.xiaoo.kaleido.admin.application.query;

import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;

import java.util.List;

/**
 * 品牌查询服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IBrandQueryService {

    /**
     * 查询所有品牌列表
     *
     * @return 品牌信息响应列表
     */
    List<BrandInfoResponse> findAll();

    /**
     * 根据ID查询品牌详情
     *
     * @param brandId 品牌ID
     * @return 品牌信息响应
     */
    BrandInfoResponse findById(String brandId);
}
