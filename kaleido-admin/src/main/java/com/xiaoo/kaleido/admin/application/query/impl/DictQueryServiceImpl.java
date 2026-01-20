package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.convertor.DictConvertor;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
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
        log.debug("根据ID查询字典信息，dictId={}", dictId);
        
        // 1. 调用仓储层查询字典聚合根
        DictAggregate dictAggregate = dictRepository.findById(dictId);
        if (dictAggregate == null) {
            throw AdminException.of(AdminErrorCode.DICT_NOT_EXIST);
        }
        
        // 2. 转换为响应对象
        return dictConvertor.toResponse(dictAggregate);
    }

    @Override
    public DictResponse findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        log.debug("根据类型编码和字典编码查询字典信息，typeCode={}, dictCode={}", typeCode, dictCode);
        
        // 1. 调用仓储层查询字典聚合根
        DictAggregate dictAggregate = dictRepository.findByTypeCodeAndDictCode(typeCode, dictCode);
        if (dictAggregate == null) {
            throw AdminException.of(AdminErrorCode.DICT_NOT_EXIST);
        }
        
        // 2. 转换为响应对象
        return dictConvertor.toResponse(dictAggregate);
    }

    @Override
    public List<DictResponse> findByTypeCode(String typeCode) {
        log.debug("根据类型编码查询字典列表，typeCode={}", typeCode);
        
        // 1. 调用仓储层查询字典聚合根列表
        List<DictAggregate> dictAggregateList = dictRepository.findByTypeCode(typeCode);
        
        // 2. 转换为响应对象列表
        return dictAggregateList.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public PageInfo<DictResponse> pageQueryDicts(DictPageQueryReq pageQueryReq) {
        log.debug("分页查询字典列表，pageQueryReq={}", pageQueryReq);
        
        // 1. 启动PageHelper分页
        PageHelper.startPage(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());
        
        // 2. 调用仓储层进行分页查询
        List<DictAggregate> aggregates = dictRepository.pageQueryByCondition(pageQueryReq);
        
        // 3. 转换为响应对象列表
        List<DictResponse> responseList = aggregates.stream()
                .map(dictConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 4. 构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }
}
