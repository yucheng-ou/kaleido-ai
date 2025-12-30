package com.xiaoo.kaleido.admin.application.query;

import com.xiaoo.kaleido.api.admin.query.DictPageQueryReq;
import com.xiaoo.kaleido.api.admin.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.response.DictResponse;
import com.xiaoo.kaleido.base.response.PageResp;

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
     * @return 字典响应对象
     */
    DictResponse findById(String dictId);

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典响应对象
     */
    DictResponse findByTypeCodeAndDictCode(String typeCode, String dictCode);

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典响应对象列表
     */
    List<DictResponse> findByTypeCode(String typeCode);

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典响应对象列表
     */
    List<DictResponse> findEnabledByTypeCode(String typeCode);

    /**
     * 查询字典列表（根据查询条件）
     *
     * @param queryReq 查询条件
     * @return 字典响应对象列表
     */
    List<DictResponse> queryDicts(DictQueryReq queryReq);

    /**
     * 分页查询字典列表
     *
     * @param pageQueryReq 分页查询条件
     * @return 分页结果
     */
    PageResp<DictResponse> pageQueryDicts(DictPageQueryReq pageQueryReq);

    /**
     * 根据ID查询字典（别名方法，用于门面层）
     *
     * @param dictId 字典ID
     * @return 字典响应对象
     */
    default DictResponse getDictById(String dictId) {
        return findById(dictId);
    }
}
