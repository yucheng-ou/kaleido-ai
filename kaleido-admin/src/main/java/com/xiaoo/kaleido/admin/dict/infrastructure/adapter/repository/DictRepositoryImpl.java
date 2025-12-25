package com.xiaoo.kaleido.admin.dict.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.dict.domain.adapter.repository.DictRepository;
import com.xiaoo.kaleido.admin.dict.domain.model.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.dict.infrastructure.dao.po.DictPO;
import com.xiaoo.kaleido.admin.dict.infrastructure.mapper.DictMapper;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
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
public class DictRepositoryImpl implements DictRepository {

    private final DictMapper dictMapper;

    @Override
    @Transactional
    public DictAggregate save(DictAggregate dictAggregate) {
        DictPO po = convertToPO(dictAggregate);
        if (po.getId() == null) {
            // 插入
            dictMapper.insert(po);
        } else {
            // 更新
            dictMapper.updateById(po);
        }
        return dictAggregate;
    }

    @Override
    public Optional<DictAggregate> findById(String id) {
        Long longId = id != null ? Long.parseLong(id) : null;
        return Optional.ofNullable(dictMapper.selectById(longId))
                .map(this::convertToEntity);
    }

    @Override
    public Optional<DictAggregate> findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        DictPO po = dictMapper.findByTypeCodeAndDictCode(typeCode, dictCode);
        return po != null ? Optional.of(convertToEntity(po)) : Optional.empty();
    }

    @Override
    public List<DictAggregate> findByTypeCode(String typeCode) {
        List<DictPO> poList = dictMapper.findByTypeCode(typeCode);
        return poList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictAggregate> findEnabledByTypeCode(String typeCode) {
        List<DictPO> poList = dictMapper.findEnabledByTypeCode(typeCode, DataStatusEnum.ENABLE.name());
        return poList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByTypeCodeAndDictCode(String typeCode, String dictCode) {
        return dictMapper.existsByTypeCodeAndDictCode(typeCode, dictCode);
    }

    @Override
    public boolean existsByTypeCode(String typeCode) {
        return dictMapper.existsByTypeCode(typeCode);
    }

    @Override
    public void deleteById(String id) {
        Long longId = id != null ? Long.parseLong(id) : null;
        dictMapper.deleteById(longId);
    }

    @Override
    public DictAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(AdminException::dictNotExist);
    }

    @Override
    public DictAggregate findByTypeCodeAndDictCodeOrThrow(String typeCode, String dictCode) {
        return findByTypeCodeAndDictCode(typeCode, dictCode)
                .orElseThrow(AdminException::dictNotExist);
    }

    /**
     * 将字典实体转换为持久化对象
     */
    private DictPO convertToPO(DictAggregate dictAggregate) {
        DictPO po = new DictPO();
        po.setId(dictAggregate.getId() != null ? Long.parseLong(dictAggregate.getId()) : null);
        po.setTypeCode(dictAggregate.getTypeCode());
        po.setTypeName(dictAggregate.getTypeName());
        po.setDictCode(dictAggregate.getDictCode());
        po.setDictName(dictAggregate.getDictName());
        po.setDictValue(dictAggregate.getDictValue());
        po.setSort(dictAggregate.getSort());
        po.setStatus(dictAggregate.getStatus() != null ? dictAggregate.getStatus().name() : null);
        po.setCreatedAt(dictAggregate.getCreatedAt());
        po.setUpdatedAt(dictAggregate.getUpdatedAt());
        po.setDeleted(0); // 默认未删除
        return po;
    }

    /**
     * 将持久化对象转换为字典实体
     */
    private DictAggregate convertToEntity(DictPO po) {
        DictAggregate dictAggregate = DictAggregate.builder()
                .typeCode(po.getTypeCode())
                .typeName(po.getTypeName())
                .dictCode(po.getDictCode())
                .dictName(po.getDictName())
                .dictValue(po.getDictValue())
                .sort(po.getSort())
                .status(po.getStatus() != null ? DataStatusEnum.valueOf(po.getStatus()) : null)
                .build();
        dictAggregate.setId(po.getId() != null ? po.getId().toString() : null);
        return dictAggregate;
    }
}
