package com.xiaoo.kaleido.wardrobe.trigger.rpc;

import com.xiaoo.kaleido.api.wardrobe.IRpcBrandService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.application.command.BrandCommandService;
import com.xiaoo.kaleido.wardrobe.application.query.IBrandQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 品牌RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcBrandServiceImpl implements IRpcBrandService {

    private final BrandCommandService brandCommandService;
    private final IBrandQueryService brandQueryService;

    @Override
    public Result<String> createBrand(@Valid CreateBrandCommand command) {
        log.info("RPC创建品牌，品牌名称: {}", command.getName());
        
        String brandId = brandCommandService.createBrand(command);
        return Result.success(brandId);
    }

    @Override
    public Result<Void> updateBrand(@NotBlank String brandId, @Valid UpdateBrandCommand command) {
        log.info("RPC更新品牌，品牌ID: {}", brandId);
        
        brandCommandService.updateBrand(brandId, command);
        return Result.success();
    }

    @Override
    public Result<Void> deleteBrand(@NotBlank String brandId) {
        log.info("RPC删除品牌，品牌ID: {}", brandId);
        
        brandCommandService.deleteBrand(brandId);
        return Result.success();
    }

    @Override
    public Result<List<BrandInfoResponse>> getAllBrands() {
        log.info("RPC查询所有品牌列表");
        
        List<BrandInfoResponse> brandList = brandQueryService.findAll();
        return Result.success(brandList);
    }

    @Override
    public Result<BrandInfoResponse> getBrandById(@NotBlank String brandId) {
        log.info("RPC查询品牌详情，品牌ID: {}", brandId);
        
        BrandInfoResponse brandInfo = brandQueryService.findById(brandId);
        return Result.success(brandInfo);
    }
}
