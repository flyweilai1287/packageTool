@echo off
rem ʹ�ø�ʽ�� upload.bat tool 1
rem tool��Ŀ��·���ļ��
rem 1��ʾ�����������������£�2��ʾ�����£�3��ʾ��ִ��1��ִ��2
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