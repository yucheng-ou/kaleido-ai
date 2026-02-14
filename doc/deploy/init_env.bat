@echo off
chcp 65001 >nul 2>&1
echo 正在批量设置用户级环境变量...
echo.

:: 将变量的内容替换为你自己的
setx NACOS_HOST "127.0.0.1"
setx NACOS_PORT "8848"
setx NACOS_NAMESPACE "30d71fbd-2d24-4757-81f4-679d26f0ed93"
setx NACOS_DUBBO_NAMESPACE "aa3a3ee8-fb98-43e5-b3da-11b368d88c21"
setx SEATA_HOST "117.72.115.40"
setx SEATA_GROUPLIST_PORT "8091"
setx SENTINEL_HOST "117.72.115.40"
setx SENTINEL_DASHBOARD_PORT "8858"
setx SENTINEL_TRANSPORT_PORT "8719"

setx AI_BASE_URL "https://api.siliconflow.cn"
setx AI_API_KEY "sk-ckjuvrbndhainyxlzxiocyiyfxldvroevmxklptutmnaaugr"
setx CHAT_MODEL "deepseek-ai/DeepSeek-V3"
setx EMBEDDING_MODEL "BAAI/bge-m3"

setx MILVUS_HOST "127.0.0.1"
setx MILVUS_PORT "19530"
setx MILVUS_USERNAME "root"
setx MILVUS_PASSWORD "Milvus"

setx MONGODB_HOST "127.0.0.1"
setx MONGODB_PORT "27017"
setx MONGODB_USERNAME "root"
setx MONGODB_PASSWORD "kaleido123"

setx minio_endpoint "http://127.0.0.1:9000"
setx minio_fileHost "http://127.0.0.1:9000"
setx minio_accessKey "admin"
setx minio_secretKey "admin123"

setx rabbitmq_address "127.0.0.1"
setx rabbitmq_port "5672"
setx rabbitmq_username "admin"
setx rabbitmq_password "kaleido123"

setx xxl_job.accessToken "default_token"
setx xxl_job.admin.address "127.0.0.1:9091"
setx xxl_job.executor.appName "kaleido-job-executor"
setx xxl_job.executor.ip "127.0.0.1"
setx xxl_job.executor.port "9999"

setx REDIS_HOST "127.0.0.1"
setx REDIS_PORT "6379"
setx REDIS_PASSWORD "kaleido123"

:: 分表的第一张表地址
setx MYSQL_URL_DB0 "jdbc:mysql://127.0.0.1:3306/kaleido_0?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true"
:: 分表的第二张表地址
setx MYSQL_URL_DB1 "jdbc:mysql://127.0.0.1:3306/kaleido_1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true"
setx MYSQL_PASSWORD "kaleido123"

echo.
echo 所有变量设置完成！新终端输入 echo %%变量名%% 验证
pause
