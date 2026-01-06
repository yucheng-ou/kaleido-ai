package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.api.admin.dict.query.DictQueryReq;
import com.xiaoo.kaleido.api.admin.dict.query.DictPageQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Mapper
public interface DictDao extends BaseMapper<DictPO> {

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典持久化对象
     */
    DictPO findByTypeCodeAndDictCode(@Param("typeCode") String typeCode, @Param("dictCode") String dictCode);

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典持久化对象列表
     */
    List<DictPO> findByTypeCode(@Param("typeCode") String typeCode);

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @param status 状态
     * @return 启用的字典持久化对象列表
     */
    List<DictPO> findEnabledByTypeCode(@Param("typeCode") String typeCode, @Param("status") String status);

    /**
     * 检查字典类型编码和字典编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 是否存在
     */
    boolean existsByTypeCodeAndDictCode(@Param("typeCode") String typeCode, @Param("dictCode") String dictCode);

    /**
     * 检查字典类型编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @return 是否存在
     */
    boolean existsByTypeCode(@Param("typeCode") String typeCode);

    /**
     * 根据条件查询字典列表
     *
     * @param queryReq 查询条件
     * @return 字典持久化对象列表
     */
    List<DictPO> selectByCondition(@Param("queryReq") DictQueryReq queryReq);

    /**
     * 根据条件分页查询字典
     *
     * @param pageQueryReq 分页查询条件
     * @return 字典列表
     */
    List<DictPO> selectByPageCondition(@Param("pageQueryReq") DictPageQueryReq pageQueryReq);
}
