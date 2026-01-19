package com.xiaoo.kaleido.wardrobe.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 衣柜服务错误码枚举
 * <p>
 * 定义衣柜服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Getter
public enum WardrobeErrorCode implements ErrorCode {

    // ========== 通用错误码 ==========
    /**
     * 数据不存在：根据ID查询时未找到对应记录
     */
    DATA_NOT_FOUND("WARDROBE_DATA_NOT_FOUND", "数据不存在"),

    /**
     * 数据已存在：尝试创建已存在的数据
     */
    DATA_ALREADY_EXISTS("WARDROBE_DATA_ALREADY_EXISTS", "数据已存在"),

    /**
     * 数据状态异常：数据状态不符合操作要求
     */
    DATA_STATUS_ERROR("WARDROBE_DATA_STATUS_ERROR", "数据状态异常"),

    /**
     * 操作失败：通用操作失败错误
     */
    OPERATE_FAILED("WARDROBE_OPERATE_FAILED", "操作失败"),

    /**
     * 查询失败：查询操作失败
     */
    QUERY_FAIL("WARDROBE_QUERY_FAIL", "查询失败"),

    /**
     * 参数不能为空：通用参数空值校验失败
     */
    PARAM_NOT_NULL("WARDROBE_PARAM_NOT_NULL", "参数不能为空"),

    /**
     * 参数格式错误：参数不符合格式规范
     */
    PARAM_FORMAT_ERROR("WARDROBE_PARAM_FORMAT_ERROR", "参数格式错误"),

    /**
     * 参数长度超限：参数超过最大长度限制
     */
    PARAM_LENGTH_ERROR("WARDROBE_PARAM_LENGTH_ERROR", "参数长度超限"),

    /**
     * 批量操作参数错误：批量操作参数无效
     */
    BATCH_OPERATION_PARAM_ERROR("WARDROBE_BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),

    /**
     * 权限不足：用户没有操作权限
     */
    PERMISSION_DENIED("WARDROBE_PERMISSION_DENIED", "权限不足"),

    /**
     * 数据不属于用户：尝试操作不属于当前用户的数据
     */
    DATA_NOT_BELONG_TO_USER("WARDROBE_DATA_NOT_BELONG_TO_USER", "数据不属于当前用户"),

    // ========== 品牌相关错误码 ==========
    /**
     * 品牌不存在：根据ID查询品牌时未找到对应记录
     */
    BRAND_NOT_FOUND("WARDROBE_BRAND_NOT_FOUND", "品牌不存在"),

    /**
     * 品牌名称已存在：品牌名称已存在
     */
    BRAND_NAME_EXISTS("WARDROBE_BRAND_NAME_EXISTS", "品牌名称已存在"),

    // ========== 服装相关错误码 ==========
    /**
     * 服装不存在：根据ID查询服装时未找到对应记录
     */
    CLOTHING_NOT_FOUND("WARDROBE_CLOTHING_NOT_FOUND", "服装不存在"),

    /**
     * 服装名称已存在：同用户下服装名称已存在
     */
    CLOTHING_NAME_EXISTS("WARDROBE_CLOTHING_NAME_EXISTS", "服装名称在用户下已存在"),

    /**
     * 服装所有者不匹配：只有服装所有者可以操作
     */
    CLOTHING_OWNER_MISMATCH("WARDROBE_CLOTHING_OWNER_MISMATCH", "只有服装所有者可以操作"),

    /**
     * 服装不属于用户：验证服装所属用户时发现服装不属于指定用户
     */
    CLOTHING_NOT_BELONG_TO_USER("WARDROBE_CLOTHING_NOT_BELONG_TO_USER", "服装不属于该用户"),

    /**
     * 服装类型编码无效：服装类型编码不存在或无效
     */
    CLOTHING_TYPE_CODE_INVALID("WARDROBE_CLOTHING_TYPE_CODE_INVALID", "服装类型编码无效"),

    // ========== 存储位置相关错误码 ==========
    /**
     * 存储位置不存在：根据ID查询存储位置时未找到对应记录
     */
    LOCATION_NOT_FOUND("WARDROBE_LOCATION_NOT_FOUND", "存储位置不存在"),

    /**
     * 存储位置名称已存在：同用户下存储位置名称已存在
     */
    LOCATION_NAME_EXISTS("WARDROBE_LOCATION_NAME_EXISTS", "存储位置名称在用户下已存在"),

    /**
     * 位置被引用：位置被服装引用，不能删除
     */
    LOCATION_HAS_REFERENCES("WARDROBE_LOCATION_HAS_REFERENCES", "位置被服装引用，不能删除"),

    // ========== 图片相关错误码 ==========
    /**
     * 图片不存在：根据ID查询图片时未找到对应记录
     */
    IMAGE_NOT_FOUND("WARDROBE_IMAGE_NOT_FOUND", "图片不存在"),

    /**
     * 图片上传失败：图片上传操作失败
     */
    IMAGE_UPLOAD_FAILED("WARDROBE_IMAGE_UPLOAD_FAILED", "图片上传失败"),

    /**
     * 图片格式不支持：上传的图片格式不支持
     */
    IMAGE_FORMAT_NOT_SUPPORTED("WARDROBE_IMAGE_FORMAT_NOT_SUPPORTED", "图片格式不支持"),

    /**
     * 图片大小超限：上传的图片大小超过限制
     */
    IMAGE_SIZE_EXCEEDED("WARDROBE_IMAGE_SIZE_EXCEEDED", "图片大小超过限制"),

    /**
     * 主图已存在：实体已存在主图，不能重复设置
     */
    PRIMARY_IMAGE_EXISTS("WARDROBE_PRIMARY_IMAGE_EXISTS", "主图已存在"),

    /**
     * 多个主图：不能设置多个主图
     */
    MULTIPLE_PRIMARY_IMAGES("WARDROBE_MULTIPLE_PRIMARY_IMAGES", "不能设置多个主图"),

    /**
     * 服装图片数量超限：服装图片数量超过限制
     */
    CLOTHING_IMAGE_LIMIT_EXCEEDED("WARDROBE_CLOTHING_IMAGE_LIMIT_EXCEEDED", "服装图片数量超过限制"),

    // ========== 搭配相关错误码 ==========
    /**
     * 搭配不存在：根据ID查询搭配时未找到对应记录
     */
    OUTFIT_NOT_FOUND("WARDROBE_OUTFIT_NOT_FOUND", "搭配不存在"),

    /**
     * 搭配名称已存在：同用户下搭配名称已存在
     */
    OUTFIT_NAME_EXISTS("WARDROBE_OUTFIT_NAME_EXISTS", "搭配名称在用户下已存在"),

    /**
     * 搭配服装数量超限：搭配关联的服装数量超过限制
     */
    OUTFIT_CLOTHING_LIMIT_EXCEEDED("WARDROBE_OUTFIT_CLOTHING_LIMIT_EXCEEDED", "搭配服装数量超过限制"),

    // ========== 位置记录相关错误码 ==========
    /**
     * 位置记录不存在：根据ID查询位置记录时未找到对应记录
     */
    LOCATION_RECORD_NOT_FOUND("WARDROBE_LOCATION_RECORD_NOT_FOUND", "位置记录不存在"),

    /**
     * 服装已在目标位置：尝试将服装添加到已存在的位置
     */
    CLOTHING_ALREADY_IN_LOCATION("WARDROBE_CLOTHING_ALREADY_IN_LOCATION", "服装已在目标位置"),

    /**
     * 位置记录创建失败：位置记录创建操作失败
     */
    LOCATION_RECORD_CREATE_FAILED("WARDROBE_LOCATION_RECORD_CREATE_FAILED", "位置记录创建失败");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    WardrobeErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
