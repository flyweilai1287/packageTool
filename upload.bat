@echo off
rem 使用格式： upload.bat tool 1
rem tool是目标路径的简称
rem 1表示仅复制生产机不更新，2表示仅更新，3表示先执行1再执行2
set dateStr=%Date:~0,4%%Date:~5,2%%Date:~8,2%

set sourceDir=%cd%
if "%1%"=="tool" goto doSetSourcePackageTool

:doSetSourcePackageTool
set sourceDir=F:\leo\artifacts\update\%dateStr%
set destDir=%sourceDir%\..\update1\
set bakDir=%sourceDir%\..\update\old
goto doExecute

:doExecute
Set uploadFlag=%2%
set excludePostfix=log,bak,
rem cd /d %~dp0

@echo on
java -jar packageTool_v2.jar %sourceDir% %destDir% %bakDir% %uploadFlag% %excludePostfix%
pause