package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;
import com.xiaoo.kaleido.admin.domain.dict.adapter.repository.IDictRepository;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.domain.dict.service.IDictDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class DictCommandService {

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

    public void updateDict(String dictId, UpdateDictCommand command) {
        // 1. 调用领域服务更新字典
        DictAggregate dictAggregate = dictDomainService.updateDict(
                dictId,
                command.getTypeName(),
                command.getDictName(),
                command.getDictValue(),
                command.getSort()
        );

        // 2. 保存字典
        dictRepository.update(dictAggregate);

        log.info("字典更新成功，字典ID: {}", dictId);
    }

    public void enableDict(String dictId) {
        // 1. 调用领域服务启用字典
        DictAggregate dictAggregate = dictDomainService.enableDict(dictId);

        // 2. 保存字典
        dictRepository.update(dictAggregate);

        log.info("字典启用成功，字典ID: {}", dictId);
    }

    public void disableDict(String dictId) {
        // 1. 调用领域服务禁用字典
        DictAggregate dictAggregate = dictDomainService.disableDict(dictId);

        // 2. 保存字典
        dictRepository.update(dictAggregate);

        log.info("字典禁用成功，字典ID: {}", dictId);
    }

    @Transactional
    public void deleteDict(String dictId) {
        // 1. 调用领域服务获取要删除的字典对象
        DictAggregate dictAggregate = dictDomainService.deleteDict(dictId);

        // 2. 执行软删除：标记为删除状态并更新
        dictRepository.deleteById(dictAggregate.getId());

        log.info("字典软删除成功，字典ID: {}", dictId);
    }
}
