#!/usr/bin/env python3
# 生成字典表插入SQL语句

import uuid
from datetime import datetime

# 字典类型定义
dict_types = [
    {
        "type_code": "CLOTHING_TYPE",
        "type_name": "服装类型",
        "items": [
            "T恤", "衬衫", "裤子", "外套", "毛衣",
            "裙子", "连衣裙", "短裤", "牛仔裤", "运动服",
            "睡衣", "内衣", "袜子", "帽子", "鞋子"
        ]
    },
    {
        "type_code": "COLOR",
        "type_name": "颜色",
        "items": [
            "红色", "橙色", "黄色", "绿色", "蓝色",
            "紫色", "黑色", "白色", "灰色", "棕色",
            "粉色", "银色", "金色"
        ]
    },
    {
        "type_code": "SEASON",
        "type_name": "季节",
        "items": ["春季", "夏季", "秋季", "冬季"]
    }
]

def generate_dict_code(type_code, item_name, index):
    """生成更有意义的字典编码"""
    # 根据类型和项目名称生成编码
    if type_code == "CLOTHING_TYPE":
        # 服装类型使用英文缩写
        clothing_map = {
            "T恤": "TSHIRT",
            "衬衫": "SHIRT",
            "裤子": "PANTS",
            "外套": "COAT",
            "毛衣": "SWEATER",
            "裙子": "SKIRT",
            "连衣裙": "DRESS",
            "短裤": "SHORTS",
            "牛仔裤": "JEANS",
            "运动服": "SPORTSWEAR",
            "睡衣": "PAJAMAS",
            "内衣": "UNDERWEAR",
            "袜子": "SOCKS",
            "帽子": "HAT",
            "鞋子": "SHOES"
        }
        return clothing_map.get(item_name, f"CLOTHING_{index:03d}")
    
    elif type_code == "COLOR":
        # 颜色使用英文名称
        color_map = {
            "红色": "RED",
            "橙色": "ORANGE",
            "黄色": "YELLOW",
            "绿色": "GREEN",
            "蓝色": "BLUE",
            "紫色": "PURPLE",
            "黑色": "BLACK",
            "白色": "WHITE",
            "灰色": "GRAY",
            "棕色": "BROWN",
            "粉色": "PINK",
            "银色": "SILVER",
            "金色": "GOLD"
        }
        return color_map.get(item_name, f"COLOR_{index:03d}")
    
    elif type_code == "SEASON":
        # 季节使用英文名称
        season_map = {
            "春季": "SPRING",
            "夏季": "SUMMER",
            "秋季": "AUTUMN",
            "冬季": "WINTER"
        }
        return season_map.get(item_name, f"SEASON_{index:03d}")
    
    # 默认情况
    return f"{type_code}_{index:03d}"

def generate_sql():
    """生成SQL插入语句"""
    sql_lines = []
    sql_lines.append("-- 字典表数据插入")
    sql_lines.append("-- 生成时间: " + datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    sql_lines.append("")
    
    # 颜色对应的十六进制代码
    color_hex_map = {
        "红色": "#FF0000",
        "橙色": "#FFA500",
        "黄色": "#FFFF00",
        "绿色": "#00FF00",
        "蓝色": "#0000FF",
        "紫色": "#800080",
        "黑色": "#000000",
        "白色": "#FFFFFF",
        "灰色": "#808080",
        "棕色": "#A52A2A",
        "粉色": "#FFC0CB",
        "银色": "#C0C0C0",
        "金色": "#FFD700"
    }
    
    # 为每个字典类型生成数据
    for dict_type in dict_types:
        type_code = dict_type["type_code"]
        type_name = dict_type["type_name"]
        items = dict_type["items"]
        
        sql_lines.append(f"-- {type_name} ({type_code})")
        
        for i, item_name in enumerate(items, 1):
            # 生成UUID
            item_id = str(uuid.uuid4())
            
            # 生成更有意义的字典编码
            dict_code = generate_dict_code(type_code, item_name, i)
            
            # 设置dict_value：只有颜色类型设置十六进制代码，其他为NULL
            dict_value = "NULL"
            if type_code == "COLOR" and item_name in color_hex_map:
                dict_value = f"'{color_hex_map[item_name]}'"
            
            # 构建INSERT语句（删除status字段）
            sql = f"""INSERT INTO `t_dict` (`id`, `created_at`, `updated_at`, `deleted`, `lock_version`, `type_code`, `type_name`, `dict_code`, `dict_name`, `dict_value`, `sort`) VALUES (
    '{item_id}',
    NOW(),
    NOW(),
    0,
    0,
    '{type_code}',
    '{type_name}',
    '{dict_code}',
    '{item_name}',
    {dict_value},
    {i}
);"""
            sql_lines.append(sql)
        
        sql_lines.append("")
    
    return "\n".join(sql_lines)

def main():
    """主函数"""
    sql_content = generate_sql()
    
    # 输出到控制台
    print(sql_content)
    
    # 保存到文件
    with open("dict_insert.sql", "w", encoding="utf-8") as f:
        f.write(sql_content)
    
    print("\nSQL已保存到 dict_insert.sql")

if __name__ == "__main__":
    main()
