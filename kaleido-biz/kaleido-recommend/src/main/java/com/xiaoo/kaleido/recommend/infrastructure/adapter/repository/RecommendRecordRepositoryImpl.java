package com.xiaoo.kaleido.recommend.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.infrastructure.adapter.repository.convertor.RecommendRecordInfraConvertor;
import com.xiaoo.kaleido.recommend.infrastructure.dao.RecommendRecordDao;
import com.xiaoo.kaleido.recommend.infrastructure.dao.po.RecommendRecordPO;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐记录仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RecommendRecordRepositoryImpl implements IRecommendRecordRepository {

    private final RecommendRecordDao recommendRecordDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecommendRecordAggregate save(RecommendRecordAggregate recommendRecordAggregate) {
        try {
            // 1.转换RecommendRecordAggregate为RecommendRecordPO
            RecommendRecordPO recommendRecordPO = RecommendRecordInfraConvertor.INSTANCE.toPO(recommendRecordAggregate);

            // 2.保存推荐记录
            recommendRecordDao.insert(recommendRecordPO);

            log.info("推荐记录保存成功，记录ID: {}, 用户ID: {}, 提示词长度: {}, 穿搭ID: {}",
                    recommendRecordAggregate.getId(), recommendRecordAggregate.getUserId(),
                    recommendRecordAggregate.getPromptLength(), recommendRecordAggregate.getOutfitId());

            // 3.返回保存后的聚合根
            return RecommendRecordInfraConvertor.INSTANCE.toAggregate(recommendRecordPO);
        } catch (Exception e) {
            log.error("推荐记录保存失败，记录ID: {}, 原因: {}", recommendRecordAggregate.getId(), e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.OPERATE_FAILED, "推荐记录保存失败");
        }
    }

    @Override
    public RecommendRecordAggregate findById(String id) {
        try {
            // 1.查询推荐记录基本信息
            RecommendRecordPO recommendRecordPO = recommendRecordDao.selectById(id);
            if (recommendRecordPO == null) {
                throw RecommendException.of(BizErrorCode.DATA_NOT_EXIST);
            }

            // 2.转换为RecommendRecordAggregate
            return RecommendRecordInfraConvertor.INSTANCE.toAggregate(recommendRecordPO);
        } catch (RecommendException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询推荐记录失败，记录ID: {}, 原因: {}", id, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.QUERY_FAIL, "推荐记录查询失败");
        }
    }

    @Override
    public List<RecommendRecordAggregate> findByUserId(String userId) {
        try {
            // 1.查询推荐记录基本信息列表
            List<RecommendRecordPO> recommendRecordPOs = recommendRecordDao.findByUserId(userId);

            // 2.转换为RecommendRecordAggregate列表
            return recommendRecordPOs.stream()
                    .map(RecommendRecordInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户推荐记录列表失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.QUERY_FAIL, "用户推荐记录列表查询失败");
        }
    }

    @Override
    public RecommendRecordAggregate findByOutfitId(String outfitId) {
        try {
            // 1.查询推荐记录基本信息
            RecommendRecordPO recommendRecordPO = recommendRecordDao.findByOutfitId(outfitId);
            if (recommendRecordPO == null) {
                return null;
            }

            // 2.转换为RecommendRecordAggregate
            return RecommendRecordInfraConvertor.INSTANCE.toAggregate(recommendRecordPO);
        } catch (Exception e) {
            log.error("根据穿搭ID查询推荐记录失败，穿搭ID: {}, 原因: {}", outfitId, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.QUERY_FAIL, "根据穿搭ID查询推荐记录失败");
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            // 逻辑删除
            recommendRecordDao.deleteById(id);

            log.info("推荐记录删除成功，记录ID: {}", id);
        } catch (Exception e) {
            log.error("推荐记录删除失败，记录ID: {}, 原因: {}", id, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.OPERATE_FAILED, "推荐记录删除失败");
        }
    }

    @Override
    public List<RecommendRecordAggregate> findByUserIdAndHasOutfit(String userId, boolean hasOutfit) {
        try {
            // 1.查询推荐记录基本信息列表
            List<RecommendRecordPO> recommendRecordPOs = recommendRecordDao.findByUserIdAndHasOutfit(userId, hasOutfit);

            // 2.转换为RecommendRecordAggregate列表
            return recommendRecordPOs.stream()
                    .map(RecommendRecordInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户推荐记录列表（按是否有关联穿搭）失败，用户ID: {}, hasOutfit: {}, 原因: {}",
                    userId, hasOutfit, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.QUERY_FAIL, "用户推荐记录列表查询失败");
        }
    }

    @Override
    public RecommendRecordAggregate findByExecutionId(String executionId) {
        try {
            // 1.查询推荐记录基本信息
            RecommendRecordPO recommendRecordPO = recommendRecordDao.findByExecutionId(executionId);
            if (recommendRecordPO == null) {
                return null;
            }

            // 2.转换为RecommendRecordAggregate
            return RecommendRecordInfraConvertor.INSTANCE.toAggregate(recommendRecordPO);
        } catch (Exception e) {
            log.error("根据执行记录ID查询推荐记录失败，执行记录ID: {}, 原因: {}", executionId, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.QUERY_FAIL, "根据执行记录ID查询推荐记录失败");
        }
    }

    @Override
    public void update(RecommendRecordAggregate recommendRecordAggregate) {
        try {
            // 1.转换RecommendRecordAggregate为RecommendRecordPO
            RecommendRecordPO recommendRecordPO = RecommendRecordInfraConvertor.INSTANCE.toPO(recommendRecordAggregate);


            // 2.更新推荐记录
            int updatedRows = recommendRecordDao.updateByIdAndUserId(recommendRecordPO);
            
            if (updatedRows == 0) {
                log.error("推荐记录更新失败，未找到匹配的记录，记录ID: {}, 用户ID: {}", 
                        recommendRecordAggregate.getId(), recommendRecordAggregate.getUserId());
                throw RecommendException.of(RecommendErrorCode.OPERATE_FAILED, "推荐记录更新失败，记录不存在或已被删除");
            }

            log.info("推荐记录更新成功，记录ID: {}, 用户ID: {}, 状态: {}, 穿搭ID: {}",
                    recommendRecordAggregate.getId(), recommendRecordAggregate.getUserId(),
                    recommendRecordAggregate.getStatus(), recommendRecordAggregate.getOutfitId());
        } catch (Exception e) {
            log.error("推荐记录更新失败，记录ID: {}, 原因: {}", recommendRecordAggregate.getId(), e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.OPERATE_FAILED, "推荐记录更新失败");
        }
    }
}
