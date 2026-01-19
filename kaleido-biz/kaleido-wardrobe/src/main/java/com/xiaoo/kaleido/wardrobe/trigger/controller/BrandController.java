package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.wardrobe.application.command.BrandCommandService;
import com.xiaoo.kaleido.wardrobe.application.query.IBrandQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌API控制器
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wardrobe/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandCommandService brandCommandService;
    private final IBrandQueryService brandQueryService;

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    @PostMapping
    public Result<String> createBrand(@Valid @RequestBody CreateBrandCommand command) {
        String brandId = brandCommandService.createBrand(command);
        return Result.success(brandId);
    }

    /**
     * 更新品牌信息
     *
     * @param brandId 品牌ID，不能为空
     * @param command 更新品牌命令
     * @return 空响应
     */
    @PutMapping("/{brandId}")
    public Result<Void> updateBrand(
            @NotBlank(message = "品牌ID不能为空")
            @PathVariable String brandId,
            @Valid @RequestBody UpdateBrandCommand command) {
        brandCommandService.updateBrand(brandId, command);
        return Result.success();
    }

    /**
     * 删除品牌（逻辑删除）
     *
     * @param brandId 品牌ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{brandId}")
    public Result<Void> deleteBrand(
            @NotBlank(message = "品牌ID不能为空")
            @PathVariable String brandId) {
        brandCommandService.deleteBrand(brandId);
        return Result.success();
    }

    /**
     * 查询所有品牌列表
     *
     * @return 品牌信息响应列表
     */
    @GetMapping
    public Result<List<BrandInfoResponse>> listBrands() {
        List<BrandInfoResponse> brandList = brandQueryService.findAll();
        return Result.success(brandList);
    }

    /**
     * 根据ID查询品牌详情
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌信息响应
     */
    @GetMapping("/{brandId}")
    public Result<BrandInfoResponse> getBrand(
            @NotBlank(message = "品牌ID不能为空")
            @PathVariable String brandId) {
        BrandInfoResponse brandInfo = brandQueryService.findById(brandId);
        return Result.success(brandInfo);
    }
}
