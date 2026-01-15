package com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 品牌聚合根
 * <p>
 * 品牌领域模型的核心聚合根，封装品牌实体及其业务规则，确保业务完整性
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BrandAggregate extends BaseEntity {

    /**
     * 品牌名称（唯一）
     */
    private String name;

    /**
     * 品牌Logo路径（在minio中的文件路径）
     */
    private String logoPath;

    /**
     * 品牌描述
     */
    private String description;

    /**
     * 国家/地区
     */
    private String country;

    /**
     * 品牌状态
     */
    private DataStatusEnum status;

    /**
     * 创建新品牌聚合根
     * <p>
     * 用于创建新品牌时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param name        品牌名称，不能为空
     * @param logoPath    品牌Logo路径（在minio中的文件路径），可为空
     * @param description 品牌描述，可为空
     * @param country     国家/地区，可为空
     * @return 品牌聚合根
     */
    public static BrandAggregate create(
            String name,
            String logoPath,
            String description,
            String country) {
        return BrandAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name)
                .logoPath(logoPath)
                .description(description)
                .country(country)
                .status(DataStatusEnum.ENABLE)
                .build();
    }

    /**
     * 更新品牌信息
     * <p>
     * 更新品牌的Logo、描述和国家信息
     * 注意：状态校验在Service层完成，这里只负责更新信息
     *
     * @param logoPath    新品牌Logo路径（在minio中的文件路径），可为空
     * @param description 新品牌描述，可为空
     * @param country     新国家/地区，可为空
     */
    public void updateInfo(String logoPath, String description, String country) {
        this.logoPath = logoPath;
        this.description = description;
        this.country = country;
    }

    /**
     * 启用品牌
     * <p>
     * 将品牌状态设置为启用
     * 注意：状态校验在Service层完成
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用品牌
     * <p>
     * 将品牌状态设置为禁用
     * 注意：状态校验在Service层完成
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 验证品牌是否可以操作
     * <p>
     * 检查品牌是否处于启用状态
     *
     * @return 如果品牌处于启用状态返回true，否则返回false
     */
    public boolean isEnabled() {
        return this.status == DataStatusEnum.ENABLE;
    }

    /**
     * 验证品牌名称是否匹配
     *
     * @param name 品牌名称
     * @return 如果名称匹配返回true，否则返回false
     */
    public boolean isNameMatch(String name) {
        return this.name.equals(name);
    }
}
