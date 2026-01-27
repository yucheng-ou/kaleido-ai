package com.xiaoo.kaleido.wardrobe.trigger.rpc;

import com.xiaoo.kaleido.api.wardrobe.IRpcOutfitService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.application.command.OutfitCommandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 穿搭RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcOutfitServiceImpl implements IRpcOutfitService {

    private final OutfitCommandService outfitCommandService;

    @Override
    public Result<String> createOutfitWithClothingsAndImages(
            @NotBlank String userId,
            @Valid CreateOutfitWithClothingsCommand command
    ) {
        log.info("RPC创建穿搭，用户ID: {}, 穿搭名称: {}, 服装数量: {}, 图片数量: {}",
                userId, command.getName(),
                command.getClothingIds().size(), command.getImages().size());

        String outfitId = outfitCommandService.createOutfitWithClothingsAndImages(userId, command);
        return Result.success(outfitId);
    }
}
