package com.xiaoo.kaleido.wardrobe.domain.image.enums;

/**
 * 领域类型枚举
 * <p>
 * 标识图片所属的业务领域
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
public enum DomainType {
    
    /**
     * 服装领域
     */
    CLOTHING("clothing", "服装"),
    
    /**
     * 位置领域
     */
    LOCATION("location", "位置"),
    
    /**
     * 穿搭领域
     */
    OUTFIT("outfit", "穿搭");
    
    private final String code;
    private final String description;
    
    DomainType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据code获取枚举
     */
    public static DomainType fromCode(String code) {
        for (DomainType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的领域类型: " + code);
    }
}
