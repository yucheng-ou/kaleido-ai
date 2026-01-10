@echo off
REM ============================================
REM Kaleido 项目一键生成所有API文档脚本
REM 版本: 3.0 - 支持RPC文档生成
REM 作者: 自动修复
REM 描述: 一键生成所有服务的smart-doc API文档（HTTP + RPC）
REM 新增功能:
REM 1. 支持生成Dubbo RPC接口文档
REM 2. 为每个服务生成HTTP和RPC两份文档
REM 3. 改进文档目录结构
REM 4. 增强错误处理和统计
REM ============================================

echo.
echo ============================================
echo    Kaleido 项目 API 文档生成工具 (v3.0)
echo ============================================
echo.

chcp 65001 >nul
setlocal enabledelayedexpansion

REM 检查Maven是否安装
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo [错误] 未找到Maven，请先安装Maven并添加到PATH环境变量
    pause
    exit /b 1
)

echo [信息] 检测到Maven，开始生成文档...
echo.

REM 创建文档输出目录
if not exist "doc" (
    mkdir doc
    echo [信息] 创建文档输出目录: doc
)

set HTTP_SUCCESS_COUNT=0
set RPC_SUCCESS_COUNT=0
set HTTP_FAIL_COUNT=0
set RPC_FAIL_COUNT=0

echo [信息] 开始生成4个服务的API文档（HTTP + RPC）...
echo.

REM 1. kaleido-admin
echo ============================================
echo [处理] 正在生成管理后台服务的文档...
echo [目录] kaleido-admin
echo ============================================
if exist "kaleido-admin" (
    pushd "kaleido-admin"
    
    REM 生成HTTP API文档
    echo [执行] HTTP文档: mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_http_result "管理后台服务"
    
    REM 生成RPC文档
    echo [执行] RPC文档: mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    call :check_rpc_result "管理后台服务"
    
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-admin
    set /a HTTP_FAIL_COUNT+=1
    set /a RPC_FAIL_COUNT+=1
)
echo.

REM 2. kaleido-auth
echo ============================================
echo [处理] 正在生成认证服务的文档...
echo [目录] kaleido-auth
echo ============================================
if exist "kaleido-auth" (
    pushd "kaleido-auth"
    
    REM 生成HTTP API文档（认证服务没有RPC接口，只生成HTTP文档）
    echo [执行] HTTP文档: mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_http_result "认证服务"
    
    echo [信息] 认证服务没有RPC接口，跳过RPC文档生成
    set /a RPC_SUCCESS_COUNT+=1
    
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-auth
    set /a HTTP_FAIL_COUNT+=1
    set /a RPC_FAIL_COUNT+=1
)
echo.

REM 3. kaleido-biz\kaleido-user
echo ============================================
echo [处理] 正在生成用户服务的文档...
echo [目录] kaleido-biz\kaleido-user
echo ============================================
if exist "kaleido-biz\kaleido-user" (
    pushd "kaleido-biz\kaleido-user"
    
    REM 生成HTTP API文档
    echo [执行] HTTP文档: mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_http_result "用户服务"
    
    REM 生成RPC文档
    echo [执行] RPC文档: mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    call :check_rpc_result "用户服务"
    
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-biz\kaleido-user
    set /a HTTP_FAIL_COUNT+=1
    set /a RPC_FAIL_COUNT+=1
)
echo.

REM 4. kaleido-notice
echo ============================================
echo [处理] 正在生成通知服务的文档...
echo [目录] kaleido-notice
echo ============================================
if exist "kaleido-notice" (
    pushd "kaleido-notice"
    
    REM 生成HTTP API文档
    echo [执行] HTTP文档: mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_http_result "通知服务"
    
    REM 生成RPC文档
    echo [执行] RPC文档: mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    mvn smart-doc:rpc-html -DskipTests=true -Dsmart-doc.configFile=src/main/resources/smart-doc-rpc-config.json
    call :check_rpc_result "通知服务"
    
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-notice
    set /a HTTP_FAIL_COUNT+=1
    set /a RPC_FAIL_COUNT+=1
)
echo.

echo ============================================
echo [总结] 文档生成完成
echo ============================================
echo.
echo [HTTP文档统计] 成功: %HTTP_SUCCESS_COUNT% 个服务，失败: %HTTP_FAIL_COUNT% 个服务
echo [RPC文档统计]  成功: %RPC_SUCCESS_COUNT% 个服务，失败: %RPC_FAIL_COUNT% 个服务
echo.

REM 显示生成的文档位置
echo [文档位置总结]
echo ============================================
if exist "doc\admin\index.html" (
    echo 管理后台HTTP文档: doc\admin\index.html
)
if exist "doc\admin\rpc\index.html" (
    echo 管理后台RPC文档:  doc\admin\rpc\index.html
)

if exist "doc\auth\index.html" (
    echo 认证服务HTTP文档: doc\auth\index.html
)

if exist "doc\user\index.html" (
    echo 用户服务HTTP文档: doc\user\index.html
)
if exist "doc\user\rpc\index.html" (
    echo 用户服务RPC文档:  doc\user\rpc\index.html
)

if exist "doc\notice\index.html" (
    echo 通知服务HTTP文档: doc\notice\index.html
)
if exist "doc\notice\rpc\index.html" (
    echo 通知服务RPC文档:  doc\notice\rpc\index.html
)
echo ============================================
echo.

if %HTTP_FAIL_COUNT% gtr 0 (
    echo [警告] 有 %HTTP_FAIL_COUNT% 个服务的HTTP文档生成失败
)
if %RPC_FAIL_COUNT% gtr 0 (
    echo [警告] 有 %RPC_FAIL_COUNT% 个服务的RPC文档生成失败
)

if %HTTP_FAIL_COUNT% equ 0 if %RPC_FAIL_COUNT% equ 0 (
    echo [成功] 所有服务文档生成完成！
    echo.
) else (
    echo [提示] 请检查失败服务的smart-doc配置
    echo [建议] 可以尝试在项目根目录执行: mvn clean install -DskipTests
    echo.
)

echo [完成] 脚本执行完毕
pause
exit /b 0

REM ============================================
REM 函数：检查HTTP文档生成结果
REM 参数：服务名称
REM ============================================
:check_http_result
set SERVICE_NAME=%~1

REM 使用临时变量捕获errorlevel
set MVN_EXIT_CODE=%errorlevel%

if %MVN_EXIT_CODE% equ 0 (
    echo [成功] %SERVICE_NAME% HTTP文档生成完成
    set /a HTTP_SUCCESS_COUNT+=1
) else (
    echo [失败] %SERVICE_NAME% HTTP文档生成失败 (错误码: %MVN_EXIT_CODE%)
    set /a HTTP_FAIL_COUNT+=1
)
goto :eof

REM ============================================
REM 函数：检查RPC文档生成结果
REM 参数：服务名称
REM ============================================
:check_rpc_result
set SERVICE_NAME=%~1

REM 使用临时变量捕获errorlevel
set MVN_EXIT_CODE=%errorlevel%

if %MVN_EXIT_CODE% equ 0 (
    echo [成功] %SERVICE_NAME% RPC文档生成完成
    set /a RPC_SUCCESS_COUNT+=1
) else (
    echo [失败] %SERVICE_NAME% RPC文档生成失败 (错误码: %MVN_EXIT_CODE%)
    set /a RPC_FAIL_COUNT+=1
)
goto :eof
