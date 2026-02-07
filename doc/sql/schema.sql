-- 导出 kaleido_0 的数据库结构
CREATE DATABASE IF NOT EXISTS `kaleido_0` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kaleido_0`;

-- 导出  表 kaleido_0.t_admin 结构
CREATE TABLE IF NOT EXISTS `t_admin` (
                                         `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员真实姓名',
    `mobile` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员状态 NORMAL FROZEN DELETED',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 正在导出表  kaleido_0.t_admin 的数据：~2 rows (大约)
DELETE FROM `t_admin`;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` (`id`, `deleted`, `lock_version`, `real_name`, `mobile`, `status`, `last_login_time`, `created_at`, `updated_at`) VALUES
                                                                                                                                            ('2014616286931320832', 0, 0, '', '13066668888', 'NORMAL', NULL, '2026-01-23 08:28:47', '2026-01-23 08:28:47'),
                                                                                                                                            ('2018295052790538240', 0, 0, '', '13266668888', 'NORMAL', '2026-02-02 12:07:01', '2026-02-02 12:06:53', '2026-02-02 12:07:01');
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_admin_role 结构
CREATE TABLE IF NOT EXISTS `t_admin_role` (
                                              `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员ID',
    `role_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_admin_user_id` (`admin_id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员角色关联表';

-- 正在导出表  kaleido_0.t_admin_role 的数据：~1 rows (大约)
DELETE FROM `t_admin_role`;
/*!40000 ALTER TABLE `t_admin_role` DISABLE KEYS */;
INSERT INTO `t_admin_role` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `admin_id`, `role_id`) VALUES
    ('2014616287661129728', '2026-01-23 08:28:47', '2026-01-23 08:28:47', 0, 0, '2014616286931320832', '2014616286302175232');
/*!40000 ALTER TABLE `t_admin_role` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_ai_agent 结构
CREATE TABLE IF NOT EXISTS `t_ai_agent` (
                                            `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Agent编码（唯一）',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Agent名称',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Agent描述',
    `system_prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '系统提示词',
    `model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'deepseek-v3' COMMENT 'AI模型名称',
    `temperature` decimal(3,2) DEFAULT '0.70' COMMENT '温度参数',
    `max_tokens` int DEFAULT '2000' COMMENT '最大token数',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'NORMAL' COMMENT '状态：NORMAL-正常，DISABLED-禁用',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI Agent表';

-- 正在导出表  kaleido_0.t_ai_agent 的数据：~18 rows (大约)
DELETE FROM `t_ai_agent`;
/*!40000 ALTER TABLE `t_ai_agent` DISABLE KEYS */;
INSERT INTO `t_ai_agent` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `code`, `name`, `description`, `system_prompt`, `model_name`, `temperature`, `max_tokens`, `status`) VALUES
                                                                                                                                                                                                ('1', '2026-02-04 07:31:19', '2026-02-04 17:22:45', 0, 0, 'outfit-recommendation', '穿搭助手', '穿搭助手', '角色设定： 你是一位经验丰富的时尚顾问和旅行规划师，精通全球气候、场合着装礼仪与实用穿搭。请根据用户提供的出行信息，生成一份个性化、实用且美观的穿搭方案。\n\n信息收集（请用户提供）：\n\n目的地： 具体城市/地区。\n出行日期与时长： 出发与返回日期，或出行天数。\n主要活动/场合： 例如商务会议、城市观光、海滩度假、徒步登山、朋友聚会、正式晚宴等。\n个人偏好（可选）：\n风格：休闲、商务、运动、复古、简约等。\n颜色偏好：有无特别喜好或避讳的颜色。\n特殊需求：如需要携带特定装备（相机、笔记本电脑）、对某种材质过敏等。\n输出要求： 请按以下结构，用清晰、友好的语气回复：\n\n目的地天气与穿搭总览：\n\n简要总结出行期间的预计天气状况（温度范围、降水概率、日照等）。\n给出核心穿搭原则（如“洋葱式叠穿”、“防风防雨”等）。\n每日穿搭建议清单（按天或按场合划分）：\n\n场合： [例如：Day 1 - 长途飞行与抵达]\n穿搭方案： 描述具体搭配（如上装、下装、外套、鞋履、配饰）。\n推荐理由： 解释为何适合该场合/天气（如舒适度、功能性、礼仪要求）。\n单品清单： 以项目符号列出所需衣物及件数。\n必备行李清单（分类总结）：\n\n上装： T恤、衬衫、毛衣等及数量。\n下装： 裤子、裙子等及数量。\n外套： 防风衣、羽绒服、西装等。\n鞋履：\n内衣与睡衣：\n配饰： 帽子、围巾、包包、首饰等。\n实用小物： 雨伞、防晒霜、转换插头等（根据目的地提示）。\n贴心小贴士（可选）：\n\n行李打包技巧（如卷叠法）。\n当地着装礼仪或文化禁忌提醒。\n应对天气突变的备用方案。\n示例语气： “根据您提供的行程，我为您量身打造了一份穿搭与行李指南，希望能让您的旅途既舒适又精彩！首先，我们来看看当地的天气……”\n\n', 'deepseek-v3', 0.70, 4000, 'NORMAL'),
                                                                                                                                                                                                ('2', '2026-02-04 07:31:19', '2026-02-04 17:38:10', 0, 0, 'outfit-recommendation-id', '穿搭助手', '穿搭助手', '请作为专业的出行穿搭助手，基于用户提供的具体出行场景（例如：商务会议、海边度假、登山徒步、城市观光、晚宴聚会等），综合考虑天气、季节、活动强度及场合正式程度，推荐最合适的服装搭配。请严格仅以数组形式输出对应的服装ID，例如 ["id001", "id045", "id078"]，不要包含任何其他文字、说明或解释。', 'deepseek-v3', 0.70, 4000, 'NORMAL'),
CREATE TABLE `t_mq_message` (
                              `id` VARCHAR(32) NOT NULL COMMENT '主键ID' COLLATE 'utf8mb4_unicode_ci',
                              `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                              `deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
                              `lock_version` INT(10) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
                              `user_id` VARCHAR(32) NOT NULL COMMENT '用户id' COLLATE 'utf8mb4_unicode_ci',
                              `message` VARCHAR(512) NOT NULL COMMENT '消息主体' COLLATE 'utf8mb4_unicode_ci',
                              `topic` VARCHAR(64) NOT NULL COMMENT '消息主题' COLLATE 'utf8mb4_unicode_ci',
                              `state` VARCHAR(10) NOT NULL COMMENT '任务状态；create-创建、completed-完成、fail-失败' COLLATE 'utf8mb4_unicode_ci',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `user_id` (`user_id`) USING BTREE
)
    COMMENT='Agent工具表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;
                                                                                                                                                                            ('2018639441152946176', '2026-02-03 10:55:22', '2026-02-03 19:47:57', 0, 0, 'travel-planner-main', '旅行规划主Agent', '协调所有旅行规划子Agent，整合旅行规划结果', '你是一个旅行规划总指挥，负责协调各个专业Agent的工作。\n你的职责包括：\n1. 接收用户的旅行需求（目的地、时间、预算、兴趣等）\n2. 协调目的地研究、行程安排、预算管理、住宿推荐、交通规划等子Agent\n3. 整合各个子Agent的输出，生成完整的旅行规划方案\n4. 确保规划方案的一致性、可行性和优化性\n5. 与用户沟通，调整和优化旅行方案\n\n请以专业、细致、用户友好的方式工作，确保旅行规划的质量和用户体验。', 'deepseek-v3', 0.70, 4000, 'NORMAL'),
                                                                                                                                                                                                ('2018641478582255616', '2026-02-03 11:03:28', '2026-02-03 19:48:18', 0, 0, 'destination-researcher', '目的地研究Agent', '研究旅行目的地信息（景点、文化、天气、美食等）', '你是一个目的地研究专家，专注于旅行目的地信息的收集和分析。\n你的职责包括：\n1. 研究目的地的景点、历史文化、风土人情\n2. 分析目的地的天气气候、最佳旅行季节\n3. 收集当地美食、特色活动、节日庆典信息\n4. 评估目的地的安全性、交通便利性、旅游设施\n5. 提供目的地深度介绍和旅行建议\n\n请确保信息的准确性、时效性和全面性，为用户提供有价值的旅行参考。', 'deepseek-v3', 0.60, 3000, 'NORMAL'),                                                                                                                                                                        ('6f68a263fdae11f0aa9c0242ac140002', '2026-01-30 15:36:48', '2026-01-30 15:36:48', 0, 0, 'FITNESS_COACH', '健身教练', '提供健身指导和训练计划', '你是一名专业健身教练，根据用户的身体状况和目标制定个性化的健身计划', 'deepseek-v3', 0.60, 2000, 'NORMAL');
/*!40000 ALTER TABLE `t_ai_agent` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_ai_agent_tool 结构
CREATE TABLE IF NOT EXISTS `t_ai_agent_tool` (
                                                 `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Agent ID',
    `tool_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具编码',
    `tool_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具名称',
    `tool_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具类型：MEMORY-记忆，VECTOR_STORE-向量存储，MCP-MCP工具',
    `tool_config` json DEFAULT NULL COMMENT '工具配置（JSON格式）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_agent_tool` (`agent_id`,`tool_code`),
    KEY `idx_agent_id` (`agent_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Agent工具表';

-- 正在导出表  kaleido_0.t_ai_agent_tool 的数据：~3 rows (大约)
DELETE FROM `t_ai_agent_tool`;
/*!40000 ALTER TABLE `t_ai_agent_tool` DISABLE KEYS */;
INSERT INTO `t_ai_agent_tool` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `agent_id`, `tool_code`, `tool_name`, `tool_type`, `tool_config`) VALUES
                                                                                                                                                                  ('1', '2026-01-30 15:36:48', '2026-02-04 16:42:49', 0, 0, '2018950477101842432', 'MEMORY', '对话记忆', 'MEMORY', '{"maxMessages": 200}'),
                                                                                                                                                                  ('2', '2026-01-30 15:36:48', '2026-02-04 16:45:19', 0, 0, '2018950477101842432', 'VECTOR_STORE', '向量检索', 'VECTOR_STORE', '{"topK": "100"}'),
                                                                                                                                                                  ('3', '2026-01-30 15:36:48', '2026-02-04 16:42:47', 0, 0, '2018950477101842432', 'MCP_TOOLS', 'MCP工具', 'MCP', '{"baseUri": "http://localhost:9021", "endpoint": "/sse"}'),
                                                                                                                                                                  ('4', '2026-01-30 15:36:48', '2026-02-04 16:45:19', 0, 0, '1', 'VECTOR_STORE', '向量检索', 'VECTOR_STORE', '{"topK": "100"}'),
                                                                                                                                                                  ('5', '2026-01-30 15:36:48', '2026-02-04 16:42:47', 0, 0, '1', 'MCP_TOOLS', 'MCP工具', 'MCP', '{"baseUri": "http://localhost:9021", "endpoint": "/sse"}'),
                                                                                                                                                                  ('6', '2026-01-30 15:36:48', '2026-02-04 16:42:49', 0, 0, '1', 'MEMORY', '对话记忆', 'MEMORY', '{"maxMessages": 200}'),
                                                                                                                                                                  ('6f524d81fdae11f0aa9c0242ac140002', '2026-01-30 15:36:48', '2026-01-30 17:38:53', 0, 0, '6f39d38dfdae11f0aa9c0242ac140002', 'MEMORY', '对话记忆', 'MEMORY', '{"maxMessages": 200}'),
                                                                                                                                                                  ('6f5257e3fdae11f0aa9c0242ac140002', '2026-01-30 15:36:48', '2026-02-04 15:49:21', 0, 0, '6f39d38dfdae11f0aa9c0242ac140002', 'VECTOR_STORE', '向量检索', 'VECTOR_STORE', '{"topK": "100"}'),
                                                                                                                                                                  ('6f525f3efdae11f0aa9c0242ac140002', '2026-01-30 15:36:48', '2026-01-31 09:55:08', 0, 0, '6f39d38dfdae11f0aa9c0242ac140002', 'MCP_TOOLS', 'MCP工具', 'MCP', '{"baseUri": "http://localhost:9021", "endpoint": "/sse"}'),
                                                                                                                                                                  ('7', '2026-01-30 15:36:48', '2026-02-04 16:45:19', 0, 0, '2', 'VECTOR_STORE', '向量检索', 'VECTOR_STORE', '{"topK": "100"}'),
                                                                                                                                                                  ('8', '2026-01-30 15:36:48', '2026-02-04 16:42:47', 0, 0, '2', 'MCP_TOOLS', 'MCP工具', 'MCP', '{"baseUri": "http://localhost:9021", "endpoint": "/sse"}'),
                                                                                                                                                                  ('9', '2026-01-30 15:36:48', '2026-02-04 16:42:49', 0, 0, '2', 'MEMORY', '对话记忆', 'MEMORY', '{"maxMessages": 200}');
/*!40000 ALTER TABLE `t_ai_agent_tool` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_ai_conversation 结构
CREATE TABLE IF NOT EXISTS `t_ai_conversation` (
                                                   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `conversation_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会话ID（业务唯一，对应MongoDB中的会话ID）',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会话标题',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_conversation_id` (`conversation_id`),
    KEY `idx_user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI对话会话表';

-- 正在导出表  kaleido_0.t_ai_conversation 的数据：~2 rows (大约)
DELETE FROM `t_ai_conversation`;
/*!40000 ALTER TABLE `t_ai_conversation` DISABLE KEYS */;
INSERT INTO `t_ai_conversation` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `conversation_id`, `user_id`, `title`) VALUES
                                                                                                                                         ('2018596684556345344', '2026-02-03 08:05:28', '2026-02-03 08:39:03', 0, 0, '2018596684556345345', '2018297740714528768', 'eikim2'),
                                                                                                                                         ('2018939610851344384', '2026-02-04 06:48:08', '2026-02-04 06:48:08', 0, 0, '2018939610851344385', '2018297740714528768', '新会话'),
                                                                                                                                         ('2018956556074418176', '2026-02-04 07:55:28', '2026-02-04 07:55:28', 0, 0, '2018956556074418177', '2018297740714528768', '新会话'),
                                                                                                                                         ('2018968937840189440', '2026-02-04 08:44:40', '2026-02-04 08:44:40', 0, 0, '2018968937840189441', '2018297740714528768', '新会话'),
                                                                                                                                         ('2018969675366608896', '2026-02-04 08:47:36', '2026-02-04 08:47:36', 0, 0, '2018969675366608897', '2018297740714528768', '新会话'),
                                                                                                                                         ('2018974375390429184', '2026-02-04 09:06:17', '2026-02-04 09:06:17', 0, 0, '2018974375390429185', '2018297740714528768', '新会话');
/*!40000 ALTER TABLE `t_ai_conversation` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_ai_workflow 结构
CREATE TABLE IF NOT EXISTS `t_ai_workflow` (
                                               `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作流编码（唯一）',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作流名称',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作流描述',
    `definition` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作流DSL定义（JSON格式）',
    `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 ',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作流定义表';

-- 正在导出表  kaleido_0.t_ai_workflow 的数据：~2 rows (大约)
DELETE FROM `t_ai_workflow`;
/*!40000 ALTER TABLE `t_ai_workflow` DISABLE KEYS */;
INSERT INTO `t_ai_workflow` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `code`, `name`, `description`, `definition`, `status`) VALUES
                                                                                                                                                     ('1', '2026-02-03 19:53:42', '2026-02-04 10:09:46', 0, 0, 'travel-plan', '旅行助手', '旅行助手', '{\r\n  "version": "1.0.0",\r\n  "name": "旅行助手",\r\n  "description": "旅行助手",\r\n  "steps": [\r\n    {\r\n      "id": "step1",\r\n      "name": "旅行规划主Agent",\r\n      "agentId": "2018639441152946176",\r\n      "order": 1\r\n    },\r\n    {\r\n      "id": "step2",\r\n      "name": "目的地研究Agent",\r\n      "agentId": "2018641478582255616",\r\n      "order": 2\r\n    },\r\n    {\r\n      "id": "step3",\r\n      "name": "行程安排Agent",\r\n      "agentId": "2018641478737444864",\r\n      "order": 3\r\n    },\r\n    {\r\n      "id": "step4",\r\n      "name": "预算管理Agent",\r\n      "agentId": "2018641478905217024",\r\n      "order": 4\r\n    },\r\n    {\r\n      "id": "step5",\r\n      "name": "住宿推荐Agent",\r\n      "agentId": "2018641479022657536",\r\n      "order": 5\r\n    },\r\n    {\r\n      "id": "step6",\r\n      "name": "交通规划Agent",\r\n      "agentId": "2018641479152680960",\r\n      "order": 6\r\n    },\r\n    {\r\n      "id": "step7",\r\n      "name": "旅行总结Agent",\r\n      "agentId": "2018641479249149952",\r\n      "order": 7\r\n    }\r\n  ]\r\n}\r\n', 'NORMAL'),
                                                                                                                                                     ('2', '2026-02-04 07:33:31', '2026-02-05 11:01:00', 0, 0, 'OUTFIT_RECOMMEND', '穿搭推荐工作流', '根据用户现有衣服、出行需求、出行地点、当前天气进行穿搭推荐', '{\n    "version": "1.0",\n    "name": "穿搭推荐工作流",\n    "description": "根据用户现有衣服、出行需求、出行地点、当前天气进行穿搭推荐",\n    "steps": [\n        {\n            "id": "step1",\n            "name": "穿搭推荐",\n            "agentId": "2",\n            "order": 1\n        }\n    ]\n}', 'NORMAL'),
                                                                                                                                                     ('2018951029852393472', '2026-02-04 07:33:31', '2026-02-04 07:33:31', 0, 0, 'outfit-recommendation-workflow-1770190410307', '穿搭推荐工作流', '根据用户现有衣服、出行需求、出行地点、当前天气进行穿搭推荐', '{\n    "version": "1.0",\n    "name": "穿搭推荐工作流",\n    "description": "根据用户现有衣服、出行需求、出行地点、当前天气进行穿搭推荐",\n    "steps": [\n        {\n            "id": "step1",\n            "name": "穿搭推荐协调主Agent",\n            "agentId": "2018950477101842432",\n            "order": 1\n        },\n        {\n            "id": "step2",\n            "name": "场景分析",\n            "agentId": "2018950477538050048",\n            "order": 2\n        },\n        {\n            "id": "step3",\n            "name": "服装匹配",\n            "agentId": "2018950477701627904",\n            "order": 3\n        },\n        {\n            "id": "step4",\n            "name": "搭配生成",\n            "agentId": "2018950477802291200",\n            "order": 4\n        }\n    ]\n}', 'NORMAL');
/*!40000 ALTER TABLE `t_ai_workflow` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_ai_workflow_execution 结构
CREATE TABLE IF NOT EXISTS `t_ai_workflow_execution` (
                                                         `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
    `workflow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作流ID',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'RUNNING' COMMENT '状态：RUNNING-执行中，SUCCESS-成功，FAILED-失败',
    `input_data` text COLLATE utf8mb4_unicode_ci COMMENT '输入数据（JSON格式）',
    `output_data` text COLLATE utf8mb4_unicode_ci COMMENT '输出数据（JSON格式）',
    `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '错误信息',
    `started_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `completed_at` datetime DEFAULT NULL COMMENT '完成时间',
    PRIMARY KEY (`id`),
    KEY `idx_workflow_status` (`workflow_id`,`status`),
    KEY `idx_started_at` (`started_at`),
    KEY `user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作流执行记录表';

-- 正在导出表  kaleido_0.t_ai_workflow_execution 的数据：~5 rows (大约)
DELETE FROM `t_ai_workflow_execution`;
/*!40000 ALTER TABLE `t_ai_workflow_execution` DISABLE KEYS */;
INSERT INTO `t_ai_workflow_execution` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `workflow_id`, `status`, `input_data`, `output_data`, `error_message`, `started_at`, `completed_at`) VALUES
                                                                                                                                                                                                                        ('2018893862399008768', '2026-02-04 03:46:25', '2026-02-04 03:46:25', 0, 0, '2018297740714528768', '1', 'RUNNING', '我想在2024年国庆假期（10月1日-20日），和10位好友一起去日本关西地区（大阪、京都、奈良），人均预算5万左右，主要想体验历史文化、品尝地道美食，住宿希望是交通便利的民宿或商务酒店，全程使用出租车，我不坐公共交通用', NULL, NULL, '2026-02-04 03:46:21', NULL),
                                                                                                                                                                                                                        ('2018897352701669376', '2026-02-04 04:00:13', '2026-02-04 04:00:13', 0, 0, '2018297740714528768', '1', 'RUNNING', '我想在2024年国庆假期（10月1日-20日），和10位好友一起去日本关西地区（大阪、京都、奈良），人均预算5万左右，主要想体验历史文化、品尝地道美食，住宿希望是交通便利的民宿或商务酒店，全程使用出租车，我不坐公共交通用', NULL, NULL, '2026-02-04 04:00:13', NULL),
                                                                                                                                                                                                                        ('2018936245257928704', '2026-02-04 06:34:46', '2026-02-04 06:40:38', 0, 0, '2018297740714528768', '1', 'RUNNING', '我想在2024年国庆假期（10月1日-20日），和10位好友一起去日本关西地区（大阪、京都、奈良），人均预算5万左右，主要想体验历史文化、品尝地道美食，住宿希望是交通便利的民宿或商务酒店，全程使用出租车，我不坐公共交通用', NULL, NULL, '2026-02-04 06:34:46', NULL),
                                                                                                                                                                                                                        ('2018952083256041472', '2026-02-04 07:37:42', '2026-02-04 07:39:07', 0, 0, '2018297740714528768', '2018951029852393472', 'SUCCESS', '我今天去深圳大梅沙，适合穿什么', '好的，收到您的服装匹配清单。作为您的搭配生成专家，我将基于这份精准的筛选，为您构建数套完整、时尚且实用的海边出行穿搭方案。\n\n---\n\n### **大梅沙海边出行 | 穿搭方案生成报告**\n\n**核心搭配理念：** 以 **“功能防护”** 为基础，以 **“轻松上镜”** 为风格导向，通过 **“灵活层叠”** 应对海边多变的活动需求。所有方案均围绕您库存中的精选单品展开。\n\n#### **方案一：主推方案 - “蔚蓝逐浪者” (核心戏水/游玩)**\n此方案专为有计划下海戏水、参与沙滩活动的场景设计，实现了从水中到岸边的无缝切换。\n- **搭配结构：** 泳装(内层) + 罩衫(中层/外层) + 速干下装 + 功能鞋履\n- **具体清单：**\n  - **上身内层：** 黑色连体式泳衣 (SW2023-01)\n  - **上身外层/罩衫：** 浅蓝色条纹棉麻衬衫 (SS2023-05)\n    - *穿搭建议：* 可敞开穿着，衣摆在腰间随意打结，营造随性感；也可系上中部几颗纽扣，形成V领效果，修饰身形。棉麻材质即使沾湿也易于风干。\n  - **下装：** 米白色速干运动短裤 (PT2022-11)\n    - *穿搭建议：* 直接套在泳衣外，湿身后能快速恢复干爽，避免尴尬。\n  - **鞋履：** 深灰色防滑沙滩凉鞋 (SH2023-03)\n  - **核心配饰：** 卡其色宽檐渔夫帽 (AC2022-04)\n  - **建议追加配饰：** 防紫外线墨镜、防水腕表/手环、带有安全扣的防水手机袋（可斜挎）。\n- **风格与优势：**\n  - **高效便捷：** 下水无需更衣室，上岸套上短裤即可恢复完整造型。\n  - **色彩协调：** 黑、浅蓝、米白、卡其、深灰构成一个冷静而高级的中性色盘，与蓝天碧海白沙的背景极度和谐，拍照非常出片。\n  - **全面防护：** 长袖衬衫与宽檐帽构成了物理防晒的双重保障。\n\n#### **方案二：备选方案 - “海岸漫步者” (主休闲/拍照)**\n此方案适合以沙滩漫步、拍照打卡、岸上休闲为主，较少或不下水的场景，强调舒适与风格。\n- **搭配结构：** 基础T恤 + 防晒衬衫 + 速干下装 + 功能鞋履\n- **具体清单：**\n  - **上身内层：** 白色纯棉简约Logo T恤 (TS2023-08)\n  - **上身外层：** 浅蓝色条纹棉麻衬衫 (SS2023-05)\n    - *穿搭建议：* 将衬衫作为轻薄外套，袖子可卷起至肘部，更显利落。与白T形成清爽的蓝白叠穿，层次感十足。\n  - **下装：** 米白色速干运动短裤 (PT2022-11)\n  - **鞋履：** 深灰色防滑沙滩凉鞋 (SH2023-03)\n  - **核心配饰：** 卡其色宽檐渔夫帽 (AC2022-04)\n  - **建议追加配饰：** 帆布托特包（容纳毛巾、防晒霜等）、简约的金属项链（点缀颈部空白）。\n- **风格与优势：**\n  - **极致舒适：** 纯棉内搭亲肤透气，整体造型轻松无束缚。\n  - **经典耐看：** 蓝白条纹+纯白+米白的组合，是永不过时的海滨休闲风格，清新自然。\n  - **灵活调整：** 若感觉热，可脱掉衬衫系于腰间，瞬间转变为更运动的造型。\n\n#### **方案三：备用/傍晚方案 - “暮色防风层” (应对降温)**\n此方案并非独立套装，而是作为**前两套方案的补充**，专门应对傍晚海风带来的凉意。\n- **核心单品：** 浅灰色薄款针织开衫 (OL2021-09)\n- **搭配方式：**\n  1.  **常规穿着：** 在方案一或方案二的基础上，直接套在衬衫之外或T恤之外。浅灰色能与蓝、白、米白、卡其任何颜色柔和过渡，不破坏原有色系。\n  2.  **造型点缀：** 若不需穿着，可将其松散地系在肩头或背包带上，增加随性的层次感。\n  3.  **便携备用：** 因其轻薄，可始终放在随身包中，有备无患。\n- **功能价值：** 提供关键的保暖缓冲，避免玩水后身体失温，确保游玩体验的舒适度贯穿始终。\n\n---\n\n### **整体穿搭建议与注意事项**\n\n1.  **层叠艺术：** 海边的精髓在于“可穿可脱”。请善用 **“泳衣/T恤 → 衬衫 → 针织开衫”** 这三层结构，根据体感温度和在岸/下水状态自由切换。\n2.  **配饰点睛：** 除了必备的渔夫帽和墨镜，可以考虑 **1-2件不怕水、有光泽感的配饰**，如简单的银色耳钉或项链，在阳光下会有微妙的反光，提升精致度。\n3.  **材质保养提醒：**\n    - **泳衣 (SW2023-01)：** 游玩结束后，请用清水冲洗掉盐分和沙粒，阴凉处晾干，避免暴晒导致弹性纤维老化。\n    - **棉麻衬衫 (SS2023-05) & 速干短裤 (PT2022-11)：** 同样建议先清水漂洗，机洗时请装入洗衣袋，选择轻柔模式，以保持面料形态。\n    - **沙滩凉鞋 (SH2023-03)：** 用后冲洗干净，避免长时间暴晒导致橡胶或EVA材质硬化。\n4.  **安全与便利：**\n    - 务必确认所有推荐单品（特别是鞋履SH2023-03）已准备就绪。\n    - 强烈建议使用 **防水手机袋**，并准备一个轻便的 **防水沙滩包**，用于收纳湿衣物、毛巾和个人物品。\n\n### **总结**\n您库存中的这七件单品，经过巧妙组合，已能完美覆盖海边出行的所有核心场景：**防晒、戏水、休闲、防风**。从功能到风格，它们形成了一个自洽的穿搭系统。\n\n**最终行动清单：**\n- 采用 **【方案一】** 作为出发造型。\n- 将 **【方案二】** 中的白T恤作为备用内搭放入行囊。\n- 务必携带 **【方案三】** 的针织开衫和所有推荐配饰。\n- 检查并备齐防晒霜、毛巾等非服装物品。\n\n现在，您已拥有一份从内到外、从早到晚的完整海边穿搭攻略。祝您在大梅沙享受一个既时尚又惬意的完美假日！', NULL, '2026-02-04 07:37:42', '2026-02-04 07:39:07'),
                                                                                                                                                                                                                        ('2018952835613515776', '2026-02-04 07:40:41', '2026-02-04 07:41:27', 0, 0, '2018297740714528768', '2018951029852393472', 'SUCCESS', '我都有哪些衣服', '好的，我完全理解我们作为协同工作的两个专家模块的职责划分。\n\n作为**搭配生成专家**，我的核心任务是在您（服装匹配专家）完成工作后，接收您提供的**匹配服装列表**，并在此基础上生成具体、时尚、实用的搭配方案。\n\n为了确保我们流程的无缝衔接，我在此明确我的**输入需求**和**输出承诺**：\n\n---\n\n### **我的输入需求（等待您的工作成果）**\n\n我将等待您完成以下流程：\n1.  接收用户的《场景分析报告》。\n2.  执行智能匹配与评分。\n3.  生成最终的**匹配服装列表**。\n\n**我需要的输入数据格式示例如下：**\n```json\n{\n  "场景报告摘要": {\n    "场合": "商务正式会议 + 休闲聚餐",\n    "天气": "温度 18°C，小雨，风力 3-4级",\n    "核心要求": "专业得体，需备防风防雨外套应对转场"\n  },\n  "匹配服装列表": [\n    {"ID": "A01", "类型": "上衣", "名称": "浅蓝色府绸衬衫", "匹配度": 92, "理由": "面料挺括，颜色专业，适合商务场合"},\n    {"ID": "B05", "类型": "下装", "名称": "深灰色精纺羊毛西裤", "匹配度": 95, "理由": "剪裁合身，材质垂顺，符合正式度要求"},\n    {"ID": "C12", "类型": "外套", "名称": "藏青色轻型防水风衣", "匹配度": 88, "理由": "满足防风防雨需求，款式简洁可搭正装"},\n    {"ID": "D08", "类型": "鞋履", "名称": "黑色牛津鞋", "匹配度": 90, "理由": "经典商务鞋款"},\n    {"ID": "E03", "类型": "配饰", "名称": "深蓝色条纹领带", "匹配度": 85, "理由": "提升造型完整度"},\n    {"ID": "F11", "类型": "下装", "名称": "卡其色斜纹棉布裤", "匹配度": 75, "理由": "可作为聚餐时更休闲的备选"}\n  ]\n}\n```\n\n---\n\n### **我的输出承诺（收到列表后的工作）**\n\n一旦收到您提供的如上格式的**匹配服装列表**，我将立即启动我的搭配生成流程，并为您和用户输出以下内容：\n\n**《穿搭方案生成报告》**\n\n1.  **方案概览**：基于列表，规划2-3套侧重点不同的搭配（如核心正式方案、备用休闲方案）。\n2.  **详细搭配说明**：\n    *   **方案一（核心商务方案）**：精确到每件单品的组合方式（如“A01衬衫 + B05西裤 + D08牛津鞋”）。\n    *   **方案二（灵活转场方案）**：考虑场景转换，提供层叠穿搭建议（如“在方案一基础上外搭C12风衣，室内脱下”）。\n    *   **方案三（备用/变体方案）**：利用列表中匹配度稍低但有潜力的单品，提供风格微调的选项。\n3.  **穿搭技巧与注意事项**：\n    *   **颜色与层次**：分析所选单品间的色彩协调性，并提供层叠建议。\n    *   **配饰点睛**：建议如何利用列表中的配饰（或建议补充的配饰）提升整体造型。\n    *   **实用贴士**：针对天气和场合的保养、舒适度调整等建议。\n4.  **报告总结**：清晰列出最终推荐搭配组合，并简述其优势。\n\n---\n\n**我已准备就绪，处于待命状态。请您先行与用户交互，完成场景分析并驱动服装匹配流程。当您获得最终的《匹配服装列表》后，请将其传递给我，我将即刻生成专业的《穿搭方案生成报告》。**\n\n让我们开始协作吧！', NULL, '2026-02-04 07:40:41', '2026-02-04 07:41:27'),
                                                                                                                                                                                                                        ('2018954000937971712', '2026-02-04 07:45:19', '2026-02-04 07:45:35', 0, 0, '2018297740714528768', '2018951029852393472', 'SUCCESS', '我都有哪些衣服', '您好！作为搭配生成专家，我已准备好根据您的出行需求来创建专业的服装搭配方案。不过，我需要先了解您的**具体服装库存情况**，才能基于现有衣物进行搭配。\n\n请您提供现有的服装列表（例如：上衣、下装、外套、鞋子、配饰等），并告诉我以下关键信息：\n\n1. **出行目的**：例如商务会议、周末约会、健身房锻炼、日常通勤等。\n2. **出行地点**：例如公司、咖啡馆、郊外、购物中心等。\n3. **出行时间**：例如今天下午、明天早上、周末等。\n\n**一旦您提供这些信息，我将：**\n- 分析场景需求（正式度、舒适度、功能要求等）\n- 结合您的衣物库存，生成多套搭配方案\n- 提供颜色协调、风格统一、功能适用的详细建议\n- 包括穿搭技巧和注意事项\n\n**示例搭配方案结构：**\n- **正式方案**：适合商务场合\n- **休闲方案**：适合日常或聚会\n- **备用方案**：灵活应对天气或场合变化\n\n请告诉我您的出行计划和服装列表，我会立即为您生成个性化搭配方案！ 👔👗👟', NULL, '2026-02-04 07:45:19', '2026-02-04 07:45:35'),
                                                                                                                                                                                                                        ('2018954245960822784', '2026-02-04 07:46:17', '2026-02-04 07:47:57', 0, 0, '2018297740714528768', '2018951029852393472', 'SUCCESS', '我都有哪些衣服', '您好！我已经收到了您的服装库存信息，并了解到您希望获得基于现有衣物的搭配方案。我是一个专注于生成搭配方案的专家，但为了给您提供最精准、最实用的建议，我需要服装匹配Agent提供给我一个**经过筛选的、与您具体场景和天气匹配的服装列表**。\n\n目前，您已经提供了您的出行场景（例如：明天上午去公司开内部小组会议，地点在上海），这是一个非常清晰的指令。\n\n**作为搭配生成专家，我现在的状态是：**\n我已准备就绪，随时可以工作。我的工作流程是：\n1.  **接收输入**：等待服装匹配Agent根据您的场景和天气，从您的库存中筛选出最合适的衣物清单。\n2.  **创意生成**：基于这份清单，我将运用我的专业知识，从**颜色协调、风格统一、功能适用性**三个核心维度出发，为您生成多套完整的搭配方案。\n3.  **输出报告**：我将以清晰、美观的格式，为您呈现包含详细说明、穿搭建议和注意事项的完整穿搭推荐报告。\n\n**举个具体例子：**\n假设服装匹配Agent提供给我的清单是：\n*   **上装**：Nike黑色圆领长袖T恤（基础商务系列）、Nike深灰色连帽卫衣（运动休闲系列）\n*   **下装**：Nike黑色修身运动长裤（基础商务系列）、Nike深灰色束脚运动裤（运动休闲系列）\n*   **外套**：Nike黑色轻薄运动夹克（基础商务系列）\n*   **鞋履**：Nike Air Max 270 React (黑色/白色)\n*   **配饰**：（假设库存有）Nike黑色运动袜、简约黑色皮带\n\n**那么，我将为您生成如下方案：**\n\n---\n\n### **【穿搭推荐报告】**\n**场景**：商务休闲（内部会议） | **地点**：上海 | **天气参考**：室内恒温，清爽干练为主\n\n#### **方案一：正式方案（推荐首选）**\n**风格**：智能运动风 / 商务休闲\n**搭配思路**：用“基础商务”系列单品构建正式感，通过鞋款和细节融入休闲与活力，符合现代办公环境。\n*   **上衣**：Nike黑色圆领长袖T恤（作为内搭打底，简洁利落）\n*   **下装**：Nike黑色修身运动长裤（版型利落，避免过于松垮的运动感）\n*   **外套**：Nike黑色轻薄运动夹克（整套黑色的核心，材质挺括，可随时穿脱应对空调环境）\n*   **鞋履**：Nike Air Max 270 React (黑色/白色)（选择黑色部分为主的款式，与全身色调呼应，提升整体感）\n*   **配饰**：简约黑色皮带（如果裤子有裤袢，强烈建议使用，能瞬间提升精神度和正式感）、黑色运动袜\n*   **穿搭建议**：\n    *   **层叠技巧**：将T恤整齐地塞进裤腰，系上皮带，这是提升商务感的关键一步。\n    *   **细节处理**：夹克拉链可拉开，露出内搭的T恤，形成层次。保持衣物平整无褶皱。\n    *   **备用方案**：若觉得全黑过于沉闷，可将内搭T恤更换为浅灰色（如有），或在配饰上使用一块银色或深蓝色手表提亮。\n\n#### **方案二：休闲方案（舒适之选）**\n**风格**：舒适休闲风\n**搭配思路**：突出舒适与轻松感，适合会议氛围较为轻松或会后有休闲安排的情况。\n*   **上衣**：Nike深灰色连帽卫衣（单穿，提供充足的舒适度和亲和力）\n*   **下装**：Nike深灰色束脚运动裤（与卫衣同色系，打造套装般的协调感）\n*   **外套**：Nike黑色轻薄运动夹克（可穿可搭在肩上或系在腰间，增加造型层次和功能性）\n*   **鞋履**：Nike Air Max 270 React (黑色/白色)（完美匹配运动休闲风格）\n*   **配饰**：黑色运动袜\n*   **穿搭建议**：\n    *   **风格统一**：卫衣与束脚裤的“运动套装”式搭配本身就很完整，无需过多修饰。\n    *   **外套运用**：将黑色夹克随意披上，可以平衡全身的灰色调，增加一点稳重感。\n    *   **注意事项**：确保卫衣和运动裤干净整洁，无起球或明显污渍，以维持得体的外观。\n\n#### **通用注意事项与保养建议**\n1.  **颜色协调**：您的库存以黑、深灰、白为主，属于经典中性色，互相搭配几乎不会出错。遵循“同色系深浅搭配”或“中性色互搭”原则即可。\n2.  **风格平衡**：想增加商务感，就多使用“基础商务”系列单品（如修身长裤、圆领T恤、夹克），并注意整理仪容（如塞衣角）。想保持休闲感，就使用“运动休闲”系列单品（如卫衣、束脚裤）。\n3.  **鞋履是关键**：Air Max 270 React是一款风格偏休闲运动的鞋款。在商务休闲场合，尽量保持其干净，并选择与裤子颜色接近的袜子，有助于视觉延伸。\n4.  **保养提示**：\n    *   运动夹克和修身长裤建议悬挂存放，避免膝盖和肘部起包。\n    *   深色衣物，尤其是黑色，洗涤时建议反面清洗，并使用冷水，以减缓褪色。\n    *   所有衣物在穿着前，检查是否有线头或粘毛，用粘毛器简单处理，质感立刻升级。\n\n---\n\n**请您将具体的出行场景告知服装匹配Agent。一旦它提供给我筛选后的服装列表，我将立刻为您启动搭配方案生成流程，打造专属于您的时尚实用的穿搭方案！**', NULL, '2026-02-04 07:46:17', '2026-02-04 07:47:57'),
                                                                                                                                                                                                                        ('2018955861459927040', '2026-02-04 07:52:42', '2026-02-04 07:55:13', 0, 0, '2018297740714528768', '2018951029852393472', 'SUCCESS', '我都有哪些衣服', '好的，完全理解！为了给您提供最精准、最实用的搭配方案，我需要扮演好“搭配生成专家”的角色，从您的衣橱数据库中智能筛选，并为您生成多套场景化方案。\n\n**现在，请您告诉我本次搭配的具体需求：**\n\n1.  **核心场景：** 您即将面临的主要场合是什么？（例如：重要商务会议、周五便装日、周末郊游、朋友生日派对、健身训练等）\n2.  **风格偏好：** 您希望呈现怎样的整体风格？（例如：干练专业、舒适休闲、时尚潮流、温柔知性、运动活力等）\n3.  **天气/季节：** 出行时的天气和温度如何？（例如：春秋微凉、夏季炎热、冬季寒冷、室内恒温等）\n4.  **特别要求：** 是否有需要特别注意的事项？（例如：需要长时间步行、有晚餐安排、希望突出某件单品等）\n\n**请提供以上信息，我将立即执行以下流程：**\n1.  **场景分析：** 解析您的需求，确定穿搭的正式度、功能性和风格基调。\n2.  **数据库调用：** 从您的服装列表中，智能筛选出符合场景的所有单品。\n3.  **方案生成：** 结合色彩、廓形、材质，为您构建**2-3套**完整搭配（包含正式首选、休闲备选等）。\n4.  **报告输出：** 提供每套方案的详细单品列表、搭配逻辑、穿搭技巧及注意事项。\n\n**示例：**\n*   **如果您说：** “明天有个客户提案会，希望看起来专业又有点亲和力，办公室空调比较冷。”\n*   **我将生成：** 一套以西装/衬衫为核心，搭配针织内搭和得体裤装/半裙的“专业亲和”方案，并附上室内外温差应对技巧。\n\n请随时告诉我您的具体计划，我随时准备为您服务！', NULL, '2026-02-04 07:52:42', '2026-02-04 07:55:13'),
                                                                                                                                                                                                                        ('2019240749694169088', '2026-02-05 02:44:45', '2026-02-05 02:44:46', 0, 0, '2018297740714528768', '2', 'SUCCESS', '我今天去深圳 穿什么', '["2018943862952857600", "2018943455459446784", "2018943795953045504"]', NULL, '2026-02-05 02:44:45', '2026-02-05 02:44:46'),
                                                                                                                                                                                                                        ('2019245135057108992', '2026-02-05 03:02:11', '2026-02-05 03:02:14', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943862952857600", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:02:11', '2026-02-05 03:02:14'),
                                                                                                                                                                                                                        ('2019245512787738624', '2026-02-05 03:03:41', '2026-02-05 03:03:42', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:03:41', '2026-02-05 03:03:42'),
                                                                                                                                                                                                                        ('2019249219088662528', '2026-02-05 03:18:24', '2026-02-05 03:18:25', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943401151598592", "2018943744912560128", "2018943556999352320"]', NULL, '2026-02-05 03:18:24', '2026-02-05 03:18:25'),
                                                                                                                                                                                                                        ('2019249545942384640', '2026-02-05 03:19:42', '2026-02-05 03:19:43', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943401151598592", "2018943744912560128", "2018943556999352320"]', NULL, '2026-02-05 03:19:42', '2026-02-05 03:19:43'),
                                                                                                                                                                                                                        ('2019250187641536512', '2026-02-05 03:22:15', '2026-02-05 03:22:16', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943401151598592", "2018943744912560128", "2018943556999352320"]', NULL, '2026-02-05 03:22:15', '2026-02-05 03:22:16'),
                                                                                                                                                                                                                        ('2019250283040980992', '2026-02-05 03:22:38', '2026-02-05 03:22:39', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320", "2018943237280141312"]', NULL, '2026-02-05 03:22:38', '2026-02-05 03:22:39'),
                                                                                                                                                                                                                        ('2019250925214089216', '2026-02-05 03:25:11', '2026-02-05 03:25:12', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:25:11', '2026-02-05 03:25:12'),
                                                                                                                                                                                                                        ('2019250983607189504', '2026-02-05 03:25:25', '2026-02-05 03:25:26', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:25:25', '2026-02-05 03:25:26'),
                                                                                                                                                                                                                        ('2019251218702123008', '2026-02-05 03:26:21', '2026-02-05 03:26:22', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:26:21', '2026-02-05 03:26:22'),
                                                                                                                                                                                                                        ('2019254042437599232', '2026-02-05 03:37:34', '2026-02-05 03:37:35', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:37:34', '2026-02-05 03:37:35'),
                                                                                                                                                                                                                        ('2019255268524933120', '2026-02-05 03:42:27', '2026-02-05 03:42:28', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:42:27', '2026-02-05 03:42:28'),
                                                                                                                                                                                                                        ('2019255819933302784', '2026-02-05 03:44:38', '2026-02-05 03:44:39', 0, 0, '2018297740714528768', '2', 'SUCCESS', '周末去深圳玩', '["2018943145387134976", "2018943455459446784", "2018943556999352320"]', NULL, '2026-02-05 03:44:38', '2026-02-05 03:44:39');
/*!40000 ALTER TABLE `t_ai_workflow_execution` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_dict 结构
CREATE TABLE IF NOT EXISTS `t_dict` (
                                        `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `type_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型编码',
    `type_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型名称',
    `dict_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典编码',
    `dict_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
    `dict_value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典值',
    `sort` int DEFAULT '0' COMMENT '排序',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_type_dict_code` (`type_code`,`dict_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典表';

-- 正在导出表  kaleido_0.t_dict 的数据：~41 rows (大约)
DELETE FROM `t_dict`;
/*!40000 ALTER TABLE `t_dict` DISABLE KEYS */;
INSERT INTO `t_dict` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `type_code`, `type_name`, `dict_code`, `dict_name`, `dict_value`, `sort`) VALUES
                                                                                                                                                                 ('07434c19-f12b-4493-b1df-456a632310d9', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'BROWN', '棕色', '#A52A2A', 10),
                                                                                                                                                                 ('16938b19-d4ba-4acf-b606-b227862ae7cb', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'GRAY', '灰色', '#808080', 9),
                                                                                                                                                                 ('18124a76-158d-48eb-a0ff-d1a2399e1283', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'COLOR', '颜色', 'ORANGE', '橙色', '#FFA500', 2),
                                                                                                                                                                 ('1e954af9-23ae-47e5-b4c4-83bca0dda529', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'WHITE', '白色', '#FFFFFF', 8),
                                                                                                                                                                 ('1ea320c1-cc86-4d81-9a87-785ef6b61cbf', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SHORTS', '短裤', NULL, 8),
                                                                                                                                                                 ('2', '2026-01-19 15:16:40', '2026-01-19 15:17:09', 0, 0, 'COIN_CONFIG', '金币服务配置', 'OUTFIT_RECOMMEND', '搭配推荐消耗', '5', 40),
                                                                                                                                                                 ('2010911697145053184', '2026-01-13 11:08:04', '2026-01-13 11:08:04', 0, 0, 'NOTICE_TEMPLATE', '通知模板类型', 'VERIFY_CODE_TEMPLATE_USER', '用户短信验证码通知模板', 'VERIFY_CODE_USER', 1),
                                                                                                                                                                 ('2010912019968049152', '2026-01-13 11:09:21', '2026-01-13 11:09:21', 0, 0, 'NOTICE_TEMPLATE', '通知模板类型', 'VERIFY_CODE_TEMPLATE_ADMIN', '管理后台短信验证码通知模板', 'VERIFY_CODE_ADMIN', 1),
                                                                                                                                                                 ('2010912019968049153', '2026-01-15 15:44:37', '2026-01-15 15:45:21', 0, 0, 'TAG_TYPE', '标签类型', 'CLOTHING', '服装', NULL, 1),
                                                                                                                                                                 ('2010912019968049154', '2026-01-15 15:44:37', '2026-01-15 15:45:24', 0, 0, 'TAG_TYPE', '标签类型', 'OUTFIT', '搭配', NULL, 2),
                                                                                                                                                                 ('2010912019968049155', '2026-01-19 15:16:39', '2026-01-19 15:16:55', 0, 0, 'COIN_CONFIG', '金币服务配置', 'INITIAL_BALANCE', '初始余额', '100', 10),
                                                                                                                                                                 ('2010912019968049156', '2026-01-19 15:16:40', '2026-01-19 15:17:01', 0, 0, 'COIN_CONFIG', '金币服务配置', 'INVITE_REWARD', '邀请奖励', '100', 20),
                                                                                                                                                                 ('2010912019968049157', '2026-01-19 15:16:40', '2026-01-19 15:17:05', 0, 0, 'COIN_CONFIG', '金币服务配置', 'LOCATION_COST', '位置创建消耗', '50', 30),
                                                                                                                                                                 ('2010912019968049158', '2026-01-19 15:16:40', '2026-01-19 15:17:09', 0, 0, 'COIN_CONFIG', '金币服务配置', 'OUTFIT_COST', '搭配创建消耗', '80', 40),
                                                                                                                                                                 ('217ad027-d4e8-4bec-a3c4-4e9782058b61', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'BLACK', '黑色', '#000000', 7),
                                                                                                                                                                 ('25a24393-0e8e-4d4e-921a-b1515ac341a2', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SOCKS', '袜子', NULL, 13),
                                                                                                                                                                 ('290420b9-64b5-4634-9baa-5a26e208efd6', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SKIRT', '裙子', NULL, 6),
                                                                                                                                                                 ('2e40127b-2efc-4f6d-87df-61ffd79cb242', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'SEASON', '季节', 'SUMMER', '夏季', NULL, 2),
                                                                                                                                                                 ('3521a3f2-ae87-4e2c-b82c-b12f5e4726d7', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'PAJAMAS', '睡衣', NULL, 11),
                                                                                                                                                                 ('45f543df-0f08-4ecf-905c-ed7438b58afb', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'PANTS', '裤子', NULL, 3),
                                                                                                                                                                 ('5171cebd-133c-4a91-ba9d-e5b39944dfb2', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SWEATER', '毛衣', NULL, 5),
                                                                                                                                                                 ('548b5baa-bdad-42d6-8fa5-733075fe6902', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'SEASON', '季节', 'SPRING', '春季', NULL, 1),
                                                                                                                                                                 ('5bd78600-9af3-4ad0-850a-b7025b632abd', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'PURPLE', '紫色', '#800080', 6),
                                                                                                                                                                 ('660e8523-54b8-4eb7-8e26-b7d165cde2f3', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'SEASON', '季节', 'AUTUMN', '秋季', NULL, 3),
                                                                                                                                                                 ('6d0a2b17-fe15-458f-8032-674a68356247', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'BLUE', '蓝色', '#0000FF', 5),
                                                                                                                                                                 ('6e814050-4a44-4e6b-82a4-6c0d63fe2836', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SHOES', '鞋子', NULL, 15),
                                                                                                                                                                 ('70565b07-d58d-4a7a-a132-ac1111f6d46b', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'SEASON', '季节', 'WINTER', '冬季', NULL, 4),
                                                                                                                                                                 ('765726f8-d2ee-462c-b45d-cbbdecf17f19', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'GREEN', '绿色', '#00FF00', 4),
                                                                                                                                                                 ('76f6bafa-ea11-43ee-a54b-47367ae6095d', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'COAT', '外套', NULL, 4),
                                                                                                                                                                 ('8647adcb-a2a9-423d-bdb5-04ca35c2c8e1', '2026-01-22 16:17:17', '2026-01-22 16:17:17', 0, 0, 'CLOTHING_TYPE', '服装类型', 'TSHIRT', 'T恤', NULL, 1),
                                                                                                                                                                 ('980c29f4-a508-48db-b2fd-033eda53bf0b', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SHIRT', '衬衫', NULL, 2),
                                                                                                                                                                 ('9a8af17c-c66b-48c3-bf01-a95852a45407', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'HAT', '帽子', NULL, 14),
                                                                                                                                                                 ('b5199205-25a2-43c5-a05e-9cb12a58486a', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'YELLOW', '黄色', '#FFFF00', 3),
                                                                                                                                                                 ('b7a93631-446f-4e0b-a610-ae8eeaae4879', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'COLOR', '颜色', 'RED', '红色', '#FF0000', 1),
                                                                                                                                                                 ('ca96f6c4-4db6-4ce6-89b4-2e451142a06b', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'JEANS', '牛仔裤', NULL, 9),
                                                                                                                                                                 ('cc5bc3a1-4b74-48d2-9749-bbcb10f7496f', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'GOLD', '金色', '#FFD700', 13),
                                                                                                                                                                 ('d92585eb-73de-4886-8630-11aa8bfd75c0', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'DRESS', '连衣裙', NULL, 7),
                                                                                                                                                                 ('dd0cf331-50b2-45e5-81dd-0f4329258980', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'UNDERWEAR', '内衣', NULL, 12),
                                                                                                                                                                 ('e9ab540a-f01e-4042-bc43-6082840e4bdd', '2026-01-22 16:17:18', '2026-01-22 16:17:18', 0, 0, 'CLOTHING_TYPE', '服装类型', 'SPORTSWEAR', '运动服', NULL, 10),
                                                                                                                                                                 ('ed61cf71-ce8e-4b8a-a21d-651c67d0a268', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'PINK', '粉色', '#FFC0CB', 11),
                                                                                                                                                                 ('f98a3df8-bcb8-4c5c-8ca7-da9d78c9bc10', '2026-01-22 16:17:19', '2026-01-22 16:17:19', 0, 0, 'COLOR', '颜色', 'SILVER', '银色', '#C0C0C0', 12);
/*!40000 ALTER TABLE `t_dict` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_notice 结构
CREATE TABLE IF NOT EXISTS `t_notice` (
                                          `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `target_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目标地址',
    `target_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推送目标类型 普通用户 管理员',
    `notice_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知类型  SMS  EMAIL WECHAT',
    `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知状态',
    `business_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务类型',
    `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知内容',
    `result_message` text COLLATE utf8mb4_unicode_ci COMMENT '发送结果信息',
    `sent_at` datetime DEFAULT NULL COMMENT '发送时间',
    `retry_num` int DEFAULT '0' COMMENT '当前重试次数',
    `next_retry_at` datetime DEFAULT NULL COMMENT '下次重试时间',
    `max_retry_num` int DEFAULT '3' COMMENT '最大重试次数',
    PRIMARY KEY (`id`),
    KEY `idx_status` (`status`),
    KEY `idx_business_type` (`business_type`),
    KEY `target_type` (`target_type`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- 正在导出表  kaleido_0.t_notice 的数据：~73 rows (大约)
DELETE FROM `t_notice`;
/*!40000 ALTER TABLE `t_notice` DISABLE KEYS */;
INSERT INTO `t_notice` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `target_address`, `target_type`, `notice_type`, `status`, `business_type`, `content`, `result_message`, `sent_at`, `retry_num`, `next_retry_at`, `max_retry_num`) VALUES
                                                                                                                                                                                                                                                           ('2014618537909506048', '2026-01-23 08:37:44', '2026-01-23 08:37:44', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[043927] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 08:37:44', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014618615319580672', '2026-01-23 08:38:02', '2026-01-23 08:38:02', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[511064] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 08:38:02', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014621539428945920', '2026-01-23 08:49:40', '2026-01-23 08:49:40', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[610337] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 08:49:40', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014623008941101056', '2026-01-23 08:55:30', '2026-01-23 08:55:30', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[418881] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 08:55:30', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014624538486329344', '2026-01-23 09:01:35', '2026-01-23 09:01:35', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[241361] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:01:35', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014626668219682816', '2026-01-23 09:10:02', '2026-01-23 09:10:02', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[826066] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:10:02', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014628337284239360', '2026-01-23 09:16:40', '2026-01-23 09:16:40', 0, 0, '13061596465', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[372908] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:16:40', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014628501080199168', '2026-01-23 09:17:19', '2026-01-23 09:17:19', 0, 0, '13061596466', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[951924] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:17:19', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014628718450003968', '2026-01-23 09:18:11', '2026-01-23 09:18:11', 0, 0, '13061596466', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[722398] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:18:11', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014628758165868544', '2026-01-23 09:18:21', '2026-01-23 09:18:21', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[125240] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 09:18:21', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642152390483968', '2026-01-23 10:11:34', '2026-01-23 10:11:34', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[365460] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:34', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642161743781888', '2026-01-23 10:11:36', '2026-01-23 10:11:36', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[555612] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:36', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642175341715456', '2026-01-23 10:11:40', '2026-01-23 10:11:40', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[714912] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:40', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642179628294144', '2026-01-23 10:11:41', '2026-01-23 10:11:41', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[771805] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:41', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642185298993152', '2026-01-23 10:11:42', '2026-01-23 10:11:42', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[617116] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:42', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642189786898432', '2026-01-23 10:11:43', '2026-01-23 10:11:43', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[751746] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:43', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642192366395392', '2026-01-23 10:11:44', '2026-01-23 10:11:44', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[865911] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:44', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642200205549568', '2026-01-23 10:11:45', '2026-01-23 10:11:45', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[226628] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:11:45', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2014642304849240064', '2026-01-23 10:12:10', '2026-01-23 10:12:10', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[918445] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-23 10:12:10', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015601273696649216', '2026-01-26 01:42:46', '2026-01-26 01:42:46', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[716636] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:42:46', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015601278406852608', '2026-01-26 01:42:47', '2026-01-26 01:42:47', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[916425] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:42:47', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015601282185920512', '2026-01-26 01:42:48', '2026-01-26 01:42:48', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[671602] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:42:48', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015601285436506112', '2026-01-26 01:42:49', '2026-01-26 01:42:49', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[968017] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:42:49', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015603976208351232', '2026-01-26 01:53:31', '2026-01-26 01:53:31', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[473952] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:53:31', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015603979844812800', '2026-01-26 01:53:32', '2026-01-26 01:53:32', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[513260] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:53:32', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015603986329206784', '2026-01-26 01:53:33', '2026-01-26 01:53:33', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[556841] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:53:33', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604177413308416', '2026-01-26 01:54:19', '2026-01-26 01:54:19', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[774127] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:19', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604181121073152', '2026-01-26 01:54:20', '2026-01-26 01:54:20', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[585559] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:20', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604188209446912', '2026-01-26 01:54:21', '2026-01-26 01:54:21', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[772960] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:21', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604192999342080', '2026-01-26 01:54:22', '2026-01-26 01:54:22', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[958585] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:22', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604196405116928', '2026-01-26 01:54:23', '2026-01-26 01:54:23', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[147511] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:23', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015604200209350656', '2026-01-26 01:54:24', '2026-01-26 01:54:24', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[802475] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 01:54:24', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620254696439808', '2026-01-26 02:58:12', '2026-01-26 02:58:12', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[483557] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:12', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620267367432192', '2026-01-26 02:58:15', '2026-01-26 02:58:15', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[291792] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:15', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620322115682304', '2026-01-26 02:58:28', '2026-01-26 02:58:28', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[271801] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:28', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620397625737216', '2026-01-26 02:58:46', '2026-01-26 02:58:46', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[018238] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:46', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620404508590080', '2026-01-26 02:58:48', '2026-01-26 02:58:48', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[892726] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:48', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620407562043392', '2026-01-26 02:58:48', '2026-01-26 02:58:48', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[720171] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:48', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620409722109952', '2026-01-26 02:58:49', '2026-01-26 02:58:49', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[664807] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:49', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015620412133834752', '2026-01-26 02:58:49', '2026-01-26 02:58:49', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[948091] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 02:58:49', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621211001946112', '2026-01-26 03:02:00', '2026-01-26 03:02:00', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[757470] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:00', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621212524478464', '2026-01-26 03:02:00', '2026-01-26 03:02:00', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[008579] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:00', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621214126702592', '2026-01-26 03:02:01', '2026-01-26 03:02:01', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[567576] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:01', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621217318567936', '2026-01-26 03:02:01', '2026-01-26 03:02:01', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[020567] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:01', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621219478634496', '2026-01-26 03:02:02', '2026-01-26 03:02:02', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[287294] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:02', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621221689032704', '2026-01-26 03:02:02', '2026-01-26 03:02:02', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[368137] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:02', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621223857487872', '2026-01-26 03:02:03', '2026-01-26 03:02:03', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[011062] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:03', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621226038525952', '2026-01-26 03:02:03', '2026-01-26 03:02:03', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[755839] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:03', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015621228869681152', '2026-01-26 03:02:04', '2026-01-26 03:02:04', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[094222] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:02:04', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015623338717839360', '2026-01-26 03:10:27', '2026-01-26 03:10:27', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[541974] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:10:27', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015623343029583872', '2026-01-26 03:10:28', '2026-01-26 03:10:28', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[376395] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:10:28', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015624150701539328', '2026-01-26 03:13:41', '2026-01-26 03:13:41', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[828797] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:13:41', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015624154505773056', '2026-01-26 03:13:42', '2026-01-26 03:13:42', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[125059] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:13:42', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015624157479534592', '2026-01-26 03:13:42', '2026-01-26 03:13:42', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[586261] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:13:42', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015624159618629632', '2026-01-26 03:13:43', '2026-01-26 03:13:43', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[282182] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:13:43', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015624161266991104', '2026-01-26 03:13:43', '2026-01-26 03:13:43', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[332144] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:13:43', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635532910133248', '2026-01-26 03:58:54', '2026-01-26 03:58:54', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[148296] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:58:54', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635545576931328', '2026-01-26 03:58:57', '2026-01-26 03:58:57', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[195786] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:58:57', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635600434233344', '2026-01-26 03:59:11', '2026-01-26 03:59:11', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[261225] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:59:11', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635655593525248', '2026-01-26 03:59:24', '2026-01-26 03:59:24', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[332855] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:59:24', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635675252228096', '2026-01-26 03:59:28', '2026-01-26 03:59:28', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[747879] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:59:28', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635679522029568', '2026-01-26 03:59:29', '2026-01-26 03:59:29', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[993994] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:59:29', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015635772484583424', '2026-01-26 03:59:52', '2026-01-26 03:59:52', 0, 0, '13061596467', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[151761] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 03:59:52', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015690479064285184', '2026-01-26 07:37:15', '2026-01-26 07:37:15', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[705120] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 07:37:15', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015690487746494464', '2026-01-26 07:37:17', '2026-01-26 07:37:17', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[646780] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 07:37:17', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015690487855546368', '2026-01-26 07:37:17', '2026-01-26 07:37:17', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[693409] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 07:37:17', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015691782884655104', '2026-01-26 07:42:25', '2026-01-26 07:42:25', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[810201] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 07:42:25', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015694770168627200', '2026-01-26 07:54:18', '2026-01-26 07:54:18', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[530772] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 07:54:18', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2015697297966596096', '2026-01-26 08:04:20', '2026-01-26 08:04:20', 0, 0, '13061516467', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[381660] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-01-26 08:04:20', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2018294862239207424', '2026-02-02 12:06:08', '2026-02-02 12:06:08', 0, 0, '13266668888', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[009162] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-02-02 12:06:08', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2018294862662832128', '2026-02-02 12:06:08', '2026-02-02 12:06:08', 0, 0, '13266668888', 'ADMIN', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[500278] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-02-02 12:06:08', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2018295433088176128', '2026-02-02 12:08:24', '2026-02-02 12:08:24', 0, 0, '13266668888', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[301818] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-02-02 12:08:24', 0, NULL, 3),
                                                                                                                                                                                                                                                           ('2018297691628605440', '2026-02-02 12:17:23', '2026-02-02 12:17:23', 0, 0, '13266668889', 'USER', 'SMS', 'SUCCESS', 'VERIFY_CODE', '【kaleido】 您的短信验证码为：[788106] 有效期5分钟，请勿泄露给他人', '{"code":"SUCCESS","msg":"SUCCESS","success":true}', '2026-02-02 12:17:23', 0, NULL, 3);
/*!40000 ALTER TABLE `t_notice` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_notice_template 结构
CREATE TABLE IF NOT EXISTS `t_notice_template` (
                                                   `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `template_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
    `template_content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
    `template_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模板唯一编码',
    PRIMARY KEY (`id`),
    UNIQUE KEY `template_code` (`template_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知模板表';

-- 正在导出表  kaleido_0.t_notice_template 的数据：~4 rows (大约)
DELETE FROM `t_notice_template`;
/*!40000 ALTER TABLE `t_notice_template` DISABLE KEYS */;
INSERT INTO `t_notice_template` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `template_name`, `template_content`, `template_code`) VALUES
                                                                                                                                                        ('1', '2026-01-13 02:49:09', '2026-01-13 02:51:23', 0, 0, '用户短信验证码', '【kaleido】 您的短信验证码为：{} 有效期5分钟，请勿泄露给他人', 'VERIFY_CODE_USER'),
                                                                                                                                                        ('2', '2026-01-13 02:49:09', '2026-01-13 02:52:19', 0, 0, '管理后台短信验证码', '【kaleido】 您的短信验证码为：{} 有效期5分钟，请勿泄露给他人', 'VERIFY_CODE_ADMIN'),
                                                                                                                                                        ('2013913851363524608', '2026-01-21 17:57:34', '2026-01-21 17:57:34', 0, 0, 'mariano.yost', '755awo', '18962'),
                                                                                                                                                        ('2014222650997673984', '2026-01-22 14:24:37', '2026-01-22 14:24:37', 0, 0, 'roy.ohara', 'j50dj2', '53794');
/*!40000 ALTER TABLE `t_notice_template` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_permission 结构
CREATE TABLE IF NOT EXISTS `t_permission` (
                                              `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限编码，如：user:view',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
    `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限类型：DIRECTORY-目录 MENU-菜单 BUTTON-按钮',
    `parent_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父权限ID',
    `sort` int DEFAULT '0' COMMENT '排序',
    `icon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
    `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前端路由路径',
    `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前端组件路径',
    `is_hidden` tinyint DEFAULT '0' COMMENT '是否隐藏：0-否 1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_parent_id` (`parent_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 正在导出表  kaleido_0.t_permission 的数据：~0 rows (大约)
DELETE FROM `t_permission`;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_recommend_record_0 结构
CREATE TABLE IF NOT EXISTS `t_recommend_record_0` (
                                                      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户输入的推荐需求提示词',
    `outfit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成的穿搭ID（关联t_wardrobe_outfit）',
    `execution_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'AI工作流执行记录id',
    `status` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推荐生成状态 PROCESSING-处理中 COMPLETED-已完成 FAILED-失败',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_outfit_id` (`outfit_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `execution_id` (`execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐记录表';

-- 正在导出表  kaleido_0.t_recommend_record_0 的数据：~0 rows (大约)
DELETE FROM `t_recommend_record_0`;
/*!40000 ALTER TABLE `t_recommend_record_0` DISABLE KEYS */;
INSERT INTO `t_recommend_record_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `prompt`, `outfit_id`, `execution_id`, `status`) VALUES
                                                                                                                                                                 ('2019245135791067136', '2026-02-05 03:02:11', '2026-02-05 03:02:11', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019245135057108992', 'PROCESSING'),
                                                                                                                                                                 ('2019245512917716992', '2026-02-05 03:03:41', '2026-02-05 03:03:41', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019245512787738624', 'PROCESSING'),
                                                                                                                                                                 ('2019249219478695936', '2026-02-05 03:18:24', '2026-02-05 03:18:24', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019249219088662528', 'PROCESSING'),
                                                                                                                                                                 ('2019249546013650944', '2026-02-05 03:19:42', '2026-02-05 03:19:42', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019249545942384640', 'PROCESSING'),
                                                                                                                                                                 ('2019250187725385728', '2026-02-05 03:22:15', '2026-02-05 03:22:15', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019250187641536512', 'PROCESSING'),
                                                                                                                                                                 ('2019250283091275776', '2026-02-05 03:22:38', '2026-02-05 03:22:38', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019250283040980992', 'PROCESSING'),
                                                                                                                                                                 ('2019250925272772608', '2026-02-05 03:25:11', '2026-02-05 03:25:11', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019250925214089216', 'PROCESSING'),
                                                                                                                                                                 ('2019250983720398848', '2026-02-05 03:25:25', '2026-02-05 03:25:25', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019250983607189504', 'PROCESSING'),
                                                                                                                                                                 ('2019251218760806400', '2026-02-05 03:26:21', '2026-02-05 03:26:21', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019251218702123008', 'PROCESSING'),
                                                                                                                                                                 ('2019254042873774080', '2026-02-05 03:37:34', '2026-02-05 03:37:36', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019254042437599232', 'PROCESSING'),
                                                                                                                                                                 ('2019255268579426304', '2026-02-05 03:42:27', '2026-02-05 03:43:42', 0, 0, '2018297740714528768', '周末去深圳玩', NULL, '2019255268524933120', 'PROCESSING'),
                                                                                                                                                                 ('2019255820277207040', '2026-02-05 03:44:38', '2026-02-05 03:44:43', 0, 0, '2018297740714528768', '周末去深圳玩', '2019255825230639104', '2019255819933302784', 'COMPLETED');
/*!40000 ALTER TABLE `t_recommend_record_0` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_recommend_record_1 结构
CREATE TABLE IF NOT EXISTS `t_recommend_record_1` (
                                                      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `execution_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行记录id',
    `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户输入的推荐需求提示词',
    `outfit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成的穿搭ID（关联t_wardrobe_outfit）',
    `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推荐生成状态 PROCESSING-处理中 COMPLETED-已完成 FAILED-失败',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_outfit_id` (`outfit_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `execution_id` (`execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐记录表';

-- 正在导出表  kaleido_0.t_recommend_record_1 的数据：~0 rows (大约)
DELETE FROM `t_recommend_record_1`;
/*!40000 ALTER TABLE `t_recommend_record_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_recommend_record_1` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_role 结构
CREATE TABLE IF NOT EXISTS `t_role` (
                                        `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色编码',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
    `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 正在导出表  kaleido_0.t_role 的数据：~1 rows (大约)
DELETE FROM `t_role`;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`id`, `code`, `deleted`, `lock_version`, `name`, `description`, `created_at`, `updated_at`) VALUES
    ('2014616286302175232', 'SUPER_ADMIN', 0, 0, '超级管理员', '超级管理员', '2026-01-23 08:28:47', '2026-01-23 08:28:47');
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_role_permission 结构
CREATE TABLE IF NOT EXISTS `t_role_permission` (
                                                   `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `role_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
    `permission_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 正在导出表  kaleido_0.t_role_permission 的数据：~0 rows (大约)
DELETE FROM `t_role_permission`;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_tag 结构
CREATE TABLE IF NOT EXISTS `t_tag` (
                                       `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签ID（雪花算法）',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
    `type_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签类型编码（关联字典表t_dict.dict_code，字典类型为TAG_TYPE）',
    `color` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签颜色',
    `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签描述',
    `usage_count` int NOT NULL DEFAULT '0' COMMENT '使用次数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_tag_type_name` (`user_id`,`type_code`,`name`) COMMENT '用户下同类型标签名称唯一',
    KEY `idx_user_type` (`user_id`,`type_code`) COMMENT '用户标签类型查询索引',
    KEY `idx_usage_count` (`usage_count`) COMMENT '使用次数查询索引'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 正在导出表  kaleido_0.t_tag 的数据：~0 rows (大约)
DELETE FROM `t_tag`;
/*!40000 ALTER TABLE `t_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_tag` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_tag_relation 结构
CREATE TABLE IF NOT EXISTS `t_tag_relation` (
                                                `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联ID（雪花算法）',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `tag_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签ID',
    `entity_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '实体ID',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tag_entity` (`tag_id`,`entity_id`) COMMENT '防止重复关联',
    KEY `idx_tag_id` (`tag_id`) COMMENT '标签ID查询索引',
    KEY `idx_entity_id` (`entity_id`) COMMENT '实体ID查询索引',
    KEY `idx_user_id` (`user_id`) COMMENT '用户ID查询索引',
    KEY `idx_tag_user` (`tag_id`,`user_id`) COMMENT '标签和用户联合查询索引',
    KEY `idx_entity_user` (`entity_id`,`user_id`) COMMENT '实体和用户联合查询索引'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签关联表';

-- 正在导出表  kaleido_0.t_tag_relation 的数据：~0 rows (大约)
DELETE FROM `t_tag_relation`;
/*!40000 ALTER TABLE `t_tag_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_tag_relation` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_0 结构
CREATE TABLE IF NOT EXISTS `t_user_0` (
                                          `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `nick_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 NORMAL FROZEN DELETED',
    `invite_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请码',
    `inviter_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请人ID',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_invite_code` (`invite_code`),
    KEY `idx_inviter_id` (`inviter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 正在导出表  kaleido_0.t_user_0 的数据：~1 rows (大约)
DELETE FROM `t_user_0`;
/*!40000 ALTER TABLE `t_user_0` DISABLE KEYS */;
INSERT INTO `t_user_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `mobile`, `nick_name`, `status`, `invite_code`, `inviter_id`, `last_login_time`, `avatar`, `gender`) VALUES
    ('2018297740714528768', '2026-02-02 12:17:34', '2026-02-02 12:19:38', 0, 0, '13266668889', '用户8889', 'ACTIVE', 'ZNXAR6', '2014628374013755392', '2026-02-02 12:19:38', NULL, NULL);
/*!40000 ALTER TABLE `t_user_0` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_1 结构
CREATE TABLE IF NOT EXISTS `t_user_1` (
                                          `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `nick_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 NORMAL FROZEN DELETED',
    `invite_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请码',
    `inviter_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请人ID',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_invite_code` (`invite_code`),
    KEY `idx_inviter_id` (`inviter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 正在导出表  kaleido_0.t_user_1 的数据：~2 rows (大约)
DELETE FROM `t_user_1`;
/*!40000 ALTER TABLE `t_user_1` DISABLE KEYS */;
INSERT INTO `t_user_1` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `mobile`, `nick_name`, `status`, `invite_code`, `inviter_id`, `last_login_time`, `avatar`, `gender`) VALUES
                                                                                                                                                                                              ('2014628374013755392', '2026-01-23 09:16:49', '2026-01-23 09:16:49', 0, 0, '13061596465', '用户6465', 'ACTIVE', 'KZAC85', NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('2014628546198323200', '2026-01-23 09:17:30', '2026-01-23 09:17:30', 0, 0, '13061596466', '用户6466', 'ACTIVE', 'VGWVCK', '2014628374013755392', NULL, NULL, NULL);
/*!40000 ALTER TABLE `t_user_1` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_coin_account 结构
CREATE TABLE IF NOT EXISTS `t_user_coin_account` (
                                                     `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `balance` bigint NOT NULL DEFAULT '0' COMMENT '金币余额',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户金币账户表';

-- 正在导出表  kaleido_0.t_user_coin_account 的数据：~5 rows (大约)
DELETE FROM `t_user_coin_account`;
/*!40000 ALTER TABLE `t_user_coin_account` DISABLE KEYS */;
INSERT INTO `t_user_coin_account` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `balance`) VALUES
                                                                                                                          ('2014628829104058368', '2026-01-23 09:18:38', '2026-02-02 12:17:35', 0, 0, '2014628374013755392', 200),
                                                                                                                          ('2014628829846450176', '2026-01-23 09:18:38', '2026-01-23 09:18:38', 0, 0, '2014628546198323200', 100),
                                                                                                                          ('2014628830106497024', '2026-01-23 09:18:38', '2026-01-27 09:49:23', 0, 0, '2014628810120708096', 9090),
                                                                                                                          ('2018295476390137856', '2026-02-02 12:08:34', '2026-02-02 12:08:34', 0, 0, '2018295469066899456', 100),
                                                                                                                          ('2018297743658917888', '2026-02-02 12:17:35', '2026-02-05 03:44:39', 0, 0, '2018297740714528768', 9999400);
/*!40000 ALTER TABLE `t_user_coin_account` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_coin_stream 结构
CREATE TABLE IF NOT EXISTS `t_user_coin_stream` (
                                                    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流水类型：INCOME-收入, EXPENSE-支出',
    `amount` bigint NOT NULL COMMENT '变动金额',
    `balance_after` bigint NOT NULL COMMENT '变动后余额',
    `biz_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务类型：INVITE-邀请, LOCATION-位置, OUTFIT-搭配',
    `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务ID',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `biz_type` (`biz_type`,`biz_id`),
    KEY `idx_user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户金币流水表';

-- 正在导出表  kaleido_0.t_user_coin_stream 的数据：~30 rows (大约)
DELETE FROM `t_user_coin_stream`;
/*!40000 ALTER TABLE `t_user_coin_stream` DISABLE KEYS */;
INSERT INTO `t_user_coin_stream` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `type`, `amount`, `balance_after`, `biz_type`, `biz_id`, `remark`) VALUES
                                                                                                                                                                                 ('2014628829116641280', '2026-01-23 09:18:38', '2026-01-23 09:18:38', 0, 0, '2014628374013755392', 'INCOME', 100, 100, 'INITIAL', NULL, '账户初始化'),
                                                                                                                                                                                 ('2014628829846450177', '2026-01-23 09:18:38', '2026-01-23 09:18:38', 0, 0, '2014628546198323200', 'INCOME', 100, 100, 'INITIAL', NULL, '账户初始化'),
                                                                                                                                                                                 ('2014628830106497025', '2026-01-23 09:18:38', '2026-01-23 09:18:38', 0, 0, '2014628810120708096', 'INCOME', 100, 100, 'INITIAL', NULL, '账户初始化'),
                                                                                                                                                                                 ('2016032018684612608', '2026-01-27 06:14:24', '2026-01-27 06:14:24', 0, 0, '2014628810120708096', 'EXPENSE', 5, 95, 'OUTFIT_RECOMMEND', '2016031935612215296', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016032579865710592', '2026-01-27 06:16:38', '2026-01-27 06:16:38', 0, 0, '2014628810120708096', 'EXPENSE', 5, 90, 'OUTFIT_RECOMMEND', '2016032579236552704', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016035700243341312', '2026-01-27 06:29:02', '2026-01-27 06:29:02', 0, 0, '2014628810120708096', 'EXPENSE', 50, 40, 'LOCATION', '2016035668127633408', '创建存储位置消耗'),
                                                                                                                                                                                 ('2016036887541751808', '2026-01-27 06:33:45', '2026-01-27 06:33:45', 0, 0, '2014628810120708096', 'EXPENSE', 50, 9950, 'LOCATION', '2016036887302754304', '创建存储位置消耗'),
                                                                                                                                                                                 ('2016036906470645760', '2026-01-27 06:33:49', '2026-01-27 06:33:49', 0, 0, '2014628810120708096', 'EXPENSE', 50, 9900, 'LOCATION', '2016036906261008384', '创建存储位置消耗'),
                                                                                                                                                                                 ('2016038932789239808', '2026-01-27 06:41:52', '2026-01-27 06:41:52', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9820, 'OUTFIT', '2016038931581358080', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016038941731495936', '2026-01-27 06:41:55', '2026-01-27 06:41:55', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9815, 'OUTFIT_RECOMMEND', '2016038933321908224', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016039413443899392', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9735, 'OUTFIT', '2016039412395397120', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016039423048855552', '2026-01-27 06:43:49', '2026-01-27 06:43:49', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9730, 'OUTFIT_RECOMMEND', '2016039414672822272', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016039485917278208', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9650, 'OUTFIT', '2016039485581807616', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016039486345097216', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9645, 'OUTFIT_RECOMMEND', '2016039486080847872', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016068830249545728', '2026-01-27 08:40:41', '2026-01-27 08:40:41', 0, 0, '2014628810120708096', 'EXPENSE', 50, 9595, 'LOCATION', '2016068827565129728', '创建存储位置消耗'),
                                                                                                                                                                                 ('2016069247222083584', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9515, 'OUTFIT', '2016069246215389184', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016069249180823552', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9510, 'OUTFIT_RECOMMEND', '2016069247813472256', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016085737673523200', '2026-01-27 09:47:52', '2026-01-27 09:47:52', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9430, 'OUTFIT', '2016085736083816448', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016085810352422912', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9350, 'OUTFIT', '2016085809995841536', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016085812302774272', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9345, 'OUTFIT_RECOMMEND', '2016085810885095424', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016085885791174656', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9265, 'OUTFIT', '2016085885468147712', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016085886277713920', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9260, 'OUTFIT_RECOMMEND', '2016085885992497152', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016085891881304064', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9180, 'OUTFIT', '2016085891554082816', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016085892267180032', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9175, 'OUTFIT_RECOMMEND', '2016085892057460736', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2016086121351675904', '2026-01-27 09:49:23', '2026-01-27 09:49:23', 0, 0, '2014628810120708096', 'EXPENSE', 80, 9095, 'OUTFIT', '2016086110802935808', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2016086122102456320', '2026-01-27 09:49:23', '2026-01-27 09:49:23', 0, 0, '2014628810120708096', 'EXPENSE', 5, 9090, 'OUTFIT_RECOMMEND', '2016086121666244608', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2018295476402720768', '2026-02-02 12:08:34', '2026-02-02 12:08:34', 0, 0, '2018295469066899456', 'INCOME', 100, 100, 'INITIAL', NULL, '账户初始化'),
                                                                                                                                                                                 ('2018297743667306496', '2026-02-02 12:17:35', '2026-02-02 12:17:35', 0, 0, '2018297740714528768', 'INCOME', 100, 100, 'INITIAL', NULL, '账户初始化'),
                                                                                                                                                                                 ('2018297744887848960', '2026-02-02 12:17:35', '2026-02-02 12:17:35', 0, 0, '2014628374013755392', 'INCOME', 100, 200, 'INVITE', '2018297740714528768', '邀请新用户注册奖励'),
                                                                                                                                                                                 ('2018299109206859776', '2026-02-02 12:23:01', '2026-02-02 12:23:01', 0, 0, '2018297740714528768', 'EXPENSE', 50, 50, 'LOCATION', '2018299108279853056', '创建存储位置消耗'),
                                                                                                                                                                                 ('2019245138651594752', '2026-02-05 03:02:12', '2026-02-05 03:02:12', 0, 0, '2018297740714528768', 'EXPENSE', 5, 45, 'OUTFIT_RECOMMEND', '2019245135791067136', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019245513500737536', '2026-02-05 03:03:41', '2026-02-05 03:03:41', 0, 0, '2018297740714528768', 'EXPENSE', 5, 40, 'OUTFIT_RECOMMEND', '2019245512917716992', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019249219994599424', '2026-02-05 03:18:25', '2026-02-05 03:18:25', 0, 0, '2018297740714528768', 'EXPENSE', 5, 35, 'OUTFIT_RECOMMEND', '2019249219478695936', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019249546328227840', '2026-02-05 03:19:42', '2026-02-05 03:19:42', 0, 0, '2018297740714528768', 'EXPENSE', 5, 30, 'OUTFIT_RECOMMEND', '2019249546013650944', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019250188035768320', '2026-02-05 03:22:15', '2026-02-05 03:22:15', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999995, 'OUTFIT_RECOMMEND', '2019250187725385728', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019250223200813056', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999915, 'OUTFIT', '2019250222949113856', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019250283397464064', '2026-02-05 03:22:38', '2026-02-05 03:22:38', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999910, 'OUTFIT_RECOMMEND', '2019250283091275776', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019250925578960896', '2026-02-05 03:25:11', '2026-02-05 03:25:11', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999905, 'OUTFIT_RECOMMEND', '2019250925272772608', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019250934642851840', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999825, 'OUTFIT', '2019250932608577536', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019250983963672576', '2026-02-05 03:25:25', '2026-02-05 03:25:25', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999820, 'OUTFIT_RECOMMEND', '2019250983720398848', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019250989382713344', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999740, 'OUTFIT', '2019250989055520768', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019251219020857344', '2026-02-05 03:26:21', '2026-02-05 03:26:21', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999735, 'OUTFIT_RECOMMEND', '2019251218760806400', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019251223873667072', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999655, 'OUTFIT', '2019251223554863104', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019254044228534272', '2026-02-05 03:37:35', '2026-02-05 03:37:35', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999650, 'OUTFIT_RECOMMEND', '2019254042873774080', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019254048724828160', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999570, 'OUTFIT', '2019254048385052672', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019255269091131392', '2026-02-05 03:42:27', '2026-02-05 03:42:27', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999565, 'OUTFIT_RECOMMEND', '2019255268579426304', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019255273826500608', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999485, 'OUTFIT', '2019255273520279552', '创建穿搭搭配消耗'),
                                                                                                                                                                                 ('2019255820960874496', '2026-02-05 03:44:38', '2026-02-05 03:44:38', 0, 0, '2018297740714528768', 'EXPENSE', 5, 9999480, 'OUTFIT_RECOMMEND', '2019255820277207040', '生成AI穿搭推荐消耗'),
                                                                                                                                                                                 ('2019255825482334208', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2018297740714528768', 'EXPENSE', 80, 9999400, 'OUTFIT', '2019255825230639104', '创建穿搭搭配消耗');
/*!40000 ALTER TABLE `t_user_coin_stream` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_operate_stream_0 结构
CREATE TABLE IF NOT EXISTS `t_user_operate_stream_0` (
                                                         `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
    `operate_detail` text COLLATE utf8mb4_unicode_ci COMMENT '操作详情（JSON格式）',
    `operate_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型',
    `operate_time` datetime NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_operate_time` (`operate_time`),
    KEY `user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户操作流水表';

-- 正在导出表  kaleido_0.t_user_operate_stream_0 的数据：~2 rows (大约)
DELETE FROM `t_user_operate_stream_0`;
/*!40000 ALTER TABLE `t_user_operate_stream_0` DISABLE KEYS */;
INSERT INTO `t_user_operate_stream_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `operate_detail`, `operate_type`, `operate_time`) VALUES
                                                                                                                                                                     ('2018297740714528769', '2026-02-02 12:17:34', '2026-02-02 12:17:34', 0, 0, '2018297740714528768', '用户创建，手机号：13266668889，邀请人ID：2014628374013755392', 'CREATE', '2026-02-02 12:17:34'),
                                                                                                                                                                     ('2018298260904693760', '2026-02-02 12:19:38', '2026-02-02 12:19:38', 0, 0, '2018297740714528768', '登录', 'LOGIN', '2026-02-02 12:19:38');
/*!40000 ALTER TABLE `t_user_operate_stream_0` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_user_operate_stream_1 结构
CREATE TABLE IF NOT EXISTS `t_user_operate_stream_1` (
                                                         `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
    `operate_detail` text COLLATE utf8mb4_unicode_ci COMMENT '操作详情（JSON格式）',
    `operate_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型',
    `operate_time` datetime NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_operate_time` (`operate_time`),
    KEY `user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户操作流水表';

-- 正在导出表  kaleido_0.t_user_operate_stream_1 的数据：~2 rows (大约)
DELETE FROM `t_user_operate_stream_1`;
/*!40000 ALTER TABLE `t_user_operate_stream_1` DISABLE KEYS */;
INSERT INTO `t_user_operate_stream_1` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `operate_detail`, `operate_type`, `operate_time`) VALUES
                                                                                                                                                                     ('2014628374022144000', '2026-01-23 09:16:49', '2026-01-23 09:16:49', 0, 0, '2014628374013755392', '用户创建，手机号：13061596465，邀请人ID：无', 'CREATE', '2026-01-23 09:16:49'),
                                                                                                                                                                     ('2014628546198323201', '2026-01-23 09:17:30', '2026-01-23 09:17:30', 0, 0, '2014628546198323200', '用户创建，手机号：13061596466，邀请人ID：2014628374013755392', 'CREATE', '2026-01-23 09:17:30');
/*!40000 ALTER TABLE `t_user_operate_stream_1` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_brand 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_brand` (
                                                  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌名称（唯一）',
    `logo_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌Logo路径（在minio中的文件路径）',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='品牌表';

-- 正在导出表  kaleido_0.t_wardrobe_brand 的数据：~2 rows (大约)
DELETE FROM `t_wardrobe_brand`;
/*!40000 ALTER TABLE `t_wardrobe_brand` DISABLE KEYS */;
INSERT INTO `t_wardrobe_brand` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `name`, `logo_path`, `description`) VALUES
                                                                                                                                     ('1', '2026-01-27 14:33:54', '2026-01-27 14:33:54', 0, 0, 'nike', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 'nike'),
                                                                                                                                     ('2', '2026-01-27 14:34:09', '2026-01-27 14:34:09', 0, 0, 'adidas', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 'adidas');
/*!40000 ALTER TABLE `t_wardrobe_brand` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_clothing 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_clothing` (
                                                     `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装名称',
    `type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装类型编码',
    `color_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '颜色编码',
    `season_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '季节编码',
    `brand_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌ID',
    `size` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '尺码',
    `purchase_date` datetime DEFAULT NULL COMMENT '购买日期',
    `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
    `current_location_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前位置ID',
    `primary_image_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_brand_id` (`brand_id`),
    KEY `idx_current_location_id` (`current_location_id`),
    KEY `idx_primary_image_id` (`primary_image_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服装表';

-- 正在导出表  kaleido_0.t_wardrobe_clothing 的数据：~28 rows (大约)
DELETE FROM `t_wardrobe_clothing`;
/*!40000 ALTER TABLE `t_wardrobe_clothing` DISABLE KEYS */;
INSERT INTO `t_wardrobe_clothing` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `name`, `type_code`, `color_code`, `season_code`, `brand_id`, `size`, `purchase_date`, `price`, `description`, `current_location_id`, `primary_image_id`) VALUES
                                                                                                                                                                                                                                                                         ('2016037658824003584', '2026-01-27 06:36:49', '2026-01-27 06:36:49', 0, 0, '2014628810120708096', '黑色衬衫', 'SHIRT', 'BLACK', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2016036906261008384', '2016037658836586496'),
                                                                                                                                                                                                                                                                         ('2016037684459589632', '2026-01-27 06:36:55', '2026-01-27 06:36:55', 0, 0, '2014628810120708096', '黑色衬衫2', 'SHIRT', 'BLACK', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2016036906261008384', '2016037684459589633'),
                                                                                                                                                                                                                                                                         ('2016037709516361728', '2026-01-27 06:37:01', '2026-01-27 06:37:01', 0, 0, '2014628810120708096', '黑色衬衫3', 'SHIRT', 'BLACK', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2016036906261008384', '2016037709516361729'),
                                                                                                                                                                                                                                                                         ('2016037807579189248', '2026-01-27 06:37:24', '2026-01-27 06:37:24', 0, 0, '2014628810120708096', '蓝色衬衫', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2016036906261008384', '2016037807579189249'),
                                                                                                                                                                                                                                                                         ('2016052217253191680', '2026-01-27 07:34:40', '2026-01-27 07:34:40', 0, 0, '2014628810120708096', '蓝色衬衫2', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2016036906261008384', '2016052217265774592'),
                                                                                                                                                                                                                                                                         ('2018299936176746496', '2026-02-02 12:26:18', '2026-02-02 12:26:18', 0, 0, '2018297740714528768', '蓝色衬衫2', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018299936180940800'),
                                                                                                                                                                                                                                                                         ('2018301548957941760', '2026-02-02 12:32:42', '2026-02-02 12:32:42', 0, 0, '2018297740714528768', '蓝色衬衫3', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018301548970524672'),
                                                                                                                                                                                                                                                                         ('2018301685100855296', '2026-02-02 12:33:16', '2026-02-02 12:33:16', 0, 0, '2018297740714528768', '蓝色衬衫4', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018301685100855297'),
                                                                                                                                                                                                                                                                         ('2018302952837627904', '2026-02-02 12:38:17', '2026-02-02 12:38:17', 0, 0, '2018297740714528768', '蓝色衬衫5', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018302952837627905'),
                                                                                                                                                                                                                                                                         ('2018304895807012864', '2026-02-02 12:46:00', '2026-02-02 12:46:00', 0, 0, '2018297740714528768', '蓝色衬衫6', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018304895807012865'),
                                                                                                                                                                                                                                                                         ('2018529861462818816', '2026-02-03 03:39:56', '2026-02-03 03:39:56', 0, 0, '2018297740714528768', '蓝色衬衫8', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018529861475401728'),
                                                                                                                                                                                                                                                                         ('2018530413106069504', '2026-02-03 03:42:08', '2026-02-03 03:42:08', 0, 0, '2018297740714528768', '蓝色衬衫9', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018530413106069505'),
                                                                                                                                                                                                                                                                         ('2018530580270055424', '2026-02-03 03:42:47', '2026-02-03 03:42:47', 0, 0, '2018297740714528768', '蓝色衬衫10', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, 'u8tpx9', '2018299108279853056', '2018530580270055425'),
                                                                                                                                                                                                                                                                         ('2018941480068091904', '2026-02-04 06:55:34', '2026-02-04 06:55:34', 0, 0, '2018297740714528768', '蓝色衬衫', 'SHIRT', 'BLUE', 'AUTUMN', '1', 'L', '2026-01-23 00:00:00', 161.00, '上班穿的格子衫', '2018299108279853056', '2018941480084869120'),
                                                                                                                                                                                                                                                                         ('2018943145387134976', '2026-02-04 07:02:11', '2026-02-04 07:02:11', 0, 0, '2018297740714528768', '经典条纹衬衫', 'SHIRT', 'BLUE', 'SPRING', '1', 'M', '2025-09-15 00:00:00', 349.00, '海军蓝条纹，商务休闲两用', '2018299108279853056', '2018943145387134977'),
                                                                                                                                                                                                                                                                         ('2018943237280141312', '2026-02-04 07:02:33', '2026-02-04 07:02:33', 0, 0, '2018297740714528768', '复古牛仔夹克', 'COAT', 'BLUE', 'AUTUMN', '2', 'L', '2025-10-22 00:00:00', 899.00, '做旧处理，复古风格', '2018299108279853056', '2018943237280141313'),
                                                                                                                                                                                                                                                                         ('2018943401151598592', '2026-02-04 07:03:12', '2026-02-04 07:03:12', 0, 0, '2018297740714528768', '运动速干T恤', 'TSHIRT', 'BLACK', 'SUMMER', '1', 'XL', '2025-07-30 00:00:00', 129.00, '快干面料，适合高强度运动', '2018299108279853056', '2018943401151598593'),
                                                                                                                                                                                                                                                                         ('2018943455459446784', '2026-02-04 07:03:25', '2026-02-04 07:03:25', 0, 0, '2018297740714528768', '修身休闲裤', 'PANTS', 'GRAY', 'ALL_SEASON', '1', '32', '2026-01-08 00:00:00', 499.00, '弹力面料，修身剪裁', '2018299108279853056', '2018943455459446785'),
                                                                                                                                                                                                                                                                         ('2018943496668483584', '2026-02-04 07:03:34', '2026-02-04 07:03:34', 0, 0, '2018297740714528768', '冬季羽绒服', 'COAT', 'BLACK', 'WINTER', '1', 'XXL', '2025-12-18 00:00:00', 1599.00, '90%白鸭绒填充，极寒保暖', '2018299108279853056', '2018943496668483585'),
                                                                                                                                                                                                                                                                         ('2018943556999352320', '2026-02-04 07:03:49', '2026-02-04 07:03:49', 0, 0, '2018297740714528768', '帆布休闲鞋', 'SHOES', 'WHITE', 'SPRING', '1', '41', '2025-04-10 00:00:00', 299.00, '经典帆布鞋，百搭款式', '2018299108279853056', '2018943556999352321'),
                                                                                                                                                                                                                                                                         ('2018943614956244992', '2026-02-04 07:04:03', '2026-02-04 07:04:03', 0, 0, '2018297740714528768', '夏季短裙', 'SKIRT', 'RED', 'SUMMER', '1', 'S', '2025-06-25 00:00:00', 289.00, 'A字短裙，活泼可爱', '2018299108279853056', '2018943614956244993'),
                                                                                                                                                                                                                                                                         ('2018943689447084032', '2026-02-04 07:04:20', '2026-02-04 07:04:20', 0, 0, '2018297740714528768', '高领毛衣', 'SWEATER', 'GRAY', 'WINTER', '1', 'M', '2025-11-28 00:00:00', 659.00, '羊绒混纺，保暖舒适', '2018299108279853056', '2018943689447084033'),
                                                                                                                                                                                                                                                                         ('2018943744912560128', '2026-02-04 07:04:34', '2026-02-04 07:04:34', 0, 0, '2018297740714528768', '运动短裤', 'SHORTS', 'BLUE', 'SUMMER', '1', 'L', '2025-08-05 00:00:00', 199.00, '透气网眼设计，适合跑步', '2018299108279853056', '2018943744912560129'),
                                                                                                                                                                                                                                                                         ('2018943795953045504', '2026-02-04 07:04:46', '2026-02-04 07:04:46', 0, 0, '2018297740714528768', '商务皮鞋', 'SHOES', 'BLACK', 'ALL_SEASON', '1', '43', '2025-10-15 00:00:00', 899.00, '真皮材质，正式场合穿着', '2018299108279853056', '2018943795953045505'),
                                                                                                                                                                                                                                                                         ('2018943862952857600', '2026-02-04 07:05:02', '2026-02-04 07:05:02', 0, 0, '2018297740714528768', '商务衬衫', 'SHIRT', 'WHITE', 'ALL_SEASON', '1', 'L', '2025-11-05 00:00:00', 499.00, '免烫面料，商务正装', '2018299108279853056', '2018943862952857601'),
                                                                                                                                                                                                                                                                         ('2018943931844300800', '2026-02-04 07:05:18', '2026-02-04 07:05:18', 0, 0, '2018297740714528768', '跑步鞋', 'SHOES', 'ORANGE', 'ALL_SEASON', '1', '42', '2026-01-12 00:00:00', 799.00, '专业缓震，马拉松训练鞋', '2018299108279853056', '2018943931844300801'),
                                                                                                                                                                                                                                                                         ('2018944002870644736', '2026-02-04 07:05:35', '2026-02-04 07:05:35', 0, 0, '2018297740714528768', '运动背心', 'TSHIRT', 'BLACK', 'SUMMER', '1', 'L', '2025-07-18 00:00:00', 129.00, '透气网眼，健身训练用', '2018299108279853056', '2018944002870644737'),
                                                                                                                                                                                                                                                                         ('2018944069597827072', '2026-02-04 07:05:51', '2026-02-04 07:05:51', 0, 0, '2018297740714528768', '运动内衣', 'UNDERWEAR', 'PURPLE', 'ALL_SEASON', '1', 'M', '2026-01-18 00:00:00', 299.00, '高强度支撑，运动专用', '2018299108279853056', '2018944069597827073');
/*!40000 ALTER TABLE `t_wardrobe_clothing` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_clothing_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_clothing_image` (
                                                           `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `clothing_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装ID',
    `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '图片排序',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片类型',
    `width` int DEFAULT NULL COMMENT '图片宽度（像素）',
    `height` int DEFAULT NULL COMMENT '图片高度（像素）',
    PRIMARY KEY (`id`),
    KEY `idx_clothing_id` (`clothing_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服装图片表';

-- 正在导出表  kaleido_0.t_wardrobe_clothing_image 的数据：~29 rows (大约)
DELETE FROM `t_wardrobe_clothing_image`;
/*!40000 ALTER TABLE `t_wardrobe_clothing_image` DISABLE KEYS */;
INSERT INTO `t_wardrobe_clothing_image` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `clothing_id`, `path`, `image_order`, `is_primary`, `image_size`, `image_type`, `width`, `height`) VALUES
                                                                                                                                                                                                             ('2016037658836586496', '2026-01-27 06:36:49', '2026-01-27 06:36:49', 0, 0, '2016037658824003584', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2016037684459589633', '2026-01-27 06:36:55', '2026-01-27 06:36:55', 0, 0, '2016037684459589632', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2016037709516361729', '2026-01-27 06:37:01', '2026-01-27 06:37:01', 0, 0, '2016037709516361728', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2016037807579189249', '2026-01-27 06:37:24', '2026-01-27 06:37:24', 0, 0, '2016037807579189248', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2016052217265774592', '2026-01-27 07:34:40', '2026-01-27 07:34:40', 0, 0, '2016052217253191680', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018298744231043073', '2026-02-02 12:21:33', '2026-02-02 12:21:33', 0, 0, '2018298744231043072', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018299936180940800', '2026-02-02 12:26:18', '2026-02-02 12:26:18', 0, 0, '2018299936176746496', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018301548970524672', '2026-02-02 12:32:42', '2026-02-02 12:32:42', 0, 0, '2018301548957941760', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018301685100855297', '2026-02-02 12:33:16', '2026-02-02 12:33:16', 0, 0, '2018301685100855296', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018302952837627905', '2026-02-02 12:38:17', '2026-02-02 12:38:17', 0, 0, '2018302952837627904', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018304895807012865', '2026-02-02 12:46:00', '2026-02-02 12:46:00', 0, 0, '2018304895807012864', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018529861475401728', '2026-02-03 03:39:56', '2026-02-03 03:39:56', 0, 0, '2018529861462818816', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018530413106069505', '2026-02-03 03:42:08', '2026-02-03 03:42:08', 0, 0, '2018530413106069504', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018530580270055425', '2026-02-03 03:42:47', '2026-02-03 03:42:47', 0, 0, '2018530580270055424', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018941480084869120', '2026-02-04 06:55:34', '2026-02-04 06:55:34', 0, 0, '2018941480068091904', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'PNG', 917, 214),
                                                                                                                                                                                                             ('2018943145387134977', '2026-02-04 07:02:11', '2026-02-04 07:02:11', 0, 0, '2018943145387134976', '20250915/stripe-shirt-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943237280141313', '2026-02-04 07:02:33', '2026-02-04 07:02:33', 0, 0, '2018943237280141312', '20251022/denim-jacket-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943401151598593', '2026-02-04 07:03:12', '2026-02-04 07:03:12', 0, 0, '2018943401151598592', '20250730/dry-fit-tshirt-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943455459446785', '2026-02-04 07:03:25', '2026-02-04 07:03:25', 0, 0, '2018943455459446784', '20260108/chino-pants-002.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943496668483585', '2026-02-04 07:03:34', '2026-02-04 07:03:34', 0, 0, '2018943496668483584', '20251218/down-coat-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943556999352321', '2026-02-04 07:03:49', '2026-02-04 07:03:49', 0, 0, '2018943556999352320', '20250410/canvas-shoes-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943614956244993', '2026-02-04 07:04:03', '2026-02-04 07:04:03', 0, 0, '2018943614956244992', '20250625/summer-skirt-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943689447084033', '2026-02-04 07:04:20', '2026-02-04 07:04:20', 0, 0, '2018943689447084032', '20251128/turtleneck-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943744912560129', '2026-02-04 07:04:34', '2026-02-04 07:04:34', 0, 0, '2018943744912560128', '20250805/sports-shorts-002.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943795953045505', '2026-02-04 07:04:46', '2026-02-04 07:04:46', 0, 0, '2018943795953045504', '20251015/leather-shoes-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943862952857601', '2026-02-04 07:05:02', '2026-02-04 07:05:02', 0, 0, '2018943862952857600', '20251105/business-shirt-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018943931844300801', '2026-02-04 07:05:18', '2026-02-04 07:05:18', 0, 0, '2018943931844300800', '20260112/running-shoes-002.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018944002870644737', '2026-02-04 07:05:35', '2026-02-04 07:05:35', 0, 0, '2018944002870644736', '20250718/tank-top-001.png', 1, 1, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                             ('2018944069597827073', '2026-02-04 07:05:51', '2026-02-04 07:05:51', 0, 0, '2018944069597827072', '20260118/sports-bra-001.png', 1, 1, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `t_wardrobe_clothing_image` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_location_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_location_image` (
                                                           `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `location_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置ID',
    `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '排序序号',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
    `width` int DEFAULT NULL COMMENT '图片宽度',
    `height` int DEFAULT NULL COMMENT '图片高度',
    PRIMARY KEY (`id`),
    KEY `idx_location_id` (`location_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='位置图片表';

-- 正在导出表  kaleido_0.t_wardrobe_location_image 的数据：~5 rows (大约)
DELETE FROM `t_wardrobe_location_image`;
/*!40000 ALTER TABLE `t_wardrobe_location_image` DISABLE KEYS */;
INSERT INTO `t_wardrobe_location_image` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `location_id`, `path`, `image_order`, `is_primary`, `image_size`, `image_type`, `width`, `height`) VALUES
                                                                                                                                                                                                             ('2016035668140216320', '2026-01-27 06:28:54', '2026-01-27 06:28:54', 0, 0, '2016035668127633408', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'image/png', 917, 214),
                                                                                                                                                                                                             ('2016036887302754305', '2026-01-27 06:33:45', '2026-01-27 06:33:45', 0, 0, '2016036887302754304', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'image/png', 917, 214),
                                                                                                                                                                                                             ('2016036906261008385', '2026-01-27 06:33:49', '2026-01-27 06:33:49', 0, 0, '2016036906261008384', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'image/png', 917, 214),
                                                                                                                                                                                                             ('2016068827577712640', '2026-01-27 08:40:40', '2026-01-27 08:40:40', 0, 0, '2016068827565129728', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'image/png', 917, 214),
                                                                                                                                                                                                             ('2018299108279853057', '2026-02-02 12:23:00', '2026-02-02 12:23:00', 0, 0, '2018299108279853056', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 1, 1, 227545, 'image/png', 917, 214);
/*!40000 ALTER TABLE `t_wardrobe_location_image` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_location_record 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_location_record` (
                                                            `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `clothing_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装ID',
    `location_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置ID',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID（冗余，便于查询）',
    `record_time` datetime NOT NULL COMMENT '记录时间',
    `is_current` int DEFAULT '0' COMMENT '是否为当前位置记录',
    PRIMARY KEY (`id`),
    KEY `idx_clothing_id` (`clothing_id`),
    KEY `idx_location_id` (`location_id`),
    KEY `idx_user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='位置记录表';

-- 正在导出表  kaleido_0.t_wardrobe_location_record 的数据：~28 rows (大约)
DELETE FROM `t_wardrobe_location_record`;
/*!40000 ALTER TABLE `t_wardrobe_location_record` DISABLE KEYS */;
INSERT INTO `t_wardrobe_location_record` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `clothing_id`, `location_id`, `user_id`, `record_time`, `is_current`) VALUES
                                                                                                                                                                                 ('2016037659121799168', '2026-01-27 06:36:49', '2026-01-27 06:36:49', 0, 0, '2016037658824003584', '2016036906261008384', '2014628810120708096', '2026-01-27 06:36:49', 1),
                                                                                                                                                                                 ('2016037684589613056', '2026-01-27 06:36:55', '2026-01-27 06:36:55', 0, 0, '2016037684459589632', '2016036906261008384', '2014628810120708096', '2026-01-27 06:36:55', 1),
                                                                                                                                                                                 ('2016037709658968064', '2026-01-27 06:37:01', '2026-01-27 06:37:01', 0, 0, '2016037709516361728', '2016036906261008384', '2014628810120708096', '2026-01-27 06:37:01', 1),
                                                                                                                                                                                 ('2016037807742767104', '2026-01-27 06:37:24', '2026-01-27 06:37:24', 0, 0, '2016037807579189248', '2016036906261008384', '2014628810120708096', '2026-01-27 06:37:24', 1),
                                                                                                                                                                                 ('2016052218352099328', '2026-01-27 07:34:40', '2026-01-27 07:34:40', 0, 0, '2016052217253191680', '2016036906261008384', '2014628810120708096', '2026-01-27 07:34:40', 1),
                                                                                                                                                                                 ('2018299937711861760', '2026-02-02 12:26:18', '2026-02-02 12:26:18', 0, 0, '2018299936176746496', '2018299108279853056', '2018297740714528768', '2026-02-02 12:26:18', 1),
                                                                                                                                                                                 ('2018301550203650048', '2026-02-02 12:32:42', '2026-02-02 12:32:42', 0, 0, '2018301548957941760', '2018299108279853056', '2018297740714528768', '2026-02-02 12:32:42', 1),
                                                                                                                                                                                 ('2018301706634412032', '2026-02-02 12:33:20', '2026-02-02 12:33:20', 0, 0, '2018301685100855296', '2018299108279853056', '2018297740714528768', '2026-02-02 12:33:20', 1),
                                                                                                                                                                                 ('2018302953076703232', '2026-02-02 12:38:17', '2026-02-02 12:38:17', 0, 0, '2018302952837627904', '2018299108279853056', '2018297740714528768', '2026-02-02 12:38:17', 1),
                                                                                                                                                                                 ('2018304896092225536', '2026-02-02 12:46:00', '2026-02-02 12:46:00', 0, 0, '2018304895807012864', '2018299108279853056', '2018297740714528768', '2026-02-02 12:46:00', 1),
                                                                                                                                                                                 ('2018529863190872064', '2026-02-03 03:39:57', '2026-02-03 03:39:57', 0, 0, '2018529861462818816', '2018299108279853056', '2018297740714528768', '2026-02-03 03:39:57', 1),
                                                                                                                                                                                 ('2018530413278035968', '2026-02-03 03:42:08', '2026-02-03 03:42:08', 0, 0, '2018530413106069504', '2018299108279853056', '2018297740714528768', '2026-02-03 03:42:08', 1),
                                                                                                                                                                                 ('2018530580496547840', '2026-02-03 03:42:48', '2026-02-03 03:42:48', 0, 0, '2018530580270055424', '2018299108279853056', '2018297740714528768', '2026-02-03 03:42:48', 1),
                                                                                                                                                                                 ('2018941481607401472', '2026-02-04 06:55:34', '2026-02-04 06:55:34', 0, 0, '2018941480068091904', '2018299108279853056', '2018297740714528768', '2026-02-04 06:55:34', 1),
                                                                                                                                                                                 ('2018943145731067904', '2026-02-04 07:02:11', '2026-02-04 07:02:11', 0, 0, '2018943145387134976', '2018299108279853056', '2018297740714528768', '2026-02-04 07:02:11', 1),
                                                                                                                                                                                 ('2018943237422747648', '2026-02-04 07:02:33', '2026-02-04 07:02:33', 0, 0, '2018943237280141312', '2018299108279853056', '2018297740714528768', '2026-02-04 07:02:33', 1),
                                                                                                                                                                                 ('2018943401290010624', '2026-02-04 07:03:12', '2026-02-04 07:03:12', 0, 0, '2018943401151598592', '2018299108279853056', '2018297740714528768', '2026-02-04 07:03:12', 1),
                                                                                                                                                                                 ('2018943455568498688', '2026-02-04 07:03:25', '2026-02-04 07:03:25', 0, 0, '2018943455459446784', '2018299108279853056', '2018297740714528768', '2026-02-04 07:03:25', 1),
                                                                                                                                                                                 ('2018943496777535488', '2026-02-04 07:03:34', '2026-02-04 07:03:34', 0, 0, '2018943496668483584', '2018299108279853056', '2018297740714528768', '2026-02-04 07:03:34', 1),
                                                                                                                                                                                 ('2018943557137764352', '2026-02-04 07:03:49', '2026-02-04 07:03:49', 0, 0, '2018943556999352320', '2018299108279853056', '2018297740714528768', '2026-02-04 07:03:49', 1),
                                                                                                                                                                                 ('2018943615157571584', '2026-02-04 07:04:03', '2026-02-04 07:04:03', 0, 0, '2018943614956244992', '2018299108279853056', '2018297740714528768', '2026-02-04 07:04:03', 1),
                                                                                                                                                                                 ('2018943689593884672', '2026-02-04 07:04:20', '2026-02-04 07:04:20', 0, 0, '2018943689447084032', '2018299108279853056', '2018297740714528768', '2026-02-04 07:04:20', 1),
                                                                                                                                                                                 ('2018943745034194944', '2026-02-04 07:04:34', '2026-02-04 07:04:34', 0, 0, '2018943744912560128', '2018299108279853056', '2018297740714528768', '2026-02-04 07:04:34', 1),
                                                                                                                                                                                 ('2018943796083068928', '2026-02-04 07:04:46', '2026-02-04 07:04:46', 0, 0, '2018943795953045504', '2018299108279853056', '2018297740714528768', '2026-02-04 07:04:46', 1),
                                                                                                                                                                                 ('2018943863061909504', '2026-02-04 07:05:02', '2026-02-04 07:05:02', 0, 0, '2018943862952857600', '2018299108279853056', '2018297740714528768', '2026-02-04 07:05:02', 1),
                                                                                                                                                                                 ('2018943931944964096', '2026-02-04 07:05:18', '2026-02-04 07:05:18', 0, 0, '2018943931844300800', '2018299108279853056', '2018297740714528768', '2026-02-04 07:05:18', 1),
                                                                                                                                                                                 ('2018944002967113728', '2026-02-04 07:05:35', '2026-02-04 07:05:35', 0, 0, '2018944002870644736', '2018299108279853056', '2018297740714528768', '2026-02-04 07:05:35', 1),
                                                                                                                                                                                 ('2018944069736239104', '2026-02-04 07:05:51', '2026-02-04 07:05:51', 0, 0, '2018944069597827072', '2018299108279853056', '2018297740714528768', '2026-02-04 07:05:51', 1);
/*!40000 ALTER TABLE `t_wardrobe_location_record` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_outfit 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_outfit` (
                                                   `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `season_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '季节类型',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '穿搭名称',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '穿搭描述',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='穿搭表';

-- 正在导出表  kaleido_0.t_wardrobe_outfit 的数据：~8 rows (大约)
DELETE FROM `t_wardrobe_outfit`;
/*!40000 ALTER TABLE `t_wardrobe_outfit` DISABLE KEYS */;
INSERT INTO `t_wardrobe_outfit` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `season_type`, `name`, `description`) VALUES
                                                                                                                                                   ('2016038931581358080', '2026-01-27 06:41:52', '2026-01-27 06:41:52', 0, 0, '2014628810120708096', NULL, '测试穿搭', '测试穿搭'),
                                                                                                                                                   ('2016039412395397120', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2014628810120708096', NULL, '测试穿搭D2YB', '测试穿搭'),
                                                                                                                                                   ('2016039485581807616', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2014628810120708096', NULL, '测试穿搭3tj3', '测试穿搭'),
                                                                                                                                                   ('2016069246215389184', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2014628810120708096', NULL, '测试穿搭dNkT', '测试穿搭'),
                                                                                                                                                   ('2016085736083816448', '2026-01-27 09:47:51', '2026-01-27 09:47:51', 0, 0, '2014628810120708096', NULL, '测试穿搭ZmgF', '测试穿搭'),
                                                                                                                                                   ('2016085809995841536', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2014628810120708096', NULL, '测试穿搭xWuL', '测试穿搭'),
                                                                                                                                                   ('2016085885468147712', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2014628810120708096', NULL, '测试穿搭izv4', '测试穿搭'),
                                                                                                                                                   ('2016085891554082816', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2014628810120708096', NULL, '测试穿搭IiHO', '测试穿搭'),
                                                                                                                                                   ('2016086110802935808', '2026-01-27 09:49:21', '2026-01-27 09:49:21', 0, 0, '2014628810120708096', NULL, '测试穿搭fH5I', '测试穿搭'),
                                                                                                                                                   ('2019250222949113856', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:22', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019250932608577536', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:25', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019250989055520768', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:25', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019251223554863104', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:26', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019254048385052672', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:37', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019255273520279552', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:42', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。'),
                                                                                                                                                   ('2019255825230639104', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2018297740714528768', NULL, 'AI推荐穿搭 2026-02-05 11:44', '根据您的需求生成的AI推荐穿搭，包含多件精心挑选的服装组合。');
/*!40000 ALTER TABLE `t_wardrobe_outfit` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_outfit_clothing 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_outfit_clothing` (
                                                            `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `outfit_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '穿搭ID，关联t_wardrobe_outfit表',
    `clothing_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装ID，关联t_wardrobe_clothing表',
    PRIMARY KEY (`id`),
    KEY `idx_outfit_id` (`outfit_id`),
    KEY `idx_clothing_id` (`clothing_id`),
    KEY `idx_outfit_clothing` (`outfit_id`,`clothing_id`),
    KEY `idx_deleted` (`deleted`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='穿搭-服装关联表';

-- 正在导出表  kaleido_0.t_wardrobe_outfit_clothing 的数据：~18 rows (大约)
DELETE FROM `t_wardrobe_outfit_clothing`;
/*!40000 ALTER TABLE `t_wardrobe_outfit_clothing` DISABLE KEYS */;
INSERT INTO `t_wardrobe_outfit_clothing` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `outfit_id`, `clothing_id`) VALUES
                                                                                                                                       ('2016038931593940992', '2026-01-27 06:41:52', '2026-01-27 06:41:52', 0, 0, '2016038931581358080', '2016037684459589632'),
                                                                                                                                       ('2016038931593940993', '2026-01-27 06:41:52', '2026-01-27 06:41:52', 0, 0, '2016038931581358080', '2016037709516361728'),
                                                                                                                                       ('2016039412395397121', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2016039412395397120', '2016037684459589632'),
                                                                                                                                       ('2016039412395397122', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2016039412395397120', '2016037709516361728'),
                                                                                                                                       ('2016039485581807617', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2016039485581807616', '2016037684459589632'),
                                                                                                                                       ('2016039485581807618', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2016039485581807616', '2016037709516361728'),
                                                                                                                                       ('2016069246227972096', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2016069246215389184', '2016037684459589632'),
                                                                                                                                       ('2016069246227972097', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2016069246215389184', '2016037709516361728'),
                                                                                                                                       ('2016085736083816449', '2026-01-27 09:47:51', '2026-01-27 09:47:51', 0, 0, '2016085736083816448', '2016037684459589632'),
                                                                                                                                       ('2016085736083816450', '2026-01-27 09:47:51', '2026-01-27 09:47:51', 0, 0, '2016085736083816448', '2016037709516361728'),
                                                                                                                                       ('2016085809995841537', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2016085809995841536', '2016037684459589632'),
                                                                                                                                       ('2016085809995841538', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2016085809995841536', '2016037709516361728'),
                                                                                                                                       ('2016085885468147713', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2016085885468147712', '2016037684459589632'),
                                                                                                                                       ('2016085885468147714', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2016085885468147712', '2016037709516361728'),
                                                                                                                                       ('2016085891554082817', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2016085891554082816', '2016037684459589632'),
                                                                                                                                       ('2016085891554082818', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2016085891554082816', '2016037709516361728'),
                                                                                                                                       ('2016086110807130112', '2026-01-27 09:49:21', '2026-01-27 09:49:21', 0, 0, '2016086110802935808', '2016037684459589632'),
                                                                                                                                       ('2016086110807130113', '2026-01-27 09:49:21', '2026-01-27 09:49:21', 0, 0, '2016086110802935808', '2016037709516361728'),
                                                                                                                                       ('2019250222949113857', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2019250222949113856', '2018943401151598592'),
                                                                                                                                       ('2019250222949113858', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2019250222949113856', '2018943744912560128'),
                                                                                                                                       ('2019250222949113859', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2019250222949113856', '2018943556999352320'),
                                                                                                                                       ('2019250932616966144', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2019250932608577536', '2018943145387134976'),
                                                                                                                                       ('2019250932616966145', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2019250932608577536', '2018943455459446784'),
                                                                                                                                       ('2019250932616966146', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2019250932608577536', '2018943556999352320'),
                                                                                                                                       ('2019250989055520769', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2019250989055520768', '2018943145387134976'),
                                                                                                                                       ('2019250989055520770', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2019250989055520768', '2018943455459446784'),
                                                                                                                                       ('2019250989055520771', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2019250989055520768', '2018943556999352320'),
                                                                                                                                       ('2019251223554863105', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2019251223554863104', '2018943145387134976'),
                                                                                                                                       ('2019251223554863106', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2019251223554863104', '2018943455459446784'),
                                                                                                                                       ('2019251223554863107', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2019251223554863104', '2018943556999352320'),
                                                                                                                                       ('2019254048385052673', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2019254048385052672', '2018943145387134976'),
                                                                                                                                       ('2019254048385052674', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2019254048385052672', '2018943455459446784'),
                                                                                                                                       ('2019254048385052675', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2019254048385052672', '2018943556999352320'),
                                                                                                                                       ('2019255273520279553', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2019255273520279552', '2018943145387134976'),
                                                                                                                                       ('2019255273520279554', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2019255273520279552', '2018943455459446784'),
                                                                                                                                       ('2019255273520279555', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2019255273520279552', '2018943556999352320'),
                                                                                                                                       ('2019255825230639105', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2019255825230639104', '2018943145387134976'),
                                                                                                                                       ('2019255825230639106', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2019255825230639104', '2018943455459446784'),
                                                                                                                                       ('2019255825234833408', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2019255825230639104', '2018943556999352320');
/*!40000 ALTER TABLE `t_wardrobe_outfit_clothing` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_outfit_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_outfit_image` (
                                                         `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `outfit_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '穿搭ID',
    `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '图片排序',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片类型',
    `width` int DEFAULT NULL COMMENT '图片宽度（像素）',
    `height` int DEFAULT NULL COMMENT '图片高度（像素）',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片描述',
    PRIMARY KEY (`id`),
    KEY `idx_outfit_id` (`outfit_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='穿搭图片表';

-- 正在导出表  kaleido_0.t_wardrobe_outfit_image 的数据：~9 rows (大约)
DELETE FROM `t_wardrobe_outfit_image`;
/*!40000 ALTER TABLE `t_wardrobe_outfit_image` DISABLE KEYS */;
INSERT INTO `t_wardrobe_outfit_image` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `outfit_id`, `path`, `image_order`, `is_primary`, `image_size`, `image_type`, `width`, `height`, `description`) VALUES
                                                                                                                                                                                                                        ('2016038931610718208', '2026-01-27 06:41:52', '2026-01-27 06:41:52', 0, 0, '2016038931581358080', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016039412395397123', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2016039412395397120', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016039485581807619', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2016039485581807616', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016069246240555008', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2016069246215389184', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016085736083816451', '2026-01-27 09:47:51', '2026-01-27 09:47:51', 0, 0, '2016085736083816448', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016085809995841539', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2016085809995841536', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016085885468147715', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2016085885468147712', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016085891554082819', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2016085891554082816', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2016086110807130114', '2026-01-27 09:49:21', '2026-01-27 09:49:21', 0, 0, '2016086110802935808', '20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png', 0, 0, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019250222949113860', '2026-02-05 03:22:24', '2026-02-05 03:22:24', 0, 0, '2019250222949113856', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019250932625354752', '2026-02-05 03:25:13', '2026-02-05 03:25:13', 0, 0, '2019250932608577536', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019250989055520772', '2026-02-05 03:25:26', '2026-02-05 03:25:26', 0, 0, '2019250989055520768', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019251223554863108', '2026-02-05 03:26:22', '2026-02-05 03:26:22', 0, 0, '2019251223554863104', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019254048385052676', '2026-02-05 03:37:36', '2026-02-05 03:37:36', 0, 0, '2019254048385052672', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019255273520279556', '2026-02-05 03:42:28', '2026-02-05 03:42:28', 0, 0, '2019255273520279552', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                                                        ('2019255825234833409', '2026-02-05 03:44:39', '2026-02-05 03:44:39', 0, 0, '2019255825230639104', 'default/outfit/default-outfit-image.jpg', 1, 1, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `t_wardrobe_outfit_image` ENABLE KEYS */;

-- 导出  表 kaleido_0.t_wardrobe_storage_location 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_storage_location` (
                                                             `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置名称（同一用户下唯一）',
    `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置描述',
    `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '具体地址',
    `primary_image_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_primary_image_id` (`primary_image_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储位置表';

-- 正在导出表  kaleido_0.t_wardrobe_storage_location 的数据：~5 rows (大约)
DELETE FROM `t_wardrobe_storage_location`;
/*!40000 ALTER TABLE `t_wardrobe_storage_location` DISABLE KEYS */;
INSERT INTO `t_wardrobe_storage_location` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `name`, `description`, `address`, `primary_image_id`) VALUES
                                                                                                                                                                             ('2016035668127633408', '2026-01-27 06:28:54', '2026-01-27 06:28:54', 0, 0, '2014628810120708096', '卧室衣柜', '卧室衣柜', '卧室', '2016035668140216320'),
                                                                                                                                                                             ('2016036887302754304', '2026-01-27 06:33:45', '2026-01-27 06:33:45', 0, 0, '2014628810120708096', '卧室衣柜2', '卧室衣柜', '卧室', '2016036887302754305'),
                                                                                                                                                                             ('2016036906261008384', '2026-01-27 06:33:49', '2026-01-27 06:33:49', 0, 0, '2014628810120708096', '卧室衣柜3', '卧室衣柜', '卧室', '2016036906261008385'),
                                                                                                                                                                             ('2016068827565129728', '2026-01-27 08:40:40', '2026-01-27 08:40:40', 0, 0, '2014628810120708096', '卧室衣柜4', '卧室衣柜', '卧室', '2016068827577712640'),
                                                                                                                                                                             ('2018299108279853056', '2026-02-02 12:23:00', '2026-02-02 12:23:00', 0, 0, '2018297740714528768', '卧室衣柜', '卧室衣柜', '卧室', '2018299108279853057');
/*!40000 ALTER TABLE `t_wardrobe_storage_location` ENABLE KEYS */;


-- 导出 kaleido_1 的数据库结构
CREATE DATABASE IF NOT EXISTS `kaleido_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kaleido_1`;

-- 导出  表 kaleido_1.t_recommend_record_0 结构
CREATE TABLE IF NOT EXISTS `t_recommend_record_0` (
                                                      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户输入的推荐需求提示词',
    `outfit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成的穿搭ID（关联t_wardrobe_outfit）',
    `execution_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '执行记录id',
    `status` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推荐生成状态 PROCESSING-处理中 COMPLETED-已完成 FAILED-失败',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_outfit_id` (`outfit_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `execution_id` (`execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐记录表';

-- 正在导出表  kaleido_1.t_recommend_record_0 的数据：~10 rows (大约)
DELETE FROM `t_recommend_record_0`;
/*!40000 ALTER TABLE `t_recommend_record_0` DISABLE KEYS */;
INSERT INTO `t_recommend_record_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `prompt`, `outfit_id`, `execution_id`, `status`) VALUES
                                                                                                                                                                 ('2016031935612215296', '2026-01-27 06:14:11', '2026-01-27 06:14:11', 0, 0, '2014628810120708096', '周末游玩', NULL, NULL, ''),
                                                                                                                                                                 ('2016032579236552704', '2026-01-27 06:16:38', '2026-01-27 06:16:38', 0, 0, '2014628810120708096', '周末游玩', NULL, NULL, ''),
                                                                                                                                                                 ('2016038933321908224', '2026-01-27 06:41:53', '2026-01-27 06:41:53', 0, 0, '2014628810120708096', '周末游玩', '2016038931581358080', NULL, ''),
                                                                                                                                                                 ('2016039414672822272', '2026-01-27 06:43:47', '2026-01-27 06:43:47', 0, 0, '2014628810120708096', '周末游玩', '2016039412395397120', NULL, ''),
                                                                                                                                                                 ('2016039486080847872', '2026-01-27 06:44:04', '2026-01-27 06:44:04', 0, 0, '2014628810120708096', '周末游玩', '2016039485581807616', NULL, ''),
                                                                                                                                                                 ('2016069247813472256', '2026-01-27 08:42:20', '2026-01-27 08:42:20', 0, 0, '2014628810120708096', '周末游玩', '2016069246215389184', NULL, ''),
                                                                                                                                                                 ('2016085810885095424', '2026-01-27 09:48:09', '2026-01-27 09:48:09', 0, 0, '2014628810120708096', '周末游玩', '2016085809995841536', NULL, ''),
                                                                                                                                                                 ('2016085885992497152', '2026-01-27 09:48:27', '2026-01-27 09:48:27', 0, 0, '2014628810120708096', '周末游玩', '2016085885468147712', NULL, ''),
                                                                                                                                                                 ('2016085892057460736', '2026-01-27 09:48:28', '2026-01-27 09:48:28', 0, 0, '2014628810120708096', '周末游玩', '2016085891554082816', NULL, ''),
                                                                                                                                                                 ('2016086121666244608', '2026-01-27 09:49:23', '2026-01-27 09:49:23', 0, 0, '2014628810120708096', '周末游玩', '2016086110802935808', NULL, '');
/*!40000 ALTER TABLE `t_recommend_record_0` ENABLE KEYS */;

-- 导出  表 kaleido_1.t_recommend_record_1 结构
CREATE TABLE IF NOT EXISTS `t_recommend_record_1` (
                                                      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户输入的推荐需求提示词',
    `outfit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成的穿搭ID（关联t_wardrobe_outfit）',
    `execution_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行记录id',
    `status` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推荐生成状态 PROCESSING-处理中 COMPLETED-已完成 FAILED-失败',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_outfit_id` (`outfit_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `execution_id` (`execution_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐记录表';

-- 正在导出表  kaleido_1.t_recommend_record_1 的数据：~0 rows (大约)
DELETE FROM `t_recommend_record_1`;
/*!40000 ALTER TABLE `t_recommend_record_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_recommend_record_1` ENABLE KEYS */;

-- 导出  表 kaleido_1.t_user_0 结构
CREATE TABLE IF NOT EXISTS `t_user_0` (
                                          `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `nick_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 NORMAL FROZEN DELETED',
    `invite_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请码',
    `inviter_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请人ID',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_invite_code` (`invite_code`),
    KEY `idx_inviter_id` (`inviter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 正在导出表  kaleido_1.t_user_0 的数据：~2 rows (大约)
DELETE FROM `t_user_0`;
/*!40000 ALTER TABLE `t_user_0` DISABLE KEYS */;
INSERT INTO `t_user_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `mobile`, `nick_name`, `status`, `invite_code`, `inviter_id`, `last_login_time`, `avatar`, `gender`) VALUES
                                                                                                                                                                                              ('2014628810120708096', '2026-01-23 09:18:33', '2026-01-23 09:19:47', 0, 0, '13061596467', '用户6467', 'ACTIVE', '5SEU2S', '2014628374013755392', '2026-01-23 09:19:14', 'xxxx', NULL),
                                                                                                                                                                                              ('2018295469066899456', '2026-02-02 12:08:33', '2026-02-02 12:08:33', 0, 0, '13266668888', '用户8888', 'ACTIVE', 'RZD99D', '2014628374013755392', NULL, NULL, NULL);
/*!40000 ALTER TABLE `t_user_0` ENABLE KEYS */;

-- 导出  表 kaleido_1.t_user_1 结构
CREATE TABLE IF NOT EXISTS `t_user_1` (
                                          `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `nick_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 NORMAL FROZEN DELETED',
    `invite_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请码',
    `inviter_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请人ID',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_invite_code` (`invite_code`),
    KEY `idx_inviter_id` (`inviter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 正在导出表  kaleido_1.t_user_1 的数据：~0 rows (大约)
DELETE FROM `t_user_1`;
/*!40000 ALTER TABLE `t_user_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_1` ENABLE KEYS */;

-- 导出  表 kaleido_1.t_user_operate_stream_0 结构
CREATE TABLE IF NOT EXISTS `t_user_operate_stream_0` (
                                                         `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
    `operate_detail` text COLLATE utf8mb4_unicode_ci COMMENT '操作详情（JSON格式）',
    `operate_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型',
    `operate_time` datetime NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_operate_time` (`operate_time`),
    KEY `user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户操作流水表';

-- 正在导出表  kaleido_1.t_user_operate_stream_0 的数据：~11 rows (大约)
DELETE FROM `t_user_operate_stream_0`;
/*!40000 ALTER TABLE `t_user_operate_stream_0` DISABLE KEYS */;
INSERT INTO `t_user_operate_stream_0` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `user_id`, `operate_detail`, `operate_type`, `operate_time`) VALUES
                                                                                                                                                                     ('2014628810120708097', '2026-01-23 09:18:33', '2026-01-23 09:18:33', 0, 0, '2014628810120708096', '用户创建，手机号：13061596467，邀请人ID：2014628374013755392', 'CREATE', '2026-01-23 09:18:33'),
                                                                                                                                                                     ('2014628983634870272', '2026-01-23 09:19:14', '2026-01-23 09:19:14', 0, 0, '2014628810120708096', '登录', 'LOGIN', '2026-01-23 09:19:14'),
                                                                                                                                                                     ('2014629092938432512', '2026-01-23 09:19:40', '2026-01-23 09:19:40', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:40'),
                                                                                                                                                                     ('2014629101406732288', '2026-01-23 09:19:42', '2026-01-23 09:19:42', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:42'),
                                                                                                                                                                     ('2014629110151856128', '2026-01-23 09:19:45', '2026-01-23 09:19:45', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:45'),
                                                                                                                                                                     ('2014629114610401280', '2026-01-23 09:19:46', '2026-01-23 09:19:46', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:46'),
                                                                                                                                                                     ('2014629116657221632', '2026-01-23 09:19:46', '2026-01-23 09:19:46', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:46'),
                                                                                                                                                                     ('2014629118758567936', '2026-01-23 09:19:47', '2026-01-23 09:19:47', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:47'),
                                                                                                                                                                     ('2014629120344014848', '2026-01-23 09:19:47', '2026-01-23 09:19:47', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:47'),
                                                                                                                                                                     ('2014629121921073152', '2026-01-23 09:19:47', '2026-01-23 09:19:47', 0, 0, '2014628810120708096', '更新头像', 'UPDATE_AVATAR', '2026-01-23 09:19:47'),
                                                                                                                                                                     ('2018295469079482368', '2026-02-02 12:08:33', '2026-02-02 12:08:33', 0, 0, '2018295469066899456', '用户创建，手机号：13266668888，邀请人ID：2014628374013755392', 'CREATE', '2026-02-02 12:08:33');
/*!40000 ALTER TABLE `t_user_operate_stream_0` ENABLE KEYS */;

-- 导出  表 kaleido_1.t_user_operate_stream_1 结构
CREATE TABLE IF NOT EXISTS `t_user_operate_stream_1` (
                                                         `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
    `operate_detail` text COLLATE utf8mb4_unicode_ci COMMENT '操作详情（JSON格式）',
    `operate_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型',
    `operate_time` datetime NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_operate_time` (`operate_time`),
    KEY `user_id` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户操作流水表';

-- 正在导出表  kaleido_1.t_user_operate_stream_1 的数据：~0 rows (大约)
DELETE FROM `t_user_operate_stream_1`;
/*!40000 ALTER TABLE `t_user_operate_stream_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_operate_stream_1` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
