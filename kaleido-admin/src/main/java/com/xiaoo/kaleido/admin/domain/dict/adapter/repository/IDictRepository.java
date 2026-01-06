package com.xiaoo.kaleido.admin.domain.dict.adapter.repository;

import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;

import java.util.List;
import java.util.Optional;

/**
 * 字典仓储接口（领域层）
 * 定义字典聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
public interface IDictRepository {

    /**
     * 保存字典
     *
     * @param dictAggregate 字典聚合根
     */
    void save(DictAggregate dictAggregate);

    /**
     * 更新字典
     *
     * @param dictAggregate 字典聚合根
     */
    void update(DictAggregate dictAggregate);

    /**
     * 根据ID查找字典聚合根
     *
     * @param id 字典ID
     * @return 字典聚合根（如果存在）
     */
    Optional<DictAggregate> findById(String id);

    /**
     * 根据ID查找字典聚合根，如果不存在则抛出异常
     *
     * @param id 字典ID
     * @return 字典聚合根
     */
    DictAggregate findByIdOrThrow(String id);

    /**
     * 根据字典类型编码和字典编码查找字典聚合根
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典聚合根（如果存在）
     */
    Optional<DictAggregate> findByTypeCodeAndDictCode(String typeCode, String dictCode);

    /**
     * 根据字典类型编码和字典编码查找字典聚合根，如果不存在则抛出异常
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典聚合根
     */
    DictAggregate findByTypeCodeAndDictCodeOrThrow(String typeCode, String dictCode);

    /**
     * 根据字典类型编码查找字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典列表
     */
    List<DictAggregate> findByTypeCode(String typeCode);

    /**
     * 根据字典类型编码查找启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典列表
     */
    List<DictAggregate> findEnabledByTypeCode(String typeCode);

    /**
     * 检查字典类型编码和字典编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 是否存在
     */
    boolean existsByTypeCodeAndDictCode(String typeCode, String dictCode);

    /**
     * 检查字典类型编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @return 是否存在
     */
    boolean existsByTypeCode(String typeCode);

    /**
     * 删除字典
     *
     * @param id 字典ID
     */
    void deleteById(String id);

    /**
     * 根据条件查询字典列表
     *
     * @param queryReq 查询条件
     * @return 字典列表
     */
    List<DictAggregate> queryByCondition(DictQueryReq queryReq);

    /**
     * 根据条件分页查询字典
     *
     * @param pageQueryReq 分页查询条件
     * @return 字典列表
     */
    List<DictAggregate> pageQueryByCondition(DictPageQueryReq pageQueryReq);
}
