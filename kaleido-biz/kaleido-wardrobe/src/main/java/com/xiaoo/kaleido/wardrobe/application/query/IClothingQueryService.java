package com.xiaoo.kaleido.wardrobe.application.query;

import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;

import java.util.List;

/**
 * 服装查询服务接口
 * <p>
 * 服装应用层查询服务，负责服装相关的读操作，包括服装信息查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
public interface IClothingQueryService {

    /**
     * 根据用户ID查询服装列表

     * 根据用户ID查询该用户的所有服装列表
     *
     * @param userId 用户ID，不能为空
     * @return 服装信息响应列表，如果不存在则返回空列表
     */
    List<ClothingInfoResponse> findByUserId(String userId);

    /**
     * 根据ID查询服装信息

     * 根据服装ID查询服装详细信息，如果服装不存在或用户不匹配则返回null
     *
     * @param clothingId 服装ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 服装信息响应，如果服装不存在或用户不匹配则返回null
     */
    ClothingInfoResponse findById(String clothingId, String userId);
}
