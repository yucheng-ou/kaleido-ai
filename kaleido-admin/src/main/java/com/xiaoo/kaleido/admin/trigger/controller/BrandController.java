package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaoo.kaleido.admin.application.command.IBrandCommandService;
import com.xiaoo.kaleido.admin.application.query.IBrandQueryService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理API（管理后台）
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/wardrobe/brand")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandCommandService brandCommandService;
    private final IBrandQueryService brandQueryService;

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    @SaCheckPermission(value = "admin:wardrobe:brand:update", type = StpAdminUtil.TYPE)
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
    @SaCheckPermission(value = "admin:wardrobe:brand:update", type = StpAdminUtil.TYPE)
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
    @SaCheckPermission(value = "admin:wardrobe:brand:delete", type = StpAdminUtil.TYPE)
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
    @SaCheckPermission(value = "admin:wardrobe:brand:read", type = StpAdminUtil.TYPE)
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
    @SaCheckPermission(value = "admin:wardrobe:brand:read", type = StpAdminUtil.TYPE)
    @GetMapping("/{brandId}")
    public Result<BrandInfoResponse> getBrand(
            @NotBlank(message = "品牌ID不能为空")
            @PathVariable String brandId) {
        BrandInfoResponse brandInfo = brandQueryService.findById(brandId);
        return Result.success(brandInfo);
    }
}
