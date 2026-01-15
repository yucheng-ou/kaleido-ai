package com.xiaoo.kaleido.tag.application.query.impl;

import com.xiaoo.kaleido.api.tag.response.TagInfoResponse;
import com.xiaoo.kaleido.tag.application.convertor.TagConvertor;
import com.xiaoo.kaleido.tag.application.query.TagQueryService;
import com.xiaoo.kaleido.tag.domain.adapter.repository.ITagRepository;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.types.exception.TagException;
import com.xiaoo.kaleido.tag.types.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签查询服务实现
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagQueryServiceImpl implements TagQueryService {

    private final ITagRepository tagRepository;
    private final TagConvertor tagConvertor;

    @Override
    public TagInfoResponse findById(String tagId) {
        // 1.查询标签
        return tagRepository.findById(tagId)
                .map(tagConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public List<TagInfoResponse> findByUserIdAndTypeCode(String userId, String typeCode) {
        try {
            // 1.查询标签列表
            List<TagAggregate> tagAggregates = tagRepository.findByUserIdAndTypeCode(userId, typeCode);
            
            // 2.转换为响应对象
            return tagAggregates.stream()
                    .map(tagConvertor::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户标签列表失败，用户ID: {}, 类型编码: {}, 原因: {}", userId, typeCode, e.getMessage(), e);
            throw TagException.of(TagErrorCode.TAG_QUERY_FAIL);
        }
    }

    @Override
    public List<String> findEntityIdsByTagId(String tagId) {
        try {
            // 1.查询标签
            TagAggregate tagAggregate = tagRepository.findByIdOrThrow(tagId);
            
            // 2.获取关联的实体ID列表
            return tagAggregate.getAllAssociatedEntityIds();
        } catch (Exception e) {
            log.error("查询标签关联实体列表失败，标签ID: {}, 原因: {}", tagId, e.getMessage(), e);
            throw TagException.of(TagErrorCode.TAG_QUERY_FAIL);
        }
    }
}
