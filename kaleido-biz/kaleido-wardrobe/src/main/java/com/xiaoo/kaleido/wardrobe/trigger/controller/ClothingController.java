package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.command.CreateClothingWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateClothingCommand;
import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.wardrobe.application.command.ClothingCommandService;
import com.xiaoo.kaleido.wardrobe.application.query.IClothingQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服装API控制器
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wardrobe/clothing")
@RequiredArgsConstructor
public class ClothingController {

    private final ClothingCommandService clothingCommandService;
    private final IClothingQueryService clothingQueryService;

    /**
     * 创建服装（包含图片）
     *
     * @param command 创建服装命令
     * @return 创建的服装ID
     */
    @PostMapping
    public Result<String> createClothing(@Valid @RequestBody CreateClothingWithImagesCommand command) {
        String userId = StpUserUtil.getLoginId();
        String clothingId = clothingCommandService.createClothingWithImages(userId, command);
        return Result.success(clothingId);
    }

    /**
     * 更新服装信息（包含图片）
     *
     * @param command 更新服装命令
     * @return 空响应
     */
    @PutMapping("/{clothingId}")
    public Result<Void> updateClothing(@Valid @RequestBody UpdateClothingCommand command) {
        String userId = StpUserUtil.getLoginId();
        clothingCommandService.updateClothing(userId, command);
        return Result.success();
    }

    /**
     * 删除服装
     *
     * @param clothingId 服装ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{clothingId}")
    public Result<Void> deleteClothing(
            @NotBlank(message = "服装ID不能为空")
            @PathVariable String clothingId) {
        String userId = StpUserUtil.getLoginId();
        clothingCommandService.deleteClothing(clothingId, userId);
        return Result.success();
    }

    /**
     * 查询用户服装列表
     *
     * @return 服装信息响应列表
     */
    @GetMapping("/list")
    public Result<List<ClothingInfoResponse>> listClothing() {
        List<ClothingInfoResponse> clothingList = clothingQueryService.findByUserId(StpUserUtil.getLoginId());
        return Result.success(clothingList);
    }

    /**
     * 根据ID查询服装详情
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装信息响应
     */
    @GetMapping("/{clothingId}")
    public Result<ClothingInfoResponse> getClothing(
            @NotBlank(message = "服装ID不能为空")
            @PathVariable String clothingId) {
        String userId = StpUserUtil.getLoginId();
        ClothingInfoResponse clothingInfo = clothingQueryService.findById(clothingId, userId);
        return Result.success(clothingInfo);
    }
}
