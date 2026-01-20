package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.admin.application.command.DictCommandService;
import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
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
    @SaCheckPermission(value = "admin:dict:update", type = StpAdminUtil.TYPE)
    public Result<String> createDict(@Valid @RequestBody AddDictCommand command) {
        return Result.success(dictCommandService.createDict(command));
    }

    /**
     * 更新字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @param command 更新字典命令
     * @return 操作结果
     */
    @PutMapping("/{typeCode}/{dictCode}")
    @SaCheckPermission(value = "admin:dict:update", type = StpAdminUtil.TYPE)
    public Result<Void> updateDict(
            @PathVariable String typeCode,
            @PathVariable String dictCode,
            @Valid @RequestBody UpdateDictCommand command) {

        dictCommandService.updateDict(typeCode, dictCode, command);
        return Result.success();

    }

    /**
     * 删除字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 操作结果
     */
    @DeleteMapping("/{typeCode}/{dictCode}")
    @SaCheckPermission(value = "admin:dict:delete", type = StpAdminUtil.TYPE)
    public Result<Void> deleteDict(
            @PathVariable String typeCode,
            @PathVariable String dictCode) {

        dictCommandService.deleteDict(typeCode, dictCode);
        return Result.success();
    }

    /**
     * 分页查询字典列表
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "admin:dict:read", type = StpAdminUtil.TYPE)
    public Result<PageInfo<DictResponse>> pageDicts(
            DictPageQueryReq pageQueryReq) {

        return Result.success(dictQueryService.pageQueryDicts(pageQueryReq));
    }
}
