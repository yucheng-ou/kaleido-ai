package com.xiaoo.kaleido.admin.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.admin.application.command.DictCommandService;
import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 字典控制器
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictCommandService dictCommandService;
    private final DictQueryService dictQueryService;

    /**
     * 创建字典
     *
     * @param command 添加字典命令
     * @return 字典ID
     */
    @PostMapping
    public Result<String> createDict(
            @Valid @RequestBody AddDictCommand command) {
        return Result.success(dictCommandService.createDict(command));
    }

    /**
     * 更新字典
     *
     * @param dictId  字典ID
     * @param command 更新字典命令
     * @return 操作结果
     */
    @PutMapping("/{dictId}")
    public Result<Void> updateDict(
            @PathVariable String dictId,
            @Valid @RequestBody UpdateDictCommand command) {

        dictCommandService.updateDict(dictId,command);
        return Result.success();

    }

    /**
     * 启用字典
     *
     * @param dictId 字典ID
     * @return 操作结果
     */
    @PutMapping("/{dictId}/enable")
    public Result<Void> enableDict(
            @PathVariable String dictId) {
        dictCommandService.enableDict(dictId);
        return Result.success();
    }

    /**
     * 禁用字典
     *
     * @param dictId 字典ID
     * @return 操作结果
     */
    @PutMapping("/{dictId}/disable")
    public Result<Void> disableDict(
            @PathVariable String dictId) {

        dictCommandService.disableDict(dictId);
        log.info("字典禁用成功，字典ID: {}", dictId);
        return Result.success();
    }

    /**
     * 删除字典
     *
     * @param dictId 字典ID
     * @return 操作结果
     */
    @DeleteMapping("/{dictId}")
    public Result<Void> deleteDict(
            @PathVariable String dictId) {

        dictCommandService.deleteDict(dictId);
        return Result.success();
    }

    /**
     * 分页查询字典列表
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageInfo<DictResponse>> pageDicts(
            DictPageQueryReq pageQueryReq) {

        return Result.success(dictQueryService.pageQueryDicts(pageQueryReq));
    }
}
