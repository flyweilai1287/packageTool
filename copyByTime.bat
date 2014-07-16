@echo off
rem 参数格式：copyByTime.bat now 12:00 tool
rem now表示当日，如果是其他时间可以写yyyyMMdd
rem tool是项目的原路径的简称
if not "%1%"=="now" goto doSetDate
if "%1%"=="now" goto dosetDateCurrent

:dosetDateCurrent
set dateStr=%Date:~0,4%%Date:~5,2%%Date:~8,2%
goto doSet

:dosetDate
set dateStr=%1%
goto doSet


:doSet
set sourceDir=%cd%
if "%3%"=="swap" goto doSetSource
if "%3%"=="tool" goto doSetSourcePackageTool



:doSetSourcePackageTool
set sourceDir=F:\leo\artifacts\packageTool
set destDir=%sourceDir%\..\update\%dateStr%
goto doExecute

:doSetSource
set sourceDir=E:\artifacts\swap\out\exploded
set destDir=%sourceDir%/../update/%dateStr%
goto doExecute

:doExecute
Set timeStr=%2%
set excludePostfix=log,bak,
rem cd /d %~dp0

rem java -jar packageTool_v1.jar 20140623 18:00 F:\leo\workspace\idea12\packageTool\out\production\packageTool F:\leo\artifacts\packageTool log,bak,
@echo on
java -jar packageTool_v2.jar %dateStr% %timeStr% %sourceDir% %destDir% %excludePostfix%
pause