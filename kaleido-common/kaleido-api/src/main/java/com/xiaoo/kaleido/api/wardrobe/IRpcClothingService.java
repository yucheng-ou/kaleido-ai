package com.xiaoo.kaleido.api.wardrobe;

import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 服装RPC服务接口
 *
 * @author ouyucheng
 * @date 2026/1/16
 * @dubbo
 */
public interface IRpcClothingService {

    /**
     * 根据用户ID查询服装列表
     * <p>
     * 根据用户ID查询该用户的所有服装列表
     *
     * @param userId 用户ID，不能为空
     * @return 服装信息响应列表
     */
    Result<List<ClothingInfoResponse>> getClothingListByUserId(@NotBlank String userId);

    /**
     * 根据ID查询服装详情
     * <p>
     * 根据服装ID查询服装详细信息
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装信息响应
     */
    Result<ClothingInfoResponse> getClothingById(@NotBlank String clothingId);
}
