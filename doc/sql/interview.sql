-- =============================================
-- Kaleido Interview Module Database Schema
-- Author: ouyucheng
-- Date: 2026/2/28
-- =============================================

-- ----------------------------
-- Table: t_candidates
-- Description: 候选人信息表
-- ----------------------------
DROP TABLE IF EXISTS `t_candidates`;
CREATE TABLE `t_candidates` (
    `id` VARCHAR(64) NOT NULL COMMENT '主键ID',
    `name` VARCHAR(100) DEFAULT NULL COMMENT '候选人姓名',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能列表（逗号分隔）',
    `experience_years` INT DEFAULT NULL COMMENT '工作年限',
    `raw_resume_text` TEXT DEFAULT NULL COMMENT '原始简历文本（用于RAG）',
    `status` VARCHAR(20) DEFAULT 'NEW' COMMENT '候选人状态：NEW-新建，INTERVIEWING-面试中，HIRED-已录用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='候选人信息表';

-- ----------------------------
-- Table: t_interviews
-- Description: 面试记录表
-- ----------------------------
DROP TABLE IF EXISTS `t_interviews`;
CREATE TABLE `t_interviews` (
    `id` VARCHAR(64) NOT NULL COMMENT '主键ID',
    `candidate_id` VARCHAR(64) NOT NULL COMMENT '候选人ID',
    `interview_time` DATETIME DEFAULT NULL COMMENT '面试时间',
    `interviewer_name` VARCHAR(100) DEFAULT NULL COMMENT '面试官姓名',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` INT DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    KEY `idx_candidate_id` (`candidate_id`),
    KEY `idx_interview_time` (`interview_time`),
    KEY `idx_interviewer_name` (`interviewer_name`),
    KEY `idx_created_at` (`created_at`),
    CONSTRAINT `fk_interview_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `t_candidates` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='面试记录表';
