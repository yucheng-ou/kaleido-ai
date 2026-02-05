package com.xiaoo.kaleido.ai.infrastructure.adapter.repository;

import com.xiaoo.kaleido.ai.domain.clothing.adapter.repository.IClothingVectorRepository;
import com.xiaoo.kaleido.ai.domain.clothing.model.ClothingVector;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服装向量仓储实现（基础设施层）
 * <p>
 * 负责服装向量的向量存储操作，使用Milvus作为向量存储引擎
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ClothingDocumentRepositoryImpl implements IClothingVectorRepository {

    private final MilvusVectorStore vectorStore;

    @Override
    public void save(List<ClothingVector> clothingVectors) {
        try {
            log.info("开始保存 {} 件衣服信息到Milvus", clothingVectors.size());

            // 1. 转换领域实体为Spring AI Document
            List<Document> aiDocuments = clothingVectors.stream()
                    .map(this::convertToDocument)
                    .collect(Collectors.toList());

            // 2. 保存到向量存储
            vectorStore.add(aiDocuments);

            log.info("成功保存 {} 件衣服信息到Milvus", aiDocuments.size());
        } catch (Exception e) {
            log.error("保存服装文档到向量存储失败，原因: {}", e.getMessage(), e);
            throw AiException.of(AiErrorCode.VECTOR_STORE_SAVE_FAIL);
        }
    }

    @Override
    public List<ClothingVector> searchBySimilarity(String queryText, int topK) {
        try {
            log.info("开始根据相似度搜索服装文档，查询文本: {}, topK: {}", queryText, topK);
            
            // 1. 构建搜索请求
            SearchRequest searchRequest = SearchRequest.builder()
                    .query(queryText)
                    .topK(topK)
                    .build();
            
            // 2. 执行相似度搜索
            List<Document> aiDocuments = vectorStore.similaritySearch(searchRequest);
            
            // 3. 转换回领域实体
            List<ClothingVector> clothingVectors = aiDocuments.stream()
                    .map(this::convertToClothingVector)
                    .collect(Collectors.toList());
            
            log.info("相似度搜索完成，找到 {} 个相关文档", clothingVectors.size());
            return clothingVectors;
        } catch (Exception e) {
            log.error("相似度搜索失败，查询文本: {}, topK: {}", queryText, topK, e);
            throw AiException.of(AiErrorCode.VECTOR_STORE_SEARCH_FAIL);
        }
    }

    @Override
    public void deleteByClothingId(String clothingId) {
        try {
            log.info("开始根据服装ID删除文档，服装ID: {}", clothingId);
            
            // 构建过滤条件：根据 clothingId 元数据删除
            // 先搜索获取所有匹配的文档ID
            SearchRequest searchRequest = SearchRequest.builder()
                    .query("")
                    .topK(1)
                    .filterExpression("clothingId == '" + clothingId + "'")
                    .build();
            
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            
            // 获取文档ID并删除
            List<String> documentIds = documents.stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            
            if (!documentIds.isEmpty()) {
                vectorStore.delete(documentIds);
            }
            
            log.info("成功根据服装ID删除文档，服装ID: {}, 删除文档数: {}", clothingId, documentIds.size());
        } catch (Exception e) {
            log.error("根据服装ID删除文档失败，服装ID: {}", clothingId, e);
            throw AiException.of(AiErrorCode.VECTOR_STORE_DELETE_FAIL);
        }
    }

    @Override
    public void deleteByUserId(String userId) {
        try {
            log.info("开始根据用户ID删除文档，用户ID: {}", userId);
            
            // 构建过滤条件：根据 userId 元数据删除
            // 先搜索获取所有匹配的文档ID
            SearchRequest searchRequest = SearchRequest.builder()
                    .query("")
                    .topK(10000) // 假设最多10000个文档
                    .filterExpression("userId == '" + userId + "'")
                    .build();
            
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            
            // 获取文档ID并删除
            List<String> documentIds = documents.stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            
            if (!documentIds.isEmpty()) {
                vectorStore.delete(documentIds);
            }
            
            log.info("成功根据用户ID删除文档，用户ID: {}, 删除文档数: {}", userId, documentIds.size());
        } catch (Exception e) {
            log.error("根据用户ID删除文档失败，用户ID: {}", userId, e);
            throw AiException.of(AiErrorCode.VECTOR_STORE_DELETE_FAIL);
        }
    }

    @Override
    public boolean existsByClothingId(String clothingId) {
        try {
            log.debug("检查服装文档是否存在，服装ID: {}", clothingId);
            
            // 构建搜索请求
            SearchRequest searchRequest = SearchRequest.builder()
                    .query("")
                    .topK(1)
                    .filterExpression("clothingId == '" + clothingId + "'")
                    .build();
            
            // 搜索一个文档来判断是否存在
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            
            boolean exists = !documents.isEmpty();
            log.debug("服装文档存在检查完成，服装ID: {}, 存在: {}", clothingId, exists);
            return exists;
        } catch (Exception e) {
            log.error("检查服装文档是否存在失败，服装ID: {}", clothingId, e);
            throw AiException.of(AiErrorCode.VECTOR_STORE_SEARCH_FAIL);
        }
    }

    /**
     * 将 ClothingVector 领域实体转换为 Spring AI Document
     */
    private Document convertToDocument(ClothingVector clothingVector) {
        // 使用领域实体的方法生成内容
        String content = clothingVector.generateVectorContent();

        // 构建元数据
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("clothingId", clothingVector.getClothingId());
        metadata.put("userId", clothingVector.getUserId());
        metadata.put("name", clothingVector.getName());
        metadata.put("typeName", clothingVector.getTypeName() != null ? clothingVector.getTypeName() : "");
        metadata.put("colorName", clothingVector.getColorName() != null ? clothingVector.getColorName() : "");
        metadata.put("seasonName", clothingVector.getSeasonName() != null ? clothingVector.getSeasonName() : "");
        metadata.put("brandName", clothingVector.getBrandName() != null ? clothingVector.getBrandName() : "");
        metadata.put("size", clothingVector.getSize() != null ? clothingVector.getSize() : "");
        metadata.put("price", clothingVector.getPrice() != null ? clothingVector.getPrice().toString() : "");
        metadata.put("description", clothingVector.getDescription() != null ? clothingVector.getDescription() : "");
//        metadata.put("currentLocationName", clothingVector.getCurrentLocationName() != null ? clothingVector.getCurrentLocationName() : "");
        metadata.put("wearCount", clothingVector.getWearCount() != null ? clothingVector.getWearCount().toString() : "0");

        return new Document(content, metadata);
    }

    /**
     * 将 Spring AI Document 转换回 ClothingVector 领域实体
     */
    private ClothingVector convertToClothingVector(Document aiDocument) {
        Map<String, Object> metadata = aiDocument.getMetadata();

        return ClothingVector.builder()
                .clothingId((String) metadata.get("clothingId"))
                .userId((String) metadata.get("userId"))
                .name((String) metadata.get("name"))
                .typeName((String) metadata.get("typeName"))
                .colorName((String) metadata.get("colorName"))
                .seasonName((String) metadata.get("seasonName"))
                .brandName((String) metadata.get("brandName"))
                .size((String) metadata.get("size"))
                .description((String) metadata.get("description"))
//                .currentLocationName((String) metadata.get("currentLocationName"))
                .build();
    }
}
