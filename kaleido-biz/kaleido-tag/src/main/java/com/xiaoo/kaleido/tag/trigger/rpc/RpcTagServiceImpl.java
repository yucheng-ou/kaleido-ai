package com.xiaoo.kaleido.tag.trigger.rpc;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.tag.IRpcTagService;
import com.xiaoo.kaleido.api.tag.command.AssociateTagsCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateTagsCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.tag.application.command.TagCommandService;
import com.xiaoo.kaleido.tag.application.query.TagQueryService;
import com.xiaoo.kaleido.tag.types.exception.TagException;
import com.xiaoo.kaleido.tag.types.exception.TagErrorCode;
import jakarta.validation.Valid;
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
    public Result<Integer> associateTags(@Valid AssociateTagsCommand command) {
        try {
            int successCount = tagCommandService.associateTags(command);
            log.debug("RPC关联标签成功，标签数: {}, 成功数: {}, 实体ID: {}",
                    command.getTagIds().size(), successCount, command.getEntityId());
            return Result.success(successCount);
        } catch (Exception e) {
            log.error("RPC关联标签失败，标签数: {}, 实体ID: {}, 原因: {}",
                    command.getTagIds().size(), command.getEntityId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Result<Integer> dissociateTags(@Valid DissociateTagsCommand command) {
        try {
            int successCount = tagCommandService.dissociateTags(command);
            log.debug("RPC取消关联标签成功，标签数: {}, 成功数: {}, 实体ID: {}",
                    command.getTagIds().size(), successCount, command.getEntityId());
            return Result.success(successCount);
        } catch (Exception e) {
            log.error("RPC取消关联标签失败，标签数: {}, 实体ID: {}, 原因: {}",
                    command.getTagIds().size(), command.getEntityId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Result<List<String>> getEntityIdsByTagId(String tagId) {
        // 参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }

        try {
            List<String> entityIds = tagQueryService.findEntityIdsByTagId(tagId);
            log.debug("RPC查询标签关联实体成功，标签ID: {}, 实体数量: {}", tagId, entityIds.size());
            return Result.success(entityIds);
        } catch (Exception e) {
            log.error("RPC查询标签关联实体失败，标签ID: {}, 原因: {}", tagId, e.getMessage(), e);
            throw e;
        }
    }
}
