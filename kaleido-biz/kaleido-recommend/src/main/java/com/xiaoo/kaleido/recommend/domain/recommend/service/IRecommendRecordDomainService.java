package com.xiaoo.kaleido.recommend.domain.recommend.service;

import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;

import java.util.List;

/**
 * 推荐记录领域服务接口
 * <p>
 * 处理推荐记录相关的业务逻辑，包括推荐记录创建、查询等核心领域操作
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
public interface IRecommendRecordDomainService {

    /**
     * 创建推荐记录
     * <p>
     * 根据用户ID和提示词创建新的推荐记录
     * 注意：参数校验在Service层完成
     *
     * @param userId 用户ID，不能为空
     * @param prompt 用户输入的推荐需求提示词，不能为空
     * @return 推荐记录聚合根
     * @throws RecommendException 当业务规则校验失败时抛出
     */
    RecommendRecordAggregate createRecommendRecord(String userId, String prompt);

    /**
     * 根据ID查找推荐记录，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保推荐记录存在的场景
     *
     * @param recommendRecordId 推荐记录ID字符串，不能为空
     * @return 推荐记录聚合根
     * @throws RecommendException 当推荐记录不存在时抛出
     */
    RecommendRecordAggregate findByIdOrThrow(String recommendRecordId);

    /**
     * 根据ID和用户ID查找推荐记录，如果不存在或用户不匹配则抛出异常
     * <p>
     * 用于需要验证用户权限的查询场景
     *
     * @param recommendRecordId 推荐记录ID字符串，不能为空
     * @param userId 用户ID字符串，不能为空
     * @return 推荐记录聚合根
     * @throws RecommendException 当推荐记录不存在或用户不匹配时抛出
     */
    RecommendRecordAggregate findByIdAndUserIdOrThrow(String recommendRecordId, String userId);

    /**
     * 批量获取用户的推荐记录列表
     *
     * @param userId 用户ID，不能为空
     * @return 推荐记录聚合根列表
     * @throws RecommendException 当参数无效时抛出
     */
    List<RecommendRecordAggregate> findRecommendRecordsByUserId(String userId);

    /**
     * 根据穿搭ID查找推荐记录
     * <p>
     * 用于通过穿搭ID反向查找对应的推荐记录
     *
     * @param outfitId 穿搭ID，不能为空
     * @return 推荐记录聚合根（如果存在），否则返回null
     */
    RecommendRecordAggregate findByOutfitId(String outfitId);
}
