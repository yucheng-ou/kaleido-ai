package com.xiaoo.kaleido.tag.trigger.rpc;

import com.xiaoo.kaleido.api.tag.IRpcTagService;
import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.tag.application.command.TagCommandService;
import com.xiaoo.kaleido.tag.application.query.TagQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 标签RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcTagServiceImpl implements IRpcTagService {

    private final TagCommandService tagCommandService;
    private final TagQueryService tagQueryService;

    @Override
    public Result<Void> associateTags(@NotBlank String userId, @Valid AssociateEntityCommand command) {
        tagCommandService.associateEntity(userId, command);
        return Result.success();
    }

    @Override
    public Result<Void> dissociateTags(@NotBlank String userId, @Valid DissociateEntityCommand command) {
        tagCommandService.dissociateEntity(userId, command);
        return Result.success();
    }

    @Override
    public Result<List<String>> getEntityIdsByTagId(@NotBlank String tagId) {
        List<String> entityIds = tagQueryService.findEntityIdsByTagId(tagId);
        return Result.success(entityIds);
    }
}
