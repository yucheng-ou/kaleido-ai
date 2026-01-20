package com.xiaoo.kaleido.coin.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinStreamPO;

import java.util.ArrayList;
import java.util.List;

/**
 * 金币流水基础设施层转换器
 * <p>
 * 负责CoinStream和CoinStreamPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public class CoinStreamInfraConvertor {

    public static final CoinStreamInfraConvertor INSTANCE = new CoinStreamInfraConvertor();

    /**
     * CoinStream列表转换为CoinStreamPO列表
     * <p>
     * 用于批量转换领域实体列表为持久化对象列表
     *
     * @param entityList 金币流水实体列表
     * @return 金币流水持久化对象列表
     */
    public List<CoinStreamPO> toPOList(List<CoinStream> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CoinStreamPO> list = new ArrayList<>(entityList.size());
        for (CoinStream coinStream : entityList) {
            list.add(toPO(coinStream));
        }

        return list;
    }

    /**
     * 单个CoinStream转换为CoinStreamPO
     *
     * @param coinStream 金币流水实体
     * @return 金币流水持久化对象
     */
    public CoinStreamPO toPO(CoinStream coinStream) {
        if (coinStream == null) {
            return null;
        }

        CoinStreamPO po = new CoinStreamPO();
        po.setId(coinStream.getId());
        po.setCreatedAt(coinStream.getCreatedAt());
        po.setUpdatedAt(coinStream.getUpdatedAt());
        po.setDeleted(coinStream.getDeleted());
        po.setUserId(coinStream.getUserId());
        po.setType(coinStream.getType() != null ? coinStream.getType().name() : null);
        po.setAmount(coinStream.getAmount());
        po.setBalanceAfter(coinStream.getBalanceAfter());
        po.setBizType(coinStream.getBizType() != null ? coinStream.getBizType().name() : null);
        po.setBizId(coinStream.getBizId());
        po.setRemark(coinStream.getRemark());
        // 设置乐观锁版本号，默认为1（新记录）
        po.setLockVersion(1);
        
        return po;
    }
}
