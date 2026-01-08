@echo off
REM ============================================
REM Kaleido 项目一键生成所有API文档脚本
REM 版本: 2.0 - 修复版
REM 作者: 自动修复
REM 描述: 一键生成所有服务的smart-doc API文档
REM 修复内容:
REM 1. 移除ANSI颜色代码（Windows CMD不兼容）
REM 2. 修复中文编码问题
REM 3. 修复错误码检查逻辑
REM 4. 改进错误处理和用户反馈
REM ============================================

echo.
echo ============================================
echo    Kaleido 项目 API 文档生成工具
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

set SUCCESS_COUNT=0
set FAIL_COUNT=0

echo [信息] 开始生成4个服务的API文档...
echo.

REM 1. kaleido-admin
echo ============================================
echo [处理] 正在生成管理后台服务的API文档...
echo [目录] kaleido-admin
echo ============================================
if exist "kaleido-admin" (
    pushd "kaleido-admin"
    echo [执行] mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_result "管理后台服务"
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-admin
    set /a FAIL_COUNT+=1
)
echo.

REM 2. kaleido-auth
echo ============================================
echo [处理] 正在生成认证服务的API文档...
echo [目录] kaleido-auth
echo ============================================
if exist "kaleido-auth" (
    pushd "kaleido-auth"
    echo [执行] mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_result "认证服务"
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-auth
    set /a FAIL_COUNT+=1
)
echo.

REM 3. kaleido-biz\kaleido-user
echo ============================================
echo [处理] 正在生成用户服务的API文档...
echo [目录] kaleido-biz\kaleido-user
echo ============================================
if exist "kaleido-biz\kaleido-user" (
    pushd "kaleido-biz\kaleido-user"
    echo [执行] mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_result "用户服务"
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-biz\kaleido-user
    set /a FAIL_COUNT+=1
)
echo.

REM 4. kaleido-notice
echo ============================================
echo [处理] 正在生成通知服务的API文档...
echo [目录] kaleido-notice
echo ============================================
if exist "kaleido-notice" (
    pushd "kaleido-notice"
    echo [执行] mvn smart-doc:html -DskipTests=true
    mvn smart-doc:html -DskipTests=true
    call :check_result "通知服务"
    popd
) else (
    echo [错误] 服务目录不存在: kaleido-notice
    set /a FAIL_COUNT+=1
)
echo.

echo ============================================
echo [总结] 文档生成完成
echo ============================================
echo.
echo [统计] 成功: %SUCCESS_COUNT% 个服务
echo [统计] 失败: %FAIL_COUNT% 个服务
echo.

REM 显示生成的文档位置
echo [文档位置总结]
echo ============================================
if exist "doc\admin\index.html" (
    echo 管理后台文档: doc\admin\index.html
)
if exist "doc\auth\index.html" (
    echo 认证服务文档: doc\auth\index.html
)
if exist "doc\user\index.html" (
    echo 用户服务文档: doc\user\index.html
)
if exist "doc\notice\index.html" (
    echo 通知服务文档: doc\notice\index.html
)
echo ============================================
echo.

if %FAIL_COUNT% gtr 0 (
    echo [警告] 有 %FAIL_COUNT% 个服务文档生成失败
    echo [提示] 请检查失败服务的smart-doc配置
    echo [建议] 可以尝试在项目根目录执行: mvn clean install -DskipTests
    echo.
) else (
    echo [成功] 所有服务文档生成完成！
    echo.
)

echo [完成] 脚本执行完毕
pause
exit /b 0

REM ============================================
REM 函数：检查Maven执行结果
REM 参数：服务名称
REM ============================================
:check_result
set SERVICE_NAME=%~1

REM 使用临时变量捕获errorlevel
set MVN_EXIT_CODE=%errorlevel%

if %MVN_EXIT_CODE% equ 0 (
    echo [成功] %SERVICE_NAME% 文档生成完成
    set /a SUCCESS_COUNT+=1
) else (
    echo [失败] %SERVICE_NAME% 文档生成失败 (错误码: %MVN_EXIT_CODE%)
    set /a FAIL_COUNT+=1
)
goto :eof
