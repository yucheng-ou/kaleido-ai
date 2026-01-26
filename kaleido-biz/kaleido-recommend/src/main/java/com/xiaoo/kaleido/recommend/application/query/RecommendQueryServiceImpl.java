package com.xiaoo.kaleido.recommend.application.query;

import com.xiaoo.kaleido.api.recommend.response.RecommendRecordResponse;
import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐查询服务实现类
 * <p>
 * 负责推荐相关的查询操作，遵循CQRS原则
 * 应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendQueryServiceImpl implements IRecommendQueryService {

    private final IRecommendRecordRepository recommendRecordRepository;

    /**
     * 根据ID查询推荐记录详情
     * <p>
     * 查询指定ID的推荐记录，并验证用户权限
     *
     * @param recommendRecordId 推荐记录ID
     * @param userId 用户ID（用于权限验证）
     * @return 推荐记录DTO
     * @throws RecommendException 当推荐记录不存在或用户无权限时抛出
     */
    @Override
    public RecommendRecordResponse findRecommendRecordById(String recommendRecordId, String userId) {
        // 1. 查询推荐记录（如果记录不存在，仓储层会抛出RecommendException）
        RecommendRecordAggregate recommendRecord = recommendRecordRepository.findById(recommendRecordId);

        // 2. 权限验证：检查记录是否属于当前用户
        if (!recommendRecord.getUserId().equals(userId)) {
            log.warn("用户无权限访问推荐记录，记录ID: {}, 用户ID: {}, 记录所属用户ID: {}", 
                    recommendRecordId, userId, recommendRecord.getUserId());
            throw RecommendException.of(RecommendErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        // 3. 转换为DTO并返回
        RecommendRecordResponse response = convertToResponse(recommendRecord);
        log.debug("查询推荐记录成功，记录ID: {}, 用户ID: {}", recommendRecordId, userId);
        return response;
    }

    /**
     * 查询用户的推荐记录列表
     * <p>
     * 查询指定用户的所有推荐记录，按创建时间倒序排列
     *
     * @param userId 用户ID
     * @return 推荐记录DTO列表
     */
    @Override
    public List<RecommendRecordResponse> findRecommendRecordsByUserId(String userId) {
        // 1. 查询用户的所有推荐记录
        List<RecommendRecordAggregate> recommendRecords = recommendRecordRepository.findByUserId(userId);
        
        // 2. 转换为DTO列表并返回
        List<RecommendRecordResponse> responses = recommendRecords.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        log.debug("查询用户推荐记录列表成功，用户ID: {}, 记录数量: {}", userId, responses.size());
        return responses;
    }

    /**
     * 将推荐记录聚合根转换为响应DTO
     *
     * @param aggregate 推荐记录聚合根
     * @return 推荐记录响应DTO
     */
    private RecommendRecordResponse convertToResponse(RecommendRecordAggregate aggregate) {
        return RecommendRecordResponse.builder()
                .id(aggregate.getId())
                .userId(aggregate.getUserId())
                .prompt(aggregate.getPrompt())
                .outfitId(aggregate.getOutfitId())
                .build();
    }
}
