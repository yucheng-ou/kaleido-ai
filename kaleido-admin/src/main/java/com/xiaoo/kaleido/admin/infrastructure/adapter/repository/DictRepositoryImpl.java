package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.DictInfraConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import com.xiaoo.kaleido.admin.infrastructure.dao.DictDao;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    @Transactional
    public void save(DictAggregate dictAggregate) {
        // 1. 将聚合根转换为持久化对象
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);
        
        // 2. 调用DAO层插入数据
        dictDao.insert(po);
    }

    @Override
    @Transactional
    public void update(DictAggregate dictAggregate) {
        // 1. 将聚合根转换为持久化对象
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);
        
        // 2. 调用DAO层更新数据
        dictDao.updateById(po);
    }

    @Override
    public Optional<DictAggregate> findById(String id) {
        // 1. 调用DAO层查询持久化对象
        // 2. 如果存在则转换为聚合根，否则返回空Optional
        return Optional.ofNullable(dictDao.selectById(id))
                .map(DictInfraConvertor.INSTANCE::toEntity);
    }

    @Override
    public Optional<DictAggregate> findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        // 1. 调用DAO层查询持久化对象
        DictPO po = dictDao.findByTypeCodeAndDictCode(typeCode, dictCode);
        
        // 2. 如果存在则转换为聚合根，否则返回空Optional
        return po != null ? Optional.of(DictInfraConvertor.INSTANCE.toEntity(po)) : Optional.empty();
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
    public List<DictAggregate> findEnabledByTypeCode(String typeCode) {
        // 1. 调用DAO层查询启用的持久化对象列表
        List<DictPO> poList = dictDao.findEnabledByTypeCode(typeCode, DataStatusEnum.ENABLE.name());
        
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
    public void deleteById(String id) {
        // 调用DAO层删除数据
        dictDao.deleteById(id);
    }

    @Override
    public DictAggregate findByIdOrThrow(String id) {
        // 1. 调用findById方法查询
        // 2. 如果存在则返回，否则抛出异常
        return findById(id)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.DICT_NOT_EXIST));
    }

    @Override
    public DictAggregate findByTypeCodeAndDictCodeOrThrow(String typeCode, String dictCode) {
        // 1. 调用findByTypeCodeAndDictCode方法查询
        // 2. 如果存在则返回，否则抛出异常
        return findByTypeCodeAndDictCode(typeCode, dictCode)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.DICT_NOT_EXIST));
    }

    @Override
    public List<DictAggregate> queryByCondition(DictQueryReq queryReq) {
        // 1. 调用DAO层根据条件查询持久化对象列表
        List<DictPO> poList = dictDao.selectByCondition(queryReq);
        
        // 2. 转换为聚合根列表
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
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
