package com.xiaoo.kaleido.tag.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * 标签关联实体
 * <p>
 * 表示标签与实体之间的关联关系，包含关联的基本信息和业务规则
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TagRelation extends BaseEntity {

    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 实体ID
     * 关联的实体ID，如服装ID、搭配ID等
     */
    private String entityId;

    /**
     * 用户ID
     * 创建关联的用户ID
     */
    private String userId;


    /**
     * 创建标签关联

     * 根据标签ID、实体ID和用户ID创建新的标签关联
     *
     * @param tagId    标签ID，不能为空
     * @param entityId 实体ID，不能为空
     * @param userId   用户ID，不能为空
     * @return 标签关联实体
     */
    public static TagRelation create(String tagId, String entityId, String userId) {

        return TagRelation.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .tagId(tagId)
                .entityId(entityId)
                .userId(userId)
                .build();
    }


    /**
     * 判断是否关联到指定实体
     *
     * @param entityId 实体ID
     * @return 如果关联到指定实体则返回true，否则返回false
     */
    public boolean isAssociatedWith(String entityId) {
        return this.entityId.equals(entityId);
    }

    /**
     * 判断是否属于指定用户
     *
     * @param userId 用户ID
     * @return 如果属于指定用户则返回true，否则返回false
     */
    public boolean belongsToUser(String userId) {
        return this.userId.equals(userId);
    }

    /**
     * 判断是否关联到指定标签
     *
     * @param tagId 标签ID
     * @return 如果关联到指定标签则返回true，否则返回false
     */
    public boolean isAssociatedWithTag(String tagId) {
        return this.tagId.equals(tagId);
    }
}
