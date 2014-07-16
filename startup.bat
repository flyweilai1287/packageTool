@echo off

if not "%1%"=="now" goto doSetDate
if "%1%"=="now" goto dosetDateCurrent

:dosetDateCurrent
set dateStr=%Date:~0,4%%Date:~5,2%%Date:~8,2%
goto doExecute

:dosetDate
set dateStr=%1%
goto doExecute

:doExecute
Set timeStr=%2%
rem set sourceDir=%cd%
set sourceDir="F:\leo\workspace\idea12\packageTool\out\production\packageTool"
set destDir=%3%
set excludePostfix=log,bak,
rem cd /d %~dp0

@echo on
rem java -jar F:\leo\artifacts\packageTool\packageTool_v1.jar 20140623 18:00 F:\leo\workspace\idea12\packageTool\out\production\packageTool F:\leo\artifacts\packageTool log,bak,
java -jar F:\leo\artifacts\packageTool\packageTool_v1.jar %dateStr% %timeStr% %sourceDir% %destDir% %excludePostfix%
pause