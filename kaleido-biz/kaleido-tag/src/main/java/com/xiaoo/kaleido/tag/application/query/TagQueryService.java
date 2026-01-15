package com.xiaoo.kaleido.tag.application.query;

import com.xiaoo.kaleido.api.tag.response.TagInfoResponse;

import java.util.List;

/**
 * 标签查询服务接口
 * <p>
 * 标签应用层查询服务，负责标签相关的读操作，包括标签信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface TagQueryService {

    /**
     * 根据ID查询标签信息
     * <p>
     * 根据标签ID查询标签详细信息，如果标签不存在则返回null
     *
     * @param tagId 标签ID，不能为空
     * @return 标签信息响应，如果标签不存在则返回null
     */
    TagInfoResponse findById(String tagId);

    /**
     * 根据用户ID和类型编码查询标签列表
     * <p>
     * 根据用户ID和标签类型编码查询标签列表，返回该用户下该类型的所有标签
     *
     * @param userId 用户ID，不能为空
     * @param typeCode 标签类型编码，不能为空
     * @return 标签信息响应列表，如果不存在则返回空列表
     */
    List<TagInfoResponse> findByUserIdAndTypeCode(String userId, String typeCode);

    /**
     * 根据标签ID查询关联的实体列表
     * <p>
     * 根据标签ID查询该标签关联的所有实体ID列表
     *
     * @param tagId 标签ID，不能为空
     * @return 实体ID列表，如果不存在关联则返回空列表
     */
    List<String> findEntityIdsByTagId(String tagId);
}
