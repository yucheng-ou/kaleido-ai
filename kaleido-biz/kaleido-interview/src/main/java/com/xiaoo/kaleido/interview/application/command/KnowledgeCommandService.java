package com.xiaoo.kaleido.interview.application.command;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业知识文档命令服务
 * <p>
 * 负责公司内部文档的上传、解析和向量化入库
 *
 * @author ouyucheng
 * @date 2026/3/1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeCommandService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 上传并处理企业知识文档
     *
     * @param file 文档文件（PDF/Word/Txt）
     * @return 上传结果消息
     */
    public String uploadKnowledge(MultipartFile file) {
        log.info("开始处理企业知识文档上传，文件名: {}, 大小: {} bytes",
                file.getOriginalFilename(), file.getSize());

        try {
            // 1. 解析文件
            DocumentParser documentParser = new ApacheTikaDocumentParser();
            Document document;
            try (InputStream inputStream = file.getInputStream()) {
                document = documentParser.parse(inputStream);
            }

            // 2. 切分文档
            DocumentSplitter splitter = DocumentSplitters.recursive(500, 50);
            List<TextSegment> segments = splitter.split(document);
            log.info("文档切分完成，共 {} 个片段", segments.size());

            // 3. 添加元数据 (Metadata) 并向量化
            // 为每个片段添加 doc_type: "knowledge" 标签
            List<TextSegment> taggedSegments = segments.stream()
                    .map(segment -> {
                        segment.metadata().put("doc_type", "knowledge");
                        segment.metadata().put("source_file", file.getOriginalFilename());
                        return segment;
                    })
                    .collect(Collectors.toList());

            // 4. 向量化并存入 Milvus
            List<Embedding> embeddings = embeddingModel.embedAll(taggedSegments).content();
            embeddingStore.addAll(embeddings, taggedSegments);

            log.info("企业知识文档向量化入库成功");
            return "文档上传并处理成功";

        } catch (IOException e) {
            log.error("文档读取失败: {}", e.getMessage(), e);
            throw new RuntimeException("文档处理失败", e);
        } catch (Exception e) {
            log.error("文档处理异常: {}", e.getMessage(), e);
            throw new RuntimeException("系统内部错误", e);
        }
    }
}
