package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.DictInfraConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import com.xiaoo.kaleido.admin.infrastructure.dao.DictDao;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.redis.service.RedissonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DictRepositoryImpl implements IDictRepository {

    private final DictDao dictDao;

    @Override
    public void save(DictAggregate dictAggregate) {
        // 1. 将聚合根转换为持久化对象
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);

        // 2. 调用DAO层插入数据
        dictDao.insert(po);
    }

    @Override
    @CacheInvalidate(name = "dict:", key = "#dictAggregate.typeCode + ':' + #dictAggregate.dictCode")
    public void update(DictAggregate dictAggregate) {
        // 1. 将聚合根转换为持久化对象
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);

        // 2. 调用DAO层更新数据
        dictDao.updateById(po);
    }

    @Override
    public DictAggregate findById(String id) {
        // 1. 调用DAO层查询持久化对象
        // 2. 如果存在则转换为聚合根，否则返回null
        DictPO po = dictDao.selectById(id);
        return po != null ? DictInfraConvertor.INSTANCE.toEntity(po) : null;
    }

    @Override
    @Cached(name = ":dict:", key = "#typeCode + ':' + #dictCode", expire = 60, localExpire = 1, cacheType = CacheType.BOTH, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    @CacheRefresh(refresh = 50, timeUnit = TimeUnit.MINUTES)
    public DictAggregate findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        // 1. 调用DAO层查询持久化对象
        DictPO po = dictDao.findByTypeCodeAndDictCode(typeCode, dictCode);

        // 2. 如果存在则转换为聚合根，否则返回null
        return po != null ? DictInfraConvertor.INSTANCE.toEntity(po) : null;
    }

    @Override
    public List<DictAggregate> findByTypeCode(String typeCode) {
        // 1. 调用DAO层查询持久化对象列表
        List<DictPO> poList = dictDao.findByTypeCode(typeCode);

        // 2. 转换为聚合根列表
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByTypeCodeAndDictCode(String typeCode, String dictCode) {
        // 调用DAO层检查字典编码是否存在
        return dictDao.existsByTypeCodeAndDictCode(typeCode, dictCode);
    }

    @Override
    @CacheInvalidate(name = "dict:", key = "#typeCode + ':' + #dictCode")
    public void deleteByTypeCodeAndDictCode(String typeCode, String dictCode) {
        dictDao.deleteByTypeCodeAndDictCode(typeCode, dictCode);
    }


    @Override
    public List<DictAggregate> pageQueryByCondition(DictPageQueryReq pageQueryReq) {
        // 1. 执行分页查询（PageHelper已在Service层启动）
        List<DictPO> poList = dictDao.selectByPageCondition(pageQueryReq);

        // 2. 转换为聚合根列表
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }
}
