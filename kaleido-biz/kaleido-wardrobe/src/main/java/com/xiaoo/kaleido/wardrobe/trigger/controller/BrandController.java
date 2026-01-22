package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.wardrobe.application.query.IBrandQueryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌API控制器（普通用户使用）
 * 只提供读操作接口，写操作由管理员在Admin模块处理
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

    private final IBrandQueryService brandQueryService;

    /**
     * 查询所有品牌列表
     *
     * @return 品牌信息响应列表
     */
    @GetMapping
    public Result<List<BrandInfoResponse>> listBrands() {
        return Result.success(brandQueryService.findAll());
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
        return Result.success(brandQueryService.findById(brandId));
    }
}
