package com.xiaoo.kaleido.recommend.application.command;

import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.domain.recommend.service.IRecommendRecordDomainService;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 推荐命令服务
 * <p>
 * 负责编排推荐相关的命令操作，包括创建推荐记录、删除推荐记录等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendCommandService {

    private final IRecommendRecordDomainService recommendRecordDomainService;
    private final IRecommendRecordRepository recommendRecordRepository;

    /**
     * 创建推荐记录
     * <p>
     * 用户输入提示词，创建推荐记录
     * 注意：金币扣减逻辑暂时省略，后续需要集成金币服务
     *
     * @param userId 用户ID
     * @param prompt 用户输入的推荐需求提示词
     * @return 创建的推荐记录ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createRecommendRecord(String userId, String prompt) {
        // 1.检查金币是否足够（TODO: 后续集成金币服务）
        validateCoinBalance(userId);

        // 2.调用领域服务创建推荐记录
        RecommendRecordAggregate recommendRecord = recommendRecordDomainService.createRecommendRecord(userId, prompt);

        // 3.保存推荐记录
        recommendRecordRepository.save(recommendRecord);

        // 4.扣减金币（TODO: 后续集成金币服务）
        deductCoinsForRecommendGeneration(userId, recommendRecord.getId());

        return recommendRecord.getId();
    }

    /**
     * 删除推荐记录
     *
     * @param recommendRecordId 推荐记录ID
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecommendRecord(String recommendRecordId, String userId) {
        // 1.查找推荐记录
        RecommendRecordAggregate recommendRecord = recommendRecordDomainService.findByIdAndUserIdOrThrow(
                recommendRecordId, userId);

        // 2.逻辑删除（设置deleted标志）
        // 注意：BaseEntity中deleted字段是Integer类型，这里设置为1表示已删除
        recommendRecord.setDeleted(1);

        // 3.保存删除状态
        recommendRecordRepository.save(recommendRecord);

        // 4.记录日志
        log.info("推荐记录删除成功，记录ID: {}, 用户ID: {}", recommendRecordId, userId);
    }

    /**
     * 验证用户金币余额
     *
     * @param userId 用户ID
     */
    private void validateCoinBalance(String userId) {
        // TODO: 后续集成金币服务
        log.info("验证用户金币余额（TODO），用户ID: {}", userId);
    }

    /**
     * 为推荐生成扣减金币
     *
     * @param userId 用户ID
     * @param recommendRecordId 推荐记录ID
     */
    private void deductCoinsForRecommendGeneration(String userId, String recommendRecordId) {
        // TODO: 后续集成金币服务
        log.info("扣减金币（TODO），记录ID: {}, 用户ID: {}", recommendRecordId, userId);
    }
}
