package com.xiaoo.kaleido.interview.domain.candidate.adapter.ai;

import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateProfile;

/**
 * 简历提取服务接口（Domain层定义）
 * <p>
 * 使用AI从简历文本中提取结构化信息，Domain层定义，由Infrastructure层实现
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface IResumeExtractor {

    /**
     * 从简历文本中提取结构化信息
     *
     * @param resumeText 简历文本内容
     * @return 候选人简介结构化数据
     */
    CandidateProfile extractProfile(String resumeText);
}
