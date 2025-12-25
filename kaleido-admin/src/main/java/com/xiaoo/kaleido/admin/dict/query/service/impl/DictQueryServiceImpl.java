package com.xiaoo.kaleido.admin.dict.query.service.impl;

import com.xiaoo.kaleido.admin.dict.domain.adapter.repository.DictRepository;
import com.xiaoo.kaleido.admin.dict.domain.model.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.dict.query.dto.DictDTO;
import com.xiaoo.kaleido.admin.dict.query.request.DictQueryRequest;
import com.xiaoo.kaleido.admin.dict.query.service.DictQueryService;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.response.PageResp;
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

    private final DictRepository dictRepository;

    @Override
    public DictDTO findById(String dictId) {
        log.debug("根据ID查询字典，dictId={}", dictId);
        
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);
        return convertToDTO(dictAggregate);
    }

    @Override
    public DictDTO findByTypeCodeAndDictCode(String typeCode, String dictCode) {
        log.debug("根据类型编码和字典编码查询字典，typeCode={}, dictCode={}", typeCode, dictCode);
        
        DictAggregate dictAggregate = dictRepository.findByTypeCodeAndDictCodeOrThrow(typeCode, dictCode);
        return convertToDTO(dictAggregate);
    }

    @Override
    public List<DictDTO> findByTypeCode(String typeCode) {
        log.debug("根据类型编码查询字典列表，typeCode={}", typeCode);
        
        List<DictAggregate> dictAggregateList = dictRepository.findByTypeCode(typeCode);
        return dictAggregateList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictDTO> findEnabledByTypeCode(String typeCode) {
        log.debug("根据类型编码查询启用的字典列表，typeCode={}", typeCode);
        
        List<DictAggregate> dictAggregateList = dictRepository.findEnabledByTypeCode(typeCode);
        return dictAggregateList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictDTO> queryDicts(DictQueryRequest queryRequest) {
        log.debug("根据条件查询字典列表，queryRequest={}", queryRequest);
        
        // 简单实现：获取所有然后过滤
        List<DictAggregate> allDicts = dictRepository.findByTypeCode("");
        
        return allDicts.stream()
                .filter(dict -> matchesQuery(dict, queryRequest))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PageResp<DictDTO> pageQueryDicts(DictQueryRequest queryRequest) {
        log.debug("分页查询字典列表，queryRequest={}", queryRequest);
        
        // 简单实现：先查询所有符合条件的，然后内存分页
        List<DictDTO> allDicts = queryDicts(queryRequest);
        
        // 这里需要从queryRequest中获取分页参数
        // 由于DictQueryRequest没有分页参数，这里使用默认值
        int pageNum = 1;
        int pageSize = 10;
        
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, allDicts.size());
        
        if (start >= allDicts.size()) {
            return PageResp.empty();
        }
        
        List<DictDTO> pageData = allDicts.subList(start, end);
        
        return PageResp.of(pageData, allDicts.size(), pageNum, pageSize);
    }

    /**
     * 判断字典是否匹配查询条件
     */
    private boolean matchesQuery(DictAggregate dict, DictQueryRequest queryRequest) {
        if (queryRequest.getTypeCode() != null && !queryRequest.getTypeCode().isEmpty()) {
            if (!dict.getTypeCode().equals(queryRequest.getTypeCode())) {
                return false;
            }
        }
        
        if (queryRequest.getTypeName() != null && !queryRequest.getTypeName().isEmpty()) {
            if (dict.getTypeName() == null || !dict.getTypeName().contains(queryRequest.getTypeName())) {
                return false;
            }
        }
        
        if (queryRequest.getDictCode() != null && !queryRequest.getDictCode().isEmpty()) {
            if (!dict.getDictCode().equals(queryRequest.getDictCode())) {
                return false;
            }
        }
        
        if (queryRequest.getDictName() != null && !queryRequest.getDictName().isEmpty()) {
            if (dict.getDictName() == null || !dict.getDictName().contains(queryRequest.getDictName())) {
                return false;
            }
        }
        
        if (queryRequest.getDictValue() != null && !queryRequest.getDictValue().isEmpty()) {
            if (dict.getDictValue() == null || !dict.getDictValue().contains(queryRequest.getDictValue())) {
                return false;
            }
        }
        
        if (queryRequest.getStatus() != null) {
            if (dict.getStatus() != queryRequest.getStatus()) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * 将字典实体转换为DTO
     */
    private DictDTO convertToDTO(DictAggregate dictAggregate) {
        DictDTO dto = new DictDTO();
        dto.setId(dictAggregate.getId());
        dto.setTypeCode(dictAggregate.getTypeCode());
        dto.setTypeName(dictAggregate.getTypeName());
        dto.setDictCode(dictAggregate.getDictCode());
        dto.setDictName(dictAggregate.getDictName());
        dto.setDictValue(dictAggregate.getDictValue());
        dto.setSort(dictAggregate.getSort());
        dto.setStatus(dictAggregate.getStatus());
        dto.setCreatedAt(dictAggregate.getCreatedAt());
        dto.setUpdatedAt(dictAggregate.getUpdatedAt());
        return dto;
    }
}
