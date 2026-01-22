package com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate;

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
     * 创建新品牌聚合根

     * 用于创建新品牌时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param name        品牌名称，不能为空
     * @param logoPath    品牌Logo路径（在minio中的文件路径），可为空
     * @param description 品牌描述，可为空
     * @return 品牌聚合根
     */
    public static BrandAggregate create(
            String name,
            String logoPath,
            String description) {
        return BrandAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name)
                .logoPath(logoPath)
                .description(description)
                .build();
    }

    /**
     * 更新品牌信息

     * 更新品牌的Logo和描述信息
     * 注意：状态校验在Service层完成，这里只负责更新信息
     *
     * @param logoPath    新品牌Logo路径（在minio中的文件路径），可为空
     * @param description 新品牌描述，可为空
     */
    public void updateInfo(String logoPath, String description) {
        this.logoPath = logoPath;
        this.description = description;
    }
}
