package com.xiaoo.kaleido.recommend.domain.recommend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.domain.recommend.service.IRecommendRecordDomainService;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推荐记录领域服务实现类
 * <p>
 * 实现推荐记录领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendRecordDomainServiceImpl implements IRecommendRecordDomainService {

    /**
     * 提示词最大长度限制
     */
    private static final int MAX_PROMPT_LENGTH = 1000;

    private final IRecommendRecordRepository recommendRecordRepository;

    @Override
    public RecommendRecordAggregate createRecommendRecord(String userId, String prompt, String outfitId) {
        // 1.参数校验
        validateUserId(userId);
        validatePrompt(prompt);

        // 2.业务规则校验：提示词长度限制
        if (prompt.length() > MAX_PROMPT_LENGTH) {
            throw RecommendException.of(RecommendErrorCode.PROMPT_TOO_LONG,
                    String.format("提示词长度不能超过%d个字符", MAX_PROMPT_LENGTH));
        }

        // 3.创建推荐记录聚合根
        RecommendRecordAggregate recommendRecord = RecommendRecordAggregate.create(userId, prompt, outfitId);

        // 4.记录日志
        log.info("推荐记录创建成功，记录ID: {}, 用户ID: {}, 提示词长度: {}",
                recommendRecord.getId(), userId, prompt.length());

        return recommendRecord;
    }

    @Override
    public RecommendRecordAggregate findByIdOrThrow(String recommendRecordId) {
        // 1.参数校验
        if (StrUtil.isBlank(recommendRecordId)) {
            throw RecommendException.of(RecommendErrorCode.PARAM_NOT_NULL, "推荐记录ID不能为空");
        }

        // 2.查找推荐记录
        return recommendRecordRepository.findById(recommendRecordId);
    }

    @Override
    public RecommendRecordAggregate findByIdAndUserIdOrThrow(String recommendRecordId, String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(recommendRecordId)) {
            throw RecommendException.of(RecommendErrorCode.PARAM_NOT_NULL, "推荐记录ID不能为空");
        }
        validateUserId(userId);

        // 2.查找推荐记录
        RecommendRecordAggregate recommendRecord = findByIdOrThrow(recommendRecordId);

        // 3.验证用户权限（只有记录所有者可以查询）
        if (!recommendRecord.getUserId().equals(userId)) {
            throw RecommendException.of(RecommendErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        return recommendRecord;
    }

    @Override
    public List<RecommendRecordAggregate> findRecommendRecordsByUserId(String userId) {
        // 1.参数校验
        validateUserId(userId);

        // 2.查询用户推荐记录列表
        return recommendRecordRepository.findByUserId(userId);
    }

    @Override
    public RecommendRecordAggregate findByOutfitId(String outfitId) {
        // 1.参数校验
        if (StrUtil.isBlank(outfitId)) {
            throw RecommendException.of(RecommendErrorCode.PARAM_NOT_NULL, "穿搭ID不能为空");
        }

        // 2.根据穿搭ID查找推荐记录
        return recommendRecordRepository.findByOutfitId(outfitId);
    }

    /**
     * 验证用户ID参数
     *
     * @param userId 用户ID
     */
    private void validateUserId(String userId) {
        if (StrUtil.isBlank(userId)) {
            throw RecommendException.of(RecommendErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }
    }

    /**
     * 验证提示词参数
     *
     * @param prompt 提示词
     */
    private void validatePrompt(String prompt) {
        if (StrUtil.isBlank(prompt)) {
            throw RecommendException.of(RecommendErrorCode.PROMPT_EMPTY);
        }
    }
}
