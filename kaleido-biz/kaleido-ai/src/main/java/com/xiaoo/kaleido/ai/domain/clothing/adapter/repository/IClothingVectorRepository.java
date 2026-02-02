package com.xiaoo.kaleido.ai.domain.clothing.adapter.repository;

import com.xiaoo.kaleido.ai.domain.clothing.model.ClothingVector;

import java.util.List;

/**
 * 服装向量仓储接口
 * <p>
 * 定义服装向量的持久化操作，专门处理向量存储相关的操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
public interface IClothingVectorRepository {

    /**
     * 保存服装向量列表到向量存储
     * <p>
     * 将服装向量列表转换为向量文档并保存到Milvus向量存储
     *
     * @param clothingVectors 服装向量列表，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void save(List<ClothingVector> clothingVectors);

    /**
     * 根据相似度搜索服装向量
     * <p>
     * 根据查询文本在向量存储中搜索相似的服装向量
     *
     * @param queryText 查询文本，不能为空
     * @param topK 返回最相似的前K个结果，必须大于0
     * @return 相似的服装向量列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当搜索失败时抛出
     */
    List<ClothingVector> searchBySimilarity(String queryText, int topK);

    /**
     * 根据服装ID删除向量
     * <p>
     * 从向量存储中删除指定服装ID的向量
     *
     * @param clothingId 服装ID，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当删除失败时抛出
     */
    void deleteByClothingId(String clothingId);

    /**
     * 根据用户ID删除所有向量
     * <p>
     * 从向量存储中删除指定用户的所有服装向量
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当删除失败时抛出
     */
    void deleteByUserId(String userId);

    /**
     * 检查服装向量是否存在
     * <p>
     * 检查指定服装ID的向量是否已存在于向量存储中
     *
     * @param clothingId 服装ID，不能为空
     * @return 如果存在返回true，否则返回false
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    boolean existsByClothingId(String clothingId);
}
