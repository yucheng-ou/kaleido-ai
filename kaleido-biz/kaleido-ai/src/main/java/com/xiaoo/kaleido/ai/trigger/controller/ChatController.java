package com.xiaoo.kaleido.ai.trigger.controller;

import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;
import org.springframework.web.bind.annotation.*;
import com.xiaoo.kaleido.ai.trigger.dto.ClothingDocumentDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final IChatService chatService;
    private final MilvusVectorStore vectorStore;



    @PostMapping(value = "/save")
    public void save(@RequestBody List<ClothingDocumentDto> clothingList) {
        log.info("开始保存 {} 件衣服信息到Milvus", clothingList.size());

        List<Document> documents = clothingList.stream()
                .map(this::convertToDocument)
                .toList();

        vectorStore.add(documents);

        log.info("成功保存 {} 件衣服信息到Milvus", documents.size());
    }

    /**
     * 将 ClothingDocumentDto 转换为 Spring AI Document
     */
    private Document convertToDocument(ClothingDocumentDto dto) {
        // 创建描述性文本内容
        String content = String.format(
                "这是一件%s，类型为%s，颜色为%s，适合%s季节。尺码为%s，品牌为%s。%s",
                dto.getName(),
                dto.getTypeCode(),
                dto.getColorCode(),
                dto.getSeasonCode(),
                dto.getSize(),
                dto.getBrandName() != null ? dto.getBrandName() : "未知品牌",
                dto.getDescription() != null ? "描述：" + dto.getDescription() : ""
        );

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("clothingId", dto.getClothingId());
        metadata.put("userId", dto.getUserId());
        metadata.put("name", dto.getName());
        metadata.put("typeCode", dto.getTypeCode());
        metadata.put("colorCode", dto.getColorCode());
        metadata.put("seasonCode", dto.getSeasonCode());
        metadata.put("brandName", dto.getBrandName());
        metadata.put("size", dto.getSize());
        metadata.put("price", dto.getPrice());
        metadata.put("description", dto.getDescription() != null ? dto.getDescription() : "");

        return new Document(content, metadata);
    }
}
