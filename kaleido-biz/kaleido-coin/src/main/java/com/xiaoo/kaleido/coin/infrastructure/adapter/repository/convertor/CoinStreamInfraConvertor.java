package com.xiaoo.kaleido.coin.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.infrastructure.dao.po.CoinStreamPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 金币流水基础设施层转换器
 * <p>
 * 负责CoinStream和CoinStreamPO之间的转换
 * 使用MapStruct实现，编译时生成转换代码
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface CoinStreamInfraConvertor {

    CoinStreamInfraConvertor INSTANCE = Mappers.getMapper(CoinStreamInfraConvertor.class);

    /**
     * CoinStream转换为CoinStreamPO
     * <p>
     * 用于将领域实体转换为持久化对象，便于保存到数据库
     * 注意：数据库表中没有account_id字段，所以忽略accountId字段
     *
     * @param entity 金币流水实体
     * @return 金币流水持久化对象
     */
    @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().name() : null)")
    @Mapping(target = "bizType", expression = "java(entity.getBizType() != null ? entity.getBizType().name() : null)")
    @Mapping(target = "accountId", ignore = true) // 数据库表中没有account_id字段
    CoinStreamPO toPO(CoinStream entity);

    /**
     * CoinStreamPO转换为CoinStream
     * <p>
     * 用于将持久化对象转换为领域实体，便于业务逻辑处理
     * 注意：数据库表中没有account_id字段，所以accountId需要从外部设置
     *
     * @param po 金币流水持久化对象
     * @return 金币流水实体
     */
    @Mapping(target = "type", expression = "java(po.getType() != null ? CoinStream.StreamType.valueOf(po.getType()) : null)")
    @Mapping(target = "bizType", expression = "java(po.getBizType() != null ? CoinStream.BizType.valueOf(po.getBizType()) : null)")
    @Mapping(target = "accountId", ignore = true) // 数据库表中没有account_id字段，需要从外部设置
    CoinStream toEntity(CoinStreamPO po);

    /**
     * CoinStream列表转换为CoinStreamPO列表
     * <p>
     * 用于批量转换领域实体列表为持久化对象列表
     *
     * @param entityList 金币流水实体列表
     * @return 金币流水持久化对象列表
     */
    List<CoinStreamPO> toPOList(List<CoinStream> entityList);

    /**
     * CoinStreamPO列表转换为CoinStream列表
     * <p>
     * 用于批量转换持久化对象列表为领域实体列表
     *
     * @param poList 金币流水持久化对象列表
     * @return 金币流水实体列表
     */
    List<CoinStream> toEntityList(List<CoinStreamPO> poList);
}
