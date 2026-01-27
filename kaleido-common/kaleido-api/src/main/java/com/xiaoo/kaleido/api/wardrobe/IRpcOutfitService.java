package com.xiaoo.kaleido.api.wardrobe;

import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 穿搭RPC服务接口
 *
 * @author ouyucheng
 * @date 2026/1/27
 * @dubbo
 */
public interface IRpcOutfitService {

    /**
     * 创建穿搭（包含服装和图片）
     * <p>
     * 为用户创建新的穿搭，包含服装列表和图片信息
     *
     * @param userId  用户ID，不能为空
     * @param command 创建穿搭命令，包含穿搭名称、描述、服装ID列表和图片信息
     * @return 创建的穿搭ID
     */
    Result<String> createOutfitWithClothingsAndImages(
            @NotBlank String userId,
            @Valid CreateOutfitWithClothingsCommand command
    );
}
