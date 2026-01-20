package com.xiaoo.kaleido.coin.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinAccountPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 金币账户基础设施层转换器
 * <p>
 * 负责CoinAccountAggregate和CoinAccountPO之间的转换
 * 使用MapStruct实现，编译时生成转换代码
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface CoinAccountInfraConvertor {

    CoinAccountInfraConvertor INSTANCE = Mappers.getMapper(CoinAccountInfraConvertor.class);

    /**
     * CoinAccountAggregate转换为CoinAccountPO
     * <p>
     * 用于将领域聚合根转换为持久化对象，便于保存到数据库
     *
     * @param aggregate 金币账户聚合根
     * @return 金币账户持久化对象
     */
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance")
    @Mapping(target = "lockVersion", ignore = true)
    CoinAccountPO toPO(CoinAccountAggregate aggregate);

    /**
     * CoinAccountPO转换为CoinAccountAggregate
     * <p>
     * 用于将持久化对象转换为领域聚合根，便于业务逻辑处理
     * 注意：streams字段需要单独处理，这里忽略
     *
     * @param po 金币账户持久化对象
     * @return 金币账户聚合根
     */
    @Mapping(target = "streams", ignore = true)
    CoinAccountAggregate toAggregate(CoinAccountPO po);
}
