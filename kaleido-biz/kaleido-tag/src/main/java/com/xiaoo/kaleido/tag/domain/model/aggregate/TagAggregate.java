package com.xiaoo.kaleido.tag.domain.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.tag.domain.model.entity.TagRelation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签聚合根
 * <p>
 * 标签领域模型的核心聚合根，封装标签实体及其关联关系，确保业务规则的一致性
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TagAggregate extends BaseEntity {

    /**
     * 标签ID
     */
    private String id;

    /**
     * 用户ID
     * 标签所属的用户
     */
    private String userId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型编码
     * 关联字典表t_dict.dict_code，字典类型为TAG_TYPE
     */
    private String typeCode;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 使用次数
     */
    private Integer usageCount;

    /**
     * 关联关系列表（聚合根内的实体）
     * 记录标签与实体的所有关联关系
     */
    @Builder.Default
    private List<TagRelation> relations = new ArrayList<>();


    /**
     * 创建新标签聚合根

     * 用于创建新标签时构建聚合根
     *
     * @param userId      用户ID，不能为空
     * @param name        标签名称，不能为空
     * @param typeCode    标签类型编码，不能为空
     * @param color       标签颜色，可为空
     * @param description 标签描述，可为空
     * @return 标签聚合根
     */
    public static TagAggregate create(
            String userId,
            String name,
            String typeCode,
            String color,
            String description) {
        return TagAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .name(name)
                .typeCode(typeCode)
                .color(color)
                .description(description)
                .usageCount(0)
                .build();
    }

    /**
     * 更新标签信息

     * 更新标签的名称、颜色和描述
     *
     * @param name        新标签名称，不能为空
     * @param color       新标签颜色，可为空
     * @param description 新标签描述，可为空
     */
    public void updateInfo(String name, String color, String description) {
        this.name = name;
        this.color = color;
        this.description = description;
    }

    /**
     * 关联实体到标签

     * 将指定实体关联到当前标签，会自动更新使用次数
     *
     * @param entityId 实体ID，不能为空
     * @param userId   用户ID，不能为空
     */
    public void associateEntity(String entityId, String userId) {
        // 创建关联关系
        TagRelation relation = TagRelation.create(this.id, entityId, userId);
        this.relations.add(relation);

        // 更新使用次数和更新时间
        this.usageCount++;
    }


    /**
     * 验证标签是否可以关联到指定实体类型编码

     * 根据设计文档，标签类型编码必须与实体类型编码匹配
     *
     * @param entityTypeCode 实体类型编码
     * @return 如果可以关联返回true，否则返回false
     */
    public boolean canAssociateWith(String entityTypeCode) {
        return this.typeCode.equals(entityTypeCode);
    }

    /**
     * 验证实体是否已关联
     *
     * @param entityId 实体ID
     * @return 如果实体已关联返回true，否则返回false
     */
    public boolean isEntityAssociated(String entityId) {
        return this.relations.stream()
                .anyMatch(relation -> relation.isAssociatedWith(entityId));
    }

    /**
     * 获取关联的实体数量
     *
     * @return 关联的实体数量
     */
    public int getAssociatedEntityCount() {
        return this.relations.size();
    }

    /**
     * 获取所有关联的实体ID
     *
     * @return 所有关联的实体ID列表
     */
    public List<String> getAllAssociatedEntityIds() {
        return this.relations.stream()
                .map(TagRelation::getEntityId)
                .collect(Collectors.toList());
    }

    /**
     * 获取并清空关联关系列表（用于持久化后清理）
     *
     * @return 关联关系列表
     */
    public List<TagRelation> getAndClearRelations() {
        List<TagRelation> relationsCopy = new ArrayList<>(this.relations);
        this.relations.clear();
        return relationsCopy;
    }
}
