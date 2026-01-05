package com.xiaoo.kaleido.admin.domain.dict.service;

import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;

/**
 * 字典领域服务
 * 处理跨聚合的字典业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
public interface DictDomainService {

    /**
     * 创建字典
     *
     * @param typeCode  字典类型编码
     * @param typeName  字典类型名称
     * @param dictCode  字典编码
     * @param dictName  字典名称
     * @param dictValue 字典值
     * @param sort      排序
     * @return 创建的字典
     */
    DictAggregate createDict(String typeCode, String typeName, String dictCode,
                             String dictName, String dictValue, Integer sort);

    /**
     * 更新字典信息
     *
     * @param dictId    字典ID
     * @param dictName  字典名称
     * @param dictValue 字典值
     * @param sort      排序
     * @return 更新后的字典
     */
    DictAggregate updateDict(String dictId, String dictName, String dictValue, Integer sort);

    /**
     * 更新字典类型信息
     *
     * @param dictId   字典ID
     * @param typeName 字典类型名称
     * @return 更新后的字典
     */
    DictAggregate updateDictTypeInfo(String dictId, String typeName);

    /**
     * 启用字典
     *
     * @param dictId 字典ID
     * @return 启用后的字典
     */
    DictAggregate enableDict(String dictId);

    /**
     * 禁用字典
     *
     * @param dictId 字典ID
     * @return 禁用后的字典
     */
    DictAggregate disableDict(String dictId);

    /**
     * 删除字典
     *
     * @param dictId 字典ID
     * @return 要删除的字典对象
     */
    DictAggregate deleteDict(String dictId);

    /**
     * 根据字典类型编码和字典编码获取字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典
     */
    DictAggregate getDictByCode(String typeCode, String dictCode);
    
}
