package com.xiaoo.kaleido.ai.domain.clothing.convertor;

import com.xiaoo.kaleido.ai.domain.clothing.model.ClothingVector;
import com.xiaoo.kaleido.ai.trigger.dto.ClothingDocumentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服装向量领域转换器
 * <p>
 * 负责DTO和领域模型之间的转换
 * 这是领域层的转换器，处理业务逻辑相关的转换
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Component
public class ClothingVectorConvertor {

    /**
     * 将 ClothingDocumentDto 转换为 ClothingVector 领域模型
     *
     * @param dto DTO对象
     * @return 领域模型
     */
    public ClothingVector toDomain(ClothingDocumentDto dto) {
        if (dto == null) {
            return null;
        }

        return ClothingVector.builder()
                .clothingId(dto.getClothingId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .typeName(dto.getTypeName())
                .colorName(dto.getColorName())
                .seasonName(dto.getSeasonName())
                .brandName(dto.getBrandName())
                .size(dto.getSize())
                .purchaseDate(dto.getPurchaseDate())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .currentLocationName(dto.getCurrentLocationName())
                .wearCount(dto.getWearCount())
                .images(dto.getImages())
                .build();
    }

    /**
     * 将 ClothingDocumentDto 列表转换为 ClothingVector 领域模型列表
     *
     * @param dtos DTO对象列表
     * @return 领域模型列表
     */
    public List<ClothingVector> toDomainList(List<ClothingDocumentDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 将 ClothingVector 领域模型转换为 ClothingDocumentDto
     *
     * @param clothingVector 领域模型
     * @return DTO对象
     */
    public ClothingDocumentDto toDto(ClothingVector clothingVector) {
        if (clothingVector == null) {
            return null;
        }

        ClothingDocumentDto dto = new ClothingDocumentDto();
        dto.setClothingId(clothingVector.getClothingId());
        dto.setUserId(clothingVector.getUserId());
        dto.setName(clothingVector.getName());
        dto.setTypeName(clothingVector.getTypeName());
        dto.setColorName(clothingVector.getColorName());
        dto.setSeasonName(clothingVector.getSeasonName());
        dto.setBrandName(clothingVector.getBrandName());
        dto.setSize(clothingVector.getSize());
        dto.setPurchaseDate(clothingVector.getPurchaseDate());
        dto.setPrice(clothingVector.getPrice());
        dto.setDescription(clothingVector.getDescription());
        dto.setCurrentLocationName(clothingVector.getCurrentLocationName());
        dto.setWearCount(clothingVector.getWearCount());
        dto.setImages(clothingVector.getImages());

        return dto;
    }

    /**
     * 将 ClothingVector 领域模型列表转换为 ClothingDocumentDto 列表
     *
     * @param clothingVectors 领域模型列表
     * @return DTO对象列表
     */
    public List<ClothingDocumentDto> toDtoList(List<ClothingVector> clothingVectors) {
        if (clothingVectors == null) {
            return null;
        }

        return clothingVectors.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
