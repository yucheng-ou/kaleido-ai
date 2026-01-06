package com.xiaoo.kaleido.admin.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.admin.application.command.DictCommandService;
import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import com.xiaoo.kaleido.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典控制器
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/dict")
@RequiredArgsConstructor
@Tag(name = "字典管理", description = "字典相关接口")
public class DictController {

    private final DictCommandService dictCommandService;
    private final DictQueryService dictQueryService;

    /**
     * 创建字典
     *
     * @param command 添加字典命令
     * @return 字典ID
     */
    @Operation(summary = "创建字典", description = "创建新的字典项")
    @PostMapping
    public Result<String> createDict(
            @Parameter(description = "添加字典命令", required = true)
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
    @Operation(summary = "更新字典", description = "更新字典信息")
    @PutMapping("/{dictId}")
    public Result<Void> updateDict(
            @Parameter(description = "字典ID", required = true)
            @PathVariable String dictId,
            @Parameter(description = "更新字典命令", required = true)
            @Valid @RequestBody UpdateDictCommand command) {

            // 设置字典ID到命令对象
            command.setId(dictId);
            dictCommandService.updateDict(command);
            return Result.success();

    }

    /**
     * 启用字典
     *
     * @param dictId 字典ID
     * @return 操作结果
     */
    @Operation(summary = "启用字典", description = "启用指定的字典")
    @PutMapping("/{dictId}/enable")
    public Result<Void> enableDict(
            @Parameter(description = "字典ID", required = true)
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
    @Operation(summary = "禁用字典", description = "禁用指定的字典")
    @PutMapping("/{dictId}/disable")
    public Result<Void> disableDict(
            @Parameter(description = "字典ID", required = true)
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
    @Operation(summary = "删除字典", description = "删除指定的字典")
    @DeleteMapping("/{dictId}")
    public Result<Void> deleteDict(
            @Parameter(description = "字典ID", required = true)
            @PathVariable String dictId) {

            dictCommandService.deleteDict(dictId);
            return Result.success();
    }

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典信息
     */
    @Operation(summary = "根据编码查询字典", description = "根据字典类型编码和字典编码查询字典")
    @GetMapping("/{typeCode}/{dictCode}")
    public Result<DictResponse> getDictByCode(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode,
            @Parameter(description = "字典编码", required = true)
            @PathVariable String dictCode) {

            DictResponse dict = dictQueryService.findByTypeCodeAndDictCode(typeCode, dictCode);
            return Result.success(dict);
    }

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典列表
     */
    @Operation(summary = "查询字典类型列表", description = "根据字典类型编码查询字典列表")
    @GetMapping("/type/{typeCode}")
    public Result<List<DictResponse>> getDictsByType(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode) {

            return Result.success(dictQueryService.findByTypeCode(typeCode));
    }

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典列表
     */
    @Operation(summary = "查询启用的字典列表", description = "根据字典类型编码查询启用的字典列表")
    @GetMapping("/type/{typeCode}/enabled")
    public Result<List<DictResponse>> getEnabledDictsByType(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode) {

            return Result.success(dictQueryService.findEnabledByTypeCode(typeCode));
    }

    /**
     * 查询字典列表
     *
     * @param queryReq 查询条件
     * @return 字典列表
     */
    @Operation(summary = "查询字典列表", description = "根据条件查询字典列表")
    @GetMapping("/list")
    public Result<List<DictResponse>> listDicts(
            @Parameter(description = "查询条件")
            DictQueryReq queryReq) {

            return Result.success(dictQueryService.queryDicts(queryReq));

    }

    /**
     * 分页查询字典列表
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询字典列表", description = "分页查询字典列表")
    @GetMapping("/page")
    public Result<PageInfo<DictResponse>> pageDicts(
            @Parameter(description = "分页查询条件")
            DictPageQueryReq pageQueryReq) {

            return Result.success(dictQueryService.pageQueryDicts(pageQueryReq));
    }


    /**
     * 获取字典值
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典值
     */
    @Operation(summary = "获取字典值", description = "根据字典类型编码和字典编码获取字典值")
    @GetMapping("/value/{typeCode}/{dictCode}")
    public Result<String> getDictValue(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode,
            @Parameter(description = "字典编码", required = true)
            @PathVariable String dictCode) {

            return Result.success(dictCommandService.getDictValue(typeCode, dictCode));
    }
}
