package com.xiaoo.kaleido.wardrobe.application.query;

import com.xiaoo.kaleido.api.wardrobe.response.LocationInfoResponse;

import java.util.List;

/**
 * 位置查询服务接口
 * <p>
 * 位置应用层查询服务，负责位置相关的读操作，包括位置信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface ILocationQueryService {

    /**
     * 根据位置ID查询位置详情
     * <p>
     * 根据位置ID查询位置详细信息，包括位置基本信息和图片列表
     *
     * @param locationId 位置ID，不能为空
     * @return 位置信息响应，如果位置不存在则返回null
     */
    LocationInfoResponse findById(String locationId);

    /**
     * 根据用户ID查询位置列表
     * <p>
     * 查询指定用户的所有位置列表
     *
     * @param userId 用户ID，不能为空
     * @return 位置信息响应列表，如果不存在则返回空列表
     */
    List<LocationInfoResponse> findByUserId(String userId);

    /**
     * 根据位置ID列表查询位置列表
     * <p>
     * 根据位置ID列表查询对应的位置信息列表
     *
     * @param locationIds 位置ID列表，不能为空
     * @return 位置信息响应列表，如果不存在则返回空列表
     */
    List<LocationInfoResponse> findByIds(List<String> locationIds);

    /**
     * 检查位置是否存在
     * <p>
     * 根据位置ID检查位置是否存在
     *
     * @param locationId 位置ID，不能为空
     * @return 是否存在
     */
    boolean existsById(String locationId);
}
