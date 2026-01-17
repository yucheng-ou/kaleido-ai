package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.command.CreateClothingWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateClothingCommand;
import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
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
        String clothingId = clothingCommandService.createClothingWithImages(command);
        return Result.success(clothingId);
    }

    /**
     * 更新服装信息（包含图片）
     *
     * @param clothingId 服装ID，不能为空
     * @param command 更新服装命令
     * @return 空响应
     */
    @PutMapping("/{clothingId}")
    public Result<Void> updateClothing(
            @NotBlank(message = "服装ID不能为空")
            @PathVariable String clothingId,
            @Valid @RequestBody UpdateClothingCommand command) {
        command.setClothingId(clothingId);
        clothingCommandService.updateClothing(command);
        return Result.success();
    }

    /**
     * 删除服装
     *
     * @param clothingId 服装ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{clothingId}")
    public Result<Void> deleteClothing(
            @NotBlank(message = "服装ID不能为空")
            @PathVariable String clothingId,
            @NotBlank(message = "用户ID不能为空")
            @RequestParam String userId) {
        clothingCommandService.deleteClothing(clothingId, userId);
        return Result.success();
    }

    /**
     * 根据用户ID查询服装列表
     *
     * @param userId 用户ID，不能为空
     * @return 服装信息响应列表
     */
    @GetMapping("/list")
    public Result<List<ClothingInfoResponse>> listClothing(
            @NotBlank(message = "用户ID不能为空")
            @RequestParam String userId) {
        List<ClothingInfoResponse> clothingList = clothingQueryService.findByUserId(userId);
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
        ClothingInfoResponse clothingInfo = clothingQueryService.findById(clothingId);
        return Result.success(clothingInfo);
    }
}
