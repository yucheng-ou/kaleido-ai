package com.xiaoo.kaleido.wardrobe.application.query;

import com.xiaoo.kaleido.api.wardrobe.response.OutfitInfoResponse;
import com.xiaoo.kaleido.api.wardrobe.response.WearRecordResponse;

import java.util.List;

/**
 * 穿搭查询服务接口
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface IOutfitQueryService {

    /**
     * 根据用户ID查询穿搭列表
     * <p>
     * 根据用户ID查询该用户的所有穿搭列表
     *
     * @param userId 用户ID，不能为空
     * @return 穿搭信息响应列表，如果不存在则返回空列表
     */
    List<OutfitInfoResponse> findByUserId(String userId);

    /**
     * 根据ID查询穿搭信息
     * <p>
     * 根据穿搭ID查询穿搭详细信息，包括服装列表、图片列表和穿着记录
     *
     * @param outfitId 穿搭ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 穿搭信息响应，如果穿搭不存在或用户不匹配则返回null
     */
    OutfitInfoResponse findById(String outfitId, String userId);

    /**
     * 根据穿搭ID查询穿着记录
     *
     * @param outfitId 穿搭ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 穿着记录响应列表，如果不存在或用户不匹配则返回空列表
     */
    List<WearRecordResponse> findWearRecordsByOutfitId(String outfitId, String userId);
}
