-- 数据库迁移脚本：将枚举字段从数字类型改为字符串类型
-- 执行时间：2026-01-06
-- 作者：系统自动生成

-- 1. 修改 admin_user 表的 status 字段
ALTER TABLE admin_user 
MODIFY COLUMN status varchar(20) DEFAULT 'NORMAL' 
COMMENT '管理员状态：NORMAL-正常，FROZEN-冻结，DELETED-删除';

-- 更新现有数据：将数字转换为对应的字符串
UPDATE admin_user SET status = 'NORMAL' WHERE status = '1';
UPDATE admin_user SET status = 'FROZEN' WHERE status = '2';
UPDATE admin_user SET status = 'DELETED' WHERE status = '3';

-- 2. 修改 permission 表的 type 字段
ALTER TABLE permission 
MODIFY COLUMN type varchar(20) 
COMMENT '权限类型：MENU-菜单，BUTTON-按钮，API-接口';

-- 更新现有数据：将数字转换为对应的字符串
UPDATE permission SET type = 'MENU' WHERE type = '1';
UPDATE permission SET type = 'BUTTON' WHERE type = '2';
UPDATE permission SET type = 'API' WHERE type = '3';

-- 3. 验证修改结果
SELECT 
    'admin_user' as table_name,
    status,
    COUNT(*) as count
FROM admin_user 
GROUP BY status
ORDER BY status;

SELECT 
    'permission' as table_name,
    type,
    COUNT(*) as count
FROM permission 
GROUP BY type
ORDER BY type;

-- 4. 检查表结构
DESC admin_user;
DESC permission;

-- 5. 注意事项：
-- 1) 请在业务低峰期执行此脚本
-- 2) 执行前请备份数据库
-- 3) 确保应用程序已停止或处于维护模式
-- 4) 执行后请重启应用程序
