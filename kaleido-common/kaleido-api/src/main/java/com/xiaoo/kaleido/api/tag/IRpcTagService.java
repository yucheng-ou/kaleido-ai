package com.xiaoo.kaleido.api.tag;

import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 标签RPC服务接口
 *
 * @author ouyucheng
 * @date 2026/1/15
 * @dubbo
 */
public interface IRpcTagService {

    /**
     * 关联标签
     * <p>
     * 将指定的标签关联到实体，支持批量关联多个标签
     *
     * @param userId  用户id
     * @param command 关联标签命令，包含标签ID列表、实体ID、用户ID和实体类型编码
     */
    Result<Void> associateTags(@NotBlank String userId, @Valid AssociateEntityCommand command);

    /**
     * 取消关联标签
     * <p>
     * 取消标签与实体的关联关系，支持批量取消关联多个标签
     *
     * @param userId  用户id
     * @param command 取消关联标签命令，包含标签ID列表、实体ID和用户ID
     */
    Result<Void> dissociateTags(@NotBlank String userId, @Valid DissociateEntityCommand command);

    /**
     * 查询标签关联的实体列表
     * <p>
     * 根据标签ID查询该标签关联的所有实体ID列表
     *
     * @param tagId 标签ID，不能为空
     * @return 实体ID列表
     */
    Result<List<String>> getEntityIdsByTagId(@NotBlank String tagId);
}
