package com.xiaoo.kaleido.recommend.application.query;

import com.xiaoo.kaleido.api.recommend.response.RecommendRecordResponse;

import java.util.List;

/**
 * 推荐查询服务接口
 * <p>
 * 负责推荐相关的查询操作，遵循CQRS原则
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
public interface IRecommendQueryService {

    /**
     * 根据ID查询推荐记录详情
     *
     * @param recommendRecordId 推荐记录ID
     * @param userId 用户ID（用于权限验证）
     * @return 推荐记录DTO
     */
    RecommendRecordResponse findRecommendRecordById(String recommendRecordId, String userId);

    /**
     * 查询用户的推荐记录列表
     *
     * @param userId 用户ID
     * @return 推荐记录DTO列表
     */
    List<RecommendRecordResponse> findRecommendRecordsByUserId(String userId);
}
