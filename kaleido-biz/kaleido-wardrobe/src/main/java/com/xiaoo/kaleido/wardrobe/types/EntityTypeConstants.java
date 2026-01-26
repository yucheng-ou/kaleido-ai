package com.xiaoo.kaleido.wardrobe.types;

/**
 * 实体类型常量类
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
public class EntityTypeConstants {

    private EntityTypeConstants() {
        // 防止实例化
    }

    /**
     * 服装实体类型
     */
    public static final String CLOTHING = "CLOTHING";

    /**
     * 位置实体类型
     */
    public static final String LOCATION = "LOCATION";

    /**
     * 穿搭实体类型
     */
    public static final String OUTFIT = "OUTFIT";

    /**
     * 验证实体类型是否有效
     *
     * @param entityType 实体类型
     * @return 如果实体类型有效返回true，否则返回false
     */
    public static boolean isValidEntityType(String entityType) {
        return CLOTHING.equals(entityType) 
            || LOCATION.equals(entityType) 
            || OUTFIT.equals(entityType);
    }

    /**
     * 获取所有实体类型
     *
     * @return 实体类型数组
     */
    public static String[] getAllEntityTypes() {
        return new String[]{CLOTHING, LOCATION, OUTFIT};
    }
}
