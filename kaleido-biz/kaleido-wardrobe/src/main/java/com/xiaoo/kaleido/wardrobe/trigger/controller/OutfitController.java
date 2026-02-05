package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.api.wardrobe.command.RecordOutfitWearCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateOutfitCommand;
import com.xiaoo.kaleido.api.wardrobe.response.OutfitInfoResponse;
import com.xiaoo.kaleido.api.wardrobe.response.WearRecordResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import com.xiaoo.kaleido.wardrobe.application.command.OutfitCommandService;
import com.xiaoo.kaleido.wardrobe.application.query.IOutfitQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 穿搭API控制器
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wardrobe/outfits")
@RequiredArgsConstructor
public class OutfitController {

    private final OutfitCommandService outfitCommandService;
    private final IOutfitQueryService outfitQueryService;

    /**
     * 创建穿搭（包含服装和图片）
     *
     * @param command 创建穿搭命令
     * @return 创建的穿搭ID
     */
    @PostMapping
    public Result<String> createOutfit(@Valid @RequestBody CreateOutfitWithClothingsCommand command) {
        String userId = StpUserUtil.getLoginId();
        String outfitId = outfitCommandService.createOutfitWithClothingsAndImages(userId, command);
        return Result.success(outfitId);
    }

    /**
     * 更新穿搭信息（包含服装和图片）
     *
     * @param outfitId 穿搭ID，不能为空
     * @param command  更新穿搭命令
     * @return 空响应
     */
    @PutMapping("/{outfitId}")
    public Result<Void> updateOutfit(
            @NotBlank(message = "穿搭ID不能为空")
            @PathVariable String outfitId,
            @Valid @RequestBody UpdateOutfitCommand command) {
        String userId = StpUserUtil.getLoginId();
        command.setOutfitId(outfitId);
        outfitCommandService.updateOutfit(userId, command);
        return Result.success();
    }

    /**
     * 删除穿搭
     *
     * @param outfitId 穿搭ID，不能为空
     * @param userId   用户ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{outfitId}")
    public Result<Void> deleteOutfit(
            @NotBlank(message = "穿搭ID不能为空")
            @PathVariable String outfitId,
            @NotBlank(message = "用户ID不能为空")
            @RequestParam String userId) {
        outfitCommandService.deleteOutfit(outfitId, userId);
        return Result.success();
    }

    /**
     * 记录穿搭穿着
     *
     * @param outfitId 穿搭ID，不能为空
     * @param command  记录穿着命令
     * @return 空响应
     */
    @PostMapping("/{outfitId}/wear")
    public Result<Void> recordOutfitWear(
            @NotBlank(message = "穿搭ID不能为空")
            @PathVariable String outfitId,
            @Valid @RequestBody RecordOutfitWearCommand command) {
        command.setOutfitId(outfitId);
        String userId = StpUserUtil.getLoginId();
        outfitCommandService.recordOutfitWear(userId,command);
        return Result.success();
    }

    /**
     * 查询用户穿搭列表
     *
     * @return 穿搭信息响应列表
     */
    @GetMapping("/list")
    public Result<List<OutfitInfoResponse>> listOutfits() {
        String userId = StpUserUtil.getLoginId();
        List<OutfitInfoResponse> outfitList = outfitQueryService.findByUserId(userId);
        return Result.success(outfitList);
    }

    /**
     * 根据ID查询穿搭详情
     *
     * @param outfitId 穿搭ID，不能为空
     * @return 穿搭信息响应
     */
    @GetMapping("/{outfitId}")
    public Result<OutfitInfoResponse> getOutfit(
            @NotBlank(message = "穿搭ID不能为空")
            @PathVariable String outfitId) {
        String userId = StpUserUtil.getLoginId();
        OutfitInfoResponse outfitInfo = outfitQueryService.findById(outfitId, userId);
        return Result.success(outfitInfo);
    }

    /**
     * 查询穿搭穿着记录
     *
     * @param outfitId 穿搭ID，不能为空
     * @return 穿着记录响应列表
     */
    @GetMapping("/{outfitId}/records")
    public Result<List<WearRecordResponse>> getOutfitWearRecords(
            @NotBlank(message = "穿搭ID不能为空")
            @PathVariable String outfitId) {
        String userId = StpUserUtil.getLoginId();
        List<WearRecordResponse> wearRecords = outfitQueryService.findWearRecordsByOutfitId(outfitId, userId);
        return Result.success(wearRecords);
    }
}
