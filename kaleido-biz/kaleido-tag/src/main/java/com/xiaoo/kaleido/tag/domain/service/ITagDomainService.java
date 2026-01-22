package com.xiaoo.kaleido.tag.domain.service;

import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;

import java.util.List;

/**
 * 标签领域服务接口
 * <p>
 * 处理跨实体的标签业务逻辑，包括标签创建、状态管理、关联管理等核心领域操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface ITagDomainService {

    /**
     * 创建标签

     * 根据用户ID、标签名称、类型编码等创建新标签，系统会自动生成标签ID并设置初始状态
     *
     * @param userId      用户ID，不能为空
     * @param name        标签名称，不能为空
     * @param typeCode    标签类型编码，不能为空（关联字典表t_dict.dict_code，字典类型为TAG_TYPE）
     * @param color       标签颜色，可为空
     * @param description 标签描述，可为空
     * @return 标签聚合根，包含完整的标签信息
     */
    TagAggregate createTag(String userId, String name, String typeCode, String color, String description);

    /**
     * 根据ID查找标签，如果不存在则抛出异常

     * 用于命令操作中需要确保标签存在的场景
     *
     * @param tagId 标签ID字符串，不能为空
     * @return 标签聚合根，包含完整的标签信息
     */
    TagAggregate findByIdOrThrow(String tagId);

    /**
     * 更新标签信息

     * 更新标签的名称、颜色和描述信息
     *
     * @param userId      用户ID，不能为空
     * @param tagId       标签ID，不能为空
     * @param name        新标签名称，不能为空
     * @param color       新标签颜色，可为空
     * @param description 新标签描述，可为空
     * @return 更新后的标签聚合根
     */
    TagAggregate updateTag(String userId, String tagId, String name, String color, String description);


    /**
     * 关联实体到标签

     * 将指定实体关联到标签，会自动验证标签类型与实体类型的匹配
     *
     * @param tagId          标签ID，不能为空
     * @param entityId       实体ID，不能为空
     * @param userId         用户ID，不能为空
     * @param entityTypeCode 实体类型编码，用于验证标签类型匹配
     * @return 关联后的标签聚合根
     */
    TagAggregate associateEntity(String tagId, String entityId, String userId, String entityTypeCode);

    /**
     * 取消标签与实体的关联

     * 取消标签与指定实体的关联关系
     *
     * @param tagId    标签ID，不能为空
     * @param entityId 实体ID，不能为空
     * @param userId   用户ID，不能为空
     * @return 取消关联后的标签聚合根
     */
    TagAggregate dissociateEntity(String tagId, String entityId, String userId);

    /**
     * 验证标签名称在用户下的唯一性

     * 验证同类型标签名称在用户下是否唯一
     *
     * @param userId   用户ID，不能为空
     * @param name     标签名称，不能为空
     * @param typeCode 标签类型编码，不能为空
     * @return 如果标签名称在用户下唯一返回true，否则返回false
     */
    boolean isTagNameUnique(String userId, String name, String typeCode);
}
