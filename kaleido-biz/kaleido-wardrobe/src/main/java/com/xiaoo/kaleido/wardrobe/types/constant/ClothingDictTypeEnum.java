package com.xiaoo.kaleido.wardrobe.types.constant;

import lombok.Getter;

/**
 * 服装字典类型枚举
 * 用于标识不同类型的字典数据
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Getter
public enum ClothingDictTypeEnum {
    
    /**
     * 服装类型字典
     */
    CLOTHING_TYPE("CLOTHING_TYPE", "服装类型"),
    
    /**
     * 颜色字典
     */
    COLOR("COLOR", "颜色"),
    
    /**
     * 季节字典
     */
    SEASON("SEASON", "季节");
    
    private final String code;
    private final String description;
    
    ClothingDictTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 获取字典类型编码
     *
     * @return 字典类型编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 获取字典类型描述
     *
     * @return 字典类型描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 字典类型编码
     * @return 对应的枚举，如果找不到返回null
     */
    public static ClothingDictTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (ClothingDictTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 检查编码是否有效
     *
     * @param code 字典类型编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValidCode(String code) {
        return fromCode(code) != null;
    }
    
    /**
     * 获取所有字典类型编码
     *
     * @return 字典类型编码数组
     */
    public static String[] getAllCodes() {
        ClothingDictTypeEnum[] values = values();
        String[] codes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            codes[i] = values[i].code;
        }
        return codes;
    }
    
    @Override
    public String toString() {
        return code + " - " + description;
    }
}
