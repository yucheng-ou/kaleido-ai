package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IDictCommandService;
import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.domain.dict.service.IDictDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典命令服务（应用层）
 * 负责字典相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictCommandService implements IDictCommandService {

    private final IDictRepository dictRepository;
    private final IDictDomainService dictDomainService;

    public String createDict(AddDictCommand command) {
        // 1. 调用领域服务创建字典
        DictAggregate dictAggregate = dictDomainService.createDict(
                command.getTypeCode(),
                command.getTypeName(),
                command.getDictCode(),
                command.getDictName(),
                command.getDictValue(),
                command.getSort()
        );

        // 2. 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典创建成功，字典ID: {}, 类型编码: {}, 字典编码: {}",
                dictAggregate.getId(), command.getTypeCode(), command.getDictCode());
        return dictAggregate.getId();
    }

    public void updateDict(String typeCode, String dictCode, UpdateDictCommand command) {
        // 1. 调用领域服务更新字典
        DictAggregate dictAggregate = dictDomainService.updateDict(
                typeCode,
                dictCode,
                command.getTypeName(),
                command.getDictName(),
                command.getDictValue(),
                command.getSort()
        );

        // 2. 保存字典
        dictRepository.update(dictAggregate);

        log.info("字典更新成功，类型编码: {}, 字典编码: {}", typeCode, dictCode);
    }

    public void deleteDict(String typeCode, String dictCode) {
        // 1. 删除字典
        dictRepository.deleteByTypeCodeAndDictCode(typeCode, dictCode);

        log.info("字典删除成功，类型编码: {}, 字典编码: {}", typeCode, dictCode);
    }
}
