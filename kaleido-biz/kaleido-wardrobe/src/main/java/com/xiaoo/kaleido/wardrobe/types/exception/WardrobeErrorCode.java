package com.xiaoo.kaleido.wardrobe.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 衣柜服务错误码枚举
 * 
 * 定义衣柜服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 * 
 * @author ouyucheng
 * @date 2026/1/15
 */
@Getter
public enum WardrobeErrorCode implements ErrorCode {

    // ========== 通用错误码 ==========
    /** 数据不存在：根据ID查询时未找到对应记录 */
    DATA_NOT_FOUND("WARDROBE_DATA_NOT_FOUND", "数据不存在"),
    
    /** 数据已存在：尝试创建已存在的数据 */
    DATA_ALREADY_EXISTS("WARDROBE_DATA_ALREADY_EXISTS", "数据已存在"),
    
    /** 数据状态异常：数据状态不符合操作要求 */
    DATA_STATUS_ERROR("WARDROBE_DATA_STATUS_ERROR", "数据状态异常"),
    
    /** 操作失败：通用操作失败错误 */
    OPERATE_FAILED("WARDROBE_OPERATE_FAILED", "操作失败"),
    
    /** 查询失败：查询操作失败 */
    QUERY_FAIL("WARDROBE_QUERY_FAIL", "查询失败"),
    
    /** 参数不能为空：通用参数空值校验失败 */
    PARAM_NOT_NULL("WARDROBE_PARAM_NOT_NULL", "参数不能为空"),
    
    /** 参数格式错误：参数不符合格式规范 */
    PARAM_FORMAT_ERROR("WARDROBE_PARAM_FORMAT_ERROR", "参数格式错误"),
    
    /** 参数长度超限：参数超过最大长度限制 */
    PARAM_LENGTH_ERROR("WARDROBE_PARAM_LENGTH_ERROR", "参数长度超限"),
    
    /** 批量操作参数错误：批量操作参数无效 */
    BATCH_OPERATION_PARAM_ERROR("WARDROBE_BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),
    
    /** 权限不足：用户没有操作权限 */
    PERMISSION_DENIED("WARDROBE_PERMISSION_DENIED", "权限不足"),
    
    /** 数据不属于用户：尝试操作不属于当前用户的数据 */
    DATA_NOT_BELONG_TO_USER("WARDROBE_DATA_NOT_BELONG_TO_USER", "数据不属于当前用户"),

    // ========== 品牌相关错误码 ==========
    /** 品牌不存在：根据ID查询品牌时未找到对应记录 */
    BRAND_NOT_FOUND("WARDROBE_BRAND_NOT_FOUND", "品牌不存在"),
    
    /** 品牌名称已存在：品牌名称已存在 */
    BRAND_NAME_EXISTS("WARDROBE_BRAND_NAME_EXISTS", "品牌名称已存在"),
    
    /** 品牌已禁用：尝试操作已被禁用的品牌 */
    BRAND_DISABLED("WARDROBE_BRAND_DISABLED", "品牌已禁用"),
    
    /** 品牌已是启用状态：尝试启用已启用的品牌 */
    BRAND_ALREADY_ENABLED("WARDROBE_BRAND_ALREADY_ENABLED", "品牌已是启用状态"),
    
    /** 品牌已是禁用状态：尝试禁用已禁用的品牌 */
    BRAND_ALREADY_DISABLED("WARDROBE_BRAND_ALREADY_DISABLED", "品牌已是禁用状态"),
    
    /** 品牌ID不能为空：品牌ID参数为空 */
    BRAND_ID_NOT_NULL("WARDROBE_BRAND_ID_NOT_NULL", "品牌ID不能为空"),
    
    /** 品牌名称不能为空：品牌名称参数为空 */
    BRAND_NAME_EMPTY("WARDROBE_BRAND_NAME_EMPTY", "品牌名称不能为空"),
    
    /** 品牌名称长度超限：品牌名称超过最大长度限制 */
    BRAND_NAME_LENGTH_ERROR("WARDROBE_BRAND_NAME_LENGTH_ERROR", "品牌名称长度不能超过100个字符"),
    
    /** 品牌描述长度超限：品牌描述超过最大长度限制 */
    BRAND_DESCRIPTION_LENGTH_ERROR("WARDROBE_BRAND_DESCRIPTION_LENGTH_ERROR", "品牌描述长度不能超过500个字符"),
    
    /** 品牌Logo路径长度超限：品牌Logo路径超过最大长度限制 */
    BRAND_LOGO_PATH_LENGTH_ERROR("WARDROBE_BRAND_LOGO_PATH_LENGTH_ERROR", "品牌Logo路径长度不能超过500个字符"),

    // ========== 服装相关错误码 ==========
    /** 服装不存在：根据ID查询服装时未找到对应记录 */
    CLOTHING_NOT_FOUND("WARDROBE_CLOTHING_NOT_FOUND", "服装不存在"),
    
    /** 服装名称已存在：同用户下服装名称已存在 */
    CLOTHING_NAME_EXISTS("WARDROBE_CLOTHING_NAME_EXISTS", "服装名称在用户下已存在"),
    
    /** 服装已禁用：尝试操作已被禁用的服装 */
    CLOTHING_DISABLED("WARDROBE_CLOTHING_DISABLED", "服装已禁用"),
    
    /** 服装已是启用状态：尝试启用已启用的服装 */
    CLOTHING_ALREADY_ENABLED("WARDROBE_CLOTHING_ALREADY_ENABLED", "服装已是启用状态"),
    
    /** 服装已是禁用状态：尝试禁用已禁用的服装 */
    CLOTHING_ALREADY_DISABLED("WARDROBE_CLOTHING_ALREADY_DISABLED", "服装已是禁用状态"),
    
    /** 服装ID不能为空：服装ID参数为空 */
    CLOTHING_ID_NOT_NULL("WARDROBE_CLOTHING_ID_NOT_NULL", "服装ID不能为空"),
    
    /** 服装名称不能为空：服装名称参数为空 */
    CLOTHING_NAME_EMPTY("WARDROBE_CLOTHING_NAME_EMPTY", "服装名称不能为空"),
    
    /** 服装名称长度超限：服装名称超过最大长度限制 */
    CLOTHING_NAME_LENGTH_ERROR("WARDROBE_CLOTHING_NAME_LENGTH_ERROR", "服装名称长度不能超过100个字符"),
    
    /** 用户ID不能为空：用户ID参数为空 */
    USER_ID_NOT_NULL("WARDROBE_USER_ID_NOT_NULL", "用户ID不能为空"),
    
    /** 服装类型编码不能为空：服装类型编码参数为空 */
    CLOTHING_TYPE_CODE_EMPTY("WARDROBE_CLOTHING_TYPE_CODE_EMPTY", "服装类型编码不能为空"),
    
    /** 服装类型编码无效：服装类型编码不存在或无效 */
    CLOTHING_TYPE_CODE_INVALID("WARDROBE_CLOTHING_TYPE_CODE_INVALID", "服装类型编码无效"),
    
    /** 颜色编码无效：颜色编码不存在或无效 */
    COLOR_CODE_INVALID("WARDROBE_COLOR_CODE_INVALID", "颜色编码无效"),
    
    /** 季节编码无效：季节编码不存在或无效 */
    SEASON_CODE_INVALID("WARDROBE_SEASON_CODE_INVALID", "季节编码无效"),
    
    /** 尺码长度超限：尺码超过最大长度限制 */
    SIZE_LENGTH_ERROR("WARDROBE_SIZE_LENGTH_ERROR", "尺码长度不能超过20个字符"),
    
    /** 价格格式错误：价格不符合格式规范 */
    PRICE_FORMAT_ERROR("WARDROBE_PRICE_FORMAT_ERROR", "价格格式错误"),
    
    /** 购买日期格式错误：购买日期不符合格式规范 */
    PURCHASE_DATE_FORMAT_ERROR("WARDROBE_PURCHASE_DATE_FORMAT_ERROR", "购买日期格式错误"),
    
    /** 服装描述长度超限：服装描述超过最大长度限制 */
    CLOTHING_DESCRIPTION_LENGTH_ERROR("WARDROBE_CLOTHING_DESCRIPTION_LENGTH_ERROR", "服装描述长度不能超过500个字符"),
    
    /** 品牌不存在：关联的品牌ID不存在 */
    BRAND_NOT_EXIST("WARDROBE_BRAND_NOT_EXIST", "关联的品牌不存在"),
    
    /** 位置不存在：关联的存储位置不存在 */
    LOCATION_NOT_EXIST("WARDROBE_LOCATION_NOT_EXIST", "关联的存储位置不存在"),
    
    /** 服装所有者不匹配：只有服装所有者可以操作 */
    CLOTHING_OWNER_MISMATCH("WARDROBE_CLOTHING_OWNER_MISMATCH", "只有服装所有者可以操作"),

    // ========== 存储位置相关错误码 ==========
    /** 存储位置不存在：根据ID查询存储位置时未找到对应记录 */
    LOCATION_NOT_FOUND("WARDROBE_LOCATION_NOT_FOUND", "存储位置不存在"),
    
    /** 存储位置名称已存在：同用户下存储位置名称已存在 */
    LOCATION_NAME_EXISTS("WARDROBE_LOCATION_NAME_EXISTS", "存储位置名称在用户下已存在"),
    
    /** 存储位置已禁用：尝试操作已被禁用的存储位置 */
    LOCATION_DISABLED("WARDROBE_LOCATION_DISABLED", "存储位置已禁用"),
    
    /** 存储位置已是启用状态：尝试启用已启用的存储位置 */
    LOCATION_ALREADY_ENABLED("WARDROBE_LOCATION_ALREADY_ENABLED", "存储位置已是启用状态"),
    
    /** 存储位置已是禁用状态：尝试禁用已禁用的存储位置 */
    LOCATION_ALREADY_DISABLED("WARDROBE_LOCATION_ALREADY_DISABLED", "存储位置已是禁用状态"),
    
    /** 存储位置ID不能为空：存储位置ID参数为空 */
    LOCATION_ID_NOT_NULL("WARDROBE_LOCATION_ID_NOT_NULL", "存储位置ID不能为空"),
    
    /** 存储位置名称不能为空：存储位置名称参数为空 */
    LOCATION_NAME_EMPTY("WARDROBE_LOCATION_NAME_EMPTY", "存储位置名称不能为空"),
    
    /** 存储位置名称长度超限：存储位置名称超过最大长度限制 */
    LOCATION_NAME_LENGTH_ERROR("WARDROBE_LOCATION_NAME_LENGTH_ERROR", "存储位置名称长度不能超过100个字符"),
    
    /** 存储位置描述长度超限：存储位置描述超过最大长度限制 */
    LOCATION_DESCRIPTION_LENGTH_ERROR("WARDROBE_LOCATION_DESCRIPTION_LENGTH_ERROR", "存储位置描述长度不能超过500个字符"),
    
    /** 存储位置地址长度超限：存储位置地址超过最大长度限制 */
    LOCATION_ADDRESS_LENGTH_ERROR("WARDROBE_LOCATION_ADDRESS_LENGTH_ERROR", "存储位置地址长度不能超过200个字符"),

    // ========== 位置记录相关错误码 ==========
    /** 位置记录不存在：根据ID查询位置记录时未找到对应记录 */
    LOCATION_RECORD_NOT_FOUND("WARDROBE_LOCATION_RECORD_NOT_FOUND", "位置记录不存在"),
    
    /** 位置记录ID不能为空：位置记录ID参数为空 */
    LOCATION_RECORD_ID_NOT_NULL("WARDROBE_LOCATION_RECORD_ID_NOT_NULL", "位置记录ID不能为空"),
    
    /** 服装ID不能为空：服装ID参数为空 */
    CLOTHING_ID_EMPTY("WARDROBE_CLOTHING_ID_EMPTY", "服装ID不能为空"),
    
    /** 位置ID不能为空：位置ID参数为空 */
    LOCATION_ID_EMPTY("WARDROBE_LOCATION_ID_EMPTY", "位置ID不能为空"),
    
    /** 记录时间格式错误：记录时间不符合格式规范 */
    RECORD_TIME_FORMAT_ERROR("WARDROBE_RECORD_TIME_FORMAT_ERROR", "记录时间格式错误"),
    
    /** 备注长度超限：备注超过最大长度限制 */
    NOTES_LENGTH_ERROR("WARDROBE_NOTES_LENGTH_ERROR", "备注长度不能超过500个字符"),
    
    /** 服装当前位置已存在：服装已有当前位置记录 */
    CLOTHING_CURRENT_LOCATION_EXISTS("WARDROBE_CLOTHING_CURRENT_LOCATION_EXISTS", "服装当前位置已存在"),

    // ========== 图片相关错误码 ==========
    /** 图片不存在：根据ID查询图片时未找到对应记录 */
    IMAGE_NOT_FOUND("WARDROBE_IMAGE_NOT_FOUND", "图片不存在"),
    
    /** 图片ID不能为空：图片ID参数为空 */
    IMAGE_ID_NOT_NULL("WARDROBE_IMAGE_ID_NOT_NULL", "图片ID不能为空"),
    
    /** 图片路径不能为空：图片路径参数为空 */
    IMAGE_PATH_EMPTY("WARDROBE_IMAGE_PATH_EMPTY", "图片路径不能为空"),
    
    /** 图片路径长度超限：图片路径超过最大长度限制 */
    IMAGE_PATH_LENGTH_ERROR("WARDROBE_IMAGE_PATH_LENGTH_ERROR", "图片路径长度不能超过500个字符"),
    
    /** 图片上传失败：图片上传操作失败 */
    IMAGE_UPLOAD_FAILED("WARDROBE_IMAGE_UPLOAD_FAILED", "图片上传失败"),
    
    /** 图片格式不支持：上传的图片格式不支持 */
    IMAGE_FORMAT_NOT_SUPPORTED("WARDROBE_IMAGE_FORMAT_NOT_SUPPORTED", "图片格式不支持"),
    
    /** 图片大小超限：上传的图片大小超过限制 */
    IMAGE_SIZE_EXCEEDED("WARDROBE_IMAGE_SIZE_EXCEEDED", "图片大小超过限制"),
    
    /** 图片宽度无效：图片宽度参数无效 */
    IMAGE_WIDTH_INVALID("WARDROBE_IMAGE_WIDTH_INVALID", "图片宽度无效"),
    
    /** 图片高度无效：图片高度参数无效 */
    IMAGE_HEIGHT_INVALID("WARDROBE_IMAGE_HEIGHT_INVALID", "图片高度无效"),
    
    /** 原始文件名长度超限：原始文件名超过最大长度限制 */
    FILE_NAME_LENGTH_ERROR("WARDROBE_FILE_NAME_LENGTH_ERROR", "原始文件名长度不能超过200个字符"),
    
    /** 文件类型长度超限：文件类型超过最大长度限制 */
    MIME_TYPE_LENGTH_ERROR("WARDROBE_MIME_TYPE_LENGTH_ERROR", "文件类型长度不能超过50个字符"),
    
    /** 缩略图路径长度超限：缩略图路径超过最大长度限制 */
    THUMBNAIL_PATH_LENGTH_ERROR("WARDROBE_THUMBNAIL_PATH_LENGTH_ERROR", "缩略图路径长度不能超过500个字符"),
    
    /** 图片描述长度超限：图片描述超过最大长度限制 */
    IMAGE_DESCRIPTION_LENGTH_ERROR("WARDROBE_IMAGE_DESCRIPTION_LENGTH_ERROR", "图片描述长度不能超过200个字符"),
    
    /** 图片排序序号无效：图片排序序号参数无效 */
    IMAGE_ORDER_INVALID("WARDROBE_IMAGE_ORDER_INVALID", "图片排序序号无效"),
    
    /** 图片排序序号不能为空：图片排序序号参数为空 */
    IMAGE_ORDER_NOT_NULL("WARDROBE_IMAGE_ORDER_NOT_NULL", "图片排序序号不能为空"),
    
    /** 图片路径不能为空：图片路径参数为空 */
    IMAGE_PATH_NOT_NULL("WARDROBE_IMAGE_PATH_NOT_NULL", "图片路径不能为空"),
    
    /** 是否为主图不能为空：是否为主图参数为空 */
    IMAGE_IS_PRIMARY_NOT_NULL("WARDROBE_IMAGE_IS_PRIMARY_NOT_NULL", "是否为主图不能为空"),
    
    /** 主图已存在：实体已存在主图，不能重复设置 */
    PRIMARY_IMAGE_EXISTS("WARDROBE_PRIMARY_IMAGE_EXISTS", "主图已存在"),
    
    /** 多个主图：不能设置多个主图 */
    MULTIPLE_PRIMARY_IMAGES("WARDROBE_MULTIPLE_PRIMARY_IMAGES", "不能设置多个主图"),
    
    /** 服装图片数量超限：服装图片数量超过限制 */
    CLOTHING_IMAGE_LIMIT_EXCEEDED("WARDROBE_CLOTHING_IMAGE_LIMIT_EXCEEDED", "服装图片数量超过限制"),
    
    /** 图片列表不能为空：图片列表参数为空 */
    IMAGES_NOT_NULL("WARDROBE_IMAGES_NOT_NULL", "图片列表不能为空"),

    // ========== 搭配相关错误码 ==========
    /** 搭配不存在：根据ID查询搭配时未找到对应记录 */
    OUTFIT_NOT_FOUND("WARDROBE_OUTFIT_NOT_FOUND", "搭配不存在"),
    
    /** 搭配名称已存在：同用户下搭配名称已存在 */
    OUTFIT_NAME_EXISTS("WARDROBE_OUTFIT_NAME_EXISTS", "搭配名称在用户下已存在"),
    
    /** 搭配已禁用：尝试操作已被禁用的搭配 */
    OUTFIT_DISABLED("WARDROBE_OUTFIT_DISABLED", "搭配已禁用"),
    
    /** 搭配已是启用状态：尝试启用已启用的搭配 */
    OUTFIT_ALREADY_ENABLED("WARDROBE_OUTFIT_ALREADY_ENABLED", "搭配已是启用状态"),
    
    /** 搭配已是禁用状态：尝试禁用已禁用的搭配 */
    OUTFIT_ALREADY_DISABLED("WARDROBE_OUTFIT_ALREADY_DISABLED", "搭配已是禁用状态"),
    
    /** 搭配ID不能为空：搭配ID参数为空 */
    OUTFIT_ID_NOT_NULL("WARDROBE_OUTFIT_ID_NOT_NULL", "搭配ID不能为空"),
    
    /** 搭配名称不能为空：搭配名称参数为空 */
    OUTFIT_NAME_EMPTY("WARDROBE_OUTFIT_NAME_EMPTY", "搭配名称不能为空"),
    
    /** 搭配名称长度超限：搭配名称超过最大长度限制 */
    OUTFIT_NAME_LENGTH_ERROR("WARDROBE_OUTFIT_NAME_LENGTH_ERROR", "搭配名称长度不能超过100个字符"),
    
    /** 搭配描述长度超限：搭配描述超过最大长度限制 */
    OUTFIT_DESCRIPTION_LENGTH_ERROR("WARDROBE_OUTFIT_DESCRIPTION_LENGTH_ERROR", "搭配描述长度不能超过500个字符"),
    
    /** 穿着次数无效：穿着次数参数无效 */
    WEAR_COUNT_INVALID("WARDROBE_WEAR_COUNT_INVALID", "穿着次数无效"),
    
    /** 最后穿着日期格式错误：最后穿着日期不符合格式规范 */
    LAST_WORN_DATE_FORMAT_ERROR("WARDROBE_LAST_WORN_DATE_FORMAT_ERROR", "最后穿着日期格式错误"),
    
    /** 搭配服装关联不存在：搭配与服装的关联关系不存在 */
    OUTFIT_CLOTHING_ASSOCIATION_NOT_FOUND("WARDROBE_OUTFIT_CLOTHING_ASSOCIATION_NOT_FOUND", "搭配服装关联不存在"),
    
    /** 搭配服装关联已存在：搭配已关联该服装，不能重复关联 */
    OUTFIT_CLOTHING_ASSOCIATION_EXISTS("WARDROBE_OUTFIT_CLOTHING_ASSOCIATION_EXISTS", "搭配服装关联已存在"),
    
    /** 搭配服装数量超限：搭配关联的服装数量超过限制 */
    OUTFIT_CLOTHING_LIMIT_EXCEEDED("WARDROBE_OUTFIT_CLOTHING_LIMIT_EXCEEDED", "搭配服装数量超过限制"),

    // ========== 穿着记录相关错误码 ==========
    /** 穿着记录不存在：根据ID查询穿着记录时未找到对应记录 */
    WEAR_RECORD_NOT_FOUND("WARDROBE_WEAR_RECORD_NOT_FOUND", "穿着记录不存在"),
    
    /** 穿着记录ID不能为空：穿着记录ID参数为空 */
    WEAR_RECORD_ID_NOT_NULL("WARDROBE_WEAR_RECORD_ID_NOT_NULL", "穿着记录ID不能为空"),
    
    /** 穿着日期不能为空：穿着日期参数为空 */
    WEAR_DATE_EMPTY("WARDROBE_WEAR_DATE_EMPTY", "穿着日期不能为空"),
    
    /** 穿着日期格式错误：穿着日期不符合格式规范 */
    WEAR_DATE_FORMAT_ERROR("WARDROBE_WEAR_DATE_FORMAT_ERROR", "穿着日期格式错误"),
    
    /** 穿着记录备注长度超限：穿着记录备注超过最大长度限制 */
    WEAR_RECORD_NOTES_LENGTH_ERROR("WARDROBE_WEAR_RECORD_NOTES_LENGTH_ERROR", "穿着记录备注长度不能超过500个字符"),
    
    /** 穿着记录已存在：同用户同搭配同日期穿着记录已存在 */
    WEAR_RECORD_EXISTS("WARDROBE_WEAR_RECORD_EXISTS", "穿着记录已存在"),

    // ========== 系统异常类错误码 ==========
    /** 数据库操作失败：数据库操作异常 */
    DATABASE_OPERATION_FAILED("WARDROBE_DATABASE_OPERATION_FAILED", "数据库操作失败"),
    
    /** 缓存操作失败：缓存操作异常 */
    CACHE_OPERATION_FAILED("WARDROBE_CACHE_OPERATION_FAILED", "缓存操作失败"),
    
    /** RPC调用失败：RPC服务调用异常 */
    RPC_CALL_FAILED("WARDROBE_RPC_CALL_FAILED", "RPC调用失败"),
    
    /** 消息处理失败：消息队列处理异常 */
    MESSAGE_PROCESS_FAILED("WARDROBE_MESSAGE_PROCESS_FAILED", "消息处理失败"),
    
    /** 文件存储失败：文件存储操作异常 */
    FILE_STORAGE_FAILED("WARDROBE_FILE_STORAGE_FAILED", "文件存储失败"),
    
    /** 外部服务调用失败：调用外部服务异常 */
    EXTERNAL_SERVICE_CALL_FAILED("WARDROBE_EXTERNAL_SERVICE_CALL_FAILED", "外部服务调用失败"),

    // ========== 基础设施层错误码 ==========
    /** 服装保存失败：服装保存操作失败 */
    CLOTHING_SAVE_FAIL("WARDROBE_CLOTHING_SAVE_FAIL", "服装保存失败"),
    
    /** 服装更新失败：服装更新操作失败 */
    CLOTHING_UPDATE_FAIL("WARDROBE_CLOTHING_UPDATE_FAIL", "服装更新失败"),
    
    /** 服装删除失败：服装删除操作失败 */
    CLOTHING_DELETE_FAIL("WARDROBE_CLOTHING_DELETE_FAIL", "服装删除失败"),
    
    /** 服装查询失败：服装查询操作失败 */
    CLOTHING_QUERY_FAIL("WARDROBE_CLOTHING_QUERY_FAIL", "服装查询失败"),
    
    /** 品牌保存失败：品牌保存操作失败 */
    BRAND_SAVE_FAIL("WARDROBE_BRAND_SAVE_FAIL", "品牌保存失败"),
    
    /** 品牌更新失败：品牌更新操作失败 */
    BRAND_UPDATE_FAIL("WARDROBE_BRAND_UPDATE_FAIL", "品牌更新失败"),
    
    /** 品牌删除失败：品牌删除操作失败 */
    BRAND_DELETE_FAIL("WARDROBE_BRAND_DELETE_FAIL", "品牌删除失败"),
    
    /** 品牌查询失败：品牌查询操作失败 */
    BRAND_QUERY_FAIL("WARDROBE_BRAND_QUERY_FAIL", "品牌查询失败");

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
