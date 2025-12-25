package com.xiaoo.kaleido.admin.dict.command;

import com.xiaoo.kaleido.admin.dict.domain.adapter.repository.DictRepository;
import com.xiaoo.kaleido.admin.dict.domain.model.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.dict.domain.service.DictDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
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

    private final DictRepository dictRepository;
    private final DictDomainService dictDomainService;

    /**
     * 创建字典
     *
     * @param command 创建字典命令
     * @return 字典ID
     */
    @Transactional
    public String createDict(AddDictCommand command) {
        // 验证参数
        if (command.getTypeCode() == null || command.getTypeCode().trim().isEmpty()) {
            throw AdminException.of(AdminErrorCode.DICT_TYPE_CODE_EMPTY.getCode(), AdminErrorCode.DICT_TYPE_CODE_EMPTY.getMessage());
        }
        if (command.getDictCode() == null || command.getDictCode().trim().isEmpty()) {
            throw AdminException.of(AdminErrorCode.DICT_CODE_EMPTY.getCode(), AdminErrorCode.DICT_CODE_EMPTY.getMessage());
        }
        if (command.getDictName() == null || command.getDictName().trim().isEmpty()) {
            throw AdminException.of(AdminErrorCode.DICT_NAME_EMPTY.getCode(), AdminErrorCode.DICT_NAME_EMPTY.getMessage());
        }

        // 调用领域服务创建字典
        DictAggregate dictAggregate = dictDomainService.createDict(
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
    @Transactional
    public void updateDict(UpdateDictCommand command) {
        // 验证参数
        if (command.getDictId() == null || command.getDictId().trim().isEmpty()) {
            throw AdminException.of(AdminErrorCode.DICT_NOT_EXIST.getCode(), "字典ID不能为空");
        }

        DictAggregate dictAggregate;
        if (command.getTypeName() != null && !command.getTypeName().trim().isEmpty()) {
            // 更新字典类型信息
            dictAggregate = dictDomainService.updateDictTypeInfo(command.getDictId(), command.getTypeName());
        } else {
            // 更新字典信息
            dictAggregate = dictDomainService.updateDict(command.getDictId(),
                    command.getDictName(), command.getDictValue(), command.getSort());
        }

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典更新成功，字典ID: {}", command.getDictId());
    }

    /**
     * 启用字典
     *
     * @param dictId 字典ID
     */
    @Transactional
    public void enableDict(String dictId) {
        // 调用领域服务启用字典
        DictAggregate dictAggregate = dictDomainService.enableDict(dictId);

        // 保存字典
        dictRepository.save(dictAggregate);

        log.info("字典启用成功，字典ID: {}", dictId);
    }

    /**
     * 禁用字典
     *
     * @param dictId 字典ID
     */
    @Transactional
    public void disableDict(String dictId) {
        // 调用领域服务禁用字典
        DictAggregate dictAggregate = dictDomainService.disableDict(dictId);

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
        // 调用领域服务删除字典
        dictDomainService.deleteDict(dictId);

        log.info("字典删除成功，字典ID: {}", dictId);
    }

    /**
     * 根据字典类型编码和字典编码获取字典值
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典值
     */
    public String getDictValue(String typeCode, String dictCode) {
        DictAggregate dictAggregate = dictDomainService.getDictByCode(typeCode, dictCode);
        return dictAggregate.getDictValue();
    }

    /**
     * 验证字典是否有效（存在且启用）
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 是否有效
     */
    public boolean isValidDict(String typeCode, String dictCode) {
        return dictDomainService.isValidDict(typeCode, dictCode);
    }
}
