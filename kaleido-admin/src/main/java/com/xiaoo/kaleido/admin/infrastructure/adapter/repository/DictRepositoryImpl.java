package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.DictInfraConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictDao;
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
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);
        dictDao.insert(po);
    }

    @Override
    @Transactional
    public void update(DictAggregate dictAggregate) {
        DictPO po = DictInfraConvertor.INSTANCE.toPO(dictAggregate);
        dictDao.updateById(po);
    }

    @Override
    public Optional<DictAggregate> findById(String id) {
        return Optional.ofNullable(dictDao.selectById(id))
                .map(DictInfraConvertor.INSTANCE::toEntity);
    }

    @Override
    public Optional<DictAggregate> findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        DictPO po = dictDao.findByTypeCodeAndDictCode(typeCode, dictCode);
        return po != null ? Optional.of(DictInfraConvertor.INSTANCE.toEntity(po)) : Optional.empty();
    }

    @Override
    public List<DictAggregate> findByTypeCode(String typeCode) {
        List<DictPO> poList = dictDao.findByTypeCode(typeCode);
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictAggregate> findEnabledByTypeCode(String typeCode) {
        List<DictPO> poList = dictDao.findEnabledByTypeCode(typeCode, DataStatusEnum.ENABLE.name());
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByTypeCodeAndDictCode(String typeCode, String dictCode) {
        return dictDao.existsByTypeCodeAndDictCode(typeCode, dictCode);
    }

    @Override
    public boolean existsByTypeCode(String typeCode) {
        return dictDao.existsByTypeCode(typeCode);
    }

    @Override
    public void deleteById(String id) {
        dictDao.deleteById(id);
    }

    @Override
    public DictAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.DICT_NOT_EXIST));
    }

    @Override
    public DictAggregate findByTypeCodeAndDictCodeOrThrow(String typeCode, String dictCode) {
        return findByTypeCodeAndDictCode(typeCode, dictCode)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.DICT_NOT_EXIST));
    }

    @Override
    public List<DictAggregate> queryByCondition(DictQueryReq queryReq) {
        List<DictPO> poList = dictDao.selectByCondition(queryReq);
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictAggregate> pageQueryByCondition(DictPageQueryReq pageQueryReq) {
        // 执行分页查询（PageHelper已在Service层启动）
        List<DictPO> poList = dictDao.selectByPageCondition(pageQueryReq);

        // 转换为聚合根列表
        return poList.stream()
                .map(DictInfraConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }
}
