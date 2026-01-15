package com.xiaoo.kaleido.tag.trigger.controller;

import com.xiaoo.kaleido.api.tag.command.CreateTagCommand;
import com.xiaoo.kaleido.api.tag.command.UpdateTagCommand;
import com.xiaoo.kaleido.api.tag.response.TagInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.tag.application.command.TagCommandService;
import com.xiaoo.kaleido.tag.application.query.TagQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签API控制器
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagCommandService tagCommandService;
    private final TagQueryService tagQueryService;

    /**
     * 创建标签
     *
     * @param command 创建标签命令
     * @return 空响应
     */
    @PostMapping
    public Result<Void> createTag(@Valid @RequestBody CreateTagCommand command) {
        tagCommandService.createTag(command);
        return Result.success();
    }

    /**
     * 更新标签
     *
     * @param tagId   标签ID，不能为空
     * @param command 更新标签命令
     * @return 空响应
     */
    @PutMapping("/{tagId}")
    public Result<Void> updateTag(
            @NotBlank(message = "标签ID不能为空")
            @PathVariable String tagId,
            @Valid @RequestBody UpdateTagCommand command) {
        tagCommandService.updateTag(tagId, command);
        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param tagId 标签ID，不能为空
     * @return 空响应
     */
    @DeleteMapping("/{tagId}")
    public Result<Void> deleteTag(
            @NotBlank(message = "标签ID不能为空")
            @PathVariable String tagId) {
        tagCommandService.deleteTag(tagId);
        return Result.success();
    }

    /**
     * 启用标签
     *
     * @param tagId 标签ID，不能为空
     * @return 空响应
     */
    @PutMapping("/{tagId}/enable")
    public Result<Void> enableTag(
            @NotBlank(message = "标签ID不能为空")
            @PathVariable String tagId) {
        tagCommandService.enableTag(tagId);
        return Result.success();
    }

    /**
     * 禁用标签
     *
     * @param tagId 标签ID，不能为空
     * @return 空响应
     */
    @PutMapping("/{tagId}/disable")
    public Result<Void> disableTag(
            @NotBlank(message = "标签ID不能为空")
            @PathVariable String tagId) {
        tagCommandService.disableTag(tagId);
        return Result.success();
    }

    /**
     * 根据ID查询标签
     *
     * @param tagId 标签ID，不能为空
     * @return 标签信息响应
     */
    @GetMapping("/{tagId}")
    public Result<TagInfoResponse> getTag(
            @NotBlank(message = "标签ID不能为空")
            @PathVariable String tagId) {
        TagInfoResponse tagInfo = tagQueryService.findById(tagId);
        return Result.success(tagInfo);
    }

    /**
     * 查询标签列表
     *
     * @param userId   用户ID，不能为空
     * @param typeCode 标签类型编码，不能为空
     * @return 标签信息响应列表
     */
    @GetMapping("/list")
    public Result<List<TagInfoResponse>> listTags(
            @NotBlank(message = "用户ID不能为空")
            @RequestParam String userId,
            @NotBlank(message = "标签类型编码不能为空")
            @RequestParam String typeCode) {
        List<TagInfoResponse> tagList = tagQueryService.findByUserIdAndTypeCode(userId, typeCode);
        return Result.success(tagList);
    }
}
