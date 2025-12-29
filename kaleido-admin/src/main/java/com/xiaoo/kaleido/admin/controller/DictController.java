package com.xiaoo.kaleido.admin.controller;

import com.xiaoo.kaleido.admin.application.command.AddDictCommand;
import com.xiaoo.kaleido.admin.application.command.DictCommandService;
import com.xiaoo.kaleido.admin.application.command.UpdateDictCommand;
import com.xiaoo.kaleido.admin.application.query.dto.DictDTO;
import com.xiaoo.kaleido.admin.application.query.request.DictQueryRequest;
import com.xiaoo.kaleido.admin.application.query.service.DictQueryService;
import com.xiaoo.kaleido.admin.controller.request.CreateDictRequest;
import com.xiaoo.kaleido.admin.controller.request.UpdateDictRequest;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.base.response.PageResp;
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
 * @date 2025/12/25
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
     * @param request 创建字典请求
     * @return 字典ID
     */
    @Operation(summary = "创建字典", description = "创建新的字典项")
    @PostMapping
    public Result<String> createDict(
            @Parameter(description = "创建字典请求", required = true)
            @Valid @RequestBody CreateDictRequest request) {
        try {
            log.debug("创建字典，request={}", request);
            
            AddDictCommand command = AddDictCommand.builder()
                    .typeCode(request.getTypeCode())
                    .typeName(request.getTypeName())
                    .dictCode(request.getDictCode())
                    .dictName(request.getDictName())
                    .dictValue(request.getDictValue())
                    .sort(request.getSort())
                    .build();
                    
            String dictId = dictCommandService.createDict(command);
            
            log.info("字典创建成功，字典ID: {}, 类型编码: {}, 字典编码: {}", 
                    dictId, request.getTypeCode(), request.getDictCode());
            return Result.success(dictId);
        } catch (AdminException e) {
            log.warn("创建字典失败，typeCode={}, dictCode={}, errorCode={}", 
                    request.getTypeCode(), request.getDictCode(), e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("创建字典异常，typeCode={}, dictCode={}", 
                    request.getTypeCode(), request.getDictCode(), e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 更新字典
     *
     * @param dictId 字典ID
     * @param request 更新字典请求
     * @return 操作结果
     */
    @Operation(summary = "更新字典", description = "更新字典信息")
    @PutMapping("/{dictId}")
    public Result<Void> updateDict(
            @Parameter(description = "字典ID", required = true)
            @PathVariable String dictId,
            @Parameter(description = "更新字典请求", required = true)
            @Valid @RequestBody UpdateDictRequest request) {
        try {
            log.debug("更新字典，dictId={}, request={}", dictId, request);
            
            UpdateDictCommand command = UpdateDictCommand.builder()
                    .dictId(dictId)
                    .dictName(request.getDictName())
                    .dictValue(request.getDictValue())
                    .sort(request.getSort())
                    .typeName(request.getTypeName())
                    .build();
                    
            dictCommandService.updateDict(command);
            
            log.info("字典更新成功，字典ID: {}", dictId);
            return Result.success();
        } catch (AdminException e) {
            log.warn("更新字典失败，dictId={}, errorCode={}", dictId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("更新字典异常，dictId={}", dictId, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
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
        try {
            log.debug("启用字典，dictId={}", dictId);
            
            dictCommandService.enableDict(dictId);
            
            log.info("字典启用成功，字典ID: {}", dictId);
            return Result.success();
        } catch (AdminException e) {
            log.warn("启用字典失败，dictId={}, errorCode={}", dictId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("启用字典异常，dictId={}", dictId, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
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
        try {
            log.debug("禁用字典，dictId={}", dictId);
            
            dictCommandService.disableDict(dictId);
            
            log.info("字典禁用成功，字典ID: {}", dictId);
            return Result.success();
        } catch (AdminException e) {
            log.warn("禁用字典失败，dictId={}, errorCode={}", dictId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("禁用字典异常，dictId={}", dictId, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
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
        try {
            log.debug("删除字典，dictId={}", dictId);
            
            dictCommandService.deleteDict(dictId);
            
            log.info("字典删除成功，字典ID: {}", dictId);
            return Result.success();
        } catch (AdminException e) {
            log.warn("删除字典失败，dictId={}, errorCode={}", dictId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("删除字典异常，dictId={}", dictId, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 根据ID查询字典
     *
     * @param dictId 字典ID
     * @return 字典信息
     */
    @Operation(summary = "查询字典信息", description = "根据字典ID查询字典信息")
    @GetMapping("/{dictId}")
    public Result<DictDTO> getDictById(
            @Parameter(description = "字典ID", required = true)
            @PathVariable String dictId) {
        try {
            log.debug("查询字典信息，dictId={}", dictId);
            
            DictDTO dict = dictQueryService.getDictById(dictId);
            
            return Result.success(dict);
        } catch (AdminException e) {
            log.warn("查询字典信息失败，dictId={}, errorCode={}", dictId, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("查询字典信息异常，dictId={}", dictId, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
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
    public Result<DictDTO> getDictByCode(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode,
            @Parameter(description = "字典编码", required = true)
            @PathVariable String dictCode) {
        try {
            log.debug("根据编码查询字典，typeCode={}, dictCode={}", typeCode, dictCode);
            
            DictDTO dict = dictQueryService.findByTypeCodeAndDictCode(typeCode, dictCode);
            
            return Result.success(dict);
        } catch (AdminException e) {
            log.warn("根据编码查询字典失败，typeCode={}, dictCode={}, errorCode={}", 
                    typeCode, dictCode, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("根据编码查询字典异常，typeCode={}, dictCode={}", typeCode, dictCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典列表
     */
    @Operation(summary = "查询字典类型列表", description = "根据字典类型编码查询字典列表")
    @GetMapping("/type/{typeCode}")
    public Result<List<DictDTO>> getDictsByType(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode) {
        try {
            log.debug("查询字典类型列表，typeCode={}", typeCode);
            
            List<DictDTO> dicts = dictQueryService.findByTypeCode(typeCode);
            
            return Result.success(dicts);
        } catch (AdminException e) {
            log.warn("查询字典类型列表失败，typeCode={}, errorCode={}", typeCode, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("查询字典类型列表异常，typeCode={}", typeCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典列表
     */
    @Operation(summary = "查询启用的字典列表", description = "根据字典类型编码查询启用的字典列表")
    @GetMapping("/type/{typeCode}/enabled")
    public Result<List<DictDTO>> getEnabledDictsByType(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode) {
        try {
            log.debug("查询启用的字典列表，typeCode={}", typeCode);
            
            List<DictDTO> dicts = dictQueryService.findEnabledByTypeCode(typeCode);
            
            return Result.success(dicts);
        } catch (AdminException e) {
            log.warn("查询启用的字典列表失败，typeCode={}, errorCode={}", typeCode, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("查询启用的字典列表异常，typeCode={}", typeCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 查询字典列表
     *
     * @param queryRequest 查询条件
     * @return 字典列表
     */
    @Operation(summary = "查询字典列表", description = "根据条件查询字典列表")
    @GetMapping("/list")
    public Result<List<DictDTO>> listDicts(
            @Parameter(description = "查询条件")
            DictQueryRequest queryRequest) {
        try {
            log.debug("查询字典列表，queryRequest={}", queryRequest);
            
            List<DictDTO> dicts = dictQueryService.queryDicts(queryRequest);
            
            return Result.success(dicts);
        } catch (AdminException e) {
            log.warn("查询字典列表失败，errorCode={}", e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("查询字典列表异常", e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 分页查询字典列表
     *
     * @param queryRequest 分页查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询字典列表", description = "分页查询字典列表")
    @GetMapping("/page")
    public Result<PageResp<DictDTO>> pageDicts(
            @Parameter(description = "分页查询条件")
            DictQueryRequest queryRequest) {
        try {
            log.debug("分页查询字典列表，queryRequest={}", queryRequest);
            
            PageResp<DictDTO> pageResp = dictQueryService.pageQueryDicts(queryRequest);
            
            return Result.success(pageResp);
        } catch (AdminException e) {
            log.warn("分页查询字典列表失败，errorCode={}", e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("分页查询字典列表异常", e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }

    /**
     * 验证字典是否有效
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 是否有效
     */
    @Operation(summary = "验证字典有效性", description = "验证字典是否存在且启用")
    @GetMapping("/validate/{typeCode}/{dictCode}")
    public Result<Boolean> validateDict(
            @Parameter(description = "字典类型编码", required = true)
            @PathVariable String typeCode,
            @Parameter(description = "字典编码", required = true)
            @PathVariable String dictCode) {
        try {
            log.debug("验证字典有效性，typeCode={}, dictCode={}", typeCode, dictCode);
            
            boolean valid = dictCommandService.isValidDict(typeCode, dictCode);
            
            return Result.success(valid);
        } catch (AdminException e) {
            log.warn("验证字典有效性失败，typeCode={}, dictCode={}, errorCode={}", 
                    typeCode, dictCode, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("验证字典有效性异常，typeCode={}, dictCode={}", typeCode, dictCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
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
        try {
            log.debug("获取字典值，typeCode={}, dictCode={}", typeCode, dictCode);
            
            String value = dictCommandService.getDictValue(typeCode, dictCode);
            
            return Result.success(value);
        } catch (AdminException e) {
            log.warn("获取字典值失败，typeCode={}, dictCode={}, errorCode={}", 
                    typeCode, dictCode, e.getErrorCode());
            return Result.error(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取字典值异常，typeCode={}, dictCode={}", typeCode, dictCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR.getCode(), AdminErrorCode.ADMIN_SYSTEM_ERROR.getMessage());
        }
    }
}
