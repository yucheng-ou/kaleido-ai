package com.xiaoo.kaleido.wardrobe.trigger.controller;

import com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand;
import com.xiaoo.kaleido.api.wardrobe.response.LocationInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.wardrobe.application.command.LocationCommandService;
import com.xiaoo.kaleido.wardrobe.application.query.ILocationQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 位置API控制器
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wardrobe/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationCommandService locationCommandService;
    private final ILocationQueryService locationQueryService;

    /**
     * 创建位置
     *
     * @param command 创建位置命令
     * @return 创建的位置ID
     */
    @PostMapping
    public Result<String> createLocation(@Valid @RequestBody CreateLocationWithImagesCommand command) {
        String locationId = locationCommandService.createLocation(command);
        return Result.success(locationId);
    }

    /**
     * 更新位置信息
     *
     * @param locationId 位置ID，不能为空
     * @param command 更新位置命令
     * @return 空响应
     */
    @PutMapping("/{locationId}")
    public Result<Void> updateLocation(
            @NotBlank(message = "位置ID不能为空")
            @PathVariable String locationId,
            @Valid @RequestBody UpdateLocationCommand command) {
        command.setLocationId(locationId);
        locationCommandService.updateLocation(command);
        return Result.success();
    }

    /**
     * 删除位置（逻辑删除）
     *
     * @param locationId 位置ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{locationId}")
    public Result<Void> deleteLocation(
            @NotBlank(message = "位置ID不能为空")
            @PathVariable String locationId) {
        locationCommandService.deleteLocation(locationId);
        return Result.success();
    }

    /**
     * 根据用户ID查询位置列表
     *
     * @param userId 用户ID，不能为空
     * @return 位置信息响应列表
     */
    @GetMapping
    public Result<List<LocationInfoResponse>> listLocations(
            @NotBlank(message = "用户ID不能为空")
            @RequestParam String userId) {
        List<LocationInfoResponse> locationList = locationQueryService.findByUserId(userId);
        return Result.success(locationList);
    }

    /**
     * 根据ID查询位置详情
     *
     * @param locationId 位置ID，不能为空
     * @return 位置信息响应
     */
    @GetMapping("/{locationId}")
    public Result<LocationInfoResponse> getLocation(
            @NotBlank(message = "位置ID不能为空")
            @PathVariable String locationId) {
        LocationInfoResponse locationInfo = locationQueryService.findById(locationId);
        return Result.success(locationInfo);
    }

    /**
     * 设置位置主图
     *
     * @param locationId 位置ID，不能为空
     * @param imageId 图片ID，不能为空
     * @return 空响应
     */
    @PutMapping("/{locationId}/primary-image/{imageId}")
    public Result<Void> setPrimaryImage(
            @NotBlank(message = "位置ID不能为空")
            @PathVariable String locationId,
            @NotBlank(message = "图片ID不能为空")
            @PathVariable String imageId) {
        locationCommandService.setPrimaryImage(locationId, imageId);
        return Result.success();
    }
}
