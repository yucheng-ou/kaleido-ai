package com.xiaoo.kaleido.interview.application.command;

import com.xiaoo.kaleido.api.interview.response.ResumeUploadResponse;
import com.xiaoo.kaleido.interview.domain.agent.IInterviewAgent;
import com.xiaoo.kaleido.interview.domain.candidate.adapter.ai.IResumeExtractor;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateProfile;
import com.xiaoo.kaleido.interview.domain.candidate.adapter.repository.ICandidateRepository;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.service.ICandidateDomainService;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 简历命令服务
 * <p>
 * 负责简历上传、解析、提取和入库的编排
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeCommandService {

    private final ICandidateDomainService candidateDomainService;
    private final IResumeExtractor resumeExtractor;
    private final IInterviewAgent interviewAgent;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final ICandidateRepository candidateRepository;

    /**
     * 上传并处理简历
     * <p>
     * 1. 解析文件（Apache Tika）
     * 2. 结构化提取（调用AI）
     * 3. 存入数据库（MySQL）
     * 4. 存入向量库（Milvus）
     *
     * @param file 简历文件（PDF/Word）
     * @return 简历上传响应
     */
    public ResumeUploadResponse uploadResume(MultipartFile file) {
        log.info("开始处理简历上传，文件名: {}, 大小: {} bytes",
                file.getOriginalFilename(), file.getSize());

        try {
            // 1. 解析文件内容
            String content = parseFile(file);
            log.info("简历解析完成，内容长度: {} 字符", content.length());

            // 2. AI提取结构化信息
            CandidateProfile profile = resumeExtractor.extractProfile(content);
            log.info("AI提取完成，姓名: {}, 技能: {}", profile.getName(), profile.getSkills());

            // 3. 创建候选人并存入数据库
            CandidateAggregate candidate = candidateDomainService.createCandidate(
                    profile.getName(),
                    profile.getSkills(),
                    profile.getExperienceYears(),
                    content
            );
            log.info("候选人入库成功，ID: {}", candidate.getId());

            // 4. 将简历向量化并存入向量库
            embedAndStoreResume(candidate.getId(), content);
            log.info("简历向量入库成功");

            // 5. 保存候选人信息
            candidateRepository.save(candidate);

            return ResumeUploadResponse.builder()
                    .candidateId(String.valueOf(candidate.getId()))
                    .name(profile.getName())
                    .skills(profile.getSkills())
                    .experienceYears(profile.getExperienceYears())
                    .message("简历上传成功，已入库")
                    .build();

        } catch (Exception e) {
            log.error("简历处理失败: {}", e.getMessage(), e);
            throw new RuntimeException("简历处理失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解析文件内容
     */
    private String parseFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            DocumentParser parser = new ApacheTikaDocumentParser();
            Document document = parser.parse(inputStream);
            return document.text();
        }
    }

    /**
     * 将简历向量化并存入向量库
     */
    private void embedAndStoreResume(String candidateId, String content) {
        // 分割文档
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
        List<TextSegment> segments = splitter.split(Document.from(content));

        // 为每个段添加候选人ID元数据
        segments.forEach(segment -> {
            segment.metadata().put("candidateId", candidateId);
            segment.metadata().put("doc_type", "resume");
        });

        // 生成嵌入向量
        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        // 存入向量库
        embeddingStore.addAll(embeddings, segments);

        log.info("简历向量化完成，共 {} 个段", segments.size());
    }

    /**
     * 根据职位JD生成面试问题
     *
     * @param candidateId    候选人ID
     * @param jobDescription 职位JD
     * @return 生成的面试问题
     */
    public String generateInterviewQuestions(String candidateId, String jobDescription) {
        log.info("生成面试问题，候选人ID: {}, 职位描述长度: {}", candidateId, 
                 jobDescription != null ? jobDescription.length() : 0);
        
        try {
            // 1. 输入验证
            if (StringUtils.isBlank(candidateId)) {
                throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
            }
            if (StringUtils.isBlank(jobDescription)) {
                throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, 
                    "职位描述不能为空且至少10个字符");
            }
            
            // 2. 获取候选人信息
            CandidateAggregate candidate = candidateRepository.findByIdOrThrow(candidateId);
            
            // 3. 验证候选人状态
            if (!candidate.canScheduleInterview()) {
                throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR,
                    "候选人当前状态不允许生成面试问题: " + candidate.getStatus().getDescription());
            }
            
            // 4. 构建完整提示词（包含候选人信息和职位描述）
            String prompt = buildInterviewQuestionsPrompt(candidate, jobDescription);
            
            // 5. 调用AI服务
            String questions = interviewAgent.generateInterviewQuestions(prompt);
            
            log.info("面试问题生成成功，候选人: {}, 问题长度: {}", 
                     candidate.getName(), questions.length());
            
            return questions;
            
        } catch (InterviewException e) {
            log.error("生成面试问题业务异常: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("生成面试问题技术异常: {}", e.getMessage(), e);
            throw InterviewException.of(InterviewErrorCode.AI_INTERVIEW_EXECUTION_ERROR,
                "生成面试问题失败: " + e.getMessage());
        }
    }
    
    /**
     * 构建面试问题生成提示词
     */
    private String buildInterviewQuestionsPrompt(CandidateAggregate candidate, String jobDescription) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("请为以下候选人针对职位要求生成面试问题：\n\n");
        
        // 添加候选人上下文
        prompt.append("候选人信息：\n");
        prompt.append("姓名：").append(candidate.getName()).append("\n");
        
        if (StringUtils.isNotBlank(candidate.getSkills())) {
            prompt.append("技能：").append(candidate.getSkills()).append("\n");
        }
        
        if (candidate.getExperienceYears() != null) {
            prompt.append("工作经验：").append(candidate.getExperienceYears()).append("年\n");
        }
        
        prompt.append("\n");
        prompt.append("职位要求：\n").append(jobDescription.trim()).append("\n\n");
        
        prompt.append("请基于候选人的技能和经验，针对以上职位要求生成相关面试问题。");
        
        return prompt.toString();
    }
}
