#!/usr/bin/env python3
# 生成SQL语句的脚本

import re

# Java类型到MySQL类型的映射
type_mapping = {
    "String": "varchar(255)",
    "Integer": "int",
    "Long": "bigint", 
    "BigDecimal": "decimal(10,2)",
    "Date": "datetime",
    "LocalDateTime": "datetime",
    "Boolean": "tinyint",
    "Double": "double",
    "int": "int",
    "long": "bigint",
    "boolean": "tinyint",
    "double": "double"
}

# 字段名特殊处理（根据长度调整）
field_length_adjustments = {
    "id": "varchar(64)",
    "name": "varchar(100)",
    "description": "varchar(500)",
    "status": "varchar(20)",
    "type_code": "varchar(50)",
    "dict_code": "varchar(50)",
    "color_code": "varchar(20)",
    "season_code": "varchar(20)",
    "size": "varchar(20)",
    "color": "varchar(20)",
    "mobile": "varchar(20)",
    "address": "varchar(500)",
    "path": "varchar(500)",
    "logo_path": "varchar(500)",
    "avatar": "varchar(255)",
    "image_type": "varchar(50)",
    "style": "varchar(50)",
    "season_type": "varchar(20)",
    "occasion_type": "varchar(20)",
    "notes": "varchar(500)",
    "gender": "varchar(10)",
    "real_name": "varchar(50)",
    "nick_name": "varchar(100)",
    "template_name": "varchar(100)",
    "type_name": "varchar(100)",
    "dict_name": "varchar(100)",
    "code": "varchar(100)",
    "icon": "varchar(100)",
    "component": "varchar(255)",
    "target_address": "varchar(255)",
    "target_type": "varchar(20)",
    "business_type": "varchar(50)",
    "invite_code": "varchar(50)",
    "operate_detail": "text",
    "content": "text",
    "template_content": "text",
    "result_message": "text",
    "userId": "varchar(64)",
    "user_id": "varchar(64)",
    "brand_id": "varchar(64)",
    "clothing_id": "varchar(64)",
    "location_id": "varchar(64)",
    "current_location_id": "varchar(64)",
    "primary_image_id": "varchar(64)",
    "admin_id": "varchar(64)",
    "role_id": "varchar(64)",
    "permission_id": "varchar(64)",
    "parent_id": "varchar(64)",
    "inviter_id": "varchar(64)",
    "operator_id": "varchar(64)",
    "tag_id": "varchar(64)",
    "entity_id": "varchar(64)",
    "outfit_id": "varchar(64)",
}

# BasePO的公共字段
base_fields = [
    ("id", "varchar(64)", "主键ID", "NOT NULL"),
    ("created_at", "datetime", "创建时间", "NOT NULL DEFAULT CURRENT_TIMESTAMP"),
    ("updated_at", "datetime", "更新时间", "NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"),
    ("deleted", "tinyint", "是否删除：0-未删除，1-已删除", "NOT NULL DEFAULT '0'"),
    ("lock_version", "int", "乐观锁版本号", "NOT NULL DEFAULT '0'"),
]

def get_mysql_type(java_type, field_name):
    """根据Java类型和字段名获取MySQL类型"""
    # 首先检查字段名特殊处理
    if field_name in field_length_adjustments:
        return field_length_adjustments[field_name]
    
    # 然后检查Java类型映射
    if java_type in type_mapping:
        return type_mapping[java_type]
    
    # 默认返回varchar(255)
    return "varchar(255)"

def generate_table_sql(table_name, table_comment, fields):
    """生成表的SQL语句"""
    sql_lines = []
    sql_lines.append(f"-- 导出  表 myapp.{table_name} 结构")
    sql_lines.append(f"CREATE TABLE IF NOT EXISTS `{table_name}` (")
    
    # 添加所有字段
    all_fields = base_fields + fields
    
    for i, (field_name, field_type, field_comment, extra) in enumerate(all_fields):
        line = f"    `{field_name}` {field_type} COLLATE utf8mb4_unicode_ci"
        if extra:
            line += f" {extra}"
        line += f" COMMENT '{field_comment}'"
        if i < len(all_fields) - 1:
            line += ","
        sql_lines.append(line)
    
    # 添加主键
    sql_lines.append("    PRIMARY KEY (`id`)")
    
    # 添加索引（根据字段名推断）
    indices = []
    
    # 检查需要添加索引的字段
    for field_name, _, _, _ in fields:
        if field_name.endswith("_id") and field_name != "id":
            indices.append((f"idx_{field_name}", field_name))
        elif field_name in ["user_id", "status", "created_at", "updated_at"]:
            indices.append((f"idx_{field_name}", field_name))
    
    # 添加唯一约束
    unique_fields = []
    for field_name, _, _, _ in fields:
        if field_name in ["name", "mobile", "code", "invite_code"]:
            unique_fields.append(field_name)
    
    if unique_fields:
        uk_name = "uk_" + "_".join(unique_fields[:2])
        sql_lines.append(f"    ,UNIQUE KEY `{uk_name}` ({', '.join([f'`{f}`' for f in unique_fields])})")
    
    # 添加普通索引
    for idx_name, idx_field in indices:
        sql_lines.append(f"    ,KEY `{idx_name}` (`{idx_field}`)")
    
    sql_lines.append(f") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='{table_comment}';")
    sql_lines.append("")
    sql_lines.append("-- 数据导出被取消选择。")
    sql_lines.append("")
    
    return "\n".join(sql_lines)

# 定义所有表
tables = [
    {
        "name": "t_wardrobe_brand",
        "comment": "品牌表",
        "fields": [
            ("name", "varchar(100)", "品牌名称（唯一）", "NOT NULL"),
            ("logo_path", "varchar(500)", "品牌Logo路径（在minio中的文件路径）", "DEFAULT NULL"),
            ("description", "varchar(500)", "品牌描述", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_wardrobe_clothing",
        "comment": "服装表",
        "fields": [
            ("user_id", "varchar(64)", "用户ID", "NOT NULL"),
            ("name", "varchar(100)", "服装名称", "NOT NULL"),
            ("type_code", "varchar(50)", "服装类型编码", "NOT NULL"),
            ("color_code", "varchar(20)", "颜色编码", "NOT NULL"),
            ("season_code", "varchar(20)", "季节编码", "NOT NULL"),
            ("brand_id", "varchar(64)", "品牌ID", "DEFAULT NULL"),
            ("size", "varchar(20)", "尺码", "DEFAULT NULL"),
            ("purchase_date", "datetime", "购买日期", "DEFAULT NULL"),
            ("price", "decimal(10,2)", "价格", "DEFAULT NULL"),
            ("description", "varchar(500)", "描述", "DEFAULT NULL"),
            ("current_location_id", "varchar(64)", "当前位置ID", "DEFAULT NULL"),
            ("primary_image_id", "varchar(64)", "主图ID", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_wardrobe_clothing_image",
        "comment": "服装图片表",
        "fields": [
            ("clothing_id", "varchar(64)", "服装ID", "NOT NULL"),
            ("path", "varchar(500)", "图片路径（在minio中的文件路径）", "NOT NULL"),
            ("image_order", "int", "图片排序", "DEFAULT '0'"),
            ("is_primary", "tinyint", "是否为主图", "DEFAULT '0'"),
            ("image_size", "bigint", "图片大小（字节）", "DEFAULT NULL"),
            ("image_type", "varchar(50)", "图片类型", "DEFAULT NULL"),
            ("width", "int", "图片宽度（像素）", "DEFAULT NULL"),
            ("height", "int", "图片高度（像素）", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_wardrobe_storage_location",
        "comment": "存储位置表",
        "fields": [
            ("user_id", "varchar(64)", "用户ID", "NOT NULL"),
            ("name", "varchar(100)", "位置名称（同一用户下唯一）", "NOT NULL"),
            ("description", "varchar(500)", "位置描述", "DEFAULT NULL"),
            ("address", "varchar(500)", "具体地址", "DEFAULT NULL"),
            ("primary_image_id", "varchar(64)", "主图ID", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_wardrobe_location_image",
        "comment": "位置图片表",
        "fields": [
            ("location_id", "varchar(64)", "位置ID", "NOT NULL"),
            ("path", "varchar(500)", "图片路径（在minio中的文件路径）", "NOT NULL"),
            ("image_order", "int", "排序序号", "DEFAULT '0'"),
            ("is_primary", "tinyint", "是否为主图", "DEFAULT '0'"),
            ("image_size", "bigint", "图片大小（字节）", "DEFAULT NULL"),
            ("image_type", "varchar(50)", "文件类型", "DEFAULT NULL"),
            ("width", "int", "图片宽度", "DEFAULT NULL"),
            ("height", "int", "图片高度", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_wardrobe_location_record",
        "comment": "位置记录表",
        "fields": [
            ("clothing_id", "varchar(64)", "服装ID", "NOT NULL"),
            ("location_id", "varchar(64)", "位置ID", "NOT NULL"),
            ("user_id", "varchar(64)", "用户ID（冗余，便于查询）", "NOT NULL"),
            ("record_time", "datetime", "记录时间", "NOT NULL"),
            ("notes", "varchar(500)", "备注", "DEFAULT NULL"),
            ("is_current", "int", "是否为当前位置记录", "DEFAULT '0'"),
        ]
    },
    {
        "name": "t_wardrobe_outfit",
        "comment": "穿搭表",
        "fields": [
            ("user_id", "varchar(64)", "用户ID", "NOT NULL"),
            ("name", "varchar(100)", "穿搭名称", "NOT NULL"),
            ("description", "varchar(500)", "穿搭描述", "DEFAULT NULL"),
            ("season_type", "varchar(20)", "季节类型", "DEFAULT NULL"),
            ("occasion_type", "varchar(20)", "场合类型", "DEFAULT NULL"),
            ("style", "varchar(50)", "穿搭风格", "DEFAULT NULL"),
            ("is_public", "tinyint", "是否公开", "DEFAULT '0'"),
            ("favorite_count", "int", "收藏数", "DEFAULT '0'"),
            ("view_count", "int", "浏览数", "DEFAULT '0'"),
            ("rating", "double", "评分", "DEFAULT NULL"),
            ("rating_count", "int", "评分人数", "DEFAULT '0'"),
        ]
    },
    {
        "name": "t_wardrobe_outfit_image",
        "comment": "穿搭图片表",
        "fields": [
            ("outfit_id", "varchar(64)", "穿搭ID", "NOT NULL"),
            ("path", "varchar(500)", "图片路径（在minio中的文件路径）", "NOT NULL"),
            ("image_order", "int", "图片排序", "DEFAULT '0'"),
            ("is_primary", "tinyint", "是否为主图", "DEFAULT '0'"),
            ("image_size", "bigint", "图片大小（字节）", "DEFAULT NULL"),
            ("image_type", "varchar(50)", "图片类型", "DEFAULT NULL"),
            ("width", "int", "图片宽度（像素）", "DEFAULT NULL"),
            ("height", "int", "图片高度（像素）", "DEFAULT NULL"),
            ("description", "varchar(500)", "图片描述", "DEFAULT NULL"),
        ]
    },
    {
        "name": "t_tag",
        "comment": "标签表",
        "fields": [
            ("user_id", "varchar(64)", "用户ID", "NOT NULL"),
            ("name", "varchar(100)", "标签名称", "NOT NULL"),
            ("type_code", "varchar(50)", "标签类型编码", "NOT NULL"),
            ("color", "varchar(20)", "标签颜色", "DEFAULT NULL"),
            ("description", "varchar(500)", "标签描述", "DEFAULT NULL"),
            ("usage_count", "int", "使用次数", "DEFAULT '0'"),
        ]
    },
    {
        "name": "t_tag_relation",
        "comment": "标签关联表",
        "fields": [
            ("tag_id", "varchar(64)", "标签ID", "NOT NULL"),
            ("entity_id", "varchar(64)", "实体ID", "NOT NULL"),
            ("user_id", "varchar(64)", "用户ID", "NOT NULL"),
        ]
    },
]

def main():
    """主函数"""
    all_sql = []
    
    for table in tables:
        sql = generate_table_sql(table["name"], table["comment"], table["fields"])
        all_sql.append(sql)
    
    # 输出所有SQL
    full_sql = "\n".join(all_sql)
    print(full_sql)
    
    # 保存到文件
    with open("generated_tables.sql", "w", encoding="utf-8") as f:
        f.write(full_sql)
    
    print("\nSQL已保存到 generated_tables.sql")

if __name__ == "__main__":
    main()
