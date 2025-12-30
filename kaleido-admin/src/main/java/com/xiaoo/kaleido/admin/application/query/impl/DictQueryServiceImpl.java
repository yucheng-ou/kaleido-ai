package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.convertor.DictConvertor;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.api.admin.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.response.DictResponse;
import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import com.xiaoo.kaleido.base.response.PageResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PageResp<DictResponse> pageQueryDicts(DictPageQueryReq pageQueryReq) {
        log.debug("分页查询字典列表，pageQueryReq={}", pageQueryReq);
        
        // 使用数据库分页查询（PageHelper）
        PageResp<DictAggregate> pageResult = dictRepository.pageQueryByCondition(pageQueryReq);
        
        // 转换为响应对象
        List<DictResponse> responseList = pageResult.getList().stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 构建分页响应
        PageResp<DictResponse> pageResp = new PageResp<>();
        pageResp.setList(responseList);
        pageResp.setTotal(pageResult.getTotal());
        pageResp.setPageNum(pageResult.getPageNum());
        pageResp.setPageSize(pageResult.getPageSize());
        pageResp.setTotalPage(pageResult.getPageSize() == 0 ? 0 : (long) Math.ceil((double) pageResult.getTotal() / pageResult.getPageSize()));
        return pageResp;
    }

    /**
     * 判断字典是否匹配查询条件（DictQueryReq）
     */
    private boolean matchesQuery(DictAggregate dict, DictQueryReq queryReq) {
        if (queryReq.getTypeCode() != null && !queryReq.getTypeCode().isEmpty()) {
            if (!dict.getTypeCode().equals(queryReq.getTypeCode())) {
                return false;
            }
        }
        
        if (queryReq.getTypeName() != null && !queryReq.getTypeName().isEmpty()) {
            if (dict.getTypeName() == null || !dict.getTypeName().contains(queryReq.getTypeName())) {
                return false;
            }
        }
        
        if (queryReq.getDictCode() != null && !queryReq.getDictCode().isEmpty()) {
            if (!dict.getDictCode().equals(queryReq.getDictCode())) {
                return false;
            }
        }
        
        if (queryReq.getDictName() != null && !queryReq.getDictName().isEmpty()) {
            if (dict.getDictName() == null || !dict.getDictName().contains(queryReq.getDictName())) {
                return false;
            }
        }
        
        if (queryReq.getDictValue() != null && !queryReq.getDictValue().isEmpty()) {
            if (dict.getDictValue() == null || !dict.getDictValue().contains(queryReq.getDictValue())) {
                return false;
            }
        }
        
        if (queryReq.getStatus() != null) {
            if (dict.getStatus() != queryReq.getStatus()) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * 判断字典是否匹配查询条件（DictPageQueryReq）
     */
    private boolean matchesQuery(DictAggregate dict, DictPageQueryReq pageQueryReq) {
        if (pageQueryReq.getTypeCode() != null && !pageQueryReq.getTypeCode().isEmpty()) {
            if (!dict.getTypeCode().equals(pageQueryReq.getTypeCode())) {
                return false;
            }
        }
        
        if (pageQueryReq.getTypeName() != null && !pageQueryReq.getTypeName().isEmpty()) {
            if (dict.getTypeName() == null || !dict.getTypeName().contains(pageQueryReq.getTypeName())) {
                return false;
            }
        }
        
        if (pageQueryReq.getDictCode() != null && !pageQueryReq.getDictCode().isEmpty()) {
            if (!dict.getDictCode().equals(pageQueryReq.getDictCode())) {
                return false;
            }
        }
        
        if (pageQueryReq.getDictName() != null && !pageQueryReq.getDictName().isEmpty()) {
            if (dict.getDictName() == null || !dict.getDictName().contains(pageQueryReq.getDictName())) {
                return false;
            }
        }
        
        if (pageQueryReq.getDictValue() != null && !pageQueryReq.getDictValue().isEmpty()) {
            if (dict.getDictValue() == null || !dict.getDictValue().contains(pageQueryReq.getDictValue())) {
                return false;
            }
        }
        
        if (pageQueryReq.getStatus() != null) {
            if (dict.getStatus() != pageQueryReq.getStatus()) {
                return false;
            }
        }
        
        return true;
    }
}
