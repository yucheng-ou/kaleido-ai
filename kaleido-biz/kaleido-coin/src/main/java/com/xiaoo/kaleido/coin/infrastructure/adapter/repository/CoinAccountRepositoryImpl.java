package com.xiaoo.kaleido.coin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.coin.domain.account.adapter.repository.ICoinAccountRepository;
import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.infrastructure.adapter.repository.convertor.CoinAccountInfraConvertor;
import com.xiaoo.kaleido.coin.infrastructure.adapter.repository.convertor.CoinStreamInfraConvertor;
import com.xiaoo.kaleido.coin.infrastructure.dao.CoinAccountDao;
import com.xiaoo.kaleido.coin.infrastructure.dao.CoinStreamDao;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinAccountPO;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinStreamPO;
import com.xiaoo.kaleido.coin.types.exception.CoinErrorCode;
import com.xiaoo.kaleido.coin.types.exception.CoinException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 金币账户仓储实现（基础设施层）
 * <p>
 * 金币账户仓储接口的具体实现，负责金币账户聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CoinAccountRepositoryImpl implements ICoinAccountRepository {

    private final CoinAccountDao coinAccountDao;
    private final CoinStreamDao coinStreamDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CoinAccountAggregate accountAggregate) {
        try {
            // 1.转换CoinAccountAggregate为CoinAccountPO
            CoinAccountPO accountPO = CoinAccountInfraConvertor.INSTANCE.toPO(accountAggregate);

            // 2.保存账户基本信息
            coinAccountDao.insert(accountPO);

            // 3.保存流水记录列表
            saveStreams(accountAggregate);

            log.info("金币账户保存成功，账户ID: {}, 用户ID: {}, 余额: {}, 流水记录数: {}",
                    accountAggregate.getId(), accountAggregate.getUserId(),
                    accountAggregate.getBalance(), accountAggregate.getStreams().size());
        } catch (Exception e) {
            log.error("金币账户保存失败，账户ID: {}, 原因: {}", accountAggregate.getId(), e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.OPERATE_FAILED, "金币账户保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CoinAccountAggregate accountAggregate) {
        try {
            // 1.转换CoinAccountAggregate为CoinAccountPO
            CoinAccountPO accountPO = CoinAccountInfraConvertor.INSTANCE.toPO(accountAggregate);

            // 2.更新账户基本信息
            coinAccountDao.updateById(accountPO);

            // 3.保存新增流水记录列表（流水只能新增，不能删除与修改）
            // 注意：聚合根中只包含本次操作新增的流水记录
            saveStreams(accountAggregate);

            log.info("金币账户更新成功，账户ID: {}, 用户ID: {}, 余额: {}, 新增流水记录数: {}",
                    accountAggregate.getId(), accountAggregate.getUserId(),
                    accountAggregate.getBalance(), accountAggregate.getStreams().size());
        } catch (Exception e) {
            log.error("金币账户更新失败，账户ID: {}, 原因: {}", accountAggregate.getId(), e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.OPERATE_FAILED, "金币账户更新失败");
        }
    }

    @Override
    public Optional<CoinAccountAggregate> findById(String accountId) {
        try {
            // 1.查询账户基本信息
            CoinAccountPO accountPO = coinAccountDao.findById(accountId);
            if (accountPO == null) {
                return Optional.empty();
            }

            // 2.转换为CoinAccountAggregate（不查询流水记录）
            // 注意：根据设计，findById只查询账户信息，不查询流水记录
            // 聚合根中只维护当前新增的流水信息，历史流水记录不加载到内存
            CoinAccountAggregate accountAggregate = CoinAccountInfraConvertor.INSTANCE.toAggregate(accountPO);

            return Optional.of(accountAggregate);
        } catch (Exception e) {
            log.error("查询金币账户失败，账户ID: {}, 原因: {}", accountId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.QUERY_FAIL, "金币账户查询失败");
        }
    }

    @Override
    public Optional<CoinAccountAggregate> findByUserId(String userId) {
        try {
            // 1.查询账户基本信息
            CoinAccountPO accountPO = coinAccountDao.findByUserId(userId);
            if (accountPO == null) {
                return Optional.empty();
            }

            // 2.转换为CoinAccountAggregate（不查询流水记录）
            // 注意：根据设计，findByUserId只查询账户信息，不查询流水记录
            // 聚合根中只维护当前新增的流水信息，历史流水记录不加载到内存
            CoinAccountAggregate accountAggregate = CoinAccountInfraConvertor.INSTANCE.toAggregate(accountPO);

            return Optional.of(accountAggregate);
        } catch (Exception e) {
            log.error("查询用户金币账户失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.QUERY_FAIL, "用户金币账户查询失败");
        }
    }

    @Override
    public CoinAccountAggregate findByUserIdOrThrow(String userId) {
        return findByUserId(userId)
                .orElseThrow(() -> CoinException.of(BizErrorCode.DATA_NOT_EXIST, "用户金币账户不存在"));
    }

    @Override
    public boolean existsByUserId(String userId) {
        try {
            return coinAccountDao.existsByUserId(userId);
        } catch (Exception e) {
            log.error("检查用户金币账户是否存在失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.QUERY_FAIL, "用户金币账户存在性检查失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String accountId) {
        try {
            // 1.查询账户信息获取user_id
            CoinAccountPO accountPO = coinAccountDao.findById(accountId);
            if (accountPO == null) {
                log.warn("金币账户不存在，无需删除，账户ID: {}", accountId);
                return;
            }

            // 2.删除流水记录（通过user_id删除）
            coinStreamDao.deleteByUserId(accountPO.getUserId());

            // 3.删除账户
            coinAccountDao.deleteById(accountId);

            log.info("金币账户删除成功，账户ID: {}, 用户ID: {}", accountId, accountPO.getUserId());
        } catch (Exception e) {
            log.error("金币账户删除失败，账户ID: {}, 原因: {}", accountId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.OPERATE_FAILED, "金币账户删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        try {
            // 1.查询账户ID
            CoinAccountPO accountPO = coinAccountDao.findByUserId(userId);
            if (accountPO == null) {
                log.warn("用户金币账户不存在，无需删除，用户ID: {}", userId);
                return;
            }

            // 2.删除流水记录
            coinStreamDao.deleteByUserId(userId);

            // 3.删除账户
            coinAccountDao.deleteByUserId(userId);

            log.info("用户金币账户删除成功，用户ID: {}, 账户ID: {}", userId, accountPO.getId());
        } catch (Exception e) {
            log.error("用户金币账户删除失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.OPERATE_FAILED, "用户金币账户删除失败");
        }
    }

    @Override
    public boolean existsByBizTypeAndBizId(String bizType, String bizId) {
        try {
            return coinStreamDao.existsByBizTypeAndBizId(bizType, bizId);
        } catch (Exception e) {
            log.error("检查业务是否已处理失败，业务类型: {}, 业务ID: {}, 原因: {}", bizType, bizId, e.getMessage(), e);
            throw CoinException.of(CoinErrorCode.QUERY_FAIL, "业务处理状态检查失败");
        }
    }

    /**
     * 保存流水记录列表
     *
     * @param accountAggregate 账户聚合根，包含流水记录列表
     */
    private void saveStreams(CoinAccountAggregate accountAggregate) {
        List<CoinStream> streams = accountAggregate.getStreams();
        if (streams == null || streams.isEmpty()) {
            return;
        }

        // 转换CoinStream为CoinStreamPO
        List<CoinStreamPO> streamPOs = CoinStreamInfraConvertor.INSTANCE.toPOList(streams);

        // 设置用户ID
        String userId = accountAggregate.getUserId();
        streamPOs.forEach(po -> {
            po.setUserId(userId);
            // 注意：CoinStreamPO没有accountId字段，因为数据库表中没有这个字段
            // 通过user_id关联账户
        });

        // 批量插入
        if (!streamPOs.isEmpty()) {
            coinStreamDao.insert(streamPOs);
        }
    }
}
