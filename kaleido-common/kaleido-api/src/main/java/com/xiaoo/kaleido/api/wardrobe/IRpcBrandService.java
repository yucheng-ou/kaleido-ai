package com.xiaoo.kaleido.api.wardrobe;

import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 品牌RPC服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 * @dubbo
 */
public interface IRpcBrandService {

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    Result<String> createBrand(@Valid CreateBrandCommand command);

    /**
     * 更新品牌信息
     *
     * @param brandId 品牌ID，不能为空
     * @param command 更新品牌命令
     * @return 空响应
     */
    Result<Void> updateBrand(@NotBlank String brandId, @Valid UpdateBrandCommand command);

    /**
     * 删除品牌（逻辑删除）
     *
     * @param brandId 品牌ID，不能为空
     * @return 空响应
     */
    Result<Void> deleteBrand(@NotBlank String brandId);

    /**
     * 查询所有品牌列表
     *
     * @return 品牌信息响应列表
     */
    Result<List<BrandInfoResponse>> getAllBrands();

    /**
     * 根据ID查询品牌详情
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌信息响应
     */
    Result<BrandInfoResponse> getBrandById(@NotBlank String brandId);
}
