package com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository;

import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 推荐记录仓储接口
 * <p>
 * 定义推荐记录聚合根的持久化操作，遵循仓储模式
 * 实现应放在infrastructure层
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
public interface IRecommendRecordRepository {

    /**
     * 保存推荐记录聚合根
     * <p>
     * 保存或更新推荐记录聚合根
     *
     * @param recommendRecordAggregate 推荐记录聚合根，不能为空
     * @return 保存后的推荐记录聚合根
     */
    RecommendRecordAggregate save(RecommendRecordAggregate recommendRecordAggregate);

    /**
     * 根据ID查找推荐记录聚合根
     * <p>
     * 查找指定ID的推荐记录聚合根
     *
     * @param id 推荐记录ID，不能为空
     * @return 推荐记录聚合根
     * @throws com.xiaoo.kaleido.recommend.types.exception.RecommendException 当推荐记录不存在时抛出
     */
    RecommendRecordAggregate findById(String id);

    /**
     * 根据ID查找推荐记录聚合根（包含已删除的）
     * <p>
     * 查找指定ID的推荐记录聚合根，包含已逻辑删除的记录
     *
     * @param id 推荐记录ID，不能为空
     * @return 推荐记录聚合根
     * @throws com.xiaoo.kaleido.recommend.types.exception.RecommendException 当推荐记录不存在时抛出
     */
    RecommendRecordAggregate findByIdIncludeDeleted(String id);

    /**
     * 根据用户ID查找推荐记录聚合根列表
     * <p>
     * 查找指定用户的所有推荐记录，按创建时间倒序排列
     *
     * @param userId 用户ID，不能为空
     * @return 推荐记录聚合根列表
     */
    List<RecommendRecordAggregate> findByUserId(String userId);

    /**
     * 根据穿搭ID查找推荐记录聚合根
     * <p>
     * 用于通过穿搭ID反向查找对应的推荐记录
     *
     * @param outfitId 穿搭ID，不能为空
     * @return 推荐记录聚合根（如果存在），否则返回null
     */
    RecommendRecordAggregate findByOutfitId(String outfitId);

    /**
     * 删除推荐记录聚合根
     * <p>
     * 删除指定ID的推荐记录聚合根（逻辑删除）
     *
     * @param id 推荐记录ID，不能为空
     */
    void deleteById(String id);

    /**
     * 检查推荐记录是否存在
     *
     * @param id 推荐记录ID，不能为空
     * @return 如果存在返回true，否则返回false
     */
    boolean existsById(String id);

    /**
     * 根据用户ID统计推荐记录数量
     *
     * @param userId 用户ID，不能为空
     * @return 推荐记录数量
     */
    long countByUserId(String userId);

    /**
     * 根据用户ID和是否有关联穿搭查找推荐记录
     *
     * @param userId 用户ID，不能为空
     * @param hasOutfit 是否有关联穿搭
     * @return 推荐记录聚合根列表
     */
    List<RecommendRecordAggregate> findByUserIdAndHasOutfit(String userId, boolean hasOutfit);
}
