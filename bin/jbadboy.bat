@if "%DEBUG%" == "" @echo off
rem
rem Launcher for JBadboy
rem
rem

set DIRNAME=%~dp0
set DIRNAME=%DIRNAME:~0,-1%

if exist "%DIRNAME%\jbadboy.jar" goto jbadboyjarFound

set "DIRNAME=%DIRNAME%..\dist"

if exist "%DIRNAME%\jbadboy.jar" goto jbadboyjarFound

rem not found

echo
echo "Unable to locate jbadboy.jar - please check your installation"
echo

goto quit

:jbadboyjarFound

if "%DIRNAME%" == "" set DIRNAME=.\

java -DjbadboyHome="%DIRNAME%"  -jar "%DIRNAME%\jbadboy.jar" %1

:quit 
