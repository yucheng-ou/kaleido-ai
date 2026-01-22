package com.xiaoo.kaleido.tag.trigger.controller;

import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.CreateTagCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.UpdateTagCommand;
import com.xiaoo.kaleido.api.tag.response.TagInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
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
        String userId = StpUserUtil.getLoginId();
        tagCommandService.createTag(userId, command);
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
        String userId = StpUserUtil.getLoginId();
        tagCommandService.updateTag(userId, tagId, command);
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
        String userId = StpUserUtil.getLoginId();
        TagInfoResponse tagInfo = tagQueryService.findByIdAndUserId(tagId, userId);
        return Result.success(tagInfo);
    }

    /**
     * 查询标签列表
     *
     * @param typeCode 标签类型编码，不能为空
     * @return 标签信息响应列表
     */
    @GetMapping("/list")
    public Result<List<TagInfoResponse>> listTags(
            @NotBlank(message = "标签类型编码不能为空")
            @RequestParam String typeCode) {
        String userId = StpUserUtil.getLoginId();
        List<TagInfoResponse> tagList = tagQueryService.findByUserIdAndTypeCode(userId, typeCode);
        return Result.success(tagList);
    }

    /**
     * 关联实体到标签
     *
     * @param command 关联实体命令
     * @return 空响应
     */
    @PostMapping("/associate")
    public Result<Void> associateEntity(@Valid @RequestBody AssociateEntityCommand command) {
        String userId = StpUserUtil.getLoginId();
        tagCommandService.associateEntity(userId, command);
        return Result.success();
    }

    /**
     * 取消标签与实体的关联
     *
     * @param command 取消关联实体命令
     * @return 空响应
     */
    @PostMapping("/dissociate")
    public Result<Void> dissociateEntity(@Valid @RequestBody DissociateEntityCommand command) {
        String userId = StpUserUtil.getLoginId();
        tagCommandService.dissociateEntity(userId, command);
        return Result.success();
    }
}
