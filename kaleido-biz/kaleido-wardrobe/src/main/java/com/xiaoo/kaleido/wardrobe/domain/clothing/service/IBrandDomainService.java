package com.xiaoo.kaleido.wardrobe.domain.clothing.service;


import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;

/**
 * 品牌领域服务接口
 * <p>
 * 处理品牌相关的业务逻辑，包括品牌创建、状态管理、信息更新等核心领域操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IBrandDomainService {

    /**
     * 创建品牌

     * 根据品牌名称、Logo路径和描述创建新品牌，系统会自动生成品牌ID并设置初始状态
     *
     * @param name        品牌名称，不能为空
     * @param logoPath    品牌Logo路径（在minio中的文件路径），可为空
     * @param description 品牌描述，可为空
     * @return 品牌聚合根，包含完整的品牌信息
     */
    BrandAggregate createBrand(String name, String logoPath, String description);

    /**
     * 根据ID查找品牌，如果不存在则抛出异常

     * 用于命令操作中需要确保品牌存在的场景
     *
     * @param brandId 品牌ID字符串，不能为空
     * @return 品牌聚合根，包含完整的品牌信息
     */
    BrandAggregate findByIdOrThrow(String brandId);

    /**
     * 更新品牌信息

     * 更新品牌的Logo路径和描述信息
     *
     * @param brandId     品牌ID，不能为空
     * @param logoPath    新品牌Logo路径（在minio中的文件路径），可为空
     * @param description 新品牌描述，可为空
     * @return 更新后的品牌聚合根
     */
    BrandAggregate updateBrand(String brandId, String logoPath, String description);

    /**
     * 验证品牌名称的唯一性

     * 验证品牌名称是否全局唯一
     *
     * @param name 品牌名称，不能为空
     * @return 如果品牌名称唯一返回true，否则返回false
     */
    boolean isBrandNameUnique(String name);

    /**
     * 查询品牌名称
     *
     * @param brandId 品牌ID
     * @return 品牌名称，如果品牌不存在或brandId为空则返回null
     */
    String getBrandName(String brandId);
}
