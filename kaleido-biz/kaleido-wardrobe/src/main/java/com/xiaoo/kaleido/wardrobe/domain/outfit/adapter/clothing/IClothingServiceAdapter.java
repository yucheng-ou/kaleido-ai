package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.clothing;

/**
 * 服装服务适配器接口（防腐层）
 * <p>
 * 用于解耦穿搭领域对服装领域的直接依赖，遵循防腐层设计模式
 * 通过适配器模式将外部领域模型转换为当前领域需要的模型
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public interface IClothingServiceAdapter {

    /**
     * 验证服装是否属于指定用户
     * <p>
     * 用于业务规则校验：确保穿搭中的服装都属于同一用户
     *
     * @param clothingId 服装ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 如果服装属于指定用户返回true，否则返回false
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当服装不存在或参数无效时抛出
     */
    boolean validateClothingBelongsToUser(String clothingId, String userId);

    /**
     * 批量验证服装列表是否都属于指定用户
     * <p>
     * 用于业务规则校验：确保穿搭中的所有服装都属于同一用户
     *
     * @param clothingIds 服装ID列表，不能为空
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当有任何服装不属于指定用户时抛出
     */
    void validateClothingsBelongToUser(java.util.List<String> clothingIds, String userId);
}
