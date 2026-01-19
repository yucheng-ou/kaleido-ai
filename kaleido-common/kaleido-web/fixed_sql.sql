-- 导出  表 myapp.t_wardrobe_brand 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_brand` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌名称（唯一）',
    `logo_path` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌Logo路径（在minio中的文件路径）',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='品牌表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_clothing 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_clothing` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装名称',
    `type_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装类型编码',
    `color_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '颜色编码',
    `season_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '季节编码',
    `brand_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌ID',
    `size` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '尺码',
    `purchase_date` datetime DEFAULT NULL COMMENT '购买日期',
    `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
    `current_location_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前位置ID',
    `primary_image_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_brand_id` (`brand_id`),
    KEY `idx_current_location_id` (`current_location_id`),
    KEY `idx_primary_image_id` (`primary_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服装表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_clothing_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_clothing_image` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `clothing_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装ID',
    `path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '图片排序',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片类型',
    `width` int DEFAULT NULL COMMENT '图片宽度（像素）',
    `height` int DEFAULT NULL COMMENT '图片高度（像素）',
    PRIMARY KEY (`id`),
    KEY `idx_clothing_id` (`clothing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服装图片表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_storage_location 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_storage_location` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置名称（同一用户下唯一）',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置描述',
    `address` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '具体地址',
    `primary_image_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_primary_image_id` (`primary_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储位置表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_location_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_location_image` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `location_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置ID',
    `path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '排序序号',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
    `width` int DEFAULT NULL COMMENT '图片宽度',
    `height` int DEFAULT NULL COMMENT '图片高度',
    PRIMARY KEY (`id`),
    KEY `idx_location_id` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='位置图片表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_location_record 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_location_record` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `clothing_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服装ID',
    `location_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '位置ID',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID（冗余，便于查询）',
    `record_time` datetime NOT NULL COMMENT '记录时间',
    `notes` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `is_current` int DEFAULT '0' COMMENT '是否为当前位置记录',
    PRIMARY KEY (`id`),
    KEY `idx_clothing_id` (`clothing_id`),
    KEY `idx_location_id` (`location_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='位置记录表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_outfit 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_outfit` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '穿搭名称',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '穿搭描述',
    `season_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '季节类型',
    `occasion_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场合类型',
    `style` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '穿搭风格',
    `is_public` tinyint DEFAULT '0' COMMENT '是否公开',
    `favorite_count` int DEFAULT '0' COMMENT '收藏数',
    `view_count` int DEFAULT '0' COMMENT '浏览数',
    `rating` double DEFAULT NULL COMMENT '评分',
    `rating_count` int DEFAULT '0' COMMENT '评分人数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='穿搭表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_wardrobe_outfit_image 结构
CREATE TABLE IF NOT EXISTS `t_wardrobe_outfit_image` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `outfit_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '穿搭ID',
    `path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径（在minio中的文件路径）',
    `image_order` int DEFAULT '0' COMMENT '图片排序',
    `is_primary` tinyint DEFAULT '0' COMMENT '是否为主图',
    `image_size` bigint DEFAULT NULL COMMENT '图片大小（字节）',
    `image_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片类型',
    `width` int DEFAULT NULL COMMENT '图片宽度（像素）',
    `height` int DEFAULT NULL COMMENT '图片高度（像素）',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片描述',
    PRIMARY KEY (`id`),
    KEY `idx_outfit_id` (`outfit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='穿搭图片表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_tag 结构
CREATE TABLE IF NOT EXISTS `t_tag` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
    `type_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签类型编码',
    `color` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签颜色',
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签描述',
    `usage_count` int DEFAULT '0' COMMENT '使用次数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 数据导出被取消选择。

-- 导出  表 myapp.t_tag_relation 结构
CREATE TABLE IF NOT EXISTS `t_tag_relation` (
    `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    `lock_version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `tag_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签ID',
    `entity_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '实体ID',
    `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`),
    KEY `idx_tag_id` (`tag_id`),
    KEY `idx_entity_id` (`entity_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签关联表';

-- 数据导出被取消选择。
