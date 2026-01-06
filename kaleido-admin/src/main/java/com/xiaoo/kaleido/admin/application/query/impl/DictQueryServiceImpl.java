package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.convertor.DictConvertor;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典查询服务实现
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictQueryServiceImpl implements DictQueryService {

    private final IDictRepository dictRepository;
    private final DictConvertor dictConvertor;

    @Override
    public DictResponse findById(String dictId) {
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);
        return dictConvertor.toResponse(dictAggregate);
    }

    @Override
    public DictResponse findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        DictAggregate dictAggregate = dictRepository.findByTypeCodeAndDictCodeOrThrow(typeCode, dictCode);
        return dictConvertor.toResponse(dictAggregate);
    }

    @Override
    public List<DictResponse> findByTypeCode(String typeCode) {
        log.debug("根据类型编码查询字典列表，typeCode={}", typeCode);
        
        List<DictAggregate> dictAggregateList = dictRepository.findByTypeCode(typeCode);
        return dictAggregateList.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictResponse> findEnabledByTypeCode(String typeCode) {
        log.debug("根据类型编码查询启用的字典列表，typeCode={}", typeCode);
        
        List<DictAggregate> dictAggregateList = dictRepository.findEnabledByTypeCode(typeCode);
        return dictAggregateList.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictResponse> queryDicts(DictQueryReq queryReq) {
        log.debug("根据条件查询字典列表，queryReq={}", queryReq);
        
        // 使用数据库条件查询
        List<DictAggregate> dictAggregates = dictRepository.queryByCondition(queryReq);
        
        return dictAggregates.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<DictResponse> pageQueryDicts(DictPageQueryReq pageQueryReq) {
        log.debug("分页查询字典列表，pageQueryReq={}", pageQueryReq);
        
        // 启动PageHelper分页
        PageHelper.startPage(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());
        
        // 使用数据库分页查询
        List<DictAggregate> aggregates = dictRepository.pageQueryByCondition(pageQueryReq);
        
        // 转换为响应对象
        List<DictResponse> responseList = aggregates.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }
}
