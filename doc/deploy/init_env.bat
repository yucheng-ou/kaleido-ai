@echo off
chcp 65001 >nul 2>&1
echo 正在批量设置用户级环境变量...
echo.

:: 将变量的内容替换为你自己的
setx NACOS_HOST "xxx"
setx NACOS_PORT "xxx"

setx AI_BASE_URL "xxx"
setx AI_API_KEY "xxx"

setx MILVUS_HOST "xxx"
setx MILVUS_PORT "xxx"
setx MILVUS_USERNAME "xxx"
setx MILVUS_PASSWORD "xxx"

setx MONGODB_HOST "xxx"
setx MONGODB_PORT "xxx"
setx MONGODB_USERNAME "xxx"
setx MONGODB_PASSWORD "xxx"
setx MONGODB_PORT "xxx"

setx minio_endpoint "xxx"
setx minio_fileHost "xxx"
setx minio_accessKey "xxx"
setx minio_secretKey "xxx"

setx rabbitmq_address "xxx"
setx rabbitmq_port "xxx"
setx rabbitmq_username "xxx"
setx rabbitmq_password "xxx"

setx xxl_job.accessToken "xxx"
setx xxl_job.admin.address "xxx"
setx xxl_job.executor.appName "xxx"
setx xxl_job.executor.ip "xxx"
setx xxl_job.executor.port "xxx"

setx NACOS_HOST "xxx"
setx NACOS_PORT "xxx"

setx REDIS_HOST "xxx"
setx REDIS_PORT "xxx"
setx REDIS_PASSWORD "xxx"

:: 分表的第一张表地址
setx MYSQL_URL_DB0 "xxx"
:: 分表的第二张表地址
setx MYSQL_URL_DB1 "xxx"
setx MYSQL_PASSWORD "xxx"

echo.
echo 所有变量设置完成！新终端输入 echo %%变量名%% 验证
pause