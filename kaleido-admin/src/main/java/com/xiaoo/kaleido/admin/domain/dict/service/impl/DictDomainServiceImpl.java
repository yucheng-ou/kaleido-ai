package com.xiaoo.kaleido.admin.domain.dict.service.impl;

import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.domain.dict.service.IDictDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictDomainServiceImpl implements IDictDomainService {

    private final IDictRepository dictRepository;

    @Override
    public DictAggregate createDict(String typeCode, String typeName, String dictCode,
                                    String dictName, String dictValue, Integer sort) {
        // 1. 验证字典编码唯一性
        if (dictRepository.existsByTypeCodeAndDictCode(typeCode, dictCode)) {
            throw AdminException.of(AdminErrorCode.DICT_CODE_EXIST);
        }

        // 2. 创建字典
        DictAggregate dictAggregate = DictAggregate.create(typeCode, typeName, dictCode, dictName, dictValue, sort);

        return dictAggregate;
    }

    @Override
    public DictAggregate updateDict(String typeCode, String dictCode, String typeName, String dictName, String dictValue, Integer sort) {
        // 1. 获取字典
        DictAggregate dictAggregate = dictRepository.findByTypeCodeAndDictCode(typeCode, dictCode);
        if (dictAggregate == null) {
            throw AdminException.of(AdminErrorCode.DICT_NOT_EXIST);
        }

        // 2. 更新字典信息
        dictAggregate.updateInfo(typeName, dictName, dictValue, sort);

        return dictAggregate;
    }
}
