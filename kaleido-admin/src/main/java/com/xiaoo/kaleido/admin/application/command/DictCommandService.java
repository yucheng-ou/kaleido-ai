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
    private final IDictDomainService IDictDomainService;

    /**
     * 创建字典
     *
     * @param command 创建字典命令
     * @return 字典ID
     */
    public String createDict(AddDictCommand command) {

        // 调用领域服务创建字典
        DictAggregate dictAggregate = IDictDomainService.createDict(
                command.getTypeCode(),
                command.getTypeName(),
                command.getDictCode(),
                command.getDictName(),
                command.getDictValue(),
                command.getSort()
        );

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典创建成功，字典ID: {}, 类型编码: {}, 字典编码: {}",
                dictAggregate.getId(), command.getTypeCode(), command.getDictCode());
        return dictAggregate.getId();
    }

    /**
     * 更新字典
     *
     * @param command 更新字典命令
     */
    public void updateDict(UpdateDictCommand command) {

        DictAggregate dictAggregate;
        if (command.getTypeName() != null && !command.getTypeName().trim().isEmpty()) {
            // 更新字典类型信息
            dictAggregate = IDictDomainService.updateDictTypeInfo(command.getId(), command.getTypeName());
        } else {
            // 更新字典信息
            dictAggregate = IDictDomainService.updateDict(command.getId(),
                    command.getDictName(), command.getDictValue(), command.getSort());
        }

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典更新成功，字典ID: {}", command.getId());
    }

    /**
     * 启用字典
     *
     * @param dictId 字典ID
     */
    public void enableDict(String dictId) {
        // 调用领域服务启用字典
        DictAggregate dictAggregate = IDictDomainService.enableDict(dictId);

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典启用成功，字典ID: {}", dictId);
    }

    /**
     * 禁用字典
     *
     * @param dictId 字典ID
     */
    public void disableDict(String dictId) {
        // 调用领域服务禁用字典
        DictAggregate dictAggregate = IDictDomainService.disableDict(dictId);

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典禁用成功，字典ID: {}", dictId);
    }

    /**
     * 删除字典
     *
     * @param dictId 字典ID
     */
    @Transactional
    public void deleteDict(String dictId) {
        // 调用领域服务获取要删除的字典对象
        DictAggregate dictAggregate = IDictDomainService.deleteDict(dictId);

        // 执行软删除：标记为删除状态并更新
        dictRepository.deleteById(dictAggregate.getId());

        log.info("字典软删除成功，字典ID: {}", dictId);
    }

    /**
     * 根据字典类型编码和字典编码获取字典值
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典值
     */
    public String getDictValue(String typeCode, String dictCode) {
        DictAggregate dictAggregate = IDictDomainService.getDictByCode(typeCode, dictCode);
        return dictAggregate.getDictValue();
    }

}
