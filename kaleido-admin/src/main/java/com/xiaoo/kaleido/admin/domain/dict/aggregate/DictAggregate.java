package com.xiaoo.kaleido.admin.domain.dict.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 字典聚合根（同时作为实体）
 * 封装字典相关的业务规则和一致性边界
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DictAggregate extends BaseEntity {

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private DataStatusEnum status;

    /**
     * 创建字典
     *
     * @param typeCode  字典类型编码
     * @param typeName  字典类型名称
     * @param dictCode  字典编码
     * @param dictName  字典名称
     * @param dictValue 字典值
     * @param sort      排序
     * @return 字典对象
     */
    public static DictAggregate create(String typeCode, String typeName, String dictCode,
                                       String dictName, String dictValue, Integer sort) {
        return DictAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .typeCode(typeCode)
                .typeName(typeName)
                .dictCode(dictCode)
                .dictName(dictName)
                .dictValue(dictValue)
                .sort(sort != null ? sort : 0)
                .status(DataStatusEnum.ENABLE)
                .build();
    }

    /**
     * 启用字典
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用字典
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 更新字典信息
     *
     * @param dictName  字典名称
     * @param dictValue 字典值
     * @param sort      排序
     */
    public void updateInfo(String typeName, String dictName, String dictValue, Integer sort) {
        this.typeName = typeName;
        this.dictName = dictName;
        this.dictValue = dictValue;
        if (sort != null) {
            this.sort = sort;
        }
    }

    /**
     * 判断字典是否启用
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
        return DataStatusEnum.ENABLE.equals(this.status);
    }

}
