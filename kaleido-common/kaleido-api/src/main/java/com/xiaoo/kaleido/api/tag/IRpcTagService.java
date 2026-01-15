package com.xiaoo.kaleido.api.tag;

import com.xiaoo.kaleido.api.tag.command.AssociateTagsCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateTagsCommand;
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
     * @param command 关联标签命令，包含标签ID列表、实体ID、用户ID和实体类型编码
     * @return 成功关联的数量
     */
    Result<Integer> associateTags(@Valid AssociateTagsCommand command);

    /**
     * 取消关联标签
     * <p>
     * 取消标签与实体的关联关系，支持批量取消关联多个标签
     *
     * @param command 取消关联标签命令，包含标签ID列表、实体ID和用户ID
     * @return 成功取消关联的数量
     */
    Result<Integer> dissociateTags(@Valid DissociateTagsCommand command);

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
