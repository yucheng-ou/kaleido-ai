package com.xiaoo.kaleido.ai.domain.clothing.service.impl;

import com.xiaoo.kaleido.ai.domain.clothing.adapter.repository.IClothingVectorRepository;
import com.xiaoo.kaleido.ai.domain.clothing.convertor.ClothingVectorConvertor;
import com.xiaoo.kaleido.ai.domain.clothing.model.ClothingVector;
import com.xiaoo.kaleido.ai.trigger.convertor.ClothingEventConvertor;
import com.xiaoo.kaleido.ai.trigger.dto.ClothingDocumentDto;
import com.xiaoo.kaleido.api.wardrobe.event.ClothingEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服装向量服务
 * 处理服装向量相关操作，更新向量存储
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingVectorService {

    private final IClothingVectorRepository clothingVectorRepository;
    private final ClothingEventConvertor clothingEventConvertor;
    private final ClothingVectorConvertor clothingVectorConvertor;

    /**
     * 处理服装创建事件
     *
     * @param clothingEventMessage 服装事件消息接口
     */
    @Transactional
    public void handleCreateEvent(ClothingEventMessage clothingEventMessage) {
        try {
            log.info("处理服装创建事件，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId());
            
            // 1. 检查是否已存在（幂等性处理）
            boolean exists = clothingVectorRepository.existsByClothingId(clothingEventMessage.getClothingId());
            if (exists) {
                log.warn("服装向量已存在，跳过创建，服装ID: {}", clothingEventMessage.getClothingId());
                return;
            }
            
            // 2. 转换事件消息为DTO
            ClothingDocumentDto dto = clothingEventConvertor.toDto(clothingEventMessage);
            
            // 3. 转换为领域模型并保存到向量存储
            List<ClothingVector> clothingVectors =
                    clothingVectorConvertor.toDomainList(List.of(dto));
            clothingVectorRepository.save(clothingVectors);
            
            log.info("服装创建事件处理完成，成功保存到向量存储，服装ID: {}", clothingEventMessage.getClothingId());
        } catch (Exception e) {
            log.error("处理服装创建事件失败，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId(), e);
            throw e;
        }
    }

    /**
     * 处理服装更新事件
     *
     * @param clothingEventMessage 服装事件消息接口
     */
    @Transactional
    public void handleUpdateEvent(ClothingEventMessage clothingEventMessage) {
        try {
            log.info("处理服装更新事件，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId());
            
            // 1. 先删除旧的向量
            clothingVectorRepository.deleteByClothingId(clothingEventMessage.getClothingId());
            log.info("已删除旧的服装向量，服装ID: {}", clothingEventMessage.getClothingId());
            
            // 2. 转换事件消息为DTO
            ClothingDocumentDto dto = clothingEventConvertor.toDto(clothingEventMessage);
            
            // 3. 转换为领域模型并保存到向量存储
            List<ClothingVector> clothingVectors =
                    clothingVectorConvertor.toDomainList(List.of(dto));
            clothingVectorRepository.save(clothingVectors);
            
            log.info("服装更新事件处理完成，成功更新向量存储，服装ID: {}", clothingEventMessage.getClothingId());
        } catch (Exception e) {
            log.error("处理服装更新事件失败，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId(), e);
            throw e;
        }
    }

    /**
     * 处理服装删除事件
     *
     * @param clothingEventMessage 服装事件消息接口
     */
    @Transactional
    public void handleDeleteEvent(ClothingEventMessage clothingEventMessage) {
        try {
            log.info("处理服装删除事件，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId());
            
            // 1. 检查向量是否存在
            boolean exists = clothingVectorRepository.existsByClothingId(clothingEventMessage.getClothingId());
            if (!exists) {
                log.warn("服装向量不存在，跳过删除，服装ID: {}", clothingEventMessage.getClothingId());
                return;
            }
            
            // 2. 从向量存储中删除向量
            clothingVectorRepository.deleteByClothingId(clothingEventMessage.getClothingId());
            
            log.info("服装删除事件处理完成，成功从向量存储中删除，服装ID: {}", clothingEventMessage.getClothingId());
        } catch (Exception e) {
            log.error("处理服装删除事件失败，服装ID: {}, 用户ID: {}", 
                    clothingEventMessage.getClothingId(), clothingEventMessage.getUserId(), e);
            throw e;
        }
    }
}
