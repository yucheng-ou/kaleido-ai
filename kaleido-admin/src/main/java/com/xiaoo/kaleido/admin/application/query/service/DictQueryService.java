package com.xiaoo.kaleido.admin.application.query.service;

import com.xiaoo.kaleido.admin.application.query.dto.DictDTO;
import com.xiaoo.kaleido.admin.application.query.request.DictQueryRequest;

import java.util.List;

/**
 * 字典查询服务接口
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
public interface DictQueryService {

    /**
     * 根据字典ID查询字典
     *
     * @param dictId 字典ID
     * @return 字典DTO
     */
    DictDTO findById(String dictId);

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典DTO
     */
    DictDTO findByTypeCodeAndDictCode(String typeCode, String dictCode);

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典DTO列表
     */
    List<DictDTO> findByTypeCode(String typeCode);

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典DTO列表
     */
    List<DictDTO> findEnabledByTypeCode(String typeCode);

    /**
     * 查询字典列表（根据查询条件）
     *
     * @param queryRequest 查询条件
     * @return 字典DTO列表
     */
    List<DictDTO> queryDicts(DictQueryRequest queryRequest);

    /**
     * 根据ID查询字典（别名方法，用于门面层）
     *
     * @param dictId 字典ID
     * @return 字典DTO
     */
    default DictDTO getDictById(String dictId) {
        return findById(dictId);
    }
}
