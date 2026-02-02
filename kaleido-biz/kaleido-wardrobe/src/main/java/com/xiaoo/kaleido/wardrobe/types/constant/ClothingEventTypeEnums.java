package com.xiaoo.kaleido.wardrobe.types.constant;

/**
 * 服装事件类型枚举
 * 
 * 定义服装相关的事件类型，用于区分不同操作
 * 
 * @author ouyucheng
 * @date 2026/1/31
 */
public enum ClothingEventTypeEnums {

    /**
     * 创建服装
     * 用户创建新服装
     */
    CREATE,

    /**
     * 更新服装
     * 用户更新服装信息
     */
    UPDATE,

    /**
     * 删除服装
     * 用户删除服装
     */
    DELETE;

    /**
     * 获取事件类型描述
     */
    public String getDescription() {
        return switch (this) {
            case CREATE -> "创建服装";
            case UPDATE -> "更新服装";
            case DELETE -> "删除服装";
        };
    }
}
