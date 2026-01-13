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

        log.info("字典领域服务创建字典，字典ID: {}, 类型编码: {}, 字典编码: {}",
                dictAggregate.getId(), typeCode, dictCode);

        return dictAggregate;
    }

    @Override
    public DictAggregate updateDict(String dictId, String typeName, String dictName, String dictValue, Integer sort) {
        // 1. 获取字典
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);

        // 2. 更新字典信息
        dictAggregate.updateInfo(typeName, dictName, dictValue, sort);

        log.info("字典领域服务更新字典，字典ID: {}, 字典名称: {}", dictId, dictName);
        return dictAggregate;
    }

    @Override
    public DictAggregate enableDict(String dictId) {
        // 1. 获取字典
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);

        // 2. 启用字典
        dictAggregate.enable();

        log.info("字典领域服务启用字典，字典ID: {}", dictId);
        return dictAggregate;
    }

    @Override
    public DictAggregate disableDict(String dictId) {
        // 1. 获取字典
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);

        // 2. 禁用字典
        dictAggregate.disable();

        log.info("字典领域服务禁用字典，字典ID: {}", dictId);
        return dictAggregate;
    }

    @Override
    public DictAggregate deleteDict(String dictId) {
        // 1. 获取字典（验证字典是否存在）
        DictAggregate dictAggregate = dictRepository.findByIdOrThrow(dictId);

        log.info("字典领域服务准备删除字典，字典ID: {}", dictId);
        return dictAggregate;
    }

}
