package com.xiaoo.kaleido.admin.dict.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.admin.dict.infrastructure.dao.po.DictPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Mapper
public interface DictMapper extends BaseMapper<DictPO> {

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典持久化对象
     */
    @Select("SELECT * FROM t_dict WHERE type_code = #{typeCode} AND dict_code = #{dictCode} AND deleted = 0")
    DictPO findByTypeCodeAndDictCode(@Param("typeCode") String typeCode, @Param("dictCode") String dictCode);

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param typeCode 字典类型编码
     * @return 字典持久化对象列表
     */
    @Select("SELECT * FROM t_dict WHERE type_code = #{typeCode} AND deleted = 0 ORDER BY sort ASC")
    List<DictPO> findByTypeCode(@Param("typeCode") String typeCode);

    /**
     * 根据字典类型编码查询启用的字典列表
     *
     * @param typeCode 字典类型编码
     * @param status 状态
     * @return 启用的字典持久化对象列表
     */
    @Select("SELECT * FROM t_dict WHERE type_code = #{typeCode} AND status = #{status} AND deleted = 0 ORDER BY sort ASC")
    List<DictPO> findEnabledByTypeCode(@Param("typeCode") String typeCode, @Param("status") String status);

    /**
     * 检查字典类型编码和字典编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) > 0 FROM t_dict WHERE type_code = #{typeCode} AND dict_code = #{dictCode} AND deleted = 0")
    boolean existsByTypeCodeAndDictCode(@Param("typeCode") String typeCode, @Param("dictCode") String dictCode);

    /**
     * 检查字典类型编码是否已存在
     *
     * @param typeCode 字典类型编码
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) > 0 FROM t_dict WHERE type_code = #{typeCode} AND deleted = 0")
    boolean existsByTypeCode(@Param("typeCode") String typeCode);
}
