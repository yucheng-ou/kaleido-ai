package com.xiaoo.kaleido.wardrobe.application.query;

import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;

import java.util.List;

/**
 * 品牌查询服务接口
 * <p>
 * 品牌应用层查询服务，负责品牌相关的读操作，包括品牌信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
public interface IBrandQueryService {

    /**
     * 查询所有品牌列表
     * <p>
     * 查询所有品牌列表，包括启用和禁用的品牌
     *
     * @return 品牌信息响应列表，如果不存在则返回空列表
     */
    List<BrandInfoResponse> findAll();

    /**
     * 根据ID查询品牌信息
     * <p>
     * 根据品牌ID查询品牌详细信息，如果品牌不存在则返回null
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌信息响应，如果品牌不存在则返回null
     */
    BrandInfoResponse findById(String brandId);

}
