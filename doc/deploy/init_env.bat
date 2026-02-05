@echo off
chcp 65001 >nul 2>&1
echo 正在批量设置用户级环境变量...
echo.

:: 直接替换下面的行，对应你的10个变量
setx VAR1 "D:\MyEnv\变量1路径"
setx VAR2 "E:\Tools\变量2路径"
setx VAR3 "C:\Program Files\MyTool3"
setx VAR4 "D:\Env\Var4"
setx VAR5 "E:\Soft\Var5"
setx VAR6 "C:\MyTools\Var6"
setx VAR7 "D:\Path\Var7"
setx VAR8 "E:\Env\Var8"
setx VAR9 "C:\Tools\Var9"
setx VAR10 "D:\Soft\Var10"

echo.
echo 所有变量设置完成！新终端输入 echo %%变量名%% 验证
pause