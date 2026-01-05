package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.DictInfraConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import com.xiaoo.kaleido.admin.infrastructure.mapper.DictMapper;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.response.PageResp;
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

    private final DictMapper dictMapper;
    private final DictInfraConvertor dictInfraConvertor;

    @Override
    @Transactional
    public void save(DictAggregate dictAggregate) {
        DictPO po = dictInfraConvertor.toPO(dictAggregate);
        if (po.getId() == null) {
            // 插入
            dictMapper.insert(po);
        } else {
            // 更新
            dictMapper.updateById(po);
        }
    }

    @Override
    @Transactional
    public void update(DictAggregate dictAggregate) {
        DictPO po = dictInfraConvertor.toPO(dictAggregate);
        dictMapper.updateById(po);
    }

    @Override
    public Optional<DictAggregate> findById(String id) {
        return Optional.ofNullable(dictMapper.selectById(id))
                .map(dictInfraConvertor::toEntity);
    }

    @Override
    public Optional<DictAggregate> findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        DictPO po = dictMapper.findByTypeCodeAndDictCode(typeCode, dictCode);
        return po != null ? Optional.of(dictInfraConvertor.toEntity(po)) : Optional.empty();
    }

    @Override
    public List<DictAggregate> findByTypeCode(String typeCode) {
        List<DictPO> poList = dictMapper.findByTypeCode(typeCode);
        return poList.stream()
                .map(dictInfraConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictAggregate> findEnabledByTypeCode(String typeCode) {
        List<DictPO> poList = dictMapper.findEnabledByTypeCode(typeCode, DataStatusEnum.ENABLE.name());
        return poList.stream()
                .map(dictInfraConvertor::toEntity)
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
        dictMapper.deleteById(id);
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
        List<DictPO> poList = dictMapper.selectByCondition(queryReq);
        return poList.stream()
                .map(dictInfraConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PageResp<DictAggregate> pageQueryByCondition(DictPageQueryReq pageQueryReq) {
        // 创建MyBatis Plus分页对象
        Page<DictPO> page = new Page<>(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());

        // 执行分页查询
        IPage<DictPO> poPage = dictMapper.selectByPageCondition(page, pageQueryReq);

        // 转换为聚合根列表
        List<DictAggregate> aggregateList = poPage.getRecords().stream()
                .map(dictInfraConvertor::toEntity)
                .collect(Collectors.toList());

        // 构建分页响应
        PageResp<DictAggregate> pageResp = new PageResp<>();
        pageResp.setList(aggregateList);
        pageResp.setTotal(poPage.getTotal());
        pageResp.setPageNum(poPage.getCurrent());
        pageResp.setPageSize(poPage.getSize());
        pageResp.setTotalPage(poPage.getSize() == 0 ? 0 : (long) Math.ceil((double) poPage.getTotal() / poPage.getSize()));
        return pageResp;
    }
}
