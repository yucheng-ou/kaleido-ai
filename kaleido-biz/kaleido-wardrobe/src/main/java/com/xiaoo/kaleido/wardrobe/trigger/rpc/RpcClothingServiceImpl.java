package com.xiaoo.kaleido.wardrobe.trigger.rpc;

import com.xiaoo.kaleido.api.wardrobe.IRpcClothingService;
import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.application.convertor.WardrobeConvertor;
import com.xiaoo.kaleido.wardrobe.application.query.IClothingQueryService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 服装RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcClothingServiceImpl implements IRpcClothingService {

    private final IClothingQueryService clothingQueryService;
    private final IClothingDomainService clothingDomainService;
    private final WardrobeConvertor wardrobeConvertor;

    @Override
    public Result<List<ClothingInfoResponse>> getClothingListByUserId(@NotBlank String userId) {
        log.info("RPC查询用户服装列表，用户ID: {}", userId);
        
        List<ClothingInfoResponse> clothingList = clothingQueryService.findByUserId(userId);
        return Result.success(clothingList);
    }

    @Override
    public Result<ClothingInfoResponse> getClothingById(@NotBlank String clothingId) {
        log.info("RPC查询服装详情，服装ID: {}", clothingId);
        
        // RPC服务是内部服务调用，不进行用户权限校验，直接查询服装信息
        var clothing = clothingDomainService.findByIdOrThrow(clothingId);
        ClothingInfoResponse clothingInfo = wardrobeConvertor.toClothingResponse(clothing);
        return Result.success(clothingInfo);
    }
}
